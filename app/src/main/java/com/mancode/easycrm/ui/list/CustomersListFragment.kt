package com.mancode.easycrm.ui.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mancode.easycrm.app.EasyCrmScreen
import com.mancode.easycrm.ui.list.SortOrder.BY_NAME
import com.mancode.easycrm.ui.list.SortOrder.BY_NEXT_CONTACT_DATE
import com.mancode.easycrm.ui.views.SearchView

@Composable
fun CustomersListFrag(viewModel: CustomersListViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            var searchActive by remember { mutableStateOf(false) }
            TopBarWithSearch(searchActive) { activate ->
                searchActive = activate
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(EasyCrmScreen.AddEditCustomerDialog.name)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
    ) {
        CustomersList(viewModel, navController)
    }
}

@Composable
fun TopBarWithSearch(
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
                    IconButton(onClick = {
                        viewModel.filterState.value = TextFieldValue("")
                        onSearchStateChanged(false)
                    }) {
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
