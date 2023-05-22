package com.alcorp.efeeder.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alcorp.efeeder.R
import com.alcorp.efeeder.databinding.ActivityEfeedBinding
import com.alcorp.efeeder.utils.*
import com.alcorp.efeeder.viewmodel.EfeedViewModel
import com.alcorp.efeeder.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EfeedActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEfeedBinding
    private lateinit var loadingDialog: LoadingDialog
    private val efeedViewModel: EfeedViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    private var jam1: Int = 0
    private var jam2: Int = 0
    private var jam3: Int = 0

    private var menit1: Int = 0
    private var menit2: Int = 0
    private var menit3: Int = 0

    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEfeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupToolbar()
        setDate()
        setTime()
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

    private fun setupViewText() {
        efeedViewModel.getDataKapasitas().observe(this@EfeedActivity) {
            binding.pbPakan.progress = it.toInt()
            binding.tvPakan.text = "$it%"
        }

        efeedViewModel.getDataJam1().observe(this@EfeedActivity){ hour ->
            efeedViewModel.getDataMenit1().observe(this@EfeedActivity){ minute ->
                binding.edtWaktu1.setText("$hour:$minute")
            }
        }

        efeedViewModel.getDataJam2().observe(this@EfeedActivity){ hour ->
            efeedViewModel.getDataMenit2().observe(this@EfeedActivity){ minute ->
                binding.edtWaktu2.setText("$hour:$minute")
            }
        }

        efeedViewModel.getDataJam3().observe(this@EfeedActivity){ hour ->
            efeedViewModel.getDataMenit3().observe(this@EfeedActivity){ minute ->
                binding.edtWaktu3.setText("$hour:$minute")
            }
        }

        efeedViewModel.getDataBerat1().observe(this@EfeedActivity){ binding.edtBerat1.setText(it) }
        efeedViewModel.getDataBerat2().observe(this@EfeedActivity){ binding.edtBerat2.setText(it) }
        efeedViewModel.getDataBerat3().observe(this@EfeedActivity){ binding.edtBerat3.setText(it) }
    }

    private fun init() {
        supportActionBar?.title = getString(R.string.toolbar_efeeder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadingDialog = LoadingDialog(this)

        val calendar = Calendar.getInstance()
        hour = calendar[Calendar.HOUR_OF_DAY]
        minute = calendar[Calendar.MINUTE]

        loadingDialog.showDialog()
        if (checkNetwork(this)){
            lifecycleScope.launch {
                delay(1000)
                while(isActive) {
                    setupViewText()
                    loadingDialog.hideDialog()
                    delay(1000)
                }
            }
        } else {
            loadingDialog.hideDialog()
            Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
        }
        binding.edtWaktu1.setOnClickListener(this)
        binding.edtWaktu2.setOnClickListener(this)
        binding.edtWaktu3.setOnClickListener(this)
        binding.btnUbah.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view) {
            binding.edtWaktu1 -> showDialog(1)
            binding.edtWaktu2 -> showDialog(2)
            binding.edtWaktu3 -> showDialog(3)
            binding.btnUbah -> {
                loadingDialog.showDialog()
                if (checkNetwork(this)) {
                    val berat1 = binding.edtBerat1.text.toString()
                    val berat2 = binding.edtBerat2.text.toString()
                    val berat3 = binding.edtBerat3.text.toString()

                    loadingDialog.hideDialog()
                    if (berat1 == "" || berat2 == "" || berat3 == "") {
                        Toast.makeText(this, getString(R.string.toast_berat), Toast.LENGTH_SHORT).show()
                    } else {
                        if ((berat1.toInt() > 10000 || berat1.toInt() < 0) || (berat2.toInt() > 10000 || berat2.toInt() < 0) || (berat3.toInt() > 10000 || berat3.toInt() < 0)) {
                            Toast.makeText(this, getString(R.string.toast_warning_berat), Toast.LENGTH_SHORT).show()
                        } else {
                            efeedViewModel.setDataJam1(jam1)
                            efeedViewModel.setDataJam2(jam2)
                            efeedViewModel.setDataJam3(jam3)

                            efeedViewModel.setDataMenit1(menit1)
                            efeedViewModel.setDataMenit2(menit2)
                            efeedViewModel.setDataMenit3(menit3)

                            efeedViewModel.setDataBerat1(berat1.toInt())
                            efeedViewModel.setDataBerat2(berat2.toInt())
                            efeedViewModel.setDataBerat3(berat3.toInt())

                            Toast.makeText(this, getString(R.string.toast_berhasil), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    loadingDialog.hideDialog()
                    Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateDialog(id: Int): Dialog {
        return when (id) {
            1 -> {
                TimePickerDialog(this, timeDialog1, hour, minute, DateFormat.is24HourFormat(this))
            }
            2 -> {
                TimePickerDialog(this, timeDialog2, hour, minute, DateFormat.is24HourFormat(this))
            }
            else -> {
                TimePickerDialog(this, timeDialog3, hour, minute, DateFormat.is24HourFormat(this))
            }
        }
    }

    private val timeDialog1 = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        binding.edtWaktu1.setText(setTimeFormat(hour, minute))
        jam1 = hour.setHourFormat().toInt()
        menit1 = minute.setMinuteFormat().toInt()
    }

    private val timeDialog2 = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        binding.edtWaktu2.setText(setTimeFormat(hour, minute))
        jam2 = hour.setHourFormat().toInt()
        menit2 = minute.setMinuteFormat().toInt()
    }

    private val timeDialog3 = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        binding.edtWaktu3.setText(setTimeFormat(hour, minute))
        jam3 = hour.setHourFormat().toInt()
        menit3 = minute.setMinuteFormat().toInt()
    }

    private fun setDate() {
        val calendar = Calendar.getInstance()

        val getMonth = SimpleDateFormat("MMMM")
        val getDay = SimpleDateFormat("EEEE")
        val d = Date()

        val dayName = getDay.format(d)
        val month = getMonth.format(calendar.time)

        val year = calendar[Calendar.YEAR]
        val day = calendar[Calendar.DAY_OF_WEEK]

        binding.tvTanggal.text = "$dayName $day $month $year"
    }

    private fun setTime() {
        lifecycleScope.launch {
            while(true) {
                efeedViewModel.getHour().observe(this@EfeedActivity) { hour ->
                    efeedViewModel.getMinute().observe(this@EfeedActivity) { minute ->
                        binding.tvJam.text = setTimeFormat(hour, minute)
                    }
                }
                delay(1000)
            }
        }
    }
}