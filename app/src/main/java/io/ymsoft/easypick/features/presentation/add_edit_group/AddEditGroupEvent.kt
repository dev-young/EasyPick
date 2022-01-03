package io.ymsoft.easypick.features.presentation.add_edit_group

sealed class AddEditGroupEvent {
    data class ChangeName(val name: String): AddEditGroupEvent()
    object Save: AddEditGroupEvent()
}