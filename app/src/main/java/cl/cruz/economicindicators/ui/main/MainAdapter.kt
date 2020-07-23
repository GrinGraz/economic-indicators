package cl.cruz.economicindicators.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.databinding.ItemIndicatorBinding
import cl.cruz.economicindicators.ui.detail.EconomicIndicator
import cl.cruz.economicindicators.ui.detail.formatIndicatorValue
import java.util.*


class MainAdapter(private val itemEventListener: ItemEventListener) :
    ListAdapter<EconomicIndicator, MainAdapter.EconomicIndicatorViewHolder>(DiffCallback()),
    Filterable {

    interface ItemEventListener {
        fun onItemClick(item: EconomicIndicator, position: Int)
    }

    val originalList: List<EconomicIndicator> by lazy { currentList }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EconomicIndicatorViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_indicator, parent, false)
        return EconomicIndicatorViewHolder(itemView).also { holder ->
            with(itemView) {
                setOnClickListener {
                    itemEventListener.onItemClick(
                        item = currentList[holder.adapterPosition],
                        position = holder.adapterPosition
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: EconomicIndicatorViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<EconomicIndicator>() {
        override fun areItemsTheSame(
            oldItem: EconomicIndicator,
            newItem: EconomicIndicator
        ): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(
            oldItem: EconomicIndicator,
            newItem: EconomicIndicator
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class EconomicIndicatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIndicatorBinding.bind(view)
        fun bindTo(item: EconomicIndicator) {
            binding.indicatorName.text = item.name
            binding.inidicatorValue.text = item.formatIndicatorValue()
        }
    }

    override fun getFilter(): Filter {
        var filteredList = originalList
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    submitList(originalList)
                } else {
                    filteredList = originalList.filter {
                        it.code.contains(charString.toLowerCase(Locale.getDefault()))
                                || it.name.contains(charString.toLowerCase(Locale.getDefault()))
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                filteredList = filterResults.values as MutableList<EconomicIndicator>
                submitList(filteredList)
            }
        }
    }
}


