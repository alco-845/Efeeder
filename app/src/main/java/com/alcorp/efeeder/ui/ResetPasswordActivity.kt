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
import com.alcorp.efeeder.databinding.ActivityResetPasswordBinding
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

class ResetPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                mainViewModel.getHour().observe(this@ResetPasswordActivity) { hour ->
                    mainViewModel.getMinute().observe(this@ResetPasswordActivity) { minute ->
                        binding.tvJam.text = setTimeFormat(hour, minute)
                    }
                }
                delay(1000)
            }
        }

        binding.btnLogin.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.btnLogin -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.btnReset -> {
                val loadingDialog = LoadingDialog(this)
                loadingDialog.showDialog()

                val username = binding.edtUsernameReset.text.toString()

                if (username == "") {
                    loadingDialog.hideDialog()
                    Toast.makeText(this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    auth.sendPasswordResetEmail(username)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                loadingDialog.hideDialog()
                                Toast.makeText(this@ResetPasswordActivity, "Email berhasil dikirim, silahkan check email anda", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                loadingDialog.hideDialog()
                                task.addOnFailureListener {
                                    Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }
            }
        }
    }

    private fun checkLogin() {
        val currentUser = auth.currentUser
        if(currentUser != null) {
            val i = Intent(this@ResetPasswordActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}