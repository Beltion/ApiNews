package com.apinews.framework.articles

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.core.data.articles.ArticlesDataSource
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import com.core.domain.entities.articles.ArticleErrorResponse
import com.core.domain.entities.articles.ArticleResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.net.URL

class ArticlesApiDataSource(): ArticlesDataSource {

    private val TAG = ArticlesApiDataSource::class.simpleName
        private val s = "https://newsapi.org/v2/everything?apiKey=e7a4d3493ec84a1a9232789bf7a943cf&q=sports&pageSize=10&page=1"
    override suspend fun getArticles(
            page: Int,
            successCallback: SuccessCallback,
            failureCallback: FailureCallback
    ) {
        try {

            val common = Common.retrofitService

            common.getArticleResponse(page).enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                    if (response.isSuccessful) {
                        successCallback.onComplete(response.body())
                    } else {
                        //  Every time create new Gson instance - not good...
                        try {
                            val errorResponse: ArticleErrorResponse = Gson().fromJson(
                                    response.errorBody()!!.string(),
                                    ArticleErrorResponse::class.java
                            )
                            failureCallback.onFailure("$TAG onResponse-> ", errorResponse)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            failureCallback.onFailure("$TAG onResponse !response.isSuccessful-> ", e)
                        }


                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    failureCallback.onFailure("$TAG onFailure -> ", t)
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
            failureCallback.onFailure("$TAG getArticles -> ", e)
        }



    }

    interface ArticlesApi {
        @GET("everything?apiKey=e7a4d3493ec84a1a9232789bf7a943cf&q=sports&pageSize=10")
        fun getArticleResponse(@Query("page") page: Int): Call<ArticleResponse>
    }

    object RetrofitClient{
        private var retrofit: Retrofit? = null
        fun getRetrofitClient(baseUrl: String): Retrofit {
            if (retrofit == null){
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }
    }

    object Common {
        private val BASE_URL = "https://newsapi.org/v2/"
        val retrofitService: ArticlesApi
            get() = RetrofitClient.getRetrofitClient(BASE_URL).create(ArticlesApi::class.java)


    }
}