package com.mancode.easycrm.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Sort
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomersListFragment : Fragment() {

    private val viewModel: CustomersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EasyCrmTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Easy CRM")
                                },
                                actions = {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Filled.FilterAlt,
                                            contentDescription = ""
                                        )
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Filled.Sort,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                val dirs = CustomersListFragmentDirections.actionCustomersListFragmentToAddCustomerDialog()
                                findNavController().navigate(dirs)
                            }) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    ) {
                        CustomersList(viewModel, findNavController())
                    }
                }
            }
        }
    }
}