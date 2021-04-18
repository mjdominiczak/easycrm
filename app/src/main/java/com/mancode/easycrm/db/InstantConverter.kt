package com.mancode.easycrm.db

import androidx.room.TypeConverter
import org.threeten.bp.Instant

object InstantConverter {

    @TypeConverter
    @JvmStatic
    fun toEpochMilli(instant: Instant) = instant.toEpochMilli()

    @TypeConverter
    @JvmStatic
    fun toInstant(epochMilli: Long): Instant = Instant.ofEpochMilli(epochMilli)
}