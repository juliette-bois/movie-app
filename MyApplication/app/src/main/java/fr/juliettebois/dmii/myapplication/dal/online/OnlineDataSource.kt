package fr.juliettebois.dmii.myapplication.dal.online

import fr.juliettebois.dmii.myapplication.dal.online.model.CategoryResponse
import fr.juliettebois.dmii.myapplication.dal.online.service.MovieService
import fr.juliettebois.dmii.myapplication.dal.online.utils.Result

internal class OnlineDataSource(private val service: MovieService) {
    suspend fun getCategories(): Result<List<CategoryResponse.Genre>> {
        return try {
            val response = service.getCategories()
            if (response.isSuccessful) {
                Result.Success(response.body()!!.genres)
            } else {
                Result.Error(
                    exception = Exception(),
                    message = response.message(),
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            Result.Error(
                exception = e,
                message = e.message ?: "No message",
                code = -1
            )
        }
    }
}
