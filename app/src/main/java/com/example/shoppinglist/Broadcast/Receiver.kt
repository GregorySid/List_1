package com.example.shoppinglist.Broadcast

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.shoppinglist.MainActivity
import com.example.shoppinglist.R

private const val Chan_id = "todo notify"
const val Ext_id = "exr_id"
const val Ext_name = "exr_name"

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notify = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED){
                return
            }
            if (notify.getNotificationChannel(Chan_id) == null) {
                val channel = NotificationChannel(
                    Chan_id,
                    context.getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_HIGH)
                notify.createNotificationChannel(channel)
            }
        }

        val conInt = PendingIntent.getActivity(
            context,
            0, Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Chan_id)
            .setContentTitle(intent.getStringExtra(Ext_name))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentIntent(conInt)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notify.notify(intent.getIntExtra(Ext_id, 0), notification)
    }
}