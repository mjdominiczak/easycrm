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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.mancode.easycrm.ui.list.StatusChip
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import com.mancode.easycrm.utils.addContact
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Instant

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
                            val customer by viewModel.customer.collectAsState(initial = null)
                            TopAppBar(
                                title = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = customer?.raw?.name ?: "",
                                            modifier = Modifier.weight(0.6f)
                                        )
                                        StatusChip(
                                            text = "Zbieranie danych",
                                            modifier = Modifier.weight(0.4f)
                                        )
                                    }
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
                        CustomerDetailsScreen(
                            viewModel,
                            navController
                        ) { buttonId, instant -> showDatePicker(buttonId, instant) }
                    }
                    if (openDialog) {
                        AlertDialog(
                            onDismissRequest = { openDialog = false },
                            buttons = {
                                Column {
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
                                                pickContact.launch()
                                                openDialog = false
                                            })
                                }
                            },
                            modifier = Modifier
                                .padding(24.dp) // TODO to be removed in future compose releases; https://issuetracker.google.com/issues/192682388
                        )
                    }
                }
            }
        }
    }

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

    private fun showDatePicker(buttonId: Int, instant: Instant?) {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Wybierz datę")
            .setSelection(instant?.toEpochMilli())
            .build()
        activity?.let {
            picker.show(it.supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener { stamp ->
                when (buttonId) {
                    DATE_BUTTON_LAST_CONTACT -> viewModel.updateLastContactDate(stamp)
                    DATE_BUTTON_NEXT_CONTACT -> viewModel.updateNextContactDate(stamp)
                }
            }
        }
    }

    companion object {
        const val DATE_BUTTON_LAST_CONTACT = 0
        const val DATE_BUTTON_NEXT_CONTACT = 1
    }
}