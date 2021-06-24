package com.mancode.easycrm.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import com.mancode.easycrm.db.Contact

fun dialContact(context: Context, contact: Contact) {
    val intent = if (contact.phoneNumber != null) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${contact.phoneNumber}")
        }
    } else {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.withAppendedPath(
                ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                contact.contactLookupKey
            )
        }
    }
    ContextCompat.startActivity(context, intent, null)
}