package com.aadish.ipsagram.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@SuppressLint("NewApi")
@Composable
fun DropDownMenu(menuItems: List<Pair<String, () -> Unit>?>) {
    var expended by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        // The button to be clicked
        IconButton(onClick = { expended = true}) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More option"
            )
        }

        // The menu to be shown if clicked
        DropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false}
        ) {
            menuItems.filterNotNull().forEach { (itemName, onItemCLick) ->
                DropdownMenuItem(
                    text = { Text(itemName) },
                    onClick = onItemCLick
                )
            }
        }
    }
}