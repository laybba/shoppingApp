package com.example.shoppingapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shoppingapp.Adapter.BrandAdapter
import com.example.shoppingapp.Adapter.PopularAdapter
import com.example.shoppingapp.Model.SliderModel
import com.example.shoppingapp.Adapter.SliderAdapter
import com.example.shoppingapp.ViewModel.MainViewModel
import com.example.shoppingapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private val viewModel=MainViewModel()
    private lateinit var  binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
         binding.cartBtn.setOnClickListener{ startActivity(Intent(this@MainActivity,CartActivity::class.java))}
    }



    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer{ items ->
            banners(items)
            binding.progressBarBanner.visibility=View.GONE
        })
        viewModel.loadBanners()
    }
    private fun banners(images:List<SliderModel>){
        binding.viewpagerSlider.adapter= SliderAdapter(images,binding.viewpagerSlider)
        binding.viewpagerSlider.post {
            binding.viewpagerSlider.setCurrentItem(0, false) // Explicitly set the first item
        }
        binding.viewpagerSlider.clipToPadding=false
        binding.viewpagerSlider.clipChildren=false
        binding.viewpagerSlider.offscreenPageLimit=3
        binding.viewpagerSlider.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer( 40))
        }

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
        if(images.size>1){
            binding.dotIndicator.visibility=View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewpagerSlider)
        }
    }

    private fun initBrand() {
        binding.progressBar2.visibility = View.VISIBLE

        viewModel.brands.observe(this, Observer {
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.viewBrand.adapter = BrandAdapter(it)
            binding.progressBar2.visibility = View.GONE
        })

        viewModel.loadBrand()
    }
    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE

        viewModel.popular.observe(this, Observer {
            binding.viewPopular.layoutManager = GridLayoutManager(this@MainActivity,2)
            binding.viewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })

        viewModel.loadPopular()
    }
}