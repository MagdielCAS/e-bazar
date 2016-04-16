package com.example.syllas.e_bazar;

/**
 * Created by magdi on 15/04/2016.
 */
public class ItemVestuario {

    /*Classe que define os objetos do vestuário*/

    protected String tamanho;
    protected String cor;
    protected int estadoConservacao;
    protected double preco;
    protected String ong;
    protected int idTipo;

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
    public void ItemVestuario(String ong,String tamanho,String cor,int estadoConservacao,double preco,int idTipo){
        setOng(ong);
        setTamanho(tamanho);
        setCor(cor);
        setEstadoConservacao(estadoConservacao);
        setPreco(preco);
        setIdTipo(idTipo);
    }
}