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

    private ItemVestuario criarVestuario(Cursor cursor) {
        ItemVestuario vest = new ItemVestuario();
        vest.setId(cursor.getString(0));
        vest.setIdTipo(cursor.getInt(1));
        vest.setTipo(cursor.getString(2));
        vest.setTamanho(cursor.getString(3));
        vest.setCor(cursor.getString(4));
        vest.setPreco(cursor.getDouble(5));
        vest.setEstadoConservacao(cursor.getFloat(6));
        vest.setOng(cursor.getString(7));
        vest.setImg(cursor.getString(8));
        return vest;
    }

    public void InserirBDVestuario(List<ItemVestuario> vestuario){
        db = getDb();
        ContentValues values = new ContentValues();
        for(ItemVestuario vest:vestuario){
            values.put(DatabaseHelper.Vestuario.ID_TIPO, vest.getIdTipo());
            values.put(DatabaseHelper.Vestuario.TIPO,vest.getTipo());
            values.put(DatabaseHelper.Vestuario.TAMANHO, vest.getTamanho());
            values.put(DatabaseHelper.Vestuario.COR,vest.getCor());
            values.put(DatabaseHelper.Vestuario.PRECO,vest.getPreco());
            values.put(DatabaseHelper.Vestuario.ESTADO_DE_CONSERVACAO, vest.getEstadoConservacao());
            values.put(DatabaseHelper.Vestuario.ONG,vest.getOng());
            values.put(DatabaseHelper.Vestuario.IMAGEM,vest.getImg());
            db.insert(DatabaseHelper.Vestuario.TABELA, null, values);
        }
    }

    public void RemoverBDVestuario(String id){
        db = getDb();
        String where[] = new String[]{id};
        db.delete(DatabaseHelper.Vestuario.TABELA,DatabaseHelper.Vestuario._ID + " = ?",where);
    }
}
