package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.FormRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;


public class AdapterPengaturanRekening extends RecyclerView.Adapter<AdapterPengaturanRekening.MyViewHolder> {

    private Context mContext ;
    private List<ModelPengaturanRekening> mData ;
    ArrayList<ModelPengaturanRekening> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterPengaturanRekening(Context mContext, List<ModelPengaturanRekening> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelPengaturanRekening>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterPengaturanRekening.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_pengaturan_rekening,parent,false);
        return new AdapterPengaturanRekening.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPengaturanRekening.MyViewHolder holder, final int position) {

        holder.title.setText(mData.get(position).getNama_pemilik());
        holder.desc.setText(mData.get(position).getNama_bank()+" - "+mData.get(position).getNo_rekening());
        if (mData.get(position).getIs_utama().equals("1")){
            holder.ceklis.setVisibility(View.VISIBLE);
        }
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      Intent i = new Intent(mContext, FormRekening.class);
                      i.putExtra("modelrekening", new Gson().toJson(mData.get(position))); // converting model json into string type and sending it via intent
                      i.putExtra("judul","Ubah Rekening");
                      mContext.startActivity(i);
            }
        });

//        holder.btnPinjam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
//
//                //Fetching the boolean value form sharedpreferences
//                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
//                if(!loggedIn) {
//                    //We will start the SimpananF Activity
//                    Intent intent = new Intent(mContext, Login.class);
//                    mContext.startActivity(intent);
//                }else {
//                    Intent i = new Intent(mContext, FormPinjam.class);
//                    i.putExtra("modelpinjamancepat", new Gson().toJson(mData.get(position))); // converting model json into string type and sending it via intent
//                    mContext.startActivity(i);
////                    Toast.makeText(mContext,mData.get(position).toString(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc;
        ImageView ceklis;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title) ;
            desc = (TextView) itemView.findViewById(R.id.desc) ;
            ceklis = (ImageView) itemView.findViewById(R.id.ceklis) ;



        }
    }


}
