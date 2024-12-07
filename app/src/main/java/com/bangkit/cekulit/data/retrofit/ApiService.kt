package com.bangkit.cekulit.data.retrofit

import com.bangkit.cekulit.data.response.DetailResponse
import com.bangkit.cekulit.data.response.LoginResponse
import com.bangkit.cekulit.data.response.MessageResponse
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.data.response.ProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): MessageResponse

    @FormUrlEncoded
    @POST("otp")
    suspend fun otp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): MessageResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): ProfileResponse

    @GET("data")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Query("q") query: String? = null
    ): List<ProductResponseItem>

    @GET("stories/{id}")
    suspend fun getDetailProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailResponse
}