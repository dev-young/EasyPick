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
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.model.InvalidCandidateException
import io.ymsoft.easypick.features.domain.use_case.PickUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.IndexOutOfBoundsException
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

    private val _candiList = mutableStateOf(emptyList<Candidate>())
    val candiList: State<List<Candidate>> = _candiList

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
            is GroupDetailEvent.ToggleMode -> {
                _isEditMode.value = !_isEditMode.value
            }
            is GroupDetailEvent.AddCandidate -> {
                addCandidate()
            }
            is GroupDetailEvent.Choice -> {
                choiceCandidate()
            }
            is GroupDetailEvent.ChangeName -> {
                _candiName.value = e.name
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
                val choiced = candiList.value.shuffled().subList(0, count)
                val str = choiced.joinToString { it.name }
                _eventFlow.emit(UiEvent.ShowSnackbar(str, true))
            } catch (e:IndexOutOfBoundsException) {
                _eventFlow.emit(UiEvent.ShowSnackbar("올바른 범위가 아닙니다."))
            }

        }
    }

    private fun loadCandidates(id: Int = groupId!!) {
        pickUseCases.getCandidatesById(id).onEach {
            _candiList.value = it
        }.launchIn(viewModelScope)
    }

    private fun getString(@StringRes id: Int) = resources.getString(id)

    sealed class UiEvent {
        data class ShowSnackbar(val message: String = "", val cancelable:Boolean = false) : UiEvent()

    }
}