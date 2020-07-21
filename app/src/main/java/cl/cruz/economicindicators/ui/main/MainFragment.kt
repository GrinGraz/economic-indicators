package cl.cruz.economicindicators.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.databinding.MainFragmentBinding
import cl.cruz.economicindicators.presentation.EconomicIndicatorsViewModel
import cl.cruz.economicindicators.ui.detail.DetailsFragment
import cl.cruz.economicindicators.ui.detail.EconomicIndicator
import cl.cruz.economicindicators.ui.login.LoginFragment

private const val ARG_USERNAME = "username"

class MainFragment : Fragment(), DetailsFragment.BackListener {

    companion object {
        fun newInstance(username: String): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
        }
    }

    private lateinit var indicatorsViewModel: EconomicIndicatorsViewModel
    private val indicatorsAdapter = MainAdapter(ItemEventListener())
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> closeSession()
            R.id.refresh -> refreshIndicators()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshIndicators() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        indicatorsViewModel.loadEconomicIndicators(forced = true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Welcome $username"
        setRecycler()
        setViewModel()
        binding.progressBar.visibility = View.VISIBLE
        indicatorsViewModel.loadEconomicIndicators()
    }

    private fun setViewModel() {
        indicatorsViewModel =
            ViewModelProvider(this@MainFragment).get(EconomicIndicatorsViewModel::class.java)
        with(indicatorsViewModel) {
            items.observe(viewLifecycleOwner, Observer(::showResult))
            item.observe(viewLifecycleOwner, Observer(::openIndicatorDetail))
        }
    }

    private fun showResult(indicators: List<EconomicIndicator>) {
        if (indicators.isEmpty()) showEmptyResult()
        else showIndicatorResult(indicators)
    }

    private fun showIndicatorResult(indicators: List<EconomicIndicator>) {
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        indicatorsAdapter.submitList(indicators)
    }

    private fun showEmptyResult() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun openIndicatorDetail(indicator: EconomicIndicator?) {
        if (parentFragmentManager.findFragmentByTag("DetailsFragment") == null) {
            parentFragmentManager.beginTransaction()
                .addToBackStack("main")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(
                    R.id.container,
                    DetailsFragment.newInstance(indicator, this),
                    "DetailsFragment"
                )
                .commit()
        }
    }

    private fun closeSession() {
        indicatorsViewModel.clearUser()
        parentFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.container, LoginFragment.newInstance(), "LoginFragment")
            .commit()

    }

    private fun setRecycler() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = indicatorsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ItemEventListener : MainAdapter.ItemEventListener {
        override fun onItemClick(item: EconomicIndicator, position: Int) {
            indicatorsViewModel.loadEconomicIndicator(item.code)
        }
    }

    override fun resetToolbarTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = "Economic Indicators"
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Welcome $username"
    }
}