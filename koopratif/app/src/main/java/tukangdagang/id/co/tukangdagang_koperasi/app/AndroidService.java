package tukangdagang.id.co.tukangdagang_koperasi.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.Notifikasi2;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.tesService;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.idNotification;

/**
 * Created by imam-laptop on 13/03/2016.
 */
public class AndroidService extends Service {
    private static final String TAG = "HaiService";
    private String isinotif,titlenotif;
    Bitmap bitmap;
    private final String CHANNEL_ID ="personal_notification";
    private final int NOTIFICATION_ID = 001;
    String channelId = "channel01";

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service telah dibuat", Toast.LENGTH_LONG).show();
        servicePusher();
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service di jalankan", Toast.LENGTH_LONG).show();

        //Membuat thread baru untuk servicenya
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Isikan logika untuk service yang diinginkan
                //Disini saya looping service 10x untuk mencontohkan notfikasi sebanyak 10 kali untuk 1000 milidetik


            }
        }).start();

        return Service.START_STICKY;
    }

    private void servicePusher() {
        //pusher start
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher(idNotification, options);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");

        Channel channel = pusher.subscribe(email);

        channel.bind("App\\Events\\UserMobileNotification", new SubscriptionEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                //System.out.println(data);
//                Toast.makeText(getBaseContext(),"jghgh",Toast.LENGTH_SHORT).show();
                Log.d("yuhu",data);
                try {

                    JSONObject obj = new JSONObject(data);

                    Log.d("Mp", obj.getString("message"));
                    isinotif =obj.getString("message");
                    titlenotif =obj.getString("title");
                    channelId = obj.getString("id");
                    bitmap = getBitmapFromURL(obj.getString("icon"));


                } catch (Throwable t) {
                    Log.d("Mp", "Could not parse malformed JSON: \"" + data + "\"");
                }

                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Intent intent = new Intent(getApplicationContext(), Notifikasi2.class);
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);


                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                int notificationId = Integer.parseInt(channelId);

                String namachanel = "pemberitahuan";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, namachanel, importance);
                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AndroidService.this, channelId)
                        .setSmallIcon(R.drawable.tr2)
                        .setLargeIcon(bitmap)
                        .setContentTitle(titlenotif)
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentIntent(pIntent)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(isinotif))
                        .setContentText(isinotif);




                TaskStackBuilder stackBuilder = TaskStackBuilder.create(AndroidService.this);
                stackBuilder.addNextIntent(intent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                mBuilder.setContentIntent(resultPendingIntent);

                notificationManager.notify(notificationId, mBuilder.build());


            }
        });

        Log.d("yuhu","123");
        pusher.connect();
//pusher end
    }

    private void createNotificationChannel2(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "personal Notifications2";
            String description = "Include all personal Notification2";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager =(NotificationManager) AndroidService.this.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        Toast.makeText(this, "Service di Destroy", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Service berhenti");
    }

}
