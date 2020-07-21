package cl.cruz.economicindicators.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cl.cruz.economicindicators.R
import cl.cruz.economicindicators.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance(), "LoginFragment")
                .commitNow()
        }
    }
}