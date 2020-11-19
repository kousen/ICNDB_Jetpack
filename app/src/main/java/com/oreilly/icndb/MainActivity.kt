package com.oreilly.icndb

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oreilly.icndb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: JokeViewModel by viewModels { LiveDataVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(
                this, R.layout.activity_main
            )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}
