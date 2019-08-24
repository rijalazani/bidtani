package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.LocalDataHandler;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.UserAddress;
import com.midtrans.sdk.corekit.models.UserDetail;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.WebCaraBayarAnggota;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesAnggota;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.EMAIL_SHARED_PREF;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.PROFILE_ID;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alamat;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_kota;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nama_depan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_nohp;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_status;

public class AdapterDiterimaAnggota extends RecyclerView.Adapter<AdapterDiterimaAnggota.MyViewHolder> implements TransactionFinishedCallback {

    private Context mContext ;
    private List<ModelProsesAnggota> mData ;
    ArrayList<ModelProsesAnggota> arrayList;
    private boolean loggedIn = false;
    String Norder_id="";
    String Ntotalbayar="";
    String url="";

    public AdapterDiterimaAnggota(Context mContext, List<ModelProsesAnggota> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelProsesAnggota>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterDiterimaAnggota.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_diterima_anggota,parent,false);

        return new AdapterDiterimaAnggota.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterDiterimaAnggota.MyViewHolder holder, final int position) {



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mData.get(position).getId_anggota().equals("Akun")){
                    Intent intent = new Intent(mContext, PengaturanInformasiDiri.class);
                    mContext.startActivity(intent);

                } if (mData.get(position).getNama_koperasi().equals("Rekening")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, PengaturanRekening.class);
                    mContext.startActivity(intent);
                }
            }
        });
        holder.nama_koperasi.setText(mData.get(position).getNama_koperasi());
        holder.tgl_diterima.setText(mData.get(position).getTanggal_diterima());
        Typeface customfont=Typeface.createFromAsset(mContext.getAssets(),"fonts/DroidSans.ttf");
        holder.tgl_diterima.setTypeface(customfont);
        holder.status.setText(mData.get(position).getStatus());
        holder.btn_cara_bayar.setText(mData.get(position).getFlag_bayar_nama());


        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistoriAnggota.class);
                intent.putExtra("id_anggota", mData.get(position).getId_anggota());
                intent.putExtra("nama_koperasi", mData.get(position).getNama_koperasi());
                intent.putExtra("id_koperasi", mData.get(position).getId_koperasi());
                intent.putExtra("lat", mData.get(position).getLat_koperasi());
                intent.putExtra("lng", mData.get(position).getLng_koperasi());
                intent.putExtra("notelp", mData.get(position).getNo_telepon());
//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())
                .into(holder.logo);

        holder.LdetailKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailKoprasi.class);
                intent.putExtra("idkoperasi", mData.get(position).getId_koperasi());
                intent.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                intent.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                mContext.startActivity(intent);
            }
        });
