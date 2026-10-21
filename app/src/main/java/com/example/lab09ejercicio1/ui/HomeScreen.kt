package com.example.lab09ejercicio1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onExplore: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("TU PRÓXIMO DESCUBRIMIENTO", style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary)
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(
                    Icons.Default.ShoppingCart, contentDescription = null,
                    modifier = Modifier.size(64.dp).background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f), RoundedCornerShape(18.dp)
                    ).padding(14.dp)
                )
                Text("Encuentra algo\npara ti.", style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold)
                Text("Explora productos, descubre sus detalles y compara precios en un solo lugar.",
                    style = MaterialTheme.typography.bodyLarge)
                Button(onClick = onExplore, modifier = Modifier.fillMaxWidth()) {
                    Text("Explorar productos", modifier = Modifier.padding(vertical = 6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.padding(start = 12.dp))
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Un catálogo para explorar", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold)
            Text("Belleza, tecnología, hogar y mucho más. Consulta imágenes, calificaciones y disponibilidad de cada producto.",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(4.dp))
        Text("Datos de DummyJSON · Precios en USD", style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
