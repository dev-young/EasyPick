package io.ymsoft.easypick.features.presentation.add_edit_group

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.ymsoft.easypick.R

@ExperimentalMaterialApi
@Composable
fun AddEditGroupScreen(
    navController: NavController,
    vm: AddEditGroupViewModel = hiltViewModel()
) {
    val name = vm.groupName
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Column {
        stringResource(R.string.app_name)
        TextField(
            value = name.value,
            onValueChange = { vm.onEvent(AddEditGroupEvent.ChangeName(it))},
            label = { Text("그룹 이름") }
        )

        Button(onClick = {
            vm.onEvent(AddEditGroupEvent.Save)
        }) {
            Text(text = "저장")
        }


    }
}