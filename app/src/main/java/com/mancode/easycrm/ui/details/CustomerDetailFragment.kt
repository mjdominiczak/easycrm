package com.mancode.easycrm.ui.details

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import com.google.android.material.datepicker.MaterialDatePicker
import com.mancode.easycrm.app.EasyCrmScreen
import com.mancode.easycrm.ui.details.DateButtons.LAST_CONTACT
import com.mancode.easycrm.ui.details.DateButtons.NEXT_CONTACT
import com.mancode.easycrm.ui.list.StatusChip
import com.mancode.easycrm.utils.addContact
import org.threeten.bp.Instant

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomerDetailsFrag(viewModel: CustomerDetailViewModel, navController: NavHostController) {
    var openDialog by remember { mutableStateOf(false) }
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
                            modifier = Modifier
                                .weight(0.6f)
                                .clickable {
                                    // TODO: make customer name editable
                                }
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
                            navController.navigate("${EasyCrmScreen.NoteDialog.name}/${viewModel.customerId!!}")
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
        val fragmentManager = (LocalContext.current as FragmentActivity).supportFragmentManager
        CustomerDetailsScreen(
            viewModel,
            navController
        ) { button, instant ->
            val callback = when (button) {
                LAST_CONTACT -> { stamp: Long -> viewModel.updateLastContactDate(stamp) }
                NEXT_CONTACT -> { stamp: Long -> viewModel.updateNextContactDate(stamp) }
            }
            showDatePicker(
                fragmentManager,
                instant
            ) { stamp -> callback(stamp) }
        }
    }
    val context = LocalContext.current
    val pickContactLauncher = rememberLauncherForActivityResult(PickContact()) { uri ->
        if (uri != null) {
            addSelectedContact(viewModel, context, uri)
        }
    }
    AddContactDialog(
        launchContactPicker = { pickContactLauncher.launch() },
        openDialog = openDialog
    ) {
        openDialog = false
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddContactDialog(launchContactPicker: () -> Unit, openDialog: Boolean, onDismiss: () -> Unit) {
    if (openDialog) {
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = { onDismiss() },
            buttons = {
                Column {
                    ListItem(text = { Text("Utwórz nowy kontakt") },
                        modifier = Modifier
                            .clickable {
                                addContact(context)
                                onDismiss()
                            })
                    Divider()
                    ListItem(text = { Text("Wybierz istniejący kontakt") },
                        modifier = Modifier
                            .clickable {
                                launchContactPicker()
                                onDismiss()
                            })
                }
            },
            modifier = Modifier
                .padding(24.dp) // TODO to be removed in future compose releases; https://issuetracker.google.com/issues/192682388
        )
    }
}

fun addSelectedContact(viewModel: CustomerDetailViewModel, context: Context, uri: Uri) {
    val contentResolver = context.contentResolver
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

fun showDatePicker(
    fragmentManager: FragmentManager,
    instant: Instant?,
    onDateEntered: (Long) -> Unit,
) {
    val picker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Wybierz datę")
        .setSelection(instant?.toEpochMilli())
        .build()
    picker.show(fragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { stamp ->
        onDateEntered(stamp)
    }
}

enum class DateButtons {
    LAST_CONTACT,
    NEXT_CONTACT
}
