package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.FormPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.HistoriPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.Koperasiku;
import tukangdagang.id.co.tukangdagang_koperasi.MainActivity;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.WebPengajuanPinjaman;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelBatal;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelProsesPinjaman;

import static com.facebook.FacebookSdk.getApplicationContext;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.FlistPinjaman;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_AccessToken;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.proses;

public class AdapterProsesPinjaman extends RecyclerView.Adapter<AdapterProsesPinjaman.MyViewHolder> {

    private Context mContext ;
    private Activity activity ;
    private List<ModelProsesPinjaman> mData ;
    ArrayList<ModelProsesPinjaman> arrayList;
    private boolean loggedIn = false;

    public AdapterProsesPinjaman(Context mContext, List<ModelProsesPinjaman> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelProsesPinjaman>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterProsesPinjaman.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_proses_pinjaman,parent,false);
        return new AdapterProsesPinjaman.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterProsesPinjaman.MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mData.get(position).getId_pengajuan().equals("Akun")){
//                    Intent intent = new Intent(mContext, PengaturanInformasiDiri.class);
//                    mContext.startActivity(intent);
//
//                } if (mData.get(position).getNama_koperasi().equals("Rekening")){
//                    //start NewActivity with title for actionbar and text for textview
//                    Intent intent = new Intent(mContext, PengaturanRekening.class);
//                    mContext.startActivity(intent);
//                }
            }
        });

        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistoriPinjaman.class);
//                intent.putExtra("detail", mData.get(position).getUrl_rincian_pinjaman());
                intent.putExtra("id_anggota", mData.get(position).getId_pengajuan());
                intent.putExtra("nama_koperasi", mData.get(position).getNama_koperasi());
                intent.putExtra("id_koperasi", mData.get(position).getId_koperasi());
                intent.putExtra("lat", mData.get(position).getLat_koperasi());
                intent.putExtra("lng", mData.get(position).getLng_koperasi());
                intent.putExtra("notelp", mData.get(position).getNo_telepon());

//                intent.putExtra("contentTv", "This is Battery detail...");
                mContext.startActivity(intent);
            }
        });
        holder.nama_koperasi.setText(mData.get(position).getNama_koperasi());
        holder.tgl_diajukan.setText(mData.get(position).getTanggal_diajukan());
        holder.totalpinjam.setText("Rp."+mData.get(position).getTotal_pinjaman());
        holder.status.setText(mData.get(position).getFlag_nama());
        Typeface customfont=Typeface.createFromAsset(mContext.getAssets(),"fonts/DroidSans.ttf");
        holder.tgl_diajukan.setTypeface(customfont);

        holder.LdetailKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailKoprasi.class);
                intent.putExtra("idkoperasi", mData.get(position).getId_koperasi());
                intent.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                intent.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())
                .into(holder.logo);
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
//                                intent.putExtra("123",mData.get(position).getId_pengajuan());
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

        holder.btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater Inflater = LayoutInflater.from(mContext);
                View mView = Inflater.inflate(R.layout.dialog_batal, null);
                Button batal = (Button) mView.findViewById(R.id.btn_batal);
                Button konfirmasi = (Button) mView.findViewById(R.id.btn_kirim);
                final RecyclerView myrv = mView.findViewById(R.id.listData);
                final String url_batal = Config.getSharedPreferences(mContext)+Config.FBatalPeminjaman;
                String[] id1, alasan1;
                final List<ModelBatal> lstBatalAnggota = new ArrayList<>();
                String url = Config.getSharedPreferences(mContext)+FlistPinjaman+0+proses;


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                final SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(n_info_alasan, "");
                editor.commit();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url+0
                        ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);
                                    final JSONArray alasanArray = obj.getJSONArray("alasan");
//                            Log.d("resul",response);
                                    // stop animating Shimmer and hide the layout
                                    int totaldata = alasanArray.length();
                                    for (int i = 0; i <totaldata; i++) {
                                        JSONObject alasanobject = alasanArray.getJSONObject(i);

                                        ModelBatal modelBatal = new ModelBatal(
                                                alasanobject.getString("id"),
                                                alasanobject.getString("alasan")
                                        );

                                        lstBatalAnggota.add(modelBatal);
                                    }
                                    AdapterBatal myAdapter;

                                    myAdapter = new AdapterBatal(mContext,lstBatalAnggota);
//                            myrv.setLayoutManager(manager);

                                    final LinearLayoutManager llm = new LinearLayoutManager(mContext);
                                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                                    myrv.setLayoutManager(llm);
                                    myrv.addItemDecoration(new DividerItemDecoration(mContext,1));

                                    //add ItemDecoration
//                            myrv.addItemDecoration(new DividerItemDecoration(getActivity(),1));
                                    myrv.setAdapter(myAdapter);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext,"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                            }
                        }){
                    /** Passing some request headers* */
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString(n_AccessToken, "");
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer "+token);
                        headers.put("lat", Config.getLatNow(getApplicationContext(),activity));
                        headers.put("long", Config.getLongNow(getApplicationContext(),activity));
                        return headers;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(stringRequest);

                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                konfirmasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String info_alasan = sharedPreferences.getString(n_info_alasan, "");
                        if(!info_alasan.equals("")){
//                            Toast.makeText(mContext,info_alasan+" "+mData.get(position).getId_anggota(),Toast.LENGTH_SHORT).show();
                            //Creating a string request

                            final ProgressDialog progressDialog = new ProgressDialog(mContext);
                            progressDialog.setMessage("mohon tunggu sebentar ya");
                            progressDialog.setCancelable(false);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            RequestQueue queue = Volley.newRequestQueue(mContext);
                            StringRequest request = new StringRequest(Request.Method.POST, url_batal,
                                    new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jObj = new JSONObject(response);
                                                Toast.makeText(mContext,jObj.getString("message"),Toast.LENGTH_LONG).show();
                                                mData.remove(position);
                                                notifyItemRemoved(position);

                                                if(position==0){
                                                    holder.nodata.setVisibility(View.VISIBLE);
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    intent.putExtra("lihatPinjaman", "1");
                                                    mContext.startActivity(intent);
                                                }else {
                                                    holder.nodata.setVisibility(View.GONE);
                                                }
                                                notifyDataSetChanged();
                                                dialog.dismiss();
                                                progressDialog.dismiss();

                                            } catch (JSONException e) {
                                                // JSON error
                                                e.printStackTrace();
                                                progressDialog.dismiss();
                                            }


                                        }
                                    },
                                    new Response.ErrorListener()
                                    {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            NetworkResponse response = error.networkResponse;
                                            String errorMsg = "";
                                            if(response != null && response.data != null){
                                                String errorString = new String(response.data);
                                                Log.i("log error", errorString);
                                            }
                                        }
                                    }
                            ) {
                                /** Passing some request headers* */
                                @Override
                                public Map getHeaders() throws AuthFailureError {
                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                    String token = sharedPreferences.getString(n_AccessToken, "");
                                    HashMap headers = new HashMap();
                                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                                    headers.put("Accept", "application/json");
                                    headers.put("Authorization", "Bearer "+token);
                                    headers.put("lat", Config.getLatNow(getApplicationContext(),activity));
                                    headers.put("long", Config.getLongNow(getApplicationContext(),activity));
                                    return headers;
                                }
                                @Override
                                protected Map<String, String> getParams()
                                {

                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("id_peminjaman", mData.get(position).getId_pengajuan());
                                    params.put("alasan", info_alasan);
//                        Log.i("sending ", params.toString());

                                    return params;
                                }

                            };


                            // Add the realibility on the connection.
                            request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

                            // Start the request immediately
                            queue.add(request);

                        }else{
                            Toast.makeText(mContext,"Anda belum memilih opsi pembatalan",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.getWindow().setLayout(600, 1000); //Controlling width and height.
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
//                Toast.makeText(mContext,"test",Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nama_koperasi, tgl_diajukan,totalpinjam,status;
        ImageView logo;
        Button btn_batal;
        RelativeLayout klik;
        LinearLayout nodata;
        LinearLayout LdetailKop;


        public MyViewHolder(View itemView) {
            super(itemView);


            nama_koperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            tgl_diajukan = (TextView) itemView.findViewById(R.id.tgl_diajukan) ;
            logo = (ImageView) itemView.findViewById(R.id.logo) ;
//            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            totalpinjam = (TextView) itemView.findViewById(R.id.tot_pinjam);
            status = (TextView) itemView.findViewById(R.id.status);
            btn_batal = (Button) itemView.findViewById(R.id.btn_batal);
            klik = (RelativeLayout) itemView.findViewById(R.id.klik);
            nodata = (LinearLayout) itemView.findViewById(R.id.nodata);
            LdetailKop = (LinearLayout) itemView.findViewById(R.id.LdetailKop);


        }
    }

}
