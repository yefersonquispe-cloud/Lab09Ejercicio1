package com.example.lab09ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.example.lab09ejercicio1.ui.ProductStoreApp
import com.example.lab09ejercicio1.ui.theme.Lab09Ejercicio1Theme
import com.example.lab09ejercicio1.ui.theme.StoreBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(StoreBlue.toArgb()))
        setContent {
            Lab09Ejercicio1Theme {
                ProductStoreApp()
            }
        }
    }
}
