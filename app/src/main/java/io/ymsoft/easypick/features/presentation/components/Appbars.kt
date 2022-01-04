package io.ymsoft.easypick.features.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable

@Composable
fun DefaultAppBar(title: String) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        title = {
            Text(text = title)
        })
}

@Composable
fun BackBtnAppBar(title: String, onIconClick: (() -> Unit)? = null) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = {
                onIconClick?.invoke()
            }) {
                Icon(imageVector = Icons.Rounded.ArrowBackIos, contentDescription = "back")
            }

        }
    )
}