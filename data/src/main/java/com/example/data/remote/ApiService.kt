package com.example.data.remote

import com.example.domain.entity.auth.logIn.LogInResponse
import com.example.domain.entity.auth.signUp.SignUpResponse
import com.example.domain.entity.category.AllCategory.CategoryResponse
import com.example.domain.entity.category.productOfCategory.CategoryProductResponse
import com.example.domain.entity.customerPoints.PointsResponse
import com.example.domain.entity.feedback.AddFeedbackResponse
import com.example.domain.entity.notes.*
import com.example.domain.entity.products.AllProductResponse
import com.example.domain.entity.products.DeleteFromFavouriteResponse
import com.example.domain.entity.products.FavouriteProductsResponse
import com.example.domain.entity.products.InsertToFavouriteResponse
import com.example.domain.entity.receipt.AllReceiptResponse
import retrofit2.http.*

interface ApiService {
    //Category APIs
    @GET("categories")
    suspend fun getCategory(): CategoryResponse

    @GET("categoryProducts/{categoryId}")
    suspend fun getProductOfCategory(@Path("categoryId") categoryId: Int): CategoryProductResponse

    //Product APIs
    @GET("products/{customerId}")
    suspend fun getAllProducts(@Path("customerId") customerId: Int): AllProductResponse

    @FormUrlEncoded
    @POST("products/insertFavorite")
    suspend fun insertToFavourite(
        @Field("customer_id") customerId: Int,
        @Field("product_id") productId: Int
    ): InsertToFavouriteResponse

    @FormUrlEncoded
    @POST("products/deleteFavorite")
    suspend fun deleteFromFavourite(
        @Field("customer_id") customerId: Int,
        @Field("product_id") productId: Int
    ): DeleteFromFavouriteResponse

    @GET("products/favouriteProducts/{customerId}")
    suspend fun getFavouriteProducts(@Path("customerId") customerId: Int): FavouriteProductsResponse

    //Auth
    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(
        @Field("user_name") username: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber : String,
        @Field("password") password : String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun logIn(
        @Field("email") email: String,
        @Field("password") password : String
    ): LogInResponse

    @POST("logout")
    suspend fun logout()

    //Receipt
    @GET("customerReceipts/{customerId}")
    suspend fun getCustomerReceipt(@Path("customerId") customerId: Int) : AllReceiptResponse


    //feedback
    @FormUrlEncoded
    @POST("contacts/add")
    suspend fun addFeedback(
        @Field("customer_id") customerId: Int,
        @Field("message") message: String
    ): AddFeedbackResponse


    //notes
    @GET("notes/{customerId}")
    suspend fun getAllNotes(@Path("customerId") customerId: Int): AllNotesResponse

    @FormUrlEncoded
    @POST("notes/insert")
    suspend fun addNote(
        @Field("customer_id") customerId: Int,
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int,
    ): AddNoteResponse

    @GET("newRequest")
    suspend fun getRequest(): NewRequestResponse

    @FormUrlEncoded
    @POST("notes/deleteAll")
    suspend fun deleteAllNotes(
        @Field("customer_id") customerId: Int,
    ): DeleteAllNotesResponse

    @FormUrlEncoded
    @POST("notes/update")
    suspend fun updateNote(
        @Field("customer_id") customerId: Int,
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int,
    ): UpdateNoteResponse

    @FormUrlEncoded
    @POST("notes/delete")
    suspend fun deleteNote(
        @Field("customer_id") customerId: Int,
        @Field("product_id") productId: Int,
    ): DeleteNoteResponse


    //customer points
    @GET("points/{customerId}")
    suspend fun getPoints(@Path("customerId") customerId: Int): PointsResponse

}