 package tukangdagang.id.co.tukangdagang_koperasi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.login.LoginManager;
import com.freshchat.consumer.sdk.Freshchat;
import com.google.gson.JsonArray;
import com.guna.ocrlibrary.OCRCapture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static android.view.View.VISIBLE;
import static com.guna.ocrlibrary.OcrCaptureActivity.TextBlockObject;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance2;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_imagePreferance3;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_status_upload;

public class LengkapiProfilGambar extends AppCompatActivity {
    Button btnlanjut,btnsimpan;
    ImageView upload1,upload2,upload3,gambar1,gambar2,gambar3,edit1,edit2,edit3;
    Bitmap bitmap;
    CoordinatorLayout coordinatorLayout;
    TextView lewati;
    private TextView textView;
    String idUser ="";
    ProgressBar progressBar;
    public static final int CODE_GALLERY_REQUEST = 999;
    public static final int CODE_CAMERA_REQUEST = 998;
    int nn = 0;
    String nilai_gambar1="photo",nilai_gambar2="photo",nilai_gambar3="photo";
    private String url_simpan = Config.URL+Config.FsimpanProfilGambar;
    private String url_profile = Config.URL+Config.FgetProfil;
    android.support.v7.widget.GridLayout  maingrid;
    AppBarLayout appbar;
    View thumbnail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lengkapi_profil_gambar);
        url_simpan = Config.getSharedPreferences(this)+Config.FsimpanProfilGambar;
        url_profile = Config.getSharedPreferences(this)+Config.FgetProfil;
        btnlanjut = (Button) findViewById(R.id.btnlanjut);
//        textView = findViewById(R.id.textView);
        upload1 = (ImageView) findViewById(R.id.upload1);
        upload2 = (ImageView) findViewById(R.id.upload2);
        upload3 = (ImageView) findViewById(R.id.upload3);
        gambar1 = (ImageView) findViewById(R.id.gambar1);
        gambar2 = (ImageView) findViewById(R.id.gambar2);
        gambar3 = (ImageView) findViewById(R.id.gambar3);
        edit1 = (ImageView) findViewById(R.id.edit1);
        edit2 = (ImageView) findViewById(R.id.edit2);
        edit3 = (ImageView) findViewById(R.id.edit3);
        lewati = (TextView) findViewById(R.id.lewati);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        maingrid =(android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        appbar = (AppBarLayout) findViewById(R.id.appBar);
        thumbnail = (View) findViewById(R.id.thumbnail);
        btnsimpan = (Button) findViewById(R.id.btnsimpan);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString(PROFILE_ID, "");

        Intent intent = getIntent();
        if (intent.hasExtra("pengaturan")) {
            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setTitle("Gambar");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            maingrid.setVisibility(View.GONE);
            appbar.setVisibility(View.GONE);
            thumbnail.setVisibility(View.GONE);
            btnlanjut.setVisibility(View.GONE);
            btnsimpan.setVisibility(View.VISIBLE);
//            textView.setVisibility(View.GONE);
            simpan();

        }

        upload1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(VISIBLE);
                openImage();
                nn =1;

            }
        });

        upload2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(VISIBLE);
                openImage();
                nn =2;
            }
        });
        upload3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(VISIBLE);
                openImage();
                nn =3;
            }
        });

        edit1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(VISIBLE);
                openImage();
                nn =1;

            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(VISIBLE);
                openImage();
                nn =2;

            }
        });
        edit3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(VISIBLE);
                openImage();
                nn =3;

            }
        });

        lewati.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        btnlanjut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                if(!nilai_gambar1.equals("photo") && !nilai_gambar2.equals("photo") && !nilai_gambar3.equals("photo")) {
                    Intent intent = new Intent(LengkapiProfilGambar.this,LengkapiProfilWali.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Toast.makeText(getApplicationContext(),"Data belum lengkap,Pastikan data di isi semua",Toast.LENGTH_LONG).show();
                }

            }
        });

        getData();
    }

    private void getData() {

        progressBar.setVisibility(VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_profile+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            JSONArray profileArray = obj.getJSONArray("profil");

                            JSONObject profileobject = profileArray.getJSONObject(0);

//                                String loginwith = profileobject.getString("loginwith");
                            String tes = profileobject.getString("file_ktp");



                            Glide.with(LengkapiProfilGambar.this)
                                    .asBitmap()
                                    .load(profileobject.getString("file_ktp"))
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            String image = imageToString(resource);
                                            nilai_gambar1 = image;
                                            SharedPreferences sharedPreferences = LengkapiProfilGambar.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(n_imagePreferance, image);
                                            editor.commit();

                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });

                            Glide.with(LengkapiProfilGambar.this)
                                    .asBitmap()
                                    .load(profileobject.getString("file_kk"))
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            String image = imageToString(resource);
                                            nilai_gambar2 = image;
                                            SharedPreferences sharedPreferences = LengkapiProfilGambar.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(n_imagePreferance2, image);
                                            editor.commit();

                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                            Glide.with(LengkapiProfilGambar.this)
                                    .asBitmap()
                                    .load(profileobject.getString("file_ktp_selfie"))
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            String image = imageToString(resource);
                                            nilai_gambar3 = image;
                                            SharedPreferences sharedPreferences = LengkapiProfilGambar.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(n_imagePreferance3, image);
                                            editor.commit();

                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });

                            Log.d("urlktp",tes);
                            Glide.with(LengkapiProfilGambar.this)
                                    .asBitmap()
                                    .load(profileobject.getString("file_ktp"))
                                    .into(gambar1);

                            Glide.with(LengkapiProfilGambar.this)
                                    .asBitmap()
                                    .load(profileobject.getString("file_kk"))
                                    .into(gambar2);

                            Glide.with(LengkapiProfilGambar.this)
                                    .asBitmap()
                                    .load(profileobject.getString("file_ktp_selfie"))
                                    .into(gambar3);



                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        final Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Coba lagi", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getData();
                                    }
                                });

                        snackbar.show();
                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(Config.n_AccessToken, "");
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+token);
                headers.put("lat", Config.getLatNow(getApplicationContext(),LengkapiProfilGambar.this));
                headers.put("long", Config.getLongNow(getApplicationContext(),LengkapiProfilGambar.this));
                return headers;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void simpan() {
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                String photo = sharedPreferences.getString(n_imagePreferance, "photo");
                String photo2 = sharedPreferences.getString(n_imagePreferance2, "photo");
                String photo3 = sharedPreferences.getString(n_imagePreferance3, "photo");
                if(nilai_gambar1.equals("photo") && nilai_gambar2.equals("photo") && nilai_gambar3.equals("photo")) {
                    Toast.makeText(getApplicationContext(),"Data belum lengkap,Pastikan data di isi semua",Toast.LENGTH_LONG).show();
                }else{

                    final ProgressDialog progressDialog = new ProgressDialog(LengkapiProfilGambar.this);
                    progressDialog.setMessage("mohon tunggu sebentar ya");
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    //post image to server
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_simpan,
                            new Response.Listener < String > () {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if(obj.getString("success").equals("3")){
                                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                            logout();
                                        }else {
                                            Log.d("sukseskuy", obj.getString("success"));
                                            String sukses = obj.getString("success");
                                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            if (sukses.equals("0")) {
                                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                            } else {
                                                onBackPressed();
                                            }
//                                    }else{
//                                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//                                        }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                    Toast.makeText(getApplicationContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
//                                    final Snackbar snackbar = Snackbar
//                                            .make(findViewById(android.R.id.content), getResources().getString(R.string.gagalload), Snackbar.LENGTH_INDEFINITE);
//                                    snackbar.show();
                                    Log.d("tee", error.toString());
                                    progressDialog.dismiss();
                                }
                            }) {
                        @Override
                        public Map getHeaders() throws AuthFailureError {
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            String token = sharedPreferences.getString(n_AccessToken, "");
                            HashMap headers = new HashMap();
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            headers.put("Accept", "application/json");
                            headers.put("Authorization", "Bearer "+token);
                            headers.put("lat", Config.getLatNow(getApplicationContext(),LengkapiProfilGambar.this));
                            headers.put("long", Config.getLongNow(getApplicationContext(),LengkapiProfilGambar.this));
                            return headers;
                        }
                        @Override
                        protected Map< String, String > getParams() throws AuthFailureError {
                            Map < String, String > params = new HashMap< >();
                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            String id_profile = sharedPreferences.getString(PROFILE_ID, "");
                            String photo = sharedPreferences.getString(n_imagePreferance, "photo");
                            String photo2 = sharedPreferences.getString(n_imagePreferance2, "photo");
                            String photo3 = sharedPreferences.getString(n_imagePreferance3, "photo");

                            //Adding parameters to request
                            params.put("user_id", id_profile);
                            params.put("file_ktp", nilai_gambar1);
                            params.put("file_kk", nilai_gambar2);
                            params.put("file_ktp_selfie", nilai_gambar3);


                            //returning parameter
                            return params;
                        }
                    };
                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(LengkapiProfilGambar.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(stringRequest);


                }
            }
        });
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void getPermission() {
// Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //TODO:
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void pickImage() {
        Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGallery, CODE_GALLERY_REQUEST);
//        progressBar.setVisibility(View.GONE);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openImage(){
//        progressBar.setVisibility(View.GONE);
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA},
                    CODE_CAMERA_REQUEST);
            return;
        }
        EasyImage.openCamera(LengkapiProfilGambar.this,CODE_CAMERA_REQUEST);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    EasyImage.openCamera(LengkapiProfilGambar.this,CODE_CAMERA_REQUEST);
                } else {
                    // Permission Denied
                    Toast.makeText(getApplicationContext(), "Akses kamera ditolak", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                SharedPreferences sharedPreferences = LengkapiProfilGambar.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                switch (type){
                    case CODE_CAMERA_REQUEST:
                        try {

                            InputStream inputStream = getContentResolver().openInputStream(Uri.fromFile(imageFile));
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            if(nn==1) {
                                Glide.with(LengkapiProfilGambar.this)
                                        .load(Uri.fromFile(imageFile).getPath())
                                        .into(gambar1);

                                nilai_gambar1 = imageToString(bitmap);
                                progressBar.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(n_imagePreferance, nilai_gambar1);
                                editor.commit();

                            }else if(nn==2){
                                nilai_gambar2 = imageToString(bitmap);
                                Glide.with(LengkapiProfilGambar.this)
                                        .load(Uri.fromFile(imageFile).getPath())
                                        .into(gambar2);
                                progressBar.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(n_imagePreferance2, nilai_gambar2);
                                editor.commit();

                            }
                            else if(nn==3){
                                Glide.with(LengkapiProfilGambar.this)
                                        .load(Uri.fromFile(imageFile).getPath())
                                        .into(gambar3);
                                nilai_gambar3 = imageToString(bitmap);
                                progressBar.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(n_imagePreferance3, nilai_gambar3);
                                editor.commit();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;

                }

            }
        });
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        Intent intent = getIntent();
        if (intent.hasExtra("pengaturan")) {
            theme.applyStyle(R.style.AppThemePrimary, true);
        }

        // you could also use a switch if you have many themes that could apply
        return theme;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.godown);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void logout(){
        Freshchat.resetUser(getApplicationContext());
        //Getting out sharedpreferences
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();
        //Puting the value false for loggedin
        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //Putting blank value to email
        editor.putString(Config.EMAIL_SHARED_PREF, "");
        editor.putString(Config.NAME_SHARED_PREF, "");
        editor.putString(Config.IMAGE_SHARED_PREF, "");
        editor.putString(Config.n_avatar, "");
        editor.putString(Config.n_info_nama_depan, "");
        editor.putString(Config.n_AccessToken, "");
        editor.putString(Config.PROFILE_ID, "");
        editor.putString(Config.n_info_nama_belakang, "");
        editor.putString(Config.n_info_jk, "");
        editor.putString(Config.n_info_pekerjaan, "");
        editor.putString(Config.n_info_idpekerjaan, "");
        editor.putString(Config.n_info_alamat, "");
        editor.putString(Config.n_info_rtrw, "");
        editor.putString(Config.n_info_kodepos, "");
        editor.putString(Config.n_info_provinsi, "");
        editor.putString(Config.n_info_kota, "");
        editor.putString(Config.n_info_kecamatan, "");
        editor.putString(Config.n_info_noktp, "");
        editor.putString(Config.n_info_nokk, "");
        editor.putString(Config.n_info_nohp, "");
        editor.putString(Config.n_info_notlp, "");
        editor.putString(Config.n_info_idpln, "");
        editor.putString(Config.n_info_alasan, "");
        editor.putString(Config.n_info_sortir, "");
        editor.putString(Config.n_info_sortir_pos, "");
        editor.putString(Config.n_tempatlahir, "");
        editor.putString(Config.n_tgllahir, "");
        editor.putString(Config.n_info_idkodepos, "");
        editor.putString(Config.n_avatar, "");
        editor.putString(Config.n_info_namawali, "");
        editor.putString(Config.n_info_hubungankerabat, "");
        editor.putString(Config.n_info_alamatwali, "");
        editor.putString(Config.n_info_hubunganwali, "");
        editor.putString(Config.n_info_provinsiwali, "");
        editor.putString(Config.n_info_kotawali, "");
        editor.putString(Config.n_info_kecamatanwali, "");
        editor.putString(Config.n_info_setGrade, "");

        //Saving the sharedpreferences
        editor.commit();

        LoginManager.getInstance().logOut();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finishAffinity();

    }
}
