package com.mancode.easycrm.data

import org.threeten.bp.Instant

data class Note(
    val timeStamp: Instant,
    val text: String
)