//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                PopupMenu popup = new PopupMenu(mContext, holder.buttonViewOption);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.bottom_navigation_view);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.navigation_apps:
//                                //handle menu1 click
//                                Intent intent = new Intent(mContext, Koperasiku.class);
//                                intent.putExtra("123",mData.get(position).getId_anggota());
//                                mContext.startActivity(intent);
//                                break;
//                            case R.id.navigation_keranjang:
//                                //handle menu2 click
//                                break;
//                            case R.id.navigation_faforit:
//                                //handle menu3 click
//                                break;
//                            case R.id.navigation_profile:
//                                //handle menu3 click
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//            }
//        });
        initMidtransSdk();
        setThema();
        setUser();
        holder.btn_cara_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mData.get(position).getFlag_bayar_nama().equals("Pembayaran diterima")){
                    Intent intent = new Intent(mContext, WebCaraBayarAnggota.class);
                    intent.putExtra("carabayar", mData.get(position).getUrl_pembayaran());
                    mContext.startActivity(intent);
                }else {
//                    Intent intent = new Intent(mContext, Bayar.class);
//                    intent.putExtra("totalbayar", mData.get(position).getTotal_bayar());
//                    intent.putExtra("order_id", mData.get(position).getOrder_id());
//                    mContext.startActivity(intent);

//                    MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
//                    MidtransSDK.getInstance().startPaymentUiFlow(mContext);
                    Norder_id = mData.get(position).getOrder_id();
                    Ntotalbayar = mData.get(position).getTotal_bayar();
                    url = mData.get(position).getUrl_pembayaran();
                    getdata();
                }
            }
        });
    }

    private void getdata() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.midtrans.com/v2/"+Norder_id+"/status",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("serverresponse","https://api.sandbox.midtrans.com/v2/"+Norder_id+"/status");
                            if(obj.getString("status_code").equals("404")){
                                Log.d("serverresponse","data kosong");
                                MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
                                MidtransSDK.getInstance().startPaymentUiFlow(mContext);
                            }else{
                                Log.d("serverresponse","data ada");
                                Intent intent = new Intent(mContext, WebCaraBayarAnggota.class);
                                intent.putExtra("carabayar", url);
                                mContext.startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString(n_AccessToken, "");
                String credentials = Config.MERCHANT_SERVER_KEY + ":" + "";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Basic "+base64EncodedCredentials);
                return headers;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    private void initMidtransSdk() {
        String client_key = Config.MERCHANT_CLIENT_KEY;
        String base_url = Config.MERCHANT_BASE_CHECKOUT_URL;

        SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(mContext) // context is mandatory
                .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url) //set merchant url
                .enableLog(true) // enable sdk log
                .setColorTheme(new CustomColorTheme("#FFE51255", "#00A6FF", "#FFE51255")) // will replace theme on snap theme on MAP
                .buildSDK();
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                  Intent intent= new Intent(mContext,WebCaraBayarAnggota.class);
                    intent.putExtra("carabayar", url);
                  mContext.startActivity(intent);
                    break;
                case TransactionResult.STATUS_PENDING:
//                    Toast.makeText(mContext, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    Intent i= new Intent(mContext,WebCaraBayarAnggota.class);
                    i.putExtra("carabayar", url);
                    mContext.startActivity(i);
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(mContext, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(mContext, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(mContext, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private CustomerDetails initCustomerDetails() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_status = sharedPreferences.getString(n_info_status, null);

        String info_nama = sharedPreferences.getString(n_info_nama_depan, "");
        String info_email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
        String info_nohp = sharedPreferences.getString(n_info_nohp, "");

        //define customer detail (mandatory for coreflow)
        CustomerDetails mCustomerDetails = new CustomerDetails();
        mCustomerDetails.setPhone(info_nohp);
        mCustomerDetails.setFirstName(info_nama);
        mCustomerDetails.setEmail(info_email);
        return mCustomerDetails;
    }

    private void setThema(){
        String examplePrimary = "#00A6FF";
        String examplePrimaryDark = "#0D81BE";
        String exampleSecondary = "#FFBA5C";
        CustomColorTheme colorTheme = new CustomColorTheme(examplePrimary, examplePrimaryDark, exampleSecondary);

        // or
// Set Theme color into Initialized SDK
        MidtransSDK midtransSDK = MidtransSDK.getInstance();
        midtransSDK.setColorTheme(colorTheme);

    }

    private void setUser(){
        UserDetail userDetail = LocalDataHandler.readObject("user_details", UserDetail.class);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String info_status = sharedPreferences.getString(n_info_status, null);

        String info_nama = sharedPreferences.getString(n_info_nama_depan, "");
        String info_email = sharedPreferences.getString(EMAIL_SHARED_PREF, "");
        String info_nohp = sharedPreferences.getString(n_info_nohp, "");
        String info_id = sharedPreferences.getString(PROFILE_ID, "");
        String info_alamat = sharedPreferences.getString(n_info_alamat, null);
        String  info_kota = sharedPreferences.getString(n_info_kota, null);
        String idkodepos = sharedPreferences.getString("itemKodepos1", "1");
//        if (userDetail == null) {
        userDetail = new UserDetail();
        userDetail.setUserFullName(info_nama);
        userDetail.setEmail(info_email);
        userDetail.setPhoneNumber(info_nohp);
        // set user ID as identifier of saved card (can be anything as long as unique),
        // randomly generated by SDK if not supplied
        userDetail.setUserId(info_id);

        ArrayList<UserAddress> userAddresses = new ArrayList<>();
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(info_alamat);
        userAddress.setCity(info_kota);
        userAddress.setAddressType(com.midtrans.sdk.corekit.core.Constants.ADDRESS_TYPE_BOTH);
        userAddress.setZipcode(idkodepos);
        userAddress.setCountry("IDN");
        userAddresses.add(userAddress);
        userDetail.setUserAddresses(userAddresses);
        LocalDataHandler.saveObject("user_details", userDetail);
    }

    private TransactionRequest initTransactionRequest() {
        // Create new Transaction Request
        TransactionRequest transactionRequestNew = new
                TransactionRequest(Norder_id, Integer.valueOf(Ntotalbayar));

        //set customer details
        transactionRequestNew.setCustomerDetails(initCustomerDetails());


        // set item details
        ItemDetails itemDetails = new ItemDetails("1", Integer.valueOf(Ntotalbayar), 1, "Treking Kooperatif");

        // Add item details into item detail list.
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);


        // Create creditcard options for payment
        CreditCard creditCard = new CreditCard();

        creditCard.setSaveCard(false); // when using one/two click set to true and if normal set to  false

//        this methode deprecated use setAuthentication instead
//        creditCard.setSecure(true); // when using one click must be true, for normal and two click (optional)

        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_3DS);

        // noted !! : channel migs is needed if bank type is BCA, BRI or MyBank
//        creditCard.setChannel(CreditCard.MIGS); //set channel migs
        creditCard.setBank(BankType.BCA); //set spesific acquiring bank

        transactionRequestNew.setCreditCard(creditCard);

        return transactionRequestNew;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_koperasi, tgl_diterima,status;
        ImageView logo;
        Button btn_cara_bayar;
        RelativeLayout klik;
        LinearLayout LdetailKop;


        public MyViewHolder(View itemView) {
            super(itemView);


            nama_koperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            status = (TextView) itemView.findViewById(R.id.status) ;
            tgl_diterima = (TextView) itemView.findViewById(R.id.tgl_diterima) ;
            logo = (ImageView) itemView.findViewById(R.id.logo) ;
//            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            btn_cara_bayar = (Button) itemView.findViewById(R.id.btn_cara_bayar);
            klik = (RelativeLayout) itemView.findViewById(R.id.klik) ;
            LdetailKop = (LinearLayout) itemView.findViewById(R.id.Ldetail) ;


        }
    }

}
