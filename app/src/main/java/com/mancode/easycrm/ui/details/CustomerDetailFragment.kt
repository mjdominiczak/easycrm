package com.mancode.easycrm.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mancode.easycrm.R
import com.mancode.easycrm.data.customers
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerDetailFragment : Fragment() {

    private val viewModel: CustomerDetailViewModel by viewModels()
    private val args: CustomerDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EasyCrmTheme {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = { findNavController().navigate(R.id.action_customerDetailFragment_to_noteDialog) }) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    ) {
                        CustomerDetailsScreen(viewModel, customers[args.customerId])
                    }
                }
            }
        }
    }
}