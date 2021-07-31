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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import com.mancode.easycrm.ui.list.SortOrder.BY_NAME
import com.mancode.easycrm.ui.list.SortOrder.BY_NEXT_CONTACT_DATE
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
                        topBar = {
                            val searchActive = viewModel.searchActivated.collectAsState()
                            TopBarWithSearch(
                                searchActive.value
                            ) { activate -> viewModel.onSearchStateChanged(activate) }
                        },
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
private fun TopBarWithSearch(
    searchActivated: Boolean = false,
    onSearchStateChanged: (Boolean) -> Unit
) {
    val viewModel: CustomersListViewModel = viewModel()
    Crossfade(targetState = searchActivated) { showSearch ->
        if (!showSearch) {
            TopAppBar(
                title = {
                    Text(text = "Easy CRM")
                },
                actions = {
                    IconButton(onClick = { onSearchStateChanged(true) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = ""
                        )
                    }
                    var sortOrder by viewModel.sortOrder
                    Crossfade(targetState = sortOrder) { order ->
                        when (order) {
                            BY_NAME -> {
                                IconButton(onClick = {
                                    sortOrder = BY_NEXT_CONTACT_DATE
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.SortByAlpha,
                                        contentDescription = ""
                                    )
                                }
                            }
                            BY_NEXT_CONTACT_DATE -> {
                                IconButton(onClick = { sortOrder = BY_NAME }) {
                                    Icon(
                                        imageVector = Icons.Filled.PendingActions,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
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
                    IconButton(onClick = { onSearchStateChanged(false) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                    SearchView(viewModel.filterState)
                }
            }
        }
    }
}
