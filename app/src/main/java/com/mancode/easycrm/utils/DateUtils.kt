package com.mancode.easycrm.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import org.threeten.bp.Instant

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
