package io.ymsoft.easypick.features.presentation.candi_groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.presentation.Screen
import io.ymsoft.easypick.features.presentation.candi_groups.components.GroupItem

@ExperimentalMaterialApi
@Composable
fun GroupsScreen(
    navController: NavController,
    vm: GroupsViewModel = hiltViewModel()
) {
    val state = vm.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Column {

        Button(onClick = {
            navController.navigate(Screen.AddEditGroupScreen.route)
        }) {
            Text(text = "추가하기")
        }

        LazyColumn {
            items(state.groups) {
                GroupItem(group = it, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    })
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun GroupPreview() {
    val groups = (0..5).map { CandiGroup("그룹$it") }
    LazyColumn {
        items(groups) {
            GroupItem(group = it, modifier = Modifier.fillMaxWidth())
        }
    }
}

