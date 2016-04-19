package com.example.syllas.e_bazar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magdi on 17/04/2016.
 */
public class EbazarDAO {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    public EbazarDAO(Context context){
        helper = new DatabaseHelper(context);
    }
    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
        return db;
    }
    public void close(){
        helper.close();
    }

    public List<ItemVestuario> listarVestuario(){
        Cursor cursor = getDb().query(DatabaseHelper.Vestuario.TABELA,
                DatabaseHelper.Vestuario.COLUNAS,
                null, null, null, null,null);
        List<ItemVestuario> vestuario = new ArrayList<>();
        while(cursor.moveToNext()){
            ItemVestuario vest = criarVestuario(cursor);
            vestuario.add(vest);
        }
        cursor.close();
        return vestuario;
    }

    public List<ItemOng> listarOng(){
        Cursor cursor = getDb().query(DatabaseHelper.Ong.TABELA,
                DatabaseHelper.Ong.COLUNAS,
                null, null, null, null,null);
        List<ItemOng> ongList = new ArrayList<>();
        while(cursor.moveToNext()){
            ItemOng ong = criarOngList(cursor);
            ongList.add(ong);
        }
        cursor.close();
        return ongList;
    }

    private ItemOng criarOngList(Cursor cursor) {
        ItemOng ong = new ItemOng();
        ong.setId(cursor.getString(0));
        ong.setNome(cursor.getString(1));
        ong.setIntuito(cursor.getString(2));
        ong.setCidade(cursor.getString(3));
        ong.setUF(cursor.getString(4));
        ong.setValorArrecadado(cursor.getDouble(5));
        ong.setImg(cursor.getString(6));
        return ong;
    }

    private ItemVestuario criarVestuario(Cursor cursor) {
        ItemVestuario vest = new ItemVestuario();
        vest.setId(cursor.getString(0));
        vest.setIdTipo(cursor.getInt(1));
        vest.setTipo(cursor.getString(2));
        vest.setNome(cursor.getString(3));
        vest.setTamanho(cursor.getString(4));
        vest.setCor(cursor.getString(5));
        vest.setPreco(cursor.getDouble(6));
        vest.setEstadoConservacao(cursor.getFloat(7));
        vest.setOng(cursor.getString(8));
        vest.setCampo(DatabaseHelper.Vestuario.CARRINHO,cursor.getString(9));
        return vest;
    }

    public void InserirBDVestuario(List<ItemVestuario> vestuario){
        for(ItemVestuario vest:vestuario){
            InserirBDVestuario(vest);
        }
    }

    public void InserirBDVestuario(ItemVestuario vest){
        db = getDb();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Vestuario.ID_TIPO, vest.getIdTipo());
        values.put(DatabaseHelper.Vestuario.TIPO,vest.getTipo());
        values.put(DatabaseHelper.Vestuario.NOME,vest.getNome());
        values.put(DatabaseHelper.Vestuario.TAMANHO, vest.getTamanho());
        values.put(DatabaseHelper.Vestuario.COR,vest.getCor());
        values.put(DatabaseHelper.Vestuario.PRECO,vest.getPreco());
        values.put(DatabaseHelper.Vestuario.ESTADO_DE_CONSERVACAO, vest.getEstadoConservacao());
        values.put(DatabaseHelper.Vestuario.ONG,vest.getOng());
        values.put(DatabaseHelper.Vestuario.CARRINHO,vest.isCarrinho());
        db.insert(DatabaseHelper.Vestuario.TABELA, null, values);

    }

    public void InserirBDOng(List<ItemOng> ongs){
        for(ItemOng ong:ongs){
            InserirBDOng(ong);
        }
    }

    public void InserirBDOng(ItemOng ong){
        db = getDb();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Ong.CIDADE, ong.getCidade());
        values.put(DatabaseHelper.Ong.NOME, ong.getNome());
        values.put(DatabaseHelper.Ong.INTUITO, ong.getIntuito());
        values.put(DatabaseHelper.Ong.CIDADE, ong.getCidade());
        values.put(DatabaseHelper.Ong.ESTADO, ong.getUF());
        values.put(DatabaseHelper.Ong.VALOR_ARRECADADO, ong.getValorArrecadado());
        values.put(DatabaseHelper.Ong.IMAGEM, ong.getImg());
        db.insert(DatabaseHelper.Ong.TABELA, null, values);
    }

    public void RemoverBDVestuario(String id){
        db = getDb();
        String where[] = new String[]{id};
        db.delete(DatabaseHelper.Vestuario.TABELA, DatabaseHelper.Vestuario._ID + " = ?", where);
    }

    public void RemoverBDOng(String id){
        db = getDb();
        String where[] = new String[]{id};
        db.delete(DatabaseHelper.Ong.TABELA,DatabaseHelper.Ong._ID + " = ?",where);
    }

    public void changeValueVestuario(String ID,String campo, String valor){
        db = getDb();
        List<ItemVestuario> lista = new ArrayList<ItemVestuario>();
        ItemVestuario item = new ItemVestuario();
        lista = listarVestuario();
        for (ItemVestuario i:lista) {
           // if
        }
    }
}
