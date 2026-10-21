package com.example.lab09ejercicio1.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab09ejercicio1.data.ProductApiService
import com.example.lab09ejercicio1.data.RetrofitClient
import com.example.lab09ejercicio1.ui.theme.StoreBlue
import com.example.lab09ejercicio1.ui.theme.StoreNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductStoreApp(api: ProductApiService = RetrofitClient.api) {
    val navController = rememberNavController()
    val entry by navController.currentBackStackEntryAsState()
    val route = entry?.destination?.route ?: "home"
    val isDetail = route == "products/{id}"

    fun openTab(destination: String) {
        navController.navigate(destination) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ProductStore") },
                navigationIcon = {
                    if (isDetail) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver a productos")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StoreBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = StoreNavigation) {
                NavigationBarItem(
                    selected = route == "home",
                    onClick = { openTab("home") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = route.startsWith("products"),
                    onClick = {
                        if (isDetail) navController.popBackStack("products", false)
                        else openTab("products")
                    },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    label = { Text("Productos") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen(onExplore = { openTab("products") }) }
            composable("products") {
                val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory {
                    initializer { ProductViewModel(api) }
                })
                val state by productViewModel.state.collectAsStateWithLifecycle()
                ProductListScreen(
                    state = state,
                    onRetry = productViewModel::loadProducts,
                    onProductClick = { navController.navigate("products/$it") }
                )
            }
            composable(
                route = "products/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { detailEntry ->
                val productId = requireNotNull(detailEntry.arguments).getInt("id")
                val detailViewModel: ProductDetailViewModel = viewModel(factory = viewModelFactory {
                    initializer { ProductDetailViewModel(productId, api) }
                })
                val state by detailViewModel.state.collectAsStateWithLifecycle()
                ProductDetailScreen(state = state, onRetry = detailViewModel::loadProduct)
            }
        }
    }
}
