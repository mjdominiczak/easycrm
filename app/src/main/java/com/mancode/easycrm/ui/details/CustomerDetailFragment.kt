package com.mancode.easycrm.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerDetailFragment : Fragment() {

    private val viewModel: CustomerDetailViewModel by viewModels()
    private val args: CustomerDetailFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.customerId = args.customerId
        return ComposeView(requireContext()).apply {
            setContent {
                EasyCrmTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Szczegóły")
                                },
                                navigationIcon = {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(Icons.Filled.ArrowBack, contentDescription = "")
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                val dirs =
                                    CustomerDetailFragmentDirections.actionCustomerDetailFragmentToNoteDialog(
                                        viewModel.customerId!!
                                    )
                                navController.navigate(dirs)
                            }) {
                                Icon(imageVector = Icons.Filled.NoteAdd, contentDescription = "")
                            }
                        }
                    ) {
                        CustomerDetailsScreen(viewModel, navController)
                    }
                }
            }
        }
    }
}