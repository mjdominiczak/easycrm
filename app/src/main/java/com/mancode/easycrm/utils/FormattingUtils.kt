package com.mancode.easycrm.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun Instant.formatToIsoDate(): String =
    LocalDateTime.ofInstant(this, ZoneId.systemDefault())
        .format(DateTimeFormatter.ISO_DATE)