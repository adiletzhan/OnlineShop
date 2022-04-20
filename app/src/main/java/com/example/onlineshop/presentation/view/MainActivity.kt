package com.example.onlineshop.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.data.models.Products
import com.example.onlineshop.databinding.ActivityMainBinding
import com.example.onlineshop.presentation.ProductAdapter
import com.example.onlineshop.presentation.viewmodel.MainViewModel
import com.example.onlineshop.presentation.viewmodel.MainViewModelFactory

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)

        viewBinding()

        getRequest()

        viewModel.progressSuccessLiveData.observe(this){isSuccess ->
            binding.progressBar.isVisible = !isSuccess
            binding.rvProducts.isVisible = isSuccess

        }

        viewModel.responseLiveData.observe(this){response ->
            setupRecyclerView(products = response.body()!!)
            }



        binding.button.setOnClickListener {

            binding.rvProducts.isVisible = false
            binding.progressBar.isVisible = true

            getRequest()
        }
    }

    private fun viewBinding(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getRequest(){
        viewModel.loadData()
    }

    private fun setupRecyclerView(products: Products) = binding.rvProducts.apply {
        adapter = ProductAdapter(products, context)
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}