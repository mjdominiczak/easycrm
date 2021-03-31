package com.mancode.easycrm.data

data class Contact(
    val name: String,
    val phoneNumber: String? = null,
    val email: String? = null
)
