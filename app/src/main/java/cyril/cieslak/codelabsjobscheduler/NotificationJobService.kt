package cyril.cieslak.codelabsjobscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import android.R



class NotificationJobService : JobService() {

    lateinit var mNotifyManager : NotificationManager

    val PRIMARY_CHANNEL_ID = "primary_notification_channel"


    override fun onStartJob(p0: JobParameters?): Boolean {

        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val notificationPendingIntent = getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle("Job Service")
            .setContentText("Your Job ran to completion!")
            .setContentIntent(notificationPendingIntent)
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        mNotifyManager.notify(0, builder.build())
        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    private fun createNotificationChannel () {

        // Define notification manager object.
        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.

        if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            var notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.setLightColor(Color.RED)
            notificationChannel.enableVibration(true)
            notificationChannel.setDescription("Notification from Job Service")
            mNotifyManager.createNotificationChannel(notificationChannel)



        }
    }
}