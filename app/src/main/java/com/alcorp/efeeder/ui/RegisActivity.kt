package com.alcorp.efeeder.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alcorp.efeeder.data.local.User
import com.alcorp.efeeder.data.local.UserDao
import com.alcorp.efeeder.data.local.UserRoomDatabase
import com.alcorp.efeeder.databinding.ActivityRegisBinding
import com.alcorp.efeeder.utils.setTimeFormat
import com.alcorp.efeeder.viewmodel.MainViewModel
import com.alcorp.efeeder.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegisActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisBinding
    private lateinit var database: UserRoomDatabase
    private lateinit var dao: UserDao

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupToolbar()
        init()
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

    private fun init() {
        database = UserRoomDatabase.getDatabase(applicationContext)
        dao = database.getNoteDao()

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
                mainViewModel.getHour().observe(this@RegisActivity) { hour ->
                    mainViewModel.getMinute().observe(this@RegisActivity) { minute ->
                        binding.tvJam.text = setTimeFormat(hour, minute)
                    }
                }
                delay(2500)
            }
        }

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegis.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.btnLogin -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.btnRegis -> {
                val username = binding.edtUsernameRegis.text.toString()
                val password = binding.edtPasswordRegis.text.toString()
                val konfPassword = binding.edtKonfPasswordRegis.text.toString()

                if (username == "" || password == "" || konfPassword == "") {
                    Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    if (password != konfPassword) {
                        Toast.makeText(this, "Password harus sama dengan konfirmasi password", Toast.LENGTH_SHORT).show()
                    } else {
                        dao.insert(User(username = username, password = password))
                        Toast.makeText(this@RegisActivity, "Berhasil menambah data", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}