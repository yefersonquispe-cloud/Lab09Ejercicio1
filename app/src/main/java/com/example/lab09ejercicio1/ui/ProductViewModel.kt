package com.example.lab09ejercicio1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab09ejercicio1.data.ProductApiService
import com.example.lab09ejercicio1.data.ProductModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ProductUiState<out Data> {
    data object Loading : ProductUiState<Nothing>
    data class Success<Data>(val data: Data) : ProductUiState<Data>
    data class Error(val message: String) : ProductUiState<Nothing>
}

private fun Exception.userMessage(): String = when (this) {
    is IOException -> "No pudimos conectar. Revisa tu conexión a Internet y vuelve a intentarlo."
    is HttpException -> if (code() == 404) "El producto ya no está disponible."
        else "El servicio no está disponible en este momento. Inténtalo nuevamente."
    else -> "No pudimos cargar la información. Vuelve a intentarlo."
}

class ProductViewModel(private val api: ProductApiService) : ViewModel() {
    private val mutableState = MutableStateFlow<ProductUiState<List<ProductModel>>>(ProductUiState.Loading)
    val state = mutableState.asStateFlow()
    private var request: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts() {
        if (request?.isActive == true) return
        request = viewModelScope.launch {
            mutableState.value = ProductUiState.Loading
            try {
                mutableState.value = ProductUiState.Success(api.getProducts().products)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                mutableState.value = ProductUiState.Error(error.userMessage())
            }
        }
    }
}

class ProductDetailViewModel(
    private val productId: Int,
    private val api: ProductApiService
) : ViewModel() {
    private val mutableState = MutableStateFlow<ProductUiState<ProductModel>>(ProductUiState.Loading)
    val state = mutableState.asStateFlow()
    private var request: Job? = null

    init {
        loadProduct()
    }

    fun loadProduct() {
        if (request?.isActive == true) return
        request = viewModelScope.launch {
            mutableState.value = ProductUiState.Loading
            try {
                mutableState.value = ProductUiState.Success(api.getProduct(productId))
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                mutableState.value = ProductUiState.Error(error.userMessage())
            }
        }
    }
}
