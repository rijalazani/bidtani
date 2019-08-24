package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.facebook.login.LoginManager;
import com.freshchat.consumer.sdk.FaqOptions;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.freshchat.consumer.sdk.exception.MethodNotAllowedException;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
import tukangdagang.id.co.tukangdagang_koperasi.Gantipwd;
import tukangdagang.id.co.tukangdagang_koperasi.Login;
import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.Pengaturan;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;

import static android.view.View.VISIBLE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.LOGINWITH_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_avatar;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nama_depan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nohp;

public class Profile extends Fragment implements GoogleApiClient.OnConnectionFailedListener,SwipeRefreshLayout.OnRefreshListener{

    RelativeLayout btn_logout,btn_gantipwd,btn_pengaturan,btn_bantuan,btn_faq;
    TextView scnama,info_email;
    ImageView avatar;
    private GoogleApiClient googleApiClient;
    String idprofile = "";
    ImageView imLoading;
    boolean Nfirst = false;

    Context mContext;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
//    private ImageView toolbarTitle;
    private boolean loggedIn = false;
    private String url_profile = Config.URL+Config.FgetProfile;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //bind view
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_main);
//        toolbarTitle = (ImageView) rootView.findViewById(R.id.toolbar_title);
        //set toolbar
//        getActivity().setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        url_profile = Config.getSharedPreferences(rootView.getContext())+Config.FgetProfile;
        //menghilangkan titlebar bawaan
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        btn_logout = rootView.findViewById(R.id.btn_logout);
        btn_gantipwd = rootView.findViewById(R.id.btn_gantipwd);
        btn_pengaturan = rootView.findViewById(R.id.btn_pengaturan);
        btn_bantuan = rootView.findViewById(R.id.btn_help);
        btn_faq = rootView.findViewById(R.id.btn_faq);

        scnama= rootView.findViewById(R.id.scnama);
        info_email= rootView.findViewById(R.id.info_email);
        avatar= rootView.findViewById(R.id.avatar);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        imLoading = rootView.findViewById(R.id.loadingView);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(!loggedIn){
            //We will start the SimpananF Activity
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }else if(loggedIn){
            if(isAdded()) {

                //Fetching the boolean value form sharedpreferences
                Nfirst = sharedPreferences.getBoolean(Config.FIRST_Profile, false);
                if (!Nfirst) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                        }
                    }, 500);
                }

                FreshchatConfig freshchatConfig=new FreshchatConfig("b3c9d245-eead-44ec-829e-da34c890c5c2","17e6de28-a607-4cc7-9a7d-7f592bce2cd1");
                Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);

                getdata();
                gantipwd();
                pengaturan();
                bantuan();
                faq();
                logout();
            }

        }
        return rootView;
    }

    private void faq() {
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FaqOptions faqOptions = new FaqOptions()
                        .showFaqCategoriesAsGrid(false)
                        .showContactUsOnAppBar(true)
                        .showContactUsOnFaqScreens(true)
                        .showContactUsOnFaqNotHelpful(false);

                Freshchat.showFAQs(getActivity(), faqOptions);
            }
        });
    }


    private void getdata() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String namauser = sharedPreferences.getString(Config.namauser, "");
        final String nama = sharedPreferences.getString(n_info_nama_depan, "");
        final String Navatar = sharedPreferences.getString(n_avatar, "");
        final String hp = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
        final String idUser = sharedPreferences.getString(PROFILE_ID, "");
                                    Glide.with(getContext())
                                    .load(R.drawable.bidtani)
                                    .into(avatar);
        scnama.setText(namauser);
        info_email.setText(hp);



        //        imLoading.setBackgroundResource(R.drawable.animasi_loading);
//        AnimationDrawable frameAnimation = (AnimationDrawable) imLoading
//                .getBackground();
        //Menjalankan File Animasi
//        frameAnimation.start();
//        imLoading.setVisibility(VISIBLE);




    }
    private void gantipwd() {
        btn_gantipwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Gantipwd.class);
                intent.putExtra("idprofile", idprofile);
                startActivity(intent);
            }
        });
    }
    private void pengaturan() {
        btn_pengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Pengaturan.class);
                intent.putExtra("idprofile", idprofile);
                startActivity(intent);
            }
        });
    }
    private void bantuan() {
        btn_bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String nama = sharedPreferences.getString(n_info_nama_depan, "");
                String email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
                String hp = sharedPreferences.getString(n_info_nohp, "");

 //zendesk
//                ZopimChat.init(Config.ZENDESK_ACCOUNT_KEY);
//                // build and set visitor info
//                VisitorInfo visitorInfo = new VisitorInfo.Builder()
//                        .phoneNumber(hp)
//                        .email(email)
//                        .name(nama)
//                        .build();
//
//                // visitor info can be set at any point when that information becomes available
//                ZopimChat.setVisitorInfo(visitorInfo);
//
//                // set pre chat fields as mandatory
//                PreChatForm preChatForm = new PreChatForm.Builder()
//                        .name(PreChatForm.Field.REQUIRED)
//                        .email(PreChatForm.Field.REQUIRED)
//                        .phoneNumber(PreChatForm.Field.REQUIRED)
//                        .department(PreChatForm.Field.REQUIRED)
//                        .message(PreChatForm.Field.REQUIRED_EDITABLE)
//                        .build();
//
//                // build chat config
//                ZopimChat.SessionConfig config = new ZopimChat.SessionConfig().preChatForm(preChatForm).department("My memory");
//
//                // start chat activity with config
//                ZopimChatActivity.startActivity(getActivity(), config);
//
//                // Sample breadcrumb
//                ZopimChat.trackEvent("Started chat with pre-set visitor information");

//freshchat

                //init

                //Update user information
                FreshchatUser user = Freshchat.getInstance(getApplicationContext()).getUser();
                user.setFirstName(nama).setLastName("").setEmail(email).setPhone("+62", hp);
                try {
                    Freshchat.getInstance(getApplicationContext()).setUser(user);
                } catch (MethodNotAllowedException e) {
                    e.printStackTrace();
                }
                Freshchat.showConversations(getActivity());
            }
        });
    }

    public void logout() {
        Freshchat.resetUser(getApplicationContext());
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Apa anda ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

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
                                editor.commit();

                                LoginManager.getInstance().logOut();
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                startActivity(i);
                                getActivity().finish();

                                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        if (status.isSuccess()) {
                                            Intent i = new Intent(getApplicationContext(), Login.class);
                                            startActivity(i);
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"error session", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRefresh() {
        getdata();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        ((MainActivity) getActivity()).setActionBarTitle("Profile");
//
//    }

    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }





}
