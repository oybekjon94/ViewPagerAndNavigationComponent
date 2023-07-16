package com.example.viewpager

import android.graphics.pdf.PdfDocument.Page
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = listOf<CustomDateModel>(
            CustomDateModel(R.drawable.baseline_home_24,R.drawable.apple,"Olma"),
            CustomDateModel(R.drawable.baseline_view_list_24,R.drawable.banana,"Banan"),
            CustomDateModel(R.drawable.baseline_filter_list_24,R.drawable.peach,"Shaftoli"),
            CustomDateModel(R.drawable.baseline_info_24,R.drawable.watermelon,"Tarvuz"),
        )

        val customViewPagerAdapter = CustomViewPagerAdapter(this,data.map { it.image })
        binding.myViewPager.adapter = customViewPagerAdapter

        TabLayoutMediator(binding.tabLayout,binding.myViewPager){tab,position ->
            tab.text = data[position].title
            tab.setIcon(data[position].icon)
            val badge = tab.orCreateBadge
            badge.number = Random.nextInt(10_000)
        }.attach()

        binding.myViewPager.apply {
            clipToPadding = false
            clipChildren =false
            offscreenPageLimit = 3
        }


        binding.myViewPager.apply {
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer(object :ViewPager2.PageTransformer{
                    override fun transformPage(page: View, position: Float) {
                        page.apply {
                            scaleY = 0.85F + 0.15F*(1-(abs(position)))
                        }
                    }
                })
            })
        }
    }
}