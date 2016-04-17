package com.example.syllas.e_bazar;

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
        vest.setIdTipo(cursor.getInt(1));
        vest.setTipo(cursor.getString(2));
        vest.setTamanho(cursor.getString(3));
        vest.setCor(cursor.getString(4));
        vest.setPreco(cursor.getDouble(5));
        vest.setEstadoConservacao(cursor.getFloat(6));
        vest.setOng("teste");
        vest.setImg(0);
        return vest;
    }
}
