package com.alcorp.efeeder.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alcorp.efeeder.R
import com.alcorp.efeeder.databinding.ActivityMonitoringBinding
import com.alcorp.efeeder.utils.LoadingDialog
import com.alcorp.efeeder.utils.setTimeFormat
import com.alcorp.efeeder.viewmodel.MonitoringViewModel
import com.alcorp.efeeder.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MonitoringActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonitoringBinding
    private val monitoringViewModel: MonitoringViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringBinding.inflate(layoutInflater)
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
        monitoringViewModel.getDataSuhu().observe(this){ binding.tvSuhu.text = "$it ° C" }
        monitoringViewModel.getDataOxygen().observe(this){ binding.tvOxygen.text = "$it PPM" }
        monitoringViewModel.getDataPh().observe(this){ binding.tvPh.text = it }
        monitoringViewModel.getDataSwitch().observe(this){ binding.swSuhu.isChecked = it == 1 }
    }

    private fun init() {
        supportActionBar?.title = getString(R.string.toolbar_monitoring)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val loadingDialog = LoadingDialog(this)

        lifecycleScope.launch {
            loadingDialog.showDialog()
            delay(1000)

            withContext(Dispatchers.Main) {
                setupViewText()
                loadingDialog.hideDialog()
            }
        }

        binding.swSuhu.setOnCheckedChangeListener { _, isChecked ->
            val data = if (isChecked) 1 else 0
            monitoringViewModel.setDataSwitch(data)
        }
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
                monitoringViewModel.getHour().observe(this@MonitoringActivity) { hour ->
                    monitoringViewModel.getMinute().observe(this@MonitoringActivity) { minute ->
                        binding.tvJam.text = setTimeFormat(hour, minute)
                    }
                }
                delay(2500)
            }
        }
    }
}