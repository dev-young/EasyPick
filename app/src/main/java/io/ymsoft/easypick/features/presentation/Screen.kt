package io.ymsoft.easypick.features.presentation

sealed class Screen(val route: String) {
    object GroupsScreen: Screen("groups_screen")
    object AddEditGroupScreen: Screen("add_edit_group_screen")
    object GroupScreen: Screen("group_screen")
}
