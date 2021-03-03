package fr.juliettebois.dmii.myapplication.dal.online.utils

import fr.juliettebois.dmii.myapplication.dal.online.model.CategoryResponse
import fr.juliettebois.dmii.myapplication.models.Category

fun CategoryResponse.Genre.toCategory() = Category(
    id = (id ?: "") as Int,
    name = name ?: ""
)
