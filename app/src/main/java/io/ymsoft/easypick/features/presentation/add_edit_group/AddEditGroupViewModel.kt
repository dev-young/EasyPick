package io.ymsoft.easypick.features.presentation.add_edit_group

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
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.use_case.PickUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddEditGroupViewModel @Inject constructor(
    private val pickUseCases: PickUseCases,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {
    private val resources = application.resources
    private val _groupName = mutableStateOf("")
    val groupName: State<String> = _groupName

    private val _isEditMode = mutableStateOf(false)
    val isEditMode: State<Boolean> = _isEditMode

    var groupId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("groupId")?.let {
            if (it >= 0) {
                viewModelScope.launch {
                    pickUseCases.getGroup.invoke(it)?.let {
                        groupId = it.id
                        _groupName.value = it.name
                        _isEditMode.value = true
                    }
                }
            }
        }
    }

    fun onEvent(e: AddEditGroupEvent) {
        when (e) {
            is AddEditGroupEvent.ChangeName -> {
                _groupName.value = e.name
            }
            is AddEditGroupEvent.Save -> {
                Timber.i("onEvent: " + groupName.value + " 저장!")
                viewModelScope.launch {
                    launch(Dispatchers.IO) {
                        try {
                            val group = CandiGroup(
                                id = groupId,
                                name = groupName.value
                            )
                            if (groupId == null) {
                                pickUseCases.addGroup(group)
                            } else {
                                pickUseCases.updateGroup(group)
                            }

                            _eventFlow.emit(UiEvent.SaveGroup)
                        } catch (e: InvalidCandiGroupException) {
                            e.printStackTrace()
                            _eventFlow.emit(UiEvent.ShowSnackbar(getString(R.string.msg_group_name_can_not_be_empty)))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }

            }
        }
    }

    private fun getString(@StringRes id: Int) = resources.getString(id)

    sealed class UiEvent {
        data class ShowSnackbar(val message: String = "", @StringRes val resId: Int? = null) :
            UiEvent()

        object SaveGroup : UiEvent()
    }
}