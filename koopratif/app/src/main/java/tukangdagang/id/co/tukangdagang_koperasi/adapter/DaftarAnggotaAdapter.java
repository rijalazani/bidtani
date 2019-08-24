package tukangdagang.id.co.tukangdagang_koperasi.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.DataNomor;
import tukangdagang.id.co.tukangdagang_koperasi.InformasiUmum;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.Refferal;
import tukangdagang.id.co.tukangdagang_koperasi.Uploadktp;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelDaftarAnggota;

public class DaftarAnggotaAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<ModelDaftarAnggota> modellist;
    ArrayList<ModelDaftarAnggota> arrayList;

    //constructor
    public DaftarAnggotaAdapter(Context context, List<ModelDaftarAnggota> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ModelDaftarAnggota>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv,mdescTv;
        ImageView mIconIv;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        DaftarAnggotaAdapter.ViewHolder holder;
        if (view==null){
            holder = new DaftarAnggotaAdapter.ViewHolder();
            view = inflater.inflate(R.layout.row_daftaranggota, null);

            holder.mTitleTv = view.findViewById(R.id.title_daftar_anggota);
            holder.mdescTv = view.findViewById(R.id.desc_daftar_anggota);
            holder.mIconIv = view.findViewById(R.id.icon_daftar_anggota);

            view.setTag(holder);

        }
        else {
            holder = (DaftarAnggotaAdapter.ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition).getTitle());
        holder.mdescTv.setText(modellist.get(postition).getDesc());
        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());
        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code later
                if (modellist.get(postition).getTitle().equals("Informasi Umum")){
                    Intent intent = new Intent(mContext, InformasiUmum.class);
                    mContext.startActivity(intent);

                } if (modellist.get(postition).getTitle().equals("Informasi Nomor")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, DataNomor.class);
                    mContext.startActivity(intent);
                }
                if (modellist.get(postition).getTitle().equals("Upload Gambar")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, Uploadktp.class);
                    mContext.startActivity(intent);
                }if (modellist.get(postition).getTitle().equals("Kode Referal(Tidak Wajib)")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, Refferal.class);
                    mContext.startActivity(intent);
                }if (modellist.get(postition).getTitle().equals("Informasi data Wali")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, DataNomor.class);
                    mContext.startActivity(intent);
                }

            }
        });


        return view;
    }

//    //filter
//    public void filter(String charText){
//        charText = charText.toLowerCase(Locale.getDefault());
//        modellist.clear();
//        if (charText.length()==0){
//            modellist.addAll(arrayList);
//        }
//        else {
//            for (ModelDaftarpinjaman model : arrayList){
//                if (model.getTitle().toLowerCase(Locale.getDefault())
//                        .contains(charText)){
//                    modellist.add(model);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}