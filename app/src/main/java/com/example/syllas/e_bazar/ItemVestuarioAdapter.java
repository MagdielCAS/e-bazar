package com.example.syllas.e_bazar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by magdi on 15/04/2016.
 */
public class ItemVestuarioAdapter extends RecyclerView.Adapter<ItemVestuarioAdapter.ViewHolder>{

    //Recycler view necessita de um adapter
    //Adapter é uma estrutura que recebe os dados que vão ser exibidos

    private List<ItemVestuario> itensVest;
    private Context context;
    private OnDataSelected onDataSelected;

    public ItemVestuarioAdapter(Context context, OnDataSelected onDataSelected, List<ItemVestuario> itensVest){
        this.context = context;
        this.onDataSelected = onDataSelected;
        this.itensVest = itensVest;
    }

    @Override
    public ItemVestuarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemVestuario item = itensVest.get(position);
        holder.textViewTitleCar.setText(item.getIdTipo()+"onde aparece saporra");
    }

    @Override
    public int getItemCount() {
        return itensVest.size();
    }

    public static interface OnDataSelected{
        public void onDataSelected(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTitleCar;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDataSelected != null) {
                        onDataSelected.onDataSelected(v, getAdapterPosition());
                    }
                }
            });

            textViewTitleCar = (TextView) view.findViewById(R.id.teste);
        }
    }

}
