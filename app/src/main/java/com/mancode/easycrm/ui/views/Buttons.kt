package com.mancode.easycrm.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import com.mancode.easycrm.utils.formatToIsoDate
import org.threeten.bp.Instant

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleButton(expanded: Boolean = false, instant: Instant? = null, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Schedule,
                contentDescription = "",
                alpha = 0.5f
            )
            if (expanded && instant != null) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(instant.formatToIsoDate())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleButtonPreview() {
    ScheduleButton(expanded = false, onClick = { })
}

@Preview(showBackground = true)
@Composable
fun ScheduleButtonExpandedPreview() {
    EasyCrmTheme { ScheduleButton(expanded = true, onClick = { }) }
}
