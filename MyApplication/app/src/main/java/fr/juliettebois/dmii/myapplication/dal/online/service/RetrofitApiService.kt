package fr.juliettebois.dmii.myapplication.dal.online.service

import fr.juliettebois.dmii.myapplication.dal.online.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("everything/")
    fun getArticles(@Query("q") query: String): Call<CategoryResponse>
}
