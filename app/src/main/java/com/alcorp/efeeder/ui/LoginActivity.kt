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
import com.alcorp.efeeder.databinding.ActivityLoginBinding
import com.alcorp.efeeder.utils.LoadingDialog
import com.alcorp.efeeder.utils.setTimeFormat
import com.alcorp.efeeder.viewmodel.MainViewModel
import com.alcorp.efeeder.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var pref: SharedPreferences
    private lateinit var prefEdit: SharedPreferences.Editor

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
        auth = Firebase.auth

        pref = getSharedPreferences("efeeder", MODE_PRIVATE)

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
                delay(1000)
            }
        }

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegis.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)
        binding.btnLoginTamu.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.btnRegis -> {
                val intent = Intent(this, RegisActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.btnReset -> {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.btnLogin -> {
                val loadingDialog = LoadingDialog(this)
                loadingDialog.showDialog()

                val username = binding.edtUsernameLogin.text.toString()
                val password = binding.edtPasswordLogin.text.toString()

                if (username == "" || password == "") {
                    loadingDialog.hideDialog()
                    Toast.makeText(this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                loadingDialog.hideDialog()
                                auth.currentUser
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this@LoginActivity, "Berhasil login", Toast.LENGTH_SHORT).show()
                            } else {
                                loadingDialog.hideDialog()
                                task.addOnFailureListener {
                                    Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }

            binding.btnLoginTamu -> {
                prefEdit = pref.edit()
                prefEdit.putString("tamu", "tamu")
                prefEdit.apply()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this@LoginActivity, "Berhasil login sebagai tamu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLogin() {
        val tamu = pref.getString("tamu", "")

        val currentUser = auth.currentUser
        if (tamu != null && tamu != "")  {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }  else if (currentUser != null) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}