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

public class CadastroOngActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private EbazarDAO bazarDAO; //Objeto que faz as operações no banco de dados
    private List<ItemOng> itemOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ong);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bazarDAO = new EbazarDAO(this); //passando o contexto para o bd

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId(); //item do menu

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cadastroVest) {
            this.finishAfterTransition();
            startActivity(new Intent(this, CadastroVestuarioActivity.class));
        } else if (id == R.id.listVest) {
            this.finishAfterTransition();
            startActivity(new Intent(this, ListaVestuarioActivity.class));
        } else if (id == R.id.listOng) {
            this.finishAfterTransition();
            startActivity(new Intent(this, ListaOngActivity.class));
        } else if (id == R.id.carrinho){
            this.finishAfterTransition();
            startActivity(new Intent(this, CarrinhoActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
