package com.example.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.onlineshop.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retService: StoreService

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
        val view = binding.root
        setContentView(view)
    }

    private fun getRequest(){
        val responseLiveData: LiveData<Response<StoreProducts>> = liveData {
            //val response = retService.getAlbums()
            val response = retService.getProducts()
            emit(response)

        }

        responseLiveData.observe(this, Observer {
            val productsList: MutableListIterator<ProductsItem>? = it.body()?.listIterator()
            if(productsList != null){
                while(productsList.hasNext()){
                    val productsItem: ProductsItem = productsList.next()
                    val result = " " + "Product name: ${productsItem.title}" + "\n" +
                            " " + "Product price: ${productsItem.price}" + "\n\n\n"
                    binding.textView.append(result)
                }
            }
        })
    }
}