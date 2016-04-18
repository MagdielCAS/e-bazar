package com.example.syllas.e_bazar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by magdi on 17/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO_DE_DADOS = "e_bazar";
    private static int VERSAO = 1;

    public static class Vestuario {
        public static final String TABELA = "vestuario";
        public static final String _ID = "_id";
        public static final String ID_TIPO = "id_tipo";
        public static final String TIPO = "tipo";
        public static final String TAMANHO = "tamanho";
        public static final String COR = "cor";
        public static final String PRECO = "preco";
        public static final String ESTADO_DE_CONSERVACAO = "estado_de_conservacao";
        public static final String ONG = "ong";
        public static final String IMAGEM = "nome_img_vest";
        public static final String[] COLUNAS = new String[]{ _ID, ID_TIPO, TIPO,
                TAMANHO, COR, PRECO, ESTADO_DE_CONSERVACAO,ONG,IMAGEM };
    }

    public static class Ong{
        public static final String TABELA = "ongs";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String INTUITO = "intuito";
        public static final String CIDADE = "cidade";
        public static final String ESTADO = "estado";
        public static final String VALOR_ARRECADADO = "valor_arrecadado";
        public static final String IMAGEM = "nome_img_ong";
        public static final String[] COLUNAS = new String[]{ _ID, NOME, INTUITO, CIDADE,
                ESTADO, VALOR_ARRECADADO, IMAGEM };
    }

    public DatabaseHelper(Context context){
        super(context,BANCO_DE_DADOS,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS vestuario ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_tipo INTEGER, tipo TEXT, " +
                " tamanho TEXT, cor TEXT, " +
                " preco DOUBLE, estado_de_conservacao FLOAT, "+
                "ong TEXT, nome_img_vest TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ongs ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT, intuito TEXT, cidade TEXT," +
                " estado TEXT, valor_arrecadado DOUBLE, nome_img_ong TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
