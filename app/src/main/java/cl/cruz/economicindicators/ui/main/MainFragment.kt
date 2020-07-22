package cl.cruz.economicindicators.ui.main

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.data.model.Result
import cl.cruz.economicindicators.databinding.MainFragmentBinding
import cl.cruz.economicindicators.extensions.hideKeyboardFrom
import cl.cruz.economicindicators.presentation.EconomicIndicatorsViewModel
import cl.cruz.economicindicators.ui.detail.DetailsFragment
import cl.cruz.economicindicators.ui.detail.EconomicIndicator
import cl.cruz.economicindicators.ui.login.LoginFragment
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), DetailsFragment.BackListener {

    companion object {
        private const val ARG_USERNAME = "username"
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
        setSearchView(menu)
    }

    private fun setSearchView(menu: Menu) {
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnCloseListener {
            indicatorsAdapter.filter.filter("")
            return@setOnCloseListener false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                indicatorsAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                indicatorsAdapter.filter.filter(newText)
                return false
            }
        })
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
        binding.retry.visibility = View.GONE
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
        setRetryClickListener()
        indicatorsViewModel.loadEconomicIndicators()
    }

    private fun setRetryClickListener() {
        binding.retry.setOnClickListener {
            indicatorsViewModel.loadEconomicIndicators(forced = true)
            binding.retry.isEnabled = false
            binding.errorMessage.visibility = View.GONE
            binding.retry.visibility = View.GONE
        }
    }

    private fun setRecycler() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = indicatorsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setViewModel() {
        indicatorsViewModel =
            ViewModelProvider(this@MainFragment).get(EconomicIndicatorsViewModel::class.java)
        with(indicatorsViewModel) {
            connectionState.observe(viewLifecycleOwner, Observer(::showConnectionState))
            items.observe(viewLifecycleOwner, Observer(::showIndicatorsResult))
            item.observe(viewLifecycleOwner, Observer(::showIndicatorResult))
        }
    }

    private fun showConnectionState(connectionState: ConnectionState?) {
        Snackbar.make(
            view!!,
            "Showing local data, check your internet connection",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    @Suppress("UNCHECKED_CAST")
    private fun showIndicatorsResult(indicators: Result<List<EconomicIndicator>>) {
        when (indicators) {
            is Result.Success<*> -> renderIndicators(indicators.data as List<EconomicIndicator>)
            is Result.Error -> renderError()
            is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun showIndicatorResult(indicator: Result<EconomicIndicator?>) {
        when (indicator) {
            is Result.Success<*> -> renderIndicatorDetail(indicator.data as EconomicIndicator)
            is Result.Error -> renderError()
        }
    }

    private fun renderIndicators(indicators: List<EconomicIndicator>) {
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.retry.visibility = View.GONE
        indicatorsAdapter.submitList(indicators)
    }

    private fun renderIndicatorDetail(economicIndicator: EconomicIndicator) {
        context.hideKeyboardFrom(this.view)
        if (parentFragmentManager.findFragmentByTag("DetailsFragment") == null) {
            val fragment = DetailsFragment.newInstance(economicIndicator, this)
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .addToBackStack("main")
                .add(R.id.container, fragment, "main")
                .commit()
        }
    }

    private fun renderError() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.retry.visibility = View.VISIBLE
        binding.retry.isEnabled = true
    }

    private fun closeSession() {
        indicatorsViewModel.clearUser()
        parentFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.container, LoginFragment.newInstance(), "LoginFragment")
            .commit()
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
        (activity as AppCompatActivity).supportActionBar?.title =
            resources.getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.subtitle =
            "${resources.getString(R.string.welcome)} $username"
    }
}