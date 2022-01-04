package io.ymsoft.easypick.features.presentation.candi_groups

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.presentation.Screen
import io.ymsoft.easypick.features.presentation.candi_groups.components.GroupItem
import io.ymsoft.easypick.features.presentation.components.DefaultAppBar

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GroupsScreen(
    navController: NavController,
    vm: GroupsViewModel = hiltViewModel()
) {
    val state = vm.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    HomeScaffold(navController = navController) {
        Column {
            GroupList(state.groups, onItemClick = {
                Log.i("onClick", "GroupsScreen: $it")

            }, onItemLongClick = {
                Log.i("onLongClick", "GroupsScreen: $it")
                navController.navigate(Screen.AddEditGroupScreen.route + "?groupId=${it.id}")

            })
        }
    }
}


@Composable
fun HomeScaffold(
    navController: NavController? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultAppBar(title = "그룹 목록")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.navigate(Screen.AddEditGroupScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add CandiGroup")
            }
        }) { content() }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GroupList(
    groups: List<CandiGroup>,
    onItemClick: ((item: CandiGroup) -> Unit)? = null,
    onItemLongClick: ((item: CandiGroup) -> Unit)? = null
) {
    LazyColumn {
        items(groups) { groupItem ->
            GroupItem(
                group = groupItem, modifier = Modifier
                    .combinedClickable(onClick = {
                        onItemClick?.invoke(groupItem)
                    }, onLongClick = {
                        onItemLongClick?.invoke(groupItem)
                    })

            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun GroupPreview() {
    HomeScaffold {
        val groups = (0..5).map { CandiGroup("그룹$it") }
        GroupList(groups = groups)
    }

}

