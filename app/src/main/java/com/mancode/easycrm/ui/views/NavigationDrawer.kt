package com.mancode.easycrm.ui.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mancode.easycrm.R

@Composable
fun Drawer() {
    Column {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Easy CRM",
                style = MaterialTheme.typography.h5
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        val items = listOf(
            NavDrawerItem.Home,
            NavDrawerItem.Customers,
            NavDrawerItem.Tasks,
            NavDrawerItem.Analytics,
            NavDrawerItem.Funnel,
            NavDrawerItem.Settings,
            NavDrawerItem.Help,
        )
        var itemSelected by remember { mutableStateOf(0) }
        items.forEachIndexed { index, item ->
            DrawerItem(item = item, selected = index == itemSelected) { itemSelected = index }
        }
    }
}

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
    val background by animateColorAsState(targetValue = if (selected) MaterialTheme.colors.primaryVariant else Color.Transparent)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Surface(
            modifier = Modifier
                .height(44.dp)
                .padding(horizontal = 8.dp),
            color = background,
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onItemClick(item) }
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val tint by animateColorAsState(targetValue = if (selected) MaterialTheme.colors.primary else Color.DarkGray)
                Icon(
                    imageVector = item.icon,
                    contentDescription = "",
                    tint = tint
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title)
            }
        }
    }
}

sealed class NavDrawerItem(var icon: ImageVector, var title: String) {
    object Home : NavDrawerItem(Icons.Default.Home, "Strona główna")
    object Customers : NavDrawerItem(Icons.Default.Group, "Klienci")
    object Analytics : NavDrawerItem(Icons.Default.TrendingUp, "Analityka")
    object Funnel : NavDrawerItem(Icons.Default.FilterAlt, "Lejek sprzedaży")
    object Tasks : NavDrawerItem(Icons.Default.Task, "Zadania")
    object Settings : NavDrawerItem(Icons.Default.Settings, "Ustawienia")
    object Help : NavDrawerItem(Icons.Default.Help, "Pomoc")
}