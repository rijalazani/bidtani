package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.model.modelAjukanPinjaman;

public class AjukanPinjamanAdapter extends RecyclerView.Adapter<AjukanPinjamanAdapter.MyViewHolder> {

    private Context mContext ;
    private List<modelAjukanPinjaman> mData ;
    ArrayList<modelAjukanPinjaman> arrayList;
    private boolean loggedIn = false;

    public AjukanPinjamanAdapter(Context mContext, List<modelAjukanPinjaman> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<modelAjukanPinjaman>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AjukanPinjamanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_ajukan_pinjaman,parent,false);
        return new AjukanPinjamanAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AjukanPinjamanAdapter.MyViewHolder holder, final int position) {


        holder.title.setText(mData.get(position).getTitle());
        holder.desc.setText(mData.get(position).getDesc());

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
//                    Intent i = new Intent(mContext, DaftarAnggota.class);
//                    mContext.startActivity(i);
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


        public MyViewHolder(View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title) ;
            desc = (TextView) itemView.findViewById(R.id.desc) ;


        }
    }

}
