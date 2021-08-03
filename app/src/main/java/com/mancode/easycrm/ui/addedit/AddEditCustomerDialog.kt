package com.mancode.easycrm.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mancode.easycrm.ui.list.CustomerEditor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCustomerDialog : DialogFragment() {

    private val viewModel: AddEditCustomerViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CustomerEditor { name ->
                    viewModel.insertCustomer(name)
                    navController.navigateUp()
                }
            }
        }
    }
}