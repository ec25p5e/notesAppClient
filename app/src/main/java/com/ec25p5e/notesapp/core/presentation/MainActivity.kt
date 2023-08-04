package com.ec25p5e.notesapp.core.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.ec25p5e.notesapp.core.presentation.components.Navigation
import com.ec25p5e.notesapp.core.presentation.components.StandardScaffold
import com.ec25p5e.notesapp.core.presentation.ui.theme.NotesAppTheme
import com.ec25p5e.notesapp.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = remember { SnackbarHostState() }

                    StandardScaffold(
                        navController = navController,
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Navigation(navController, scaffoldState, imageLoader)
                    }
                }
            }
        }
    }

    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
        val doesRouteMatch = backStackEntry?.destination?.route in listOf(
            Screen.NotesScreen.route,
            Screen.TodoScreen.route,
            Screen.ArchiveScreen.route,
            Screen.ProfileScreen.route,
        )

        return doesRouteMatch
    }
}