package com.alcorp.efeeder.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alcorp.efeeder.databinding.ActivityMainBinding
import com.alcorp.efeeder.utils.setTimeFormat
import com.alcorp.efeeder.viewmodel.MainViewModel
import com.alcorp.efeeder.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: SharedPreferences
    private lateinit var prefEdit: SharedPreferences.Editor
    private lateinit var username: String

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAction()
        init()
        checkLogin()
    }

    private fun setupToolbar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun checkLogin() {
        if (username == "") {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun init() {
        pref = getSharedPreferences("prefApp", MODE_PRIVATE)
        username = pref.getString("username", "").toString()

        val calendar = Calendar.getInstance()

        val getMonth = SimpleDateFormat("MMMM")
        val getDay = SimpleDateFormat("EEEE")
        val d = Date()

        val dayName = getDay.format(d)
        val month = getMonth.format(calendar.time)

        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_WEEK]

        binding.tvTanggal.text = "$dayName $day $month $year"

        lifecycleScope.launch {
            while(true) {
                mainViewModel.getHour().observe(this@MainActivity) { hour ->
                    mainViewModel.getMinute().observe(this@MainActivity) { minute ->
                        binding.tvJam.text = setTimeFormat(hour, minute)
                    }
                }
                delay(2500)
            }
        }
    }

    private fun setupAction() {
        binding.cvMonitor.setOnClickListener {
            startActivity(Intent(this, MonitoringActivity::class.java))
        }

        binding.cvFeed.setOnClickListener {
            startActivity(Intent(this, EfeedActivity::class.java))
        }

        binding.ivLogout.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        prefEdit = pref.edit()
        prefEdit.clear().apply()

        val i = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}