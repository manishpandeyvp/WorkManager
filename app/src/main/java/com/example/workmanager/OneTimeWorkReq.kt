package com.example.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeWorkReq(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {

    private var counter: Int = 0
    private val channelId = "channel_id"
    private val name = "Notification_channel"

    override fun doWork(): Result {
        setForegroundAsync(createForeGroundInfo())
        while (true){
            Log.d("workManager_counter: ", counter.toString())
            counter += 1
            for(i in 1..100000){}
        }
        return Result.success()
    }

    private fun createForeGroundInfo(): ForegroundInfo {
        val notificationId = 1
        return ForegroundInfo(notificationId, createNotification())
    }

    private fun createNotification(): Notification {
        val intent =
            WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Notif Data")
            .setTicker("Notif Data Ticker")
            .setSmallIcon(R.drawable.ic_baseline_gradient)
            .setOngoing(true)
            .addAction(R.drawable.ic_delete, "Cancel", intent)
        createNotificationChannel().also {
            builder.setChannelId(it.id)
        }
        return builder.build()
    }

    private fun createNotificationChannel(): NotificationChannel {
        val notificationManager = (applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        return NotificationChannel(
                channelId, name, NotificationManager.IMPORTANCE_LOW
        ).also { channel ->
            notificationManager.createNotificationChannel(channel)
        }
    }
}