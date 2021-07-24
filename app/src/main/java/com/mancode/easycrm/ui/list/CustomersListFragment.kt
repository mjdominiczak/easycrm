package com.mancode.easycrm.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import com.mancode.easycrm.ui.views.SearchView
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
                        topBar = { TopBarWithSearch() },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                val dirs =
                                    CustomersListFragmentDirections.actionCustomersListFragmentToAddCustomerDialog()
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

@Composable
private fun TopBarWithSearch() {
    var searchActivated by remember { mutableStateOf(false) }
    Crossfade(targetState = searchActivated) { showSearch ->
        if (!showSearch) {
            TopAppBar(
                title = {
                    Text(text = "Easy CRM")
                },
                actions = {
                    IconButton(onClick = { searchActivated = true }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = ""
                        )
                    }
                }
            )
        } else {
            Surface(
                color = MaterialTheme.colors.secondary,
                elevation = 4.dp
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(onClick = { searchActivated = false }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                    val viewModel = viewModel<CustomersListViewModel>()
                    SearchView(viewModel.filterState)
                }
            }
        }
    }
}
