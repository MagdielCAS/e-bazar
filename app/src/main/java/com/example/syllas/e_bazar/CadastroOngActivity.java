package com.example.syllas.e_bazar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class CadastroOngActivity extends AppCompatActivity {

    private EbazarDAO bazarDAO; //Objeto que faz as operações no banco de dados
    private List<ItemOng> itemOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);

        bazarDAO = new EbazarDAO(this); //passando o contexto para o bd
    }




    //Cadastra uma ong
    public void cadastraOng(View view){
        ItemOng item = new ItemOng();
        EditText etNome = (EditText) findViewById(R.id.etNomeOng);
        EditText etIntuito = (EditText) findViewById(R.id.etIntuitoOng);
        EditText etCidade = (EditText) findViewById(R.id.etCidadeOng);


        item.setNome(etNome.getText().toString());
        item.setIntuito(etIntuito.getText().toString());
        item.setCidade(etCidade.getText().toString());
        item.setUF("CE");
        item.setImg("");
        item.setValorArrecadado(0);

        bazarDAO.InserirBDOng(item);

        startActivity(new Intent(this, ListaOngActivity.class));
    }


}
