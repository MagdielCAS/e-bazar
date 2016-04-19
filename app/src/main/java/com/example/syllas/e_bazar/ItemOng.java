package com.example.syllas.e_bazar;

/**
 * Created by magdi on 16/04/2016.
 */
public class ItemOng {

    private String id;
    private String nome;
    private String intuito;
    private String cidade;
    private String UF;
    private String img;
    private double valorArrecadado;


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

    public double getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(double valorArrecadado) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setCampo (String campo,String valor) {
        switch (campo) {
            case DatabaseHelper.Ong.VALOR_ARRECADADO:
                setValorArrecadado(Double.parseDouble(valor));
                break;
            case DatabaseHelper.Ong.NOME:
                setNome(valor);
                break;
            case DatabaseHelper.Ong.ESTADO:
                setUF((valor));
                break;
            case DatabaseHelper.Ong.CIDADE:
                setCidade((valor));
                break;
            case DatabaseHelper.Ong._ID:
                setId(valor);
                break;
            case DatabaseHelper.Ong.INTUITO:
                setIntuito(valor);
                break;
        }
    }
}
