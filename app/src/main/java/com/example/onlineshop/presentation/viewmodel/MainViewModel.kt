package com.example.onlineshop.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.models.Products
import com.example.onlineshop.data.network.RetrofitInstance.api
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val TAG = "MainActivity"
class MainViewModel(
    /*private val mainRepository: MainRepository*/): ViewModel() {

    private val errorMessage = MutableLiveData<String>()
    private val responseMutableLiveData = MutableLiveData<Response<Products>>()
    val responseLiveData = responseMutableLiveData

    private var loadingMutableLiveData = MutableLiveData<Boolean>()
    var loadingLiveData = loadingMutableLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception Handled: ${throwable.localizedMessage}")
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = api.getProducts()
            //mainRepository.getProducts()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    responseLiveData.postValue(response)
                    loadingMutableLiveData.value = false
                }
            }
        }
    }

    private fun onError(message: String){
        errorMessage.value = message
        loadingMutableLiveData.value = false
    }
}