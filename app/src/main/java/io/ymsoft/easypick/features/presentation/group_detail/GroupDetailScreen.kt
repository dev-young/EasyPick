package io.ymsoft.easypick.features.presentation.group_detail

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import io.ymsoft.easypick.features.presentation.components.BackBtnAppBar
import io.ymsoft.easypick.features.presentation.group_detail.components.CandidateItem
import io.ymsoft.easypick.features.presentation.noRippleClickable
import io.ymsoft.easypick.ui.theme.EasyPickTheme
import kotlinx.coroutines.flow.collectLatest

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
                            duration = SnackbarDuration.Indefinite
                        )
                    } else scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    HomeScaffold(title, navController = navController, scaffoldState = scaffoldState) {
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
                Text(text = if (!isEditMode.value) "편집 모드" else "선택 모드", modifier = Modifier
                    .noRippleClickable { vm.onEvent(GroupDetailEvent.ToggleMode) }
                    .align(Alignment.End)
                    .padding(16.dp))


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

                    AnimatedVisibility(visible = isEditMode.value) {
                        Text(text = "삭제", modifier = Modifier.noRippleClickable { })
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


@Composable
fun HomeScaffold(
    title: State<String> = mutableStateOf("그룹이름1"),
    navController: NavController? = null,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            BackBtnAppBar(title = title.value, onIconClick = {
                navController?.navigateUp()
            })
        }, scaffoldState = scaffoldState
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

