package cl.cruz.economicindicators.ui.detail

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import cl.cruz.economicindicators.databinding.FragmentDetailsBinding
import cl.cruz.economicindicators.ui.main.MainActivity

class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_INDICATOR = "indicator"
        private var backListener: BackListener? = null
        fun newInstance(indicator: EconomicIndicator?, listener: BackListener): DetailsFragment {
            backListener = listener
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_INDICATOR, indicator)
                }
            }
        }
    }

    private var indicator: EconomicIndicator? = null
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            indicator = it.getParcelable(ARG_INDICATOR)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        setIndicatorData()
    }

    private fun setTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = indicator?.name
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""
    }

    private fun setIndicatorData() {
        with(binding) {
            indicatorCode.text = indicator?.code
            indicatorName.text = indicator?.name
            indicatorValue.text = indicator?.value.toString()
            indicatorUnit.text = indicator?.measureUnit
            indicatorDate.text = indicator?.date
        }
    }

    override fun onPause() {
        super.onPause()
        backListener?.resetToolbarTitle()
        backListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface BackListener {
        fun resetToolbarTitle()
    }
}
