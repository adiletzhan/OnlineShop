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

    private val responseMutableLiveData = MutableLiveData<Response<Products>>()
    val responseLiveData = responseMutableLiveData

    private var progressSuccessMutableLiveData = MutableLiveData<Boolean>()
    var progressSuccessLiveData = progressSuccessMutableLiveData

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {

            val response = try{
                //mainRepository.getProducts()
                api.getProducts()
        } catch (e: IOException){
            Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
                return@launch
        }

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    responseLiveData.postValue(response)
                }
            }
        }
    }
}