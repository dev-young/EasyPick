package io.ymsoft.easypick.features.presentation.add_edit_group

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ymsoft.easypick.features.domain.use_case.PickUseCases
import javax.inject.Inject

@HiltViewModel
class AddEditGroupViewModel @Inject constructor(
    private val pickUseCases: PickUseCases
) : ViewModel() {

    private val _groupName = mutableStateOf("")
    val groupName: State<String> = _groupName


    fun onEvent(e: AddEditGroupEvent) {
        when (e) {
            is AddEditGroupEvent.ChangeName -> {
                _groupName.value = e.name
            }
            is AddEditGroupEvent.Save -> {
                Log.i("TAG", "onEvent: ${groupName.value} 저장!")
            }
        }
    }

}