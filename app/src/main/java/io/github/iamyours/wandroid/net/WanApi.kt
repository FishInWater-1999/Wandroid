package io.github.iamyours.wandroid.net

import androidx.lifecycle.LiveData
import io.github.iamyours.wandroid.BuildConfig
import io.github.iamyours.wandroid.vo.ArticleVO
import io.github.iamyours.wandroid.vo.BannerVO
import io.github.iamyours.wandroid.vo.PageVO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WanApi {
    companion object {
        fun get(): WanApi {
            val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(loggingInterceptor)
            }
            return Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .client(clientBuilder.build())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WanApi::class.java)
        }
    }

    /**
     * 首页banner
     */
    @GET("banner/json")
    fun bannerList(): LiveData<ApiResponse<List<BannerVO>>>

    /**
     * 文章列表
     */
    @GET("article/list/{page}/json")
    fun articleList(
        @Path("page") page: Int
    ): LiveData<ApiResponse<PageVO<ArticleVO>>>

    /**
     * 知识体系下文章
     */
    @GET("article/list/{page}/json")
    fun articleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): LiveData<ApiResponse<PageVO<ArticleVO>>>
}