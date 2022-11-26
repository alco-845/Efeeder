package com.alcorp.efeeder.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alcorp.efeeder.data.local.UserDao
import com.alcorp.efeeder.data.local.UserRoomDatabase
import com.alcorp.efeeder.databinding.ActivityLoginBinding
import com.alcorp.efeeder.utils.setTimeFormat
import com.alcorp.efeeder.viewmodel.MainViewModel
import com.alcorp.efeeder.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: SharedPreferences
    private lateinit var prefEdit: SharedPreferences.Editor
    private lateinit var database: UserRoomDatabase
    private lateinit var dao: UserDao

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupToolbar()
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

    private fun init() {
        database = UserRoomDatabase.getDatabase(applicationContext)
        dao = database.getNoteDao()

        pref = getSharedPreferences("prefApp", MODE_PRIVATE)

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
                mainViewModel.getHour().observe(this@LoginActivity) { hour ->
                    mainViewModel.getMinute().observe(this@LoginActivity) { minute ->
                        binding.tvJam.text = setTimeFormat(hour, minute)
                    }
                }
                delay(2500)
            }
        }

        binding.btnRegis.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.btnRegis -> {
                val intent = Intent(this, RegisActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.btnLogin -> {
                val username = binding.edtUsernameLogin.text.toString()
                val password = binding.edtPasswordLogin.text.toString()

                if (username == "" || password == "") {
                    Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    val check = dao.getById(username, password)
                    if (check.isEmpty()) {
                        Toast.makeText(this@LoginActivity, "Data tidak ada", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        prefEdit = pref.edit()
                        prefEdit.putString("username", username)
                        prefEdit.apply()

                        Toast.makeText(this@LoginActivity, "Berhasil login", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkLogin() {
        val username = pref.getString("username", "")
        if (username != "") {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}