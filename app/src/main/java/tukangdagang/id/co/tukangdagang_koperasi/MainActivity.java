package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;
import tukangdagang.id.co.tukangdagang_koperasi.app.AndroidService;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Anggota;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Beranda;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Home;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Lelang;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Pinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.fragment.Profile;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.idNotification;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;

public class MainActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener  {

    TextView smsCountTxt;
    int pendingSMSCount = 10;
    private long backPrassedTime;
    BottomNavigationView bottomNavigationView;
    String channelId = "channel01";
    String pendaftaran_id,pinjaman_id,keterangan;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView tv_email,tv_nama;
    private boolean loggedIn = false;
    private String isinotif;
    private String titlenotif;
    //    private RelativeLayout tourkoperasibaru;
    Bitmap bitmap;
    TourGuide mGetStarted2;
    private final String CHANNEL_ID ="personal_notification";
    private final int NOTIFICATION_ID = 001;

    Context mContext;

    private GoogleApiClient googleApiClient;

    final Fragment fragment1 = new Beranda();
    final Fragment fragment2 = new Lelang();
    final Fragment fragment3 = new Profile();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
//    RelativeLayout tournotif,tourpinjamancepat,tourkoperasi,tourkoperasiku;

    public static String getMyString(){

        String item="12345";
//        SharedPreferences sharedPreferences = getgetSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString(n_AccessToken, "");
        return item;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        auoStart();


//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.logo_warna);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
//        bottomNavigationView.getMenu().findItem(R.id.navigation_lelang).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
//        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
//        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
//        fm.beginTransaction().add(R.id.container,fragment2, "2").commit();

        Intent intent = getIntent();

        if (intent.hasExtra("lihatPinjaman")) {
            fm.beginTransaction().add(R.id.container,fragment2, "2").commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_lelang);
        }else if (intent.hasExtra("lihatAnggota")) {
            fm.beginTransaction().add(R.id.container,fragment3, "3").commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_profil);
        }else {

            fm.beginTransaction().add(R.id.container, fragment1, "1").commit();

        }

