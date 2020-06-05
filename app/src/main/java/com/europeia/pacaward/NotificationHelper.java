package com.europeia.pacaward;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {


    public static void displayNotification(Context context, String title, String body){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, MainActivity.CHANEL_ID)
                .setSmallIcon(R.drawable.ic_logo_transparent_small)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
