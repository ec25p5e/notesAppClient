package com.ec25p5e.notesapp.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.domain.models.BottomNavItem
import com.ec25p5e.notesapp.core.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.MainFeedScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = stringResource(id = R.string.cont_descr_home),
            showFab = true,
            fabClick = {
                navController.navigate(Screen.CreatePostScreen.route)
            },
            modifierFab = Modifier.background(MaterialTheme.colorScheme.surface)
        ),
        BottomNavItem(
            route = Screen.MessageScreen.route,
            icon = Icons.Outlined.Message,
            contentDescription = "Messages",
            alertCount = 100
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profiles"
        ),
        BottomNavItem(
            route = Screen.NotesScreen.route,
            icon = Icons.Outlined.Notes,
            contentDescription = stringResource(id = R.string.cont_descr_notes),
            showFab = true,
            fabClick = {
                navController.navigate(Screen.CreateNoteScreen.route)
            },
            modifierFab = Modifier.background(MaterialTheme.colorScheme.surface)
        )
    ),
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    NavigationBar {
                        bottomNavItems.forEachIndexed { _, bottomNavItem ->
                            StandardBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = navController.currentDestination?.route?.startsWith(bottomNavItem.route) == true,
                                alertCount = bottomNavItem.alertCount,
                                enabled = bottomNavItem.icon != null
                            ) {
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route)
                                }
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            bottomNavItems.forEachIndexed { _, bottomFab ->
                if(bottomFab.showFab && navController.currentDestination?.route?.startsWith(bottomFab.route) == true) {
                    FloatingActionButton(
                        modifier = bottomFab.modifierFab,
                        onClick = bottomFab.fabClick
                    ) {
                        Icon(
                            imageVector = bottomFab.fabIcon,
                            contentDescription = bottomFab.fabContentDescription
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier
    ) {
        content()
    }
}