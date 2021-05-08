package com.mancode.easycrm.data

import com.mancode.easycrm.db.Address
import org.threeten.bp.LocalDate

data class Customer(
    val id: Int,
    val name: String,
    val address: Address,
    val webpageUrl: String,
    val dateAdded: LocalDate = LocalDate.now()
) {
    val contacts = mutableListOf<Contact>()
    var dateLastContacted: LocalDate? = null
    var dateNextContact: LocalDate? = null
}
