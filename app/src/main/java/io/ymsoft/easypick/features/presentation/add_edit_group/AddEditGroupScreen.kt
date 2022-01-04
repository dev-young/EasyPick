package io.ymsoft.easypick.features.presentation.add_edit_group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.ymsoft.easypick.features.presentation.components.BackBtnAppBar
import io.ymsoft.easypick.ui.theme.EasyPickTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditGroupScreen(
    navController: NavController,
    vm: AddEditGroupViewModel = hiltViewModel()
) {
    val isEditMode = vm.isEditMode
    val name = vm.groupName
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        vm.eventFlow.collectLatest {
            when (it) {
                is AddEditGroupViewModel.UiEvent.SaveGroup -> {
                    navController.navigateUp()
                }
                is AddEditGroupViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BackBtnAppBar(title = if (isEditMode.value)"그룹 정보 수정" else "그룹 추가", onIconClick = {
                navController.navigateUp()
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                vm.onEvent(AddEditGroupEvent.Save)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save CandiGroup")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column {
            OutlinedTextField(
                value = name.value,
                onValueChange = { vm.onEvent(AddEditGroupEvent.ChangeName(it)) },
                label = { Text("그룹 이름") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditGroupPreview() {
    EasyPickTheme {
        Scaffold(
            topBar = {
                BackBtnAppBar(title = "그룹 정보 수정")
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {

                }, backgroundColor = MaterialTheme.colors.primary) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save CandiGroup")
                }
            }
        ) {
            Column {
                OutlinedTextField(
                    value = "그룹그룹",
                    onValueChange = { },
                    label = { Text("그룹 이름") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

            }
        }
    }

}