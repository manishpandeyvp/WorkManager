package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        btn_imd.setOnClickListener {
            scheduleOneTimeTask()
        }
    }

    private fun scheduleOneTimeTask() {
        val workManager: WorkManager = WorkManager.getInstance(applicationContext)
        workManager.enqueue(oneTimeWorkReq())
    }

    private fun oneTimeWorkReq(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<OneTimeWorkReq>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
    }


}