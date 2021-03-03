package fr.juliettebois.dmii.myapplication.dal.online.service

import fr.juliettebois.dmii.myapplication.dal.online.model.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface MovieService {
    @GET("/genre/movie/list")
    suspend fun getCategories(): Response<CategoryResponse>
}
