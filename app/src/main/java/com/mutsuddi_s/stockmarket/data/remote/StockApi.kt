package com.mutsuddi_s.stockmarket.data.remote

import com.mutsuddi_s.stockmarket.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    /**
     * The ResponseBody class in Retrofit is used to represent the body
     * of an HTTP response.
     * It allows you to handle the raw data of the response body,
     * which could be in various formats such as text, binary, or other formats.
     *
     * When you make a network request using Retrofit,
     * you typically define a service interface with methods corresponding
     * to different API endpoints.
     * These methods may return a ResponseBody object when the structure
     * or type of the response body is not known in advance
     * or varies between different API calls.
     *
     * By using ResponseBody, you can read the raw bytes
     *
     *
     * of the response body and process them according to your needs.
     * This can be useful in situations where the response format is not standardized,
     * or when you need to handle different types of data dynamically.
     *
     * Overall, ResponseBody serves as an intermediary
     * for handling the body of HTTP responses,
     * allowing you to read and process the content in a flexible manner.
     * It's commonly used in Retrofit when dealing with
     * responses that may have variable or unknown formats
     * **/

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String=API_KEY
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=5min&datatype=csv")
    suspend fun getIntraDayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String= API_KEY
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String= API_KEY
    ): CompanyInfoDto

    companion object{
        const val API_KEY="DYVXGS8VRJ2QEO97"
        const val BASE_URL="https://www.alphavantage.co/"

    }
}