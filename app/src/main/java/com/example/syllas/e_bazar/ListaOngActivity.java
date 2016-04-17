package com.example.syllas.e_bazar;

import android.content.Intent;
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

public class ListaOngActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemOngAdapter.OnDataSelected {

    private List<ItemOng> itensOng = new ArrayList<ItemOng>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ong);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criarOngFake();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.cardList_ong); //pega o id do layout para alocar os cardview dinamicamente
        recyclerView.setHasFixedSize(true); //Seta os elementos de tamanho fixo, ajudar a ganhar desempenho

        linearLayoutManager = new LinearLayoutManager(this); //Define como os dados são apresentados no recycler view
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //define como uma lista vertical

        recyclerView.setLayoutManager(linearLayoutManager); //configurando o recycler view com a especificação de layout

        adapter = new ItemOngAdapter(this,this,itensOng);
        recyclerView.setAdapter(adapter);
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
        int id = item.getItemId();

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
            startActivity(new Intent(this, CadastroVestuarioActivity.class));
        } else if (id == R.id.cadastroOng) {

        } else if (id == R.id.listVest) {
            startActivity(new Intent(this, ListaVestuarioActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void criarOngFake() {
        for(int i = 0; i<10;i++){
            ItemOng sampleOng = new ItemOng();
            sampleOng.setUF("CE"+i);
            sampleOng.setIntuito("Beneficente " + i);
            sampleOng.setCidade("asdasdasd" + i);
            sampleOng.setNome("Ong nº " + i+",50");
            sampleOng.setValorArrecadado(i);
            itensOng.add(sampleOng);
        }
    }

    @Override
    public void onDataSelected(View view, int position) {
        ItemOng selectedItem = itensOng.get(position);
        Toast.makeText(this, "Ong Selecionada: "+selectedItem.getNome(), Toast.LENGTH_SHORT).show();
    }
}
