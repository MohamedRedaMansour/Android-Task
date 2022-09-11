package com.mansour.taskassignment.api

import com.mansour.taskassignment.models.responses.DataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET()
    suspend fun getData(@Url dynamicUrl: String): Response<DataResponse>

}