import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.models.LoginRequest
import com.canerdedeoglu.afinal.services.IDummyService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    private val api = ApiClient.getClient().create(IDummyService::class.java)

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun login(username: String, password: String) {
        val trimmedUsername = username.trim()
        val trimmedPassword = password.trim()

        viewModelScope.launch {
            try {
                val response = api.login(LoginRequest(trimmedUsername, trimmedPassword))
                if (response.token.isNotEmpty()) {
                    _loginState.value = true
                    saveUserId(response.id.toString())
                } else {
                    _loginState.value = false
                }
            } catch (e: Exception) {
                _loginState.value = false
            }
        }
    }

    private fun saveUserId(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_id", userId)
        editor.apply()
    }

}
