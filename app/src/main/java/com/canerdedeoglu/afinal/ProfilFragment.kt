package com.canerdedeoglu.afinal
import android.content.Context
import android.content.SharedPreferences
import android.net.http.HttpException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.databinding.FragmentProfilBinding
import com.canerdedeoglu.afinal.models.Company
import com.canerdedeoglu.afinal.models.User
import com.canerdedeoglu.afinal.services.IDummyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private val retrofit = ApiClient.getClient().create(IDummyService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.title = "Profil Detayı"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))
        //toolbar.inflateMenu(R.menu.toolbar_menu)

        // setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için

        val userId = getUserIdFromPreferences(requireContext())
        if (userId != null) {
            showLoading(true)
            loadProfile(userId)
        } else {
            Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditProfile.setOnClickListener {
            MainScope().launch {
                val user = getUserFromPreferences(requireContext())
                user?.let {
                    val action = ProfilFragmentDirections.actionToEditProfile(user)
                    Navigation.findNavController(binding.root).navigate(action)
                } ?: Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun getUserIdFromPreferences(context: Context): Int? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_id", null)?.toIntOrNull()
    }

    private suspend fun getUserFromPreferences(context: Context): User? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)?.toIntOrNull()
        return if (userId != null) {
            // Replace with actual logic to get user details from storage or API
            try {
                val userProfile = retrofit.getUserProfile(userId)
                userProfile // Return the actual user profile fetched from API
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                null
            }
        } else {
            null
        }
    }

    private fun loadProfile(userId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val userProfile = retrofit.getUserProfile(userId)
                bindUserProfile(userProfile)
                showLoading(false)
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }

    private fun bindUserProfile(user: User) {
        binding.apply {
            txtUsername.text = user.username
            txtName.text = "${user.firstName} ${user.lastName}"
            txtEmail.text = user.email
            txtPhone.text = user.phone
            txtBirthDate.text = user.birthDate
            txtGender.text = user.gender
            txtUniversity.text = user.university
            txtCompany.text = user.company.name
            txtDepartment.text = user.company.department

            Glide.with(this@ProfilFragment)
                .load(user.image)
                .into(imgProfile)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

