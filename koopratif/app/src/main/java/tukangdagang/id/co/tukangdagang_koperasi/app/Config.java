package tukangdagang.id.co.tukangdagang_koperasi.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.NumberFormat;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.MapsActivity;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class Config {
//    public static String BASEURL = "https://35utech.com/kooperatif-web/";
    public static String BASEURL = "https://35utech.com/";
    public static final String URL = BASEURL+"bidtani/index.php?r=bidtani/";

    public static String getSharedPreferences (Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString(n_AccessToken, "");

        String isi = sharedPreferences.getString(n_URL, "");

        String nilai="";
        if(!isi.equals("")){
            nilai = isi;
        }else{
            nilai = URL;
        }

        return nilai;
    }



    public static final String Fkirimulang = "kirimUlang";
    public static final String FImgKoperasi = "ImgKoperasi";
    public static final String Ftampilanggota = "tampilanggota";
    public static final String Fproduct = "product";
    public static final String Fkoperasi = "koperasi?offset=";
    public static final String Fnotif = "pemberitahuan_count";
    public static final String Fkegiatan = "list_acara";
    public static final String FlistAnggota = "list_koperasi_anggota";
    public static final String FlistUlasan = "list_review";
    public static final String FpinjamanCepat = "list_pinjaman_calon?offset=";
    public static final String FPengaturanRek = "list_rekeningku/";
    public static final String FListBank = "list_bank";
    public static final String Fubahrekening = "ubah_rekening";
    public static final String Fdeleterekening = "hapus_rekening";
    public static final String Frekeningbaru = "rekening_baru";
    public static final String FKoperasiku = "list_koperasiku?offset=";
    public static final String Fpemberitahuan = "pemberitahuan?offset=";
    public static final String Fsortir = "list_sortir_koperasi";
    public static final String FsortirPinjaman = "list_sortir_pinjaman";
    public static final String FIdKoperasi = "IdKoperasi";
    public static final String Fuploadktp = "uploadktp";
    public static final String Fupdateprofil = "updateProfil";
    public static final String Fajukanpinjaman = "pendaftaranPinjaman";
    public static final String Fsetrating = "setRating";
    public static final String Flistkerabat = "list_hubungan_waris";
    public static final String Flistkodepos = "list_pos_kode/";
    public static final String Flistpekerjaan = "list_pekerjaan";
    public static final String Flistgrade = "list_grade_koperasi";
    public static final String Flistkota = "list_kota_koperasi";
    public static final String Fliststatusnik = "list_status_koperasi";
    public static final String Fdaftarpinjaman = "daftarpinjaman";
    public static final String Fdaftarsimpanan = "daftarsimpanan";
    public static final String FGantipwd = "Gantipwd";
    public static final String Fslide = "slide";
    public static final String Floginwith = "loginwith";
    public static final String Flogin = "login";
    public static final String FgetProfile = "getProfile";
    public static final String FgetProfil = "get_profil?id=";
    public static final String Fdafatarkoperasi = "pendaftaranKoperasi";
    public static final String Fregister = "register";
    public static final String FupdateDaftar = "updateDaftar";
    public static final String FlistPengajuan = "list_pengajuan?offset=";
    public static final String FBatalPendaftaran = "batal_pendaftaran";
    public static final String FBatalPeminjaman = "batal_peminjaman";
    public static final String FlistPinjaman = "list_pinjaman?offset=";
    public static final String FsimpanProfilUmum = "profil/updateInfoPribadi";
    public static final String FsimpanProfilNomor = "profil/updateInfoNomor";
    public static final String FsimpanProfilGambar = "profil/updateInfoGambar";
    public static final String FsimpanAhliWaris = "profil/updateInfoWaris";
    public static final String FhihtoriAnggota = "pendaftaran/rincian?id=";
    public static final String FhistoriPinjaman = "pinjaman/rincian?id=";
    public static final String FrincianKoperasi = "rincian_koperasi?id=";
    public static final String FlistgambarKoperasi = "list_gambar_koperasi";
    public static final String FEstimasi = "estimasiAngsuran?";


    public static final String path = BASEURL+"public/upload/";
    public static final String URLIMGKOSONG = path+"empty-user-photo.png";
    public static final String logokoperasi = "logokoperasi/";
    public static final String Slide = "slide/";
    public static final String Gambarkoperasi = "gambarkoperasi/";




    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "hp";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_FAILURE = "failure";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String PROFILE_ID = "id_profile";
    public static final String info_cout_notif = "info_cout_notif";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String NAME_SHARED_PREF = "nama";
    public static final String SHARED_PREF_URL = "urlsharedPref";
    public static final String LOGINWITH_SHARED_PREF = "loginwith";
    public static final String IMAGE_SHARED_PREF = "gambar";
    public static final String n_info_nama_depan = "info_nama_depan";
    public static final String n_info_nama_belakang = "info_nama_belakang";
    public static final String n_info_jk = "info_jk";
    public static final String n_info_pekerjaan = "info_pekerjaan";
    public static final String n_info_idpekerjaan = "info_idpekerjaan";
    public static final String n_info_alamat = "info_alamat";
    public static final String n_info_rtrw = "info_rtrw";
    public static final String n_info_kodepos = "info_kodepos";
    public static final String n_info_provinsi = "info_provinsi";
    public static final String n_info_kota = "info_kota";
    public static final String n_info_kecamatan = "info_kecamatan";
    public static final String n_info_noktp = "info_noktp";
    public static final String n_info_nokk = "info_nokk";
    public static final String n_info_nohp = "info_nohp";
    public static final String n_info_notlp = "info_notlp";
    public static final String n_info_idpln = "info_idpln";
    public static final String n_imagePreferance = "imagePreferance";
    public static final String n_imagePreferance2 = "imagePreferance2";
    public static final String n_imagePreferance3 = "imagePreferance3";
    public static final String n_image = "image";
    public static final String n_image2 = "image2";
    public static final String n_image3 = "image3";
    public static final String n_info_status = "info_status";
    public static final String n_status_nomor = "status_nomor";
    public static final String n_status_upload = "status_upload";
    public static final String n_info_refferal = "n_info_refferal";
    public static final String n_info_alasan = "n_info_alasan";
    public static final String n_info_alasan_pinjam = "n_info_alasan_pinjam";
    public static final String n_info_sortir = "n_info_sortir";
    public static final String n_info_sortir_pos = "n_info_sortir_pos";
    public static final String n_tempatlahir = "n_tempatlahir";
    public static final String n_tgllahir = "n_tgllahir";
    public static final String n_info_idkodepos = "n_info_idkodepos";
    public static final String n_avatar = "avatar";
    public static final String n_info_namawali = "info_namawali";
    public static final String n_info_hubungankerabat = "info_hubungankerabat";
    public static final String n_info_idkerabat = "info_idkerabat";
    public static final String n_info_alamatwali = "info_alamatwali";
    public static final String n_info_hubunganwali = "info_hubunganwali";
    public static final String n_info_provinsiwali = "info_provinsiwali";
    public static final String n_info_kotawali = "info_kotawali";
    public static final String n_info_kecamatanwali = "info_kecamatanwali";
    public static final String n_info_idkodeposwali = "info_idkodeposwali";
    public static final String n_info_setGrade = "info_setGrade";
    public static final String n_info_setStatusnik = "info_setStatusnik";
    public static final String n_info_setMinpokok = "info_setMinpokok";
    public static final String n_info_setMaxpokok = "info_setMaxpokok";
    public static final String n_info_setMinwajib = "info_setMinwajib";
    public static final String n_info_setMaxwajib = "info_setMaxwajib";
    public static final String n_info_setMinbunga = "info_setMinbunga";
    public static final String n_info_setMaxbunga = "info_setMaxbunga";
    public static final String n_info_setMinwaktuproses = "info_setMinwaktuproses";
    public static final String n_info_setMaxwaktuproses = "info_setMaxwaktuproses";
    public static final String n_info_setMinpinjaman = "info_setMinpinjaman";
    public static final String n_info_setMaxpinjaman = "info_setMaxpinjaman";
    public static final String n_info_setBadanhukum = "info_setBadanhukum";
    public static final String n_info_setKota = "info_setsetKota";
    public static final String n_AccessToken = "n_AccessToken";
    public static final String n_URL = "n_URL";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String FIRST_SHARED_PREF = "first";
    public static final String FIRST_Profile = "firstprofile";
    public static final String FIRST_Pinjaman = "firstpinjaman";
    public static final String FIRST_Anggota = "firstanggota";

    public static final String proses = "&status=0";
    public static final String diterima = "&status=1";
    public static final String ditolak = "&status=2";
    public static final String bukan_anggota = "&status_anggota=2";
    public static final String sudah_anggota = "&status_anggota=1";
    public static final String idNotification = "1cee67867ec747c446b2";

    public static final Locale localeID = new Locale("in", "ID");
    public static final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public static String getLatNow(Context context , Activity activity) {
        double latitude = 0;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }

        }

        try{
            //some exception
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();
//        String provider = locationManager.getBestProvider(criteria, true);

//        Location lprovider = locationManager.getLastKnownLocation(provider);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
            }else {
                latitude = 0;
            }
        }catch(Exception e){
           latitude = 0;
            //Log.e("",e.getMessage());
        }

        return String.valueOf(latitude);
    }
    public static String getLongNow(Context context , Activity activity) {
        double longitude = 0;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

        try{
            //some exception
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);

            Location lprovider = locationManager.getLastKnownLocation(provider);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                longitude = location.getLongitude();
            }else{
                longitude = 0;
            }
        }catch(Exception e){

            //Log.e("",e.getMessage());
            longitude = 0;
        }

        return String.valueOf(longitude);
    }

    public static final String MERCHANT_BASE_CHECKOUT_URL = "https://35utech.com/midtrans/charge/index.php/";
//    public static final String MERCHANT_CLIENT_KEY = "SB-Mid-client-PODWSUaBjP75BW8G";
    public static final String MERCHANT_SERVER_KEY = "Mid-server-QuX-VY6bBsecgOSmHPdKQu-6";
//    public static final String MERCHANT_SERVER_KEY = "SB-Mid-server-GrKh3fCp7Cb7eSWzOjXsU-xW";
    public static final String MERCHANT_CLIENT_KEY = "Mid-client-oH7XF3n7Z405TJcV";
//key account zendesk
    public static final String ZENDESK_ACCOUNT_KEY = "zOq4CbU6ZaONuMQ4eT6NXZLX4YPepyN1";

}
