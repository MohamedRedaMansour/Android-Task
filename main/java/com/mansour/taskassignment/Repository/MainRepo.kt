package com.mansour.taskassignment.Repository

import com.mansour.taskassignment.api.RetrofitInstance
import javax.inject.Inject

class MainRepository @Inject constructor(){

    suspend fun getData(url: String) = RetrofitInstance.api.getData(url)
}