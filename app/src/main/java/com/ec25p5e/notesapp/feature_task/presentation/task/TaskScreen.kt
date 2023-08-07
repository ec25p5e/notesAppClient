package com.ec25p5e.notesapp.feature_task.presentation.task

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.StandardOptionsMenu
import com.ec25p5e.notesapp.core.presentation.components.StandardToolbar
import com.ec25p5e.notesapp.core.presentation.util.LottieView
import com.ec25p5e.notesapp.core.presentation.util.asString
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.domain.models.TaskFilters
import com.ec25p5e.notesapp.feature_task.presentation.components.TaskItem
import com.ec25p5e.notesapp.feature_task.presentation.util.UiEventTask
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TodoScreen(
    imageLoader: ImageLoader,
    scaffoldState: SnackbarHostState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEventTask.ShowSnackbar -> {
                    Toast.makeText(
                        context,
                        event.uiText!!.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEventTask.Navigate -> {
                    onNavigate(event.route)
                }
                else -> {
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.all_your_todo),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                StandardOptionsMenu(
                    menuItem = {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(id = R.string.order_todo_title))
                            },
                            onClick = {

                            },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_filter),
                                    contentDescription = stringResource(id = R.string.order_todo_title)
                                )
                            }
                        )
                    }
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ){
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = {tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .fillMaxWidth()
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ){
                    TaskFilters.values().forEachIndexed{ index, filter ->
                        Tab(
                            text = { Text(stringResource(id = filter.text)) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }

                if(state.isGreat) {
                    LottieView(
                        json = R.raw.thumbs_up,
                        iterations = 1,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                HorizontalPager(
                    count = TaskFilters.values().size,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    state = pagerState
                ) { page ->
                    when(TaskFilters.values()[page]){
                        TaskFilters.Today -> TaskFilter(state.tasksToday, viewModel)
                        TaskFilters.Upcoming -> TaskFilter(state.tasksUpcoming, viewModel)
                        TaskFilters.Regular -> TaskFilter(state.tasksRegular, viewModel)
                        TaskFilters.CheckLists -> TaskFilter(state.tasksChecklist, viewModel)
                        TaskFilters.All -> TaskFilter(tasks = state.allTasks, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskFilter(
    tasks: List<Task>,
    viewModel: TaskViewModel
) {
    if(tasks.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            LottieView(
                json = R.raw.empty_tasks,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    } else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 60.dp,
                start = 16.dp,
                end = 16.dp
            )
        ){
            items(tasks) {
                viewModel.onEvent(TaskEvent.GetCheckablesForTask(it.id!!.toInt()))
                TaskItem(
                    task = it,
                    checkables = viewModel.state.value.checkablesForTask
                )
            }
        }
    }
}