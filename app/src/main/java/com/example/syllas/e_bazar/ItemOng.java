package com.example.syllas.e_bazar;

/**
 * Created by magdi on 16/04/2016.
 */
public class ItemOng {

    private String nome;
    private String intuito;
    private String cidade;
    private String UF;
    private int valorArrecadado;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIntuito() {
        return intuito;
    }

    public void setIntuito(String intuito) {
        this.intuito = intuito;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public int getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(int valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public void ItemOng(){
    }

    public void ItemOng(String nome, String intuito, String cidade, String estado){
        this.setNome(nome);
        this.setCidade(cidade);
        this.setIntuito(intuito);
        this.setUF(estado);
    }

}
