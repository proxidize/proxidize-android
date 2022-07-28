package com.legacy.android;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyApplication extends Application {

    public static Context mContext;
    public static String FILENAME = "config.ini";
    public static String LOGFILE = "connection.log";
    public static final String channel_1_id = "channel1";
    public static final String channel_2_id = "servicechannel";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        createnotficationchannel();
        copyToSD("config.ini");
    }

    private void createnotficationchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(channel_1_id, "channel 1"
                    , NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("give the user some important information about the connection");
            channel1.enableLights(true);
            channel1.enableVibration(true);
            NotificationChannel servicechannel = new NotificationChannel(channel_2_id, "channel 1"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            servicechannel.setDescription("this channel is for a foreground service to let the user aware " +
                    "when he is connected to the proxy and that the application in working background");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(servicechannel);
        }


    }


    private void copyToSD(String dbName) {
        InputStream in = null;
        FileOutputStream out = null;
        File file = new File(this.getFilesDir(), dbName);
        if (file.exists()) {
            file.delete();
        }
        //create a new file
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //the assest manager is for reading the file in raw form
        AssetManager assets = getAssets();

        try {
            in = assets.open(dbName);
            out = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {///close the input and output stream

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
