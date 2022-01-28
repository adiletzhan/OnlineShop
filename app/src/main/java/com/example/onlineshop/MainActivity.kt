package com.example.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.databinding.ActivityMainBinding
import retrofit2.Response


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retService: StoreService

 //   private lateinit var products: StoreProducts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding()

        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(StoreService::class.java)

        getRequest()

    }


    private fun viewBinding(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getRequest(){
        val responseLiveData: LiveData<Response<StoreProducts>> = liveData {
            val response = retService.getProducts()
            emit(response)
        }

        responseLiveData.observe(this, {

            if(it.isSuccessful && it.body() != null) {
                setupRecyclerView(it.body()!!)
            //products = it.body()!!
            } else {
                Log.e(TAG, "Response not successful")
            }
        }
        )
    }

    private fun setupRecyclerView(products: StoreProducts) = binding.rvProducts.apply {
        adapter = ProductAdapter(products)
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}