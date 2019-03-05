package com.slack.joiple.noti_2048;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Display;
import android.widget.RemoteViews;

public class NotiBackService extends Service{
    private static boolean Working=false;
    Notification noti;
    NotificationCompat.Builder notiBuilder;
    RemoteViews collapsed,expanded;
    NotificationCompat.DecoratedCustomViewStyle style;
    NotificationManager mNotificationManager;

    Intent upIntent, rightIntent, leftIntent, downIntent, exitIntent;
    PendingIntent up,right,left,down,exit;

    int[][] field={
            {R.id.t00,R.id.t01,R.id.t02,R.id.t03},
            {R.id.t10,R.id.t11,R.id.t12,R.id.t13},
            {R.id.t20,R.id.t21,R.id.t22,R.id.t23},
            {R.id.t30,R.id.t31,R.id.t32,R.id.t33}
    };

    public final int UP=1,DOWN=2,RIGHT=3,LEFT=4,EXIT=5;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mNotificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("CompatChannel",
                    "Channel for Compatability",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        upIntent =new Intent(getApplicationContext(),NotiBackService.class);
        rightIntent =new Intent(getApplicationContext(),NotiBackService.class);
        leftIntent =new Intent(getApplicationContext(),NotiBackService.class);
        downIntent =new Intent(getApplicationContext(),NotiBackService.class);
        exitIntent =new Intent(getApplicationContext(),NotiBackService.class);

        upIntent.putExtra("flag",UP);
        rightIntent.putExtra("flag",RIGHT);
        leftIntent.putExtra("flag",LEFT);
        downIntent.putExtra("flag",DOWN);
        exitIntent.putExtra("flag",EXIT);

        up=PendingIntent.getService(getApplicationContext(),0,upIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        right=PendingIntent.getService(getApplicationContext(),1,rightIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        left=PendingIntent.getService(getApplicationContext(),2,leftIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        down=PendingIntent.getService(getApplicationContext(),3,downIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        exit=PendingIntent.getService(getApplicationContext(),4,exitIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        Working=true;
        notiBuilder=new NotificationCompat.Builder(this);
        notiBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        notiBuilder.setContentTitle("Noti 2048");
        notiBuilder.setContentText("swipe downIntent to open");
        notiBuilder.setChannelId("CompatChannel");

        collapsed=new RemoteViews(getPackageName(),R.layout.notification);
        expanded=new RemoteViews(getPackageName(),R.layout.notification_expanded);

        expanded.setOnClickPendingIntent(R.id.exitter,exit);
        expanded.setOnClickPendingIntent(R.id.upper,up);
        expanded.setOnClickPendingIntent(R.id.downer,down);
        expanded.setOnClickPendingIntent(R.id.righter,right);
        expanded.setOnClickPendingIntent(R.id.lefter,left);


        style=new NotificationCompat.DecoratedCustomViewStyle();
        notiBuilder.setCustomContentView(collapsed);
        notiBuilder.setOnlyAlertOnce(true);
        notiBuilder.setCustomBigContentView(expanded);
        style.setBuilder(notiBuilder);
        notiBuilder.setStyle(style);
        notiBuilder.setOngoing(true);

        SharedPreferences shPref=getSharedPreferences("gameSave", 0);
        SharedPreferences.Editor editor=shPref.edit();
        int coX=shPref.getInt("locX",2),coY=shPref.getInt("locY",2);
        int result=intent.getIntExtra("flag",-1);
        Log.d("EventHandler",result+"");
        switch(result){
            case UP:
                coY=coY-1<0?0:coY-1;
                editor.putInt("locY",coY);
                break;
            case DOWN:
                coY=coY+1>3?3:coY+1;
                editor.putInt("locY",coY);
                break;
            case LEFT:
                coX=coX-1<0?0:coX-1;
                editor.putInt("locX",coX);
                break;
            case RIGHT:
                coX=coX+1>3?3:coX+1;
                editor.putInt("locX",coX);
                break;
            case EXIT:
                mNotificationManager.cancel("notigame",0);
                break;
        }
        editor.commit();
        expanded.setTextViewText(field[coY][coX],"1");
        expanded.setInt(field[coY][coX],"setBackgroundColor",getColor(R.color.buttonGreen));
        if(result!=5)
        mNotificationManager.notify("notigame",0, notiBuilder.build());
        stopSelf();
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
