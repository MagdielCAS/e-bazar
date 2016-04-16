package com.example.syllas.e_bazar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_vest_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemVestuario item = itensVest.get(position);
        holder.vImg.setImageResource(item.getImg());
        holder.vTipo.setText(item.getTipo());
        holder.vTamanho.setText("Tamanho: "+item.getTamanho());
        holder.vCor.setText("Cor: "+item.getCor());
        holder.vOng.setText("Ong: "+item.getOng());
        holder.vPreco.setText("Preco: "+item.getOng());
    }

    @Override
    public int getItemCount() {
        return itensVest.size();
    }

    public static interface OnDataSelected{
        public void onDataSelected(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView vTamanho;
        public TextView vCor;
        public TextView vTipo;
        public TextView vOng;
        public TextView vPreco;
        public ImageView vImg;

        public ViewHolder(View view) {
            super(view);

            vTamanho = (TextView) view.findViewById(R.id.tamanho);
            vCor = (TextView) view.findViewById(R.id.cor);
            vOng = (TextView) view.findViewById(R.id.ong);
            vTipo = (TextView) view.findViewById(R.id.tipo);
            vPreco = (TextView) view.findViewById(R.id.preco);
            vImg = (ImageView) view.findViewById(R.id.imagem);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDataSelected != null) {
                        onDataSelected.onDataSelected(v, getAdapterPosition());
                    }
                }
            });
        }
    }

}
