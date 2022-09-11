package com.mansour.taskassignment.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mansour.taskassignment.R
import com.mansour.taskassignment.Repository.MainRepository
import com.mansour.taskassignment.models.responses.DataResponse
import com.mansour.taskassignment.utils.Event
import com.mansour.taskassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val app: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(app) {


    val dataML: MutableLiveData<Event<Resource<DataResponse>>> = MutableLiveData()

    fun callDataService(url: String) = viewModelScope.launch {
        dataML.value = Event(Resource.Loading())
        try {
            val response = mainRepository.getData(url)
            dataML.value = Event(handleMainCategoryResponse(response))

        } catch (t: Throwable) {
            when (t) {
                is IOException -> dataML.value =
                    Event(Resource.FailNetwork(app.getString(R.string.label_network_fail)))
                else -> dataML.value =
                    Event(Resource.FailNetwork(app.getString(R.string.label_network_fail)))
            }
        }
    }

    private fun handleMainCategoryResponse(response: Response<DataResponse>):
            Resource<DataResponse> {
        if (response.isSuccessful) {
            if (response.body()?.userId!! >= 0) {
                response.body()?.let {
                    return Resource.Success(it)
                }
            } else {
                response.body()?.let {
                    return Resource.Error(it.message)
                }
            }
        }
        return Resource.Error(response.message())
    }

}
