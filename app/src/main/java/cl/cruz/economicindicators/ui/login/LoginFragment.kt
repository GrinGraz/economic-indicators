package cl.cruz.economicindicators.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.databinding.FragmentLoginBinding
import cl.cruz.economicindicators.presentation.LoginFormState
import cl.cruz.economicindicators.presentation.LoginResult
import cl.cruz.economicindicators.presentation.LoginViewModel
import cl.cruz.economicindicators.ui.main.MainFragment

class LoginFragment : Fragment() {

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()

            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""
        setViewModel()
        checkUserExist()
        setTextWatcher()
        setLoginOnclick()
    }

    private fun setViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer(::showLoginFormState))
        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer(::showLoginResult))
    }

    private fun checkUserExist() {
        val userData = loginViewModel.getUserData()
        if (userData.isNotBlank()) updateUiWithUser(LoggedInUserView(userData))
    }

    private fun setTextWatcher() {
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }
        binding.username.addTextChangedListener(afterTextChangedListener)
        binding.password.addTextChangedListener(afterTextChangedListener)

        binding.password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
            false
        }
    }

    private fun setLoginOnclick() {
        binding.login.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            loginViewModel.login(
                binding.username.text.toString(), binding.password.text.toString()
            )
        }
    }

    private fun showLoginResult(loginResult: LoginResult?) {
        loginResult ?: return
        binding.loading.visibility = View.GONE
        loginResult.error?.let {
            showLoginFailed(it)
        }
        loginResult.success?.let {
            updateUiWithUser(it)
        }
    }

    private fun showLoginFormState(loginFormState: LoginFormState?) {
        loginFormState ?: return
        binding.login.isEnabled = loginFormState.isDataValid
        loginFormState.usernameError?.let {
            binding.username.error = getString(it)
        }
        loginFormState.passwordError?.let {
            binding.password.error = getString(it)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        if (parentFragmentManager.findFragmentByTag("MainFragment") == null) {
            parentFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(
                    R.id.container,
                    MainFragment.newInstance(model.displayName),
                    "MainFragment"
                )
                .commit()
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
