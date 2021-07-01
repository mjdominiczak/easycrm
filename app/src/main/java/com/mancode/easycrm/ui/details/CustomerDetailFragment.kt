package com.mancode.easycrm.ui.details

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.PickContact
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import com.mancode.easycrm.utils.addContact
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class CustomerDetailFragment : Fragment() {

    private val viewModel: CustomerDetailViewModel by viewModels()
    private val args: CustomerDetailFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    private val pickContact =
        registerForActivityResult(PickContact()) { uri ->
            if (uri != null) {
                addSelectedContact(uri)
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.customerId = args.customerId
        return ComposeView(requireContext()).apply {
            setContent {
                var openDialog by remember { mutableStateOf(false) }
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
                            var fabExpanded by remember { mutableStateOf(false) }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AnimatedVisibility(fabExpanded) {
                                    FloatingActionButton(
                                        onClick = {
                                            fabExpanded = false
                                            openDialog = true
                                        },
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.PersonAdd,
                                            contentDescription = ""
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                AnimatedVisibility(fabExpanded) {
                                    FloatingActionButton(
                                        onClick = {
                                            fabExpanded = false
                                            val dirs =
                                                CustomerDetailFragmentDirections.actionCustomerDetailFragmentToNoteDialog(
                                                    viewModel.customerId!!
                                                )
                                            navController.navigate(dirs)
                                        },
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.NoteAdd,
                                            contentDescription = ""
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                                FloatingActionButton(onClick = {
                                    fabExpanded = !fabExpanded
                                }) {
                                    val imageVector =
                                        if (!fabExpanded) Icons.Filled.Add
                                        else Icons.Filled.Close
                                    Icon(imageVector = imageVector, contentDescription = "")
                                }
                            }
                        }
                    ) {
                        CustomerDetailsScreen(viewModel, navController)
                    }
                    if (openDialog) {
                        AlertDialog(
                            onDismissRequest = { openDialog = false },
                            buttons = {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    ListItem(text = { Text("Utwórz nowy kontakt") },
                                        modifier = Modifier
                                            .clickable {
                                                addContact(context)
                                                openDialog = false
                                            })
                                    Divider()
                                    ListItem(text = { Text("Wybierz istniejący kontakt") },
                                        modifier = Modifier
                                            .clickable {
                                                pickContact()
                                                openDialog = false
                                            })
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun pickContact() = pickContact.launch()

    private fun addSelectedContact(uri: Uri) {
        val contentResolver = requireContext().contentResolver
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
        )
        contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )?.use { lookupCursor ->
            if (lookupCursor.moveToFirst()) {
                val lookupKey = lookupCursor.getString(
                    lookupCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY)
                )
                val name = lookupCursor.getString(
                    lookupCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                )
                val hasPhoneNumber = lookupCursor.getString(
                    lookupCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
                ).toInt() != 0
                viewModel.insertContact(lookupKey, name, hasPhoneNumber)
            }
        }
    }
}