package com.example.syllas.e_bazar;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaVestuarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemVestuarioAdapter.OnDataSelected {

    private List<ItemVestuario> itensVest = new ArrayList<ItemVestuario>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private EbazarDAO bazarDAO; //Objeto que faz as operações no banco de dados


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vestuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bazarDAO = new EbazarDAO(this); //passando o contexto para o bd

        //criarVestFake(); //exemplo de injeção de dados no bd (olhar metodo)
        itensVest = bazarDAO.listarVestuario(); //array com os itens a serem exibidos

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.cardList_vestuario); //pega o id do layout para alocar os cardview dinamicamente
        recyclerView.setHasFixedSize(true); //Seta os elementos de tamanho fixo, ajudar a ganhar desempenho

        linearLayoutManager = new LinearLayoutManager(this); //Define como os dados são apresentados no recycler view
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //define como uma lista vertical

        recyclerView.setLayoutManager(linearLayoutManager); //configurando o recycler view com a especificação de layout
        if(itensVest.size()==0){
            recyclerView.setBackgroundResource(R.drawable.inicial);
        }else{
            recyclerView.setBackgroundColor(getColor(R.color.cardview_light_background));
        }

        adapter = new ItemVestuarioAdapter(this,this,itensVest);
        recyclerView.setAdapter(adapter);
    }

    private void criarVestFake() {
        List<ItemVestuario> listVest = new ArrayList<ItemVestuario>(); //cria vetor
        for(int i = 0; i<10;i++){
            ItemVestuario vest = new ItemVestuario(); //item do vetor
            //adiciona informações
            vest.setIdTipo(i);
            vest.setTipo("Tipo " + i);
            vest.setTamanho("Tam " + i);
            vest.setCor("Cor " + i);
            vest.setPreco(i + (i % 2 == 0 ? 0.35 * i : 0.5 * i));
            vest.setEstadoConservacao(i % 5);
            vest.setOng("Ong " + i);
            listVest.add(vest); //adiciona item ao vetor
        }
        bazarDAO.InserirBDVestuario(listVest); //adiciona vetor ao banco de dados
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
        } else if (id == R.id.cadastroOng) {
            startActivity(new Intent(this, CadastroOngActivity.class));
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

    @Override
    public void onDataSelected(View view, int position) {
        ItemVestuario selectedItem  = itensVest.get(position);
        if(!selectedItem.isCarrinho()) {
            Toast.makeText(this, "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();
            bazarDAO.changeValueVestuario(itensVest.get(position),
                    DatabaseHelper.Vestuario.CARRINHO, "TRUE");
        }else{
            Toast.makeText(this, "Item já está no carrinho", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        bazarDAO.close();
        super.onDestroy();
    }


}
