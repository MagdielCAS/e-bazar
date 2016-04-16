package com.example.syllas.e_bazar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by magdi on 15/04/2016.
 */
public class ItemVestuario{

    /*Classe que define os objetos do vestuário*/

    private String tamanho;
    private String cor;
    private int estadoConservacao;
    private double preco;
    private String ong;
    private int idTipo;
    private String tipo;
    private int id;
    private int img;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(int estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getOng() {
        return ong;
    }

    public void setOng(String ong) {
        this.ong = ong;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public void ItemVestuario(){
    }
    public void ItemVestuario(String ong,String tamanho,String cor,int estadoConservacao,double preco,int idTipo,int id){
        setOng(ong);
        setTamanho(tamanho);
        setCor(cor);
        setEstadoConservacao(estadoConservacao);
        setPreco(preco);
        setIdTipo(idTipo);
    }

    public String getTipo() {
        switch (this.getIdTipo()){
            case 1:
                return "Calça";
            case 2:
                return "Calçado";
            case 3:
                return "Meia";
            case 4:
                return "Blusa";
            case 5:
                return "Vestido";
            case 6:
                return "Saia";
            default:
                return "Tipo "+this.getIdTipo();
        }
    }

    public void setTipo(String tipo,int id) {
        this.tipo = tipo;
        this.setIdTipo(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        switch (this.getIdTipo()){
            case 1:
                return (R.drawable.calca);
            case 2:
                return (R.drawable.calcado);
            case 3:
                return (R.drawable.meia);
            case 4:
                return (R.drawable.blusa);
            case 5:
                return (R.drawable.vestido);
            case 6:
                return (R.drawable.saia);
            default:
                return (R.drawable.negocios);
        }
    }

    public void setImg(int idImg) {
        this.img = idImg;
    }
}