package fr.juliettebois.dmii.myapplication.dal.online

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.juliettebois.dmii.myapplication.dal.CategoryDataSource
import fr.juliettebois.dmii.myapplication.dal.online.service.RetrofitApiService
import fr.juliettebois.dmii.myapplication.dal.online.utils.toCategory
import fr.juliettebois.dmii.myapplication.models.Category
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryOnlineDatasource : CategoryDataSource {
    private val service: RetrofitApiService

    init {
        val retrofit = buildClient()
        // Init the api service with retrofit
        service = retrofit.create(RetrofitApiService::class.java)
    }

    /**
     * Configure retrofit
     */
    private fun buildClient(): Retrofit {
        val httpClient = OkHttpClient.Builder().apply {
            addLogInterceptor(this)
            addApiInterceptor(this)
        }.build()
        return Retrofit
            .Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    /**
     * Add a logger to the client so that we log every request
     */
    private fun addLogInterceptor(builder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addNetworkInterceptor(httpLoggingInterceptor)
    }

    /**
     * Intercept every request to the API and automatically add
     * the api key as query parameter
     */
    private fun addApiInterceptor(builder: OkHttpClient.Builder) {
        builder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", apiKey)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
    }

    override fun getCategory(query: String): LiveData<List<Category>> {
        val _data = MutableLiveData<List<Category>> ()

        val categoryList = service.getArticles(query).execute().body()?.genres ?: listOf()

        val categories = categoryList.map {
            it.toCategory()
        }

        _data.postValue(categories)
        return _data
    }

    companion object {
        private const val apiKey = "c7b2a3387b3ee8f50ec22998efdf727b"
        private const val apiUrl = "https://api.themoviedb.org/3/"
    }
}
