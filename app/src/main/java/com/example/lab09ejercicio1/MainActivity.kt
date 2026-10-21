package com.example.lab09ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lab09ejercicio1.data.RetrofitClient
import com.example.lab09ejercicio1.ui.ProductDetailScreen
import com.example.lab09ejercicio1.ui.ProductDetailViewModel
import com.example.lab09ejercicio1.ui.ProductListScreen
import com.example.lab09ejercicio1.ui.ProductViewModel
import com.example.lab09ejercicio1.ui.theme.Lab09Ejercicio1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab09Ejercicio1Theme {
                var selectedProductId by rememberSaveable { mutableStateOf<Int?>(null) }
                val products: ProductViewModel = viewModel(factory = viewModelFactory {
                    initializer { ProductViewModel(RetrofitClient.api) }
                })
                val listState by products.state.collectAsStateWithLifecycle()
                BackHandler(enabled = selectedProductId != null) { selectedProductId = null }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (selectedProductId != null) {
                            TextButton(onClick = { selectedProductId = null }) { Text("Volver a productos") }
                        }
                    }
                ) { padding ->
                    Box(Modifier.padding(padding)) {
                        val productId = selectedProductId
                        if (productId == null) {
                            ProductListScreen(listState, products::loadProducts, onProductClick = { selectedProductId = it })
                        } else {
                            val detail: ProductDetailViewModel = viewModel(
                                key = "detail_$productId",
                                factory = viewModelFactory {
                                    initializer { ProductDetailViewModel(productId, RetrofitClient.api) }
                                }
                            )
                            val detailState by detail.state.collectAsStateWithLifecycle()
                            ProductDetailScreen(detailState, detail::loadProduct)
                        }
                    }
                }
            }
        }
    }
}
