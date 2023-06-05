package com.example.myhomebudgetcompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myhomebudgetcompose.ui.components.TabRow
import com.example.myhomebudgetcompose.ui.screens.AccountsScreen
import com.example.myhomebudgetcompose.ui.screens.BillsScreen
import com.example.myhomebudgetcompose.ui.screens.OverviewScreen
import com.example.myhomebudgetcompose.ui.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){
    val navController = rememberNavController()
    val screens = listOf(
        Screens.Overview, Screens.Accounts, Screens.Bills
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen =
        screens.find { it.route == currentDestination?.route } ?: Screens.Overview

    Scaffold(
        topBar = {
            TabRow(
                allScreens = screens,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen
            )
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier){
    NavHost(
        navController = navController,
        startDestination = Screens.Overview.route,
        modifier = modifier
    ) {
        composable(route = Screens.Overview.route){
            OverviewScreen(
                onClickSeeAllAccounts = { navController.navigateSingleTopTo(Screens.Accounts.route) },
                onClickSeeAllBills = {navController.navigateSingleTopTo(Screens.Bills.route)}
            ) }
        composable(route = Screens.Accounts.route){ AccountsScreen() }
        composable(route = Screens.Bills.route){ BillsScreen() }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
}