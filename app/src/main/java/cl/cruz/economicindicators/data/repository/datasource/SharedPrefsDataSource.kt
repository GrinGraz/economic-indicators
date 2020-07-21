package cl.cruz.economicindicators.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import cl.cruz.economicindicators.data.repository.datasource.sharedpreferences.SharedPreferencesDataSource
import java.io.IOException
import java.security.GeneralSecurityException


class SharedPrefsDataSource(applicationContext: Context) :
    SharedPreferencesDataSource {

    private var sharedPrefsEditor: SharedPreferences.Editor? = null
    private var sharedPrefs: SharedPreferences? = null

    init {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        try {
            sharedPrefs = EncryptedSharedPreferences
                .create(
                    "prefs",
                    masterKeyAlias,
                    applicationContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            sharedPrefsEditor = sharedPrefs!!.edit()
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //saveUser(username = "Christopher", password = "Ztr4ng3_P4zzw0rd.01")
    }

    override fun saveUser(username: String, password: String) {
        sharedPrefsEditor?.putString("username", username)?.apply()
        sharedPrefsEditor?.putString("password", password)?.apply()
    }

    override fun getUser(): Pair<String, String> {
        val username = sharedPrefs?.getString("username", "")
        val password = sharedPrefs?.getString("password", "")
        return Pair(username!!, password!!)
    }

    override fun clearUser() {
        sharedPrefsEditor?.putString("username", "")?.apply()
        sharedPrefsEditor?.putString("password", "")?.apply()
    }
}
