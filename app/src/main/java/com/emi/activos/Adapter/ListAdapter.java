package com.emi.activos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emi.activos.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.cardview_adapter, null);
        return new ListAdapter.ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

    }

    public void setItems(List<ListElement> items) {mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ssid, password, seguridad;

        ViewHolder(View itemView) {
            super(itemView);

            ssid = itemView.findViewById(R.id.ssid);
            password = itemView.findViewById(R.id.password);
            seguridad = itemView.findViewById(R.id.cifrado);

        }

        void bindData(final ListElement item) {

            ssid.setText(item.getSsid());
            password.setText(item.getPasword());
            seguridad.setText(item.getSeguridad());

        }
    }
}
