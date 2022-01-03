package io.ymsoft.easypick.features.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.ymsoft.easypick.features.presentation.add_edit_group.AddEditGroupScreen
import io.ymsoft.easypick.features.presentation.candi_groups.GroupsScreen
import io.ymsoft.easypick.ui.theme.EasyPickTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyPickTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.GroupsScreen.route
                ) {
                    composable(route = Screen.GroupsScreen.route) {
                        GroupsScreen(navController)
                    }

                    composable(route = Screen.AddEditGroupScreen.route) {
                        AddEditGroupScreen(navController)
                    }

//                    composable(route = Screen.GroupScreen.route) {
////                        GroupsScreen(navController)
//                    }
                }

            }
        }
    }
}