package io.ymsoft.easypick.features.presentation.candi_groups

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ymsoft.easypick.features.domain.use_case.PickUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val pickUseCases: PickUseCases
) : ViewModel() {

    private val _state = mutableStateOf(GroupsState())
    val state: State<GroupsState> = _state

    init {
        getGroups()
    }

    private fun getGroups() {
        pickUseCases.getGroups().onEach {
            _state.value = state.value.copy(groups = it)
        }.launchIn(viewModelScope)
    }

}