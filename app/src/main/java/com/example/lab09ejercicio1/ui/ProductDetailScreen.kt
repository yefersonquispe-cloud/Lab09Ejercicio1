package com.example.lab09ejercicio1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lab09ejercicio1.data.ProductModel

@Composable
fun ProductDetailScreen(state: ProductUiState<ProductModel>, onRetry: () -> Unit) {
    when (state) {
        ProductUiState.Loading -> LoadingState()
        is ProductUiState.Error -> MessageState("No pudimos cargar el producto", state.message, onRetry)
        is ProductUiState.Success -> ProductDetail(state.data)
    }
}

@Composable
private fun ProductDetail(product: ProductModel) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Surface(shape = RoundedCornerShape(24.dp)) {
            ProductImage(product.images.firstOrNull() ?: product.thumbnail, product.title,
                Modifier.fillMaxWidth().heightIn(min = 260.dp, max = 340.dp))
        }
        Text(product.category.categoryLabel(), style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)
        Text(product.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(product.price.usd(), style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text("Precio en dólares estadounidenses (USD)", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, "Calificación", Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
            Text(product.rating?.let { "${it.displayNumber()} / 5" } ?: "Sin calificaciones")
        }
        product.discountPercentage?.takeIf { it > 0 }?.let { discount ->
            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(10.dp)) {
                Text("${discount.displayNumber()}% de descuento", Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge)
            }
        }
        HorizontalDivider()
        Text("Acerca del producto", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(product.description, style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surface) {
            Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                DetailField("Categoría", product.category.categoryLabel())
                product.brand?.takeIf { it.isNotBlank() }?.let { DetailField("Marca", it) }
                DetailField("Disponibilidad", if (product.stock > 0) "${product.stock} unidades" else "Agotado")
                DetailField("Código del producto", "#${product.id}")
            }
        }
    }
}

@Composable
private fun DetailField(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(label, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}
