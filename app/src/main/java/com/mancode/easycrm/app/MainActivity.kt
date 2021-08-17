package com.mancode.easycrm.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.mancode.easycrm.ui.addedit.AddEditCustomerViewModel
import com.mancode.easycrm.ui.details.CustomerDetailViewModel
import com.mancode.easycrm.ui.details.CustomerDetailsFrag
import com.mancode.easycrm.ui.details.NoteEditor
import com.mancode.easycrm.ui.list.CustomerEditor
import com.mancode.easycrm.ui.list.CustomersListFrag
import com.mancode.easycrm.ui.list.CustomersListViewModel
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrmApp()
        }
    }
}

@Composable
fun CrmApp() {
    EasyCrmTheme {
        val navController = rememberNavController()
        EasyCrmNavHost(navController)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EasyCrmNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = EasyCrmScreen.CustomersList.name,
    ) {
        composable(EasyCrmScreen.CustomersList.name) {
            val viewModel = hiltViewModel<CustomersListViewModel>()
            CustomersListFrag(viewModel = viewModel, navController = navController)
        }
        dialog(EasyCrmScreen.AddEditCustomerDialog.name) {
            val viewModel = hiltViewModel<AddEditCustomerViewModel>()
            CustomerEditor { name ->
                viewModel.insertCustomer(name)
                navController.navigateUp()
            }
        }
        navigation(
            startDestination = "${EasyCrmScreen.CustomerDetail.name}/{customerId}",
            route = EasyCrmScreen.CustomerDetail.name
        ) {
            composable(
                route = "${EasyCrmScreen.CustomerDetail.name}/{customerId}",
                arguments = listOf(
                    navArgument("customerId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { entry ->
                val customerId = entry.arguments?.getInt("customerId")
                val viewModel = hiltViewModel<CustomerDetailViewModel>().apply {
                    this.customerId = customerId
                }
                CustomerDetailsFrag(
                    viewModel = viewModel,
                    navController = navController,
                )
            }
            dialog(
                route = "${EasyCrmScreen.NoteDialog.name}/{customerId}?noteId={noteId}",
                arguments = listOf(
                    navArgument("customerId") {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument("noteId") {
                        type = NavType.IntType
                        defaultValue = 0
                    }
                ),
                // https://stackoverflow.com/questions/68818202/animatedvisibility-doesnt-expand-height-in-dialog-in-jetpack-compose
                // FIXME introduces problem with width of the dialog
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
            ) { entry ->
                val customerId = entry.arguments?.getInt("customerId")
                val noteId = entry.arguments?.getInt("noteId")
                val viewModel = hiltViewModel<CustomerDetailViewModel>(
                    navController.getBackStackEntry(EasyCrmScreen.CustomerDetail.name)
                ).apply {
                    this.customerId = customerId
                    if (noteId != null) this.noteId = noteId
                }
                NoteEditor(viewModel, navController)
            }
        }
    }
}
