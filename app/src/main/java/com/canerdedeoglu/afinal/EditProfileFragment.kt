package com.canerdedeoglu.afinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.canerdedeoglu.afinal.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val args: EditProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.title = "Profil Düzenle"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))
        //toolbar.inflateMenu(R.menu.toolbar_menu)

        // setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için

        // Kullanıcı bilgilerini EditText bileşenlerine yerleştirme
        binding.editUsername.setText(args.User.username)
        binding.editName.setText("${args.User.firstName} ${args.User.lastName}")
        binding.editGender.setText(args.User.gender)
        binding.editEmail.setText(args.User.email)
        binding.editPhone.setText(args.User.phone)
        binding.editBirthDate.setText(args.User.birthDate)
        binding.editUniversity.setText(args.User.university)
        binding.editCompany.setText(args.User.company.name)
        binding.editDepartment.setText(args.User.company.department)

        // Kaydet butonu tıklama olayı
        binding.btnSave.setOnClickListener {
            saveChanges()
            Toast.makeText(binding.root.context, "Kullanıcı güncellendi", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun saveChanges() {
        // Burada yapılacak işlemler:
        // 1. EditText bileşenlerinden kullanıcı girişlerini al
        val updatedUsername = binding.editUsername.text.toString()
        val updatedName = binding.editName.text.toString()
        val updatedGender = binding.editGender.text.toString()
        val updatedEmail = binding.editEmail.text.toString()
        val updatedPhone = binding.editPhone.text.toString()
        val updatedBirthDate = binding.editBirthDate.text.toString()
        val updatedUniversity = binding.editUniversity.text.toString()
        val updatedCompanyName = binding.editCompany.text.toString()
        val updatedDepartment = binding.editDepartment.text.toString()

        // 2. Yeni kullanıcı nesnesini oluştur
        val updatedUser = args.User.copy(
            username = updatedUsername,
            // İlgili diğer alanlar da burada güncellenmeli
            firstName = updatedName.split(" ")[0],
            lastName = updatedName.split(" ")[1],
            gender = updatedGender,
            email = updatedEmail,
            phone = updatedPhone,
            birthDate = updatedBirthDate,
            university = updatedUniversity,
            company = args.User.company.copy(name = updatedCompanyName, department = updatedDepartment)
        )

        // 3. Güncellenmiş kullanıcıyı kaydetme işlemi (örneğin API ile güncelleme)
        //    Bu adımları API ile gerçekleştirebilinir.

        requireActivity().onBackPressed()
    }
}
