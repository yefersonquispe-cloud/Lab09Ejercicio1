package com.example.lab09ejercicio1.data

data class ProductResponse(
    val products: List<ProductModel> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0
)

data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String?,
    val images: List<String> = emptyList(),
    val rating: Double? = null,
    val brand: String? = null,
    val discountPercentage: Double? = null,
    val stock: Int = 0
)
