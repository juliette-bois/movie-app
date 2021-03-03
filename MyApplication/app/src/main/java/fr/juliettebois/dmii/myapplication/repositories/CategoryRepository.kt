package fr.juliettebois.dmii.myapplication.repositories

import fr.juliettebois.dmii.myapplication.dal.online.OnlineDataSource
import fr.juliettebois.dmii.myapplication.dal.online.utils.Result
import fr.juliettebois.dmii.myapplication.dal.online.utils.toCategory
import fr.juliettebois.dmii.myapplication.models.Category
import org.koin.core.KoinComponent
import org.koin.core.inject

class CategoryRepository() : KoinComponent {
    private val online: OnlineDataSource by inject()

    suspend fun getCategories(): Result<List<Category>> {
        return when (val result = online.getCategories()) {
            is Result.Success -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
                val categories = result.data.map {
                    it.toCategory()
                }
                Result.Success(categories)
            }
            is Result.Error -> result
        }
    }
}
