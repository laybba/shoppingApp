package com.example.shoppingapp.activity

import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import com.example.shoppingapp.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    private lateinit var binding:ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntroBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        }

    }
}