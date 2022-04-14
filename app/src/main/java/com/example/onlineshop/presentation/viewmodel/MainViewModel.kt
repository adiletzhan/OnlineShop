package com.example.onlineshop.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.models.Products
import com.example.onlineshop.data.network.RetrofitInstance.api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val TAG = "MainActivity"
class MainViewModel(
    /*private val mainRepository: MainRepository*/): ViewModel() {

    private var responseMutableLiveData = MutableLiveData<Response<Products>>()
    var responseLiveData = responseMutableLiveData

    private var progressSuccessMutableLiveData = MutableLiveData<Boolean>()
    var progressSuccessLiveData = progressSuccessMutableLiveData

    fun loadData() {
        viewModelScope.launch {

            progressSuccessMutableLiveData.value = false

            responseLiveData.value = try{
                //mainRepository.getProducts()
                api.getProducts()
        } catch (e: IOException){
            Log.e(TAG, "IOException, you might not have internet connection")
                return@launch
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
                return@launch
        }

            progressSuccessMutableLiveData.value =
                responseMutableLiveData.value!!.isSuccessful &&
                        responseMutableLiveData.value != null
        }
    }
}