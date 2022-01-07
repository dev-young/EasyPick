package io.ymsoft.easypick.features.presentation.group_detail

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import io.ymsoft.easypick.features.domain.model.Candidate
import io.ymsoft.easypick.features.domain.model.SelectableCandidate
import io.ymsoft.easypick.features.presentation.Screen
import io.ymsoft.easypick.features.presentation.components.BackBtnAppBar
import io.ymsoft.easypick.features.presentation.group_detail.components.CandidateItem
import io.ymsoft.easypick.features.presentation.group_detail.components.SwipeableSnackbarHost
import io.ymsoft.easypick.features.presentation.noRippleClickable
import io.ymsoft.easypick.ui.theme.EasyPickTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GroupDetailScreen(
    navController: NavController,
    vm: GroupDetailViewModel = hiltViewModel()
) {
    val candidatesState = vm.candidatesState.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val title = vm.groupName
    val isEditMode = vm.isEditMode

    LaunchedEffect(key1 = true) {
        vm.eventFlow.collectLatest {
            when (it) {
                is GroupDetailViewModel.UiEvent.ShowSnackbar -> {
                    if (it.cancelable) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            it.message,
                            actionLabel = "확인",
                            duration = SnackbarDuration.Indefinite,
                        )
                    } else scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
                GroupDetailViewModel.UiEvent.GroupDeleted -> {
                    navController.navigateUp()
                }
            }
        }
    }

    HomeScaffold(
        title, navController = navController,
        scaffoldState = scaffoldState,
        onEditClick = {
            navController.navigate(Screen.AddEditGroupScreen.route + "?groupId=${vm.groupId}")
        },
        onDeleteClick = {
            scope.launch {
                val r = scaffoldState.snackbarHostState.showSnackbar("${vm.groupName.value}를 삭제하시겠습니까?", "확인")
                if (r == SnackbarResult.ActionPerformed) {
                    vm.onEvent(GroupDetailEvent.DeleteGroup)
                }
            }
        }
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxSize()) {
            CandidateList(
                candies = candidatesState.list, onItemClick = {
                    Log.i("onClick", "GroupDetailScreen: $it")
                    vm.onEvent(GroupDetailEvent.OnCandiClick(it))
                }, modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedContent(
                    targetState = isEditMode,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = if (!isEditMode.value) "편집 모드" else "선택 모드", modifier = Modifier
                        .noRippleClickable { vm.onEvent(GroupDetailEvent.ToggleMode) }
                        .padding(16.dp))
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)

                ) {
                    Checkbox(checked = candidatesState.isSelectedAll, onCheckedChange = {
                        vm.onEvent(GroupDetailEvent.ToggleSelectAll)
                    })
                    Text(
                        text = "${candidatesState.selectedCount}",
                        modifier = Modifier.padding(start = 6.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    AnimatedVisibility(
                        visible = isEditMode.value && candidatesState.selectedCount > 0,
                        enter = scaleIn() + fadeIn(), exit = scaleOut() + fadeOut()
                    ) {
                        Text(text = "삭제", modifier = Modifier.noRippleClickable {
                            scope.launch {
                                val res = scaffoldState.snackbarHostState.showSnackbar(
                                    "${candidatesState.selectedCount}개의 후보를 삭제하시겠습니까?",
                                    "삭제",
                                    duration = SnackbarDuration.Short
                                )
                                if (res == SnackbarResult.ActionPerformed) {
                                    vm.onEvent(GroupDetailEvent.DeleteSelected)
                                }

                            }
                        })
                    }
                }


                AnimatedVisibility(visible = !isEditMode.value) {
                    Button(
                        onClick = { vm.onEvent(GroupDetailEvent.Choice) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)

                    ) {
                        Text(text = "고르기")
                    }
                }

                AnimatedVisibility(visible = isEditMode.value) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = vm.candiName.value,
                            onValueChange = { vm.onEvent(GroupDetailEvent.ChangeName(it)) },
                            label = { Text("후보 이름") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            modifier = Modifier.fillMaxWidth(),
                            keyboardActions = KeyboardActions(onDone = {
                                vm.onEvent(GroupDetailEvent.AddCandidate)
                            })
                        )
                    }
                }


            }


        }
    }
}


@ExperimentalMaterialApi
@Composable
fun HomeScaffold(
    title: State<String> = mutableStateOf("그룹이름1"),
    navController: NavController? = null,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            var showMenu by remember { mutableStateOf(false) }

            BackBtnAppBar(
                title = title.value,
                onIconClick = {
                    navController?.navigateUp()
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Rounded.MoreVert, null)
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = {
                            onEditClick()
                        }) {
                            Icon(Icons.Rounded.Edit, null)
                            Text(text = "수정", Modifier.padding(start = 4.dp))
                        }

                        DropdownMenuItem(onClick = { onDeleteClick() }) {
                            Icon(Icons.Rounded.Delete, null)
                            Text(text = "삭제", Modifier.padding(start = 4.dp))
                        }
                    }
                })
        },
        scaffoldState = scaffoldState,
        snackbarHost = { SwipeableSnackbarHost(scaffoldState.snackbarHostState) }
    ) { content() }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CandidateList(
    candies: List<SelectableCandidate>,
    onItemClick: ((item: SelectableCandidate) -> Unit)? = null,
    onItemLongClick: ((item: SelectableCandidate) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    FlowRow(modifier = modifier) {
        candies.forEach { item ->
            CandidateItem(
                item, modifier = Modifier
                    .combinedClickable(onClick = {
                        onItemClick?.invoke(item)
                    }, onLongClick = {
                        onItemLongClick?.invoke(item)
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
    EasyPickTheme(true) {
        HomeScaffold {
            val candies = (0..11).map { Candidate("후보$it") }.map { SelectableCandidate(it) }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CandidateList(
                    candies = candies, onItemClick = {
                        Log.i("onClick", "GroupDetailScreen: $it")
                    }, modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "후보 편집", modifier = Modifier
                        .clickable { }
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)

                    ) {
                        Checkbox(checked = false, onCheckedChange = {

                        })
                        Text(text = "0", modifier = Modifier.padding(start = 6.dp))
                        Spacer(modifier = Modifier.weight(1f))

                        AnimatedVisibility(visible = true) {
                            Text(text = "삭제", modifier = Modifier.noRippleClickable { })
                        }

                    }

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(text = "고르기")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "123",
                            onValueChange = { },
                            label = { Text("후보 이름") },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }

            }
        }
    }

}