//        Intent i = new Intent(MainActivity.this, AndroidService.class);
//        startService(i);
        servicePusher();

    }
    public void setAutStart(){
        // context is your Context


        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"));

            startActivity(intent);


        } else if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity");
                startActivity(intent);
            } catch (Exception e) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.oppo.safe",
                            "com.oppo.safe.permission.startup.StartupAppListActivity");
                    startActivity(intent);
                } catch (Exception ex) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName("com.coloros.safecenter",
                                "com.coloros.safecenter.startupapp.StartupAppListActivity");
                        startActivity(intent);
                    } catch (Exception exx) {

                    }
                }
            }
        }

    }
    public void auoStart(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Konfirmasi")
                .setMessage("Hai, tolong aktifkan Autostart  Kooperatif ya, agar notifikasi dapat diterima pada saat Kooperatif berjalan di background ")
                .setPositiveButton("Baik", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        acepted.setError(null);

                        setAutStart();
                    }
                })
                .setNegativeButton("Nanti Saja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
//                        daftar.setEnabled(true);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

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
                Log.d("yuhu", data);
                try {

                    JSONObject obj = new JSONObject(data);

                    Log.d("Mp", obj.toString());
                    isinotif = obj.getString("message");
                    titlenotif = obj.getString("title");
                    bitmap = getBitmapFromURL(obj.getString("icon"));
                    channelId = obj.getString("id");
                    pendaftaran_id = obj.getString("pendaftaran_id");
                    pinjaman_id = obj.getString("pinjaman_id");
                    keterangan = obj.getString("keterangan");


                } catch (Throwable t) {
                    Log.d("Mp", "Could not parse malformed JSON: \"" + data + "\"");
                }

                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if (keterangan.equals("pendaftaran")) {
                    Intent intent = new Intent(getApplicationContext(), HistoriAnggota.class);
                    intent.putExtra("id_anggota", pendaftaran_id);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);

                    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);


                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                    int notificationId = Integer.parseInt(channelId);
                    Log.d("idchannel", channelId);
                    String namachanel = "pemberitahuan";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel mChannel = new NotificationChannel(
                                channelId, namachanel, importance);
                        notificationManager.createNotificationChannel(mChannel);
                    }

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, channelId)
                            .setSmallIcon(R.drawable.tr2)
                            .setLargeIcon(bitmap)
                            .setContentTitle(titlenotif)
                            .setSound(soundUri)
                            .setAutoCancel(true)
                            .setContentIntent(pIntent)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(isinotif))
                            .setContentText(isinotif);


                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                    stackBuilder.addNextIntent(intent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    mBuilder.setContentIntent(resultPendingIntent);

                    notificationManager.notify(notificationId, mBuilder.build());


                }else if (keterangan.equals("pinjaman")) {
                    Intent intent = new Intent(getApplicationContext(), HistoriAnggota.class);
                    intent.putExtra("id_anggota", pinjaman_id);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);

                    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);


                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                    int notificationId = Integer.parseInt(channelId);
                    Log.d("idchannel", channelId);
                    String namachanel = "pemberitahuan";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel mChannel = new NotificationChannel(
                                channelId, namachanel, importance);
                        notificationManager.createNotificationChannel(mChannel);
                    }

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, channelId)
                            .setSmallIcon(R.drawable.tr2)
                            .setLargeIcon(bitmap)
                            .setContentTitle(titlenotif)
                            .setSound(soundUri)
                            .setAutoCancel(true)
                            .setContentIntent(pIntent)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(isinotif))
                            .setContentText(isinotif);


                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                    stackBuilder.addNextIntent(intent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    mBuilder.setContentIntent(resultPendingIntent);

                    notificationManager.notify(notificationId, mBuilder.build());


                }else{
                    Intent intent = new Intent(getApplicationContext(), Notifikasi2.class);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);

                    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);


                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                    int notificationId = Integer.parseInt(channelId);
                    Log.d("idchannel", channelId);
                    String namachanel = "pemberitahuan";
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel mChannel = new NotificationChannel(
                                channelId, namachanel, importance);
                        notificationManager.createNotificationChannel(mChannel);
                    }

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, channelId)
                            .setSmallIcon(R.drawable.tr2)
                            .setLargeIcon(bitmap)
                            .setContentTitle(titlenotif)
                            .setSound(soundUri)
                            .setAutoCancel(true)
                            .setContentIntent(pIntent)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(isinotif))
                            .setContentText(isinotif);


                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                    stackBuilder.addNextIntent(intent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    mBuilder.setContentIntent(resultPendingIntent);

                    notificationManager.notify(notificationId, mBuilder.build());
                }
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
            NotificationManager notificationManager =(NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
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

    //    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_apps:
//                    fm.beginTransaction().hide(active).show(fragment1).commit();
//                    active = fragment1;
//                    return true;
//
//                case R.id.navigation_pinjaman:
//                    fm.beginTransaction().hide(active).show(fragment2).commit();
//                    active = fragment2;
//                    return true;
//
//                case R.id.navigation_simpanan:
//                    fm.beginTransaction().hide(active).show(fragment3).commit();
//                    active = fragment3;
//                    return true;
//
//
//                case R.id.navigation_profile:
//                    fm.beginTransaction().hide(active).show(fragment4).commit();
//                    active = fragment4;
//                    return true;
//            }
//            return false;
//        }
//    };
    public void statuslogin(){

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            try {


                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                Fragment selectedFragment = new Beranda();
                switch (item.getItemId()) {
                    case R.id.navigation_apps:
                        if(!loggedIn) {
                            //We will start the SimpananF Activity
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
//                            CustomIntent.customType(MainActivity.this, "left-to-right");
                            overridePendingTransition(R.anim.goup, R.anim.nothing);
                        }else {
                            selectedFragment = new Beranda();
                        }
                        break;
                    case R.id.navigation_lelang:
                        if(!loggedIn) {
                            //We will start the SimpananF Activity
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
//                            CustomIntent.customType(MainActivity.this, "left-to-right");
                            overridePendingTransition(R.anim.goup, R.anim.nothing);
                        }else {
                            selectedFragment = new Lelang();
                        }
                        break;
                    case R.id.navigation_profil:
                        if(!loggedIn) {
                            //We will start the SimpananF Activity
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
//                            CustomIntent.customType(MainActivity.this, "left-to-right");
                            overridePendingTransition(R.anim.goup, R.anim.nothing);
                        }else {
                            selectedFragment = new Profile();
                        }
                        break;

                }
                if (selectedFragment != null) {
                    FragmentTransaction transaction =
                            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    transaction.replace(R.id.container, selectedFragment, selectedFragment.getTag());
                    transaction.commit();
                }

            }catch(Exception err){
                Toast.makeText(MainActivity.this, err.toString(), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

//            tv_nama.setText(account.getDisplayName());
//            tv_email.setText(account.getEmail());


        }

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.action_notifications);
//
//        View actionView = MenuItemCompat.getActionView(menuItem);
//        smsCountTxt = (TextView) actionView.findViewById(R.id.notification_badge);
//
//        setupBadge();
//
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.action_notifications: {
////                Toast.makeText(this,"ini notifikasi",Toast.LENGTH_SHORT).show();
//                Intent inten = new Intent(MainActivity.this,Notifikasi.class);
//                startActivity(inten);
//                return true;
//            }
//            case R.id.search: {
////                Toast.makeText(this,"ini notifikasi",Toast.LENGTH_SHORT).show();
//                Intent inten = new Intent(MainActivity.this,Cari.class);
//                startActivity(inten);
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setupBadge() {
//
//        if (smsCountTxt != null) {
//            if (pendingSMSCount == 0) {
//                if (smsCountTxt.getVisibility() != View.GONE) {
//                    smsCountTxt.setVisibility(View.GONE);
//                }
//            } else {
//                smsCountTxt.setText(String.valueOf(Math.min(pendingSMSCount, 99)));
//                if (smsCountTxt.getVisibility() != View.VISIBLE) {
//                    smsCountTxt.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        if (backPrassedTime + 2000 > System.currentTimeMillis()){

            System.exit(0);
            super.onBackPressed();
            finish();
            finishAffinity();
            return;
        }else{
            Toast.makeText(getBaseContext(),"Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show();
//            Toasty.info(getBaseContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT, true).show();
        }
        backPrassedTime = System.currentTimeMillis();

    }
//    public void setActionBarTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onResume() {
        super.onResume();
        //        Intent i = new Intent(MainActivity.this, AndroidService.class);
//        startService(i);
//        servicePusher();
    }
}
