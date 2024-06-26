package com.canerdedeoglu.afinal

import LoginViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity

class MainFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hide the toolbar
        (activity as? MainActivity)?.hideToolbar()

        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            LoginScreen(viewModel = loginViewModel, navController = findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show the toolbar when the fragment is destroyed
        (activity as? MainActivity)?.showToolbar()
    }
}
