package com.example.syllas.e_bazar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by magdi on 16/04/2016.
 */
public class ItemOngAdapter extends RecyclerView.Adapter<ItemOngAdapter.ViewHolder> {

    //Recycler view necessita de um adapter
    //Adapter é uma estrutura que recebe os dados que vão ser exibidos

    private List<ItemOng> itensOng;
    private Context context;
    private OnDataSelected onDataSelected;

    public ItemOngAdapter(Context context, OnDataSelected onDataSelected, List<ItemOng> itensOng){
        this.context = context;
        this.onDataSelected = onDataSelected;
        this.itensOng = itensOng;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_ong_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemOng item = itensOng.get(position);
        holder.vImg.setImageResource(R.drawable.ong_img_teste);
        holder.vNome.setText(item.getNome());
        holder.vCidade.setText(item.getCidade());
        holder.vEstado.setText(item.getUF());
        holder.vIntuito.setText(item.getIntuito());
        holder.vPreco.setText("R$ "+item.getValorArrecadado());
    }

    @Override
    public int getItemCount() {
        return itensOng.size();
    }

    public static interface OnDataSelected{
        public void onDataSelected(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView vNome;
        public TextView vIntuito;
        public TextView vCidade;
        public TextView vEstado;
        public TextView vPreco;
        public ImageView vImg;


        public ViewHolder(View view) {
            super(view);

            vNome = (TextView) view.findViewById(R.id.nomeOng);
            vIntuito = (TextView) view.findViewById(R.id.intencao);
            vCidade = (TextView) view.findViewById(R.id.cidade);
            vEstado = (TextView) view.findViewById(R.id.uf);
            vPreco = (TextView) view.findViewById(R.id.valor);
            vImg = (ImageView) view.findViewById(R.id.imagemOng);

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
