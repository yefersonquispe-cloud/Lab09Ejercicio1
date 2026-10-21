package com.example.lab09ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lab09ejercicio1.data.RetrofitClient
import com.example.lab09ejercicio1.ui.ProductListScreen
import com.example.lab09ejercicio1.ui.ProductViewModel
import com.example.lab09ejercicio1.ui.theme.Lab09Ejercicio1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab09Ejercicio1Theme {
                val products: ProductViewModel = viewModel(factory = viewModelFactory {
                    initializer { ProductViewModel(RetrofitClient.api) }
                })
                val state by products.state.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Box(Modifier.padding(padding)) {
                        ProductListScreen(state, products::loadProducts, onProductClick = {})
                    }
                }
            }
        }
    }
}
