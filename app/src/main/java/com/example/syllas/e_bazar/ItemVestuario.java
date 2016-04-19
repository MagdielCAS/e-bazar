package com.example.syllas.e_bazar;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Created by magdi on 15/04/2016.
 */
public class ItemVestuario{

    /*Classe que define os objetos do vestuário*/

    private String nome;
    private String tamanho;
    private String cor;
    private float estadoConservacao;
    private double preco;
    private String ong;
    private int idTipo;
    private String tipo;
    private String _id;
    private ArrayList<Drawable> fotos;
    private boolean carrinho;

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

    public float getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(float estadoConservacao) {
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
    public void ItemVestuario(int i) {
        setIdTipo(i);
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
                return this.tipo;
        }
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTipo(String tipo,int id) {
        this.tipo = tipo;
        this.setIdTipo(id);
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
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



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Drawable> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Drawable> fotos) {
        this.fotos = fotos;
    }

    public boolean isCarrinho() {
        return carrinho;
    }

    public void setCarrinho(boolean carrinho) {
        this.carrinho = carrinho;
    }

    public void setCampo (String campo,String valor) {
        switch (campo) {
            case DatabaseHelper.Vestuario.CARRINHO:
                setCarrinho(Boolean.parseBoolean(valor));
                break;
            case DatabaseHelper.Vestuario.ONG:
                setOng(valor);
                break;
            case DatabaseHelper.Vestuario.PRECO:
                setPreco(Double.parseDouble(valor));
                break;
            case DatabaseHelper.Vestuario.ESTADO_DE_CONSERVACAO:
                setEstadoConservacao(Float.parseFloat(valor));
                break;
            case DatabaseHelper.Vestuario._ID:
                setId(valor);
                break;
            case DatabaseHelper.Vestuario.COR:
                setCor(valor);
                break;
            case DatabaseHelper.Vestuario.ID_TIPO:
                setIdTipo(Integer.parseInt(valor));
                break;
            case DatabaseHelper.Vestuario.NOME:
                setNome((valor));
                break;
            case DatabaseHelper.Vestuario.TIPO:
                setTipo((valor));
                break;
            case DatabaseHelper.Vestuario.TAMANHO:
                setTamanho((valor));
                break;
        }
    }
    public boolean compareCampo (String campo,String valor){
        switch (campo){
            case DatabaseHelper.Vestuario.CARRINHO:
                return compareCarrinho(valor);
            case DatabaseHelper.Vestuario.ONG:
                return compareOng(valor);
            case DatabaseHelper.Vestuario.PRECO:
                return comparePreco(valor);
            case DatabaseHelper.Vestuario.ESTADO_DE_CONSERVACAO:
                return compareEstadoConservacao(valor);
            case DatabaseHelper.Vestuario._ID:
                return compareId(valor);
            case DatabaseHelper.Vestuario.COR:
                return compareCor(valor);
            case DatabaseHelper.Vestuario.ID_TIPO:
                return compareIdTipo(valor);
            case DatabaseHelper.Vestuario.NOME:
                return compareNome(valor);
            case DatabaseHelper.Vestuario.TIPO:
                return compareTipo(valor);
            case DatabaseHelper.Vestuario.TAMANHO:
                return compareTamanho(valor);
            default:
                return false;
        }
    }
    public boolean compareNome(String valor){ return (getNome() == valor); }
    public boolean compareTamanho(String valor){ return (getTamanho() == valor); }
    public boolean compareCor(String valor){ return (getCor() == valor); }
    public boolean compareEstadoConservacao(String valor){ return (String.valueOf(getEstadoConservacao()) == valor); }
    public boolean comparePreco(String valor){ return (String.valueOf(getPreco()) == valor); }
    public boolean compareOng(String valor){ return (getOng() == valor); }
    public boolean compareIdTipo(String valor){ return (String.valueOf(getIdTipo()) == valor); }
    public boolean compareTipo(String valor){ return (getTipo() == valor); }
    public boolean compareId(String valor){ return (getId() == valor); }
    public boolean compareCarrinho(String valor){ return (String.valueOf(isCarrinho()).toUpperCase() == valor.toUpperCase()); }
}