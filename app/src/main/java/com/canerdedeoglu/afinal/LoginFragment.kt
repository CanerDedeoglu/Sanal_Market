package com.canerdedeoglu.afinal

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.canerdedeoglu.afinal.databinding.FragmentLoginBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class LoginFragment : Fragment() {

    private lateinit var viewBinding: FragmentLoginBinding
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewBinding kullanarak fragment'ın layout'unu inflate ediyoruz
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)

        // Fragment oluşturulduğunda toolbar'ı gizliyoruz
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        // Butona tıklanma olayını dinliyoruz
        viewBinding.button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.actionToMain)
        }

        // Firebase Remote Config'i başlatıyoruz
        remoteConfig = FirebaseRemoteConfig.getInstance()

        // Remote Config ayarlarını yapılandırıyoruz
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600) // Minimum fetch aralığını 1 saat olarak ayarlıyoruz
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        // Uzak yapılandırma varsayılan değerlerini belirliyoruz
        remoteConfig.setDefaultsAsync(R.xml.remote_config)

        // Uzak yapılandırma değerlerini getirip etkinleştiriyoruz
        fetchRemoteConfig()

        // Oluşturulan view'i döndürüyoruz
        return viewBinding.root
    }

    private fun fetchRemoteConfig() {
        // Uzak yapılandırmayı getirme işlemini başlatıyoruz
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Başarılı şekilde yapılandırma getirildiyse, güncel yapılandırmaları uyguluyoruz
                    applyRemoteConfigurations()
                } else {
                    // Yapılandırma getirme başarısız olduysa, hata durumunda bir şey yapmıyoruz
                }
            }
    }

    private fun applyRemoteConfigurations() {
        // Uzak yapılandırmaları burada uyguluyoruz
        val backgroundColor = remoteConfig.getString("backGroundColor") // Uzak yapılandırmadan arka plan rengini alıyoruz
        viewBinding.root.setBackgroundColor(Color.parseColor(backgroundColor)) // Alınan rengi arka plan olarak ayarlıyoruz
    }
}
