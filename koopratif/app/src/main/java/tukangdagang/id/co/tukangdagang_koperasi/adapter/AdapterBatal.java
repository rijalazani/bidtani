package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelBatal;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan;

public class AdapterBatal extends RecyclerView.Adapter<AdapterBatal.MyViewHolder> {

    private static Context mContext ;
    private List<ModelBatal> mData ;
    ArrayList<ModelBatal> arrayList;
    public static ArrayList<ModelBatal> editModelArrayList;
    private boolean loggedIn = false;
    int selectedPosition=-1;

    public AdapterBatal(Context mContext, List<ModelBatal> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelBatal>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterBatal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_batal,parent,false);
        return new AdapterBatal.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterBatal.MyViewHolder holder, final int position) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        if(selectedPosition==position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#e8e8e8"));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String Nalasan = mData.get(position).getAlasan();
            if (mData.get(position).getId_alasan().equals("4")){
                 Nalasan = "";
                holder.etlainnya.setVisibility(View.VISIBLE);
            }else if (!mData.get(position).getId_alasan().equals("4")){
                holder.etlainnya.setVisibility(View.GONE);
            }
            editor.putString(n_info_alasan, Nalasan);
            editor.commit();
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.etlainnya.setVisibility(View.GONE);

        }

//        holder.title.setText(mData.get(position).getId_alasan());
        holder.alasan.setText(mData.get(position).getAlasan());

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                notifyDataSetChanged();


            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView alasan;
        EditText etlainnya;


        public MyViewHolder(View itemView) {
            super(itemView);


            alasan = (TextView) itemView.findViewById(R.id.alasan) ;
            this.etlainnya = (EditText) itemView.findViewById(R.id.lainnya) ;

            etlainnya.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    editModelArrayList.get(getAdapterPosition()).setEditTextValue(etlainnya.getText().toString());
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String Nalasan = etlainnya.getText().toString();
                    editor.putString(n_info_alasan, Nalasan);
                    editor.commit();
                }
            });
//            desc = (TextView) itemView.findViewById(R.id.desc) ;


        }
    }

}
