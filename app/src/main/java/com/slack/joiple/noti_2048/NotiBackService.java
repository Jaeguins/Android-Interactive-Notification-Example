package com.slack.joiple.noti_2048;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class NotiBackService extends Service {
    private static boolean Working=false;
    Notification noti;
    NotificationCompat.Builder notiBuilder;
    RemoteViews.RemoteView baseView,expandedView;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Working=true;
        notiBuilder=new NotificationCompat.Builder(this);
        notiBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        notiBuilder.setContentTitle("Noti 2048");
        notiBuilder.setContentText("swipe down to open");

        notiBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        notiBuilder.setCustomContentView(baseView);
        notiBuilder.setCustomBigContentView(expandedView)

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy(){
        Working=false;
    }
    public static boolean isWorking(){
        return Working;
    }
}
