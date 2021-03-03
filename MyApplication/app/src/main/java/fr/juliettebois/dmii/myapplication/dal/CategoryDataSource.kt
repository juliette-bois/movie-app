package fr.juliettebois.dmii.myapplication.dal

import androidx.lifecycle.LiveData
import fr.juliettebois.dmii.myapplication.models.Category

interface CategoryDataSource {
    fun getCategory(query: String): LiveData<List<Category>>
}