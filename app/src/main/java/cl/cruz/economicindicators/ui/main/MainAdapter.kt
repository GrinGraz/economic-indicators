package cl.cruz.economicindicators.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.databinding.ItemIndicatorBinding
import cl.cruz.economicindicators.ui.detail.EconomicIndicator
import cl.cruz.economicindicators.ui.detail.formatIndicatorValue

class MainAdapter(private val itemEventListener: ItemEventListener) :
    ListAdapter<EconomicIndicator, MainAdapter.EconomicIndicatorViewHolder>(DiffCallback()) {

    interface ItemEventListener {
        fun onItemClick(item: EconomicIndicator, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EconomicIndicatorViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_indicator, parent, false)
        return EconomicIndicatorViewHolder(itemView).also { holder ->
            with(itemView){
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
}


