package com.example.cocktails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktails.ui.theme.CocktailsTheme
import com.example.details.ui.DetailsRoute
import com.example.random.ui.RandomCocktailRoute
import com.example.search.ui.SearchRoute
import dagger.hilt.android.AndroidEntryPoint

internal sealed class Screen(val route: String, @StringRes val label: Int, @DrawableRes val logo: Int) {
    data object Random : Screen("random", R.string.random_screen_nav_label, R.drawable.dice)
    data object Search : Screen("search", R.string.search_screen_nav_label, R.drawable.search)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CocktailsTheme {
                val navController = rememberNavController()
                var navigationSelectedItem by remember { mutableStateOf(0) }

                val items = listOf(
                    Screen.Random,
                    Screen.Search,
                )

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = @androidx.compose.runtime.Composable {
                        NavigationBar {
                            items.forEachIndexed { index, screen ->
                                NavigationBarItem(
                                    modifier = Modifier,
                                    selected = index == navigationSelectedItem,
                                    label = { Text(text = stringResource(id = screen.label)) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.logo),
                                            contentDescription = stringResource(id = screen.label),
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    onClick = {
                                        navigationSelectedItem = index
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                )
                            }
                        }
                    }) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Random.route,
                        modifier = Modifier.padding(paddingValues = paddingValues)
                    ) {
                        composable(route = Screen.Random.route) {
                            RandomCocktailRoute()
                        }
                        composable(route = Screen.Search.route) {
                            SearchRoute {
                                navController.navigate("details")
                            }
                        }
                        composable(route = "details") {
                            DetailsRoute()
                        }
                    }
                }
            }
        }
    }
}