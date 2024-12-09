package com.bangkit.cekulit.data.retrofit

import com.bangkit.cekulit.data.response.DetailProductResponse
import com.bangkit.cekulit.data.response.FileUploadResponse
import com.bangkit.cekulit.data.response.LoginResponse
import com.bangkit.cekulit.data.response.MessageResponse
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.data.response.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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
    @POST("forget-password")
    suspend fun forgetPassword(
        @Field("email") email: String,
    ): MessageResponse

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPassword(
        @Field("email") email: String,
        @Field("newPassword") newPassword: String
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

    @FormUrlEncoded
    @PUT("profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("age") age: String, //string dulu gan
        @Field("gender") gender: String, //string dulu gan
        ): MessageResponse

    @GET("data")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Query("q") query: String? = null
    ): List<ProductResponseItem>

    @GET("data/{desc}")
    suspend fun getDetailProduct(
        @Header("Authorization") token: String,
        @Path("desc") desc: String
    ): DetailProductResponse

    @Multipart
    @POST("/predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): FileUploadResponse
}