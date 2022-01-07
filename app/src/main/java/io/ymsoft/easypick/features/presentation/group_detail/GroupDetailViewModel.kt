package io.ymsoft.easypick.features.presentation.group_detail

import android.app.Application
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ymsoft.easypick.R
import io.ymsoft.easypick.features.domain.model.Candidate
import io.ymsoft.easypick.features.domain.model.InvalidCandidateException
import io.ymsoft.easypick.features.domain.model.SelectableCandidate
import io.ymsoft.easypick.features.domain.use_case.PickUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val pickUseCases: PickUseCases,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {
    private val resources = application.resources

    private val _groupName = mutableStateOf("")
    val groupName: State<String> = _groupName

    private val _isEditMode = mutableStateOf(false)
    val isEditMode: State<Boolean> = _isEditMode

    private val _candidatesState = mutableStateOf(CandidatesState())
    val candidatesState: State<CandidatesState> = _candidatesState

    private val _candiName = mutableStateOf("")
    val candiName: State<String> = _candiName

    var groupId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("groupId")?.let {
            if (it >= 0) {
                viewModelScope.launch {
                    pickUseCases.getGroup.invoke(it)?.let {
                        _groupName.value = it.name
                        groupId = it.id
                    }
                }
                loadCandidates(it)
            }
        }
    }

    fun onEvent(e: GroupDetailEvent) {
        when (e) {
            GroupDetailEvent.ToggleMode -> {
                _isEditMode.value = !_isEditMode.value
            }
            GroupDetailEvent.AddCandidate -> {
                addCandidate()
            }
            GroupDetailEvent.Choice -> {
                choiceCandidate()
            }
            is GroupDetailEvent.ChangeName -> {
                _candiName.value = e.name
            }
            is GroupDetailEvent.OnCandiClick -> {
                e.item.selected = !e.item.selected
                val count = candidatesState.value.selectedCount + if (e.item.selected) 1 else -1
                _candidatesState.value = candidatesState.value.copy(
                    selectedCount = count,
                    isSelectedAll = count == candidatesState.value.list.size
                )
            }
            GroupDetailEvent.ToggleSelectAll -> {
                selectAll(!candidatesState.value.isSelectedAll)
            }
            GroupDetailEvent.DeleteSelected -> {
                val target = candidatesState.value.list.filter { it.selected }.map { it.candi }
                    .toTypedArray()
                viewModelScope.launch(Dispatchers.IO) {
                    pickUseCases.deleteCandidate.invoke(target)
                    loadCandidates()
                }
            }
            GroupDetailEvent.DeleteGroup -> {
                deleteGroup()
            }
        }
    }

    private fun deleteGroup() {
        groupId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                pickUseCases.deleteGroup(it)
                _eventFlow.emit(UiEvent.GroupDeleted)
            }
        }
    }

    private fun addCandidate() {
        Log.i("TAG", "onEvent: ${candiName.value} 저장 시작!")
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                try {
                    val candi = Candidate(name = candiName.value, groupId = groupId)
                    pickUseCases.addCandidate(candi)
                    Log.i("TAG", "onEvent: $candi 저장 성공!")
                    loadCandidates()
                    _candiName.value = ""
                } catch (e: InvalidCandidateException) {
                    e.printStackTrace()
                    _eventFlow.emit(UiEvent.ShowSnackbar(getString(R.string.msg_group_name_can_not_be_empty)))
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun choiceCandidate(count: Int = 1) {
        viewModelScope.launch {
            try {
                val choiced = candidatesState.value.list.let {
                    if (candidatesState.value.selectedCount == 0) it
                    else it.filter { it.selected }
                }.shuffled().subList(0, count)
                val str = choiced.joinToString { it.candi.name }
                _eventFlow.emit(UiEvent.ShowSnackbar(str, true))
            } catch (e: IndexOutOfBoundsException) {
                _eventFlow.emit(UiEvent.ShowSnackbar("올바른 범위가 아닙니다."))
            }

        }
    }

    private fun loadCandidates(id: Int = groupId!!) {
        pickUseCases.getCandidatesById(id).onEach {
            val list = it.map { SelectableCandidate(it) }
            _candidatesState.value = CandidatesState(list)
        }.launchIn(viewModelScope)
    }

    private fun selectAll(select: Boolean) {
        val list = candidatesState.value.list.apply {
            forEach {
                it.selected = select
            }
        }
        _candidatesState.value = candidatesState.value.copy(
            selectedCount = if (select) list.size else 0,
            isSelectedAll = select
        )
    }

    private fun getString(@StringRes id: Int) = resources.getString(id)

    sealed class UiEvent {
        data class ShowSnackbar(val message: String = "", val cancelable: Boolean = false) :
            UiEvent()

        object GroupDeleted: UiEvent()

    }
}