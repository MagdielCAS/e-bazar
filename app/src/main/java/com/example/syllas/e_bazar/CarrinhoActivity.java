package com.example.syllas.e_bazar;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;
import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemCarrinhoAdapter.OnDataSelected{


    private EbazarDAO ebazarDAO;
    private List<ItemVestuario> itensCarrinho = new ArrayList<ItemVestuario>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemCarrinhoAdapter adapter;
    private TextView precoTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ebazarDAO = new EbazarDAO(this);
        itensCarrinho = listCarrinhos(ebazarDAO.listarVestuario());

        recyclerView = (RecyclerView) findViewById(R.id.cardList_carrinho); //pega o id do layout para alocar os cardview dinamicamente
        recyclerView.setHasFixedSize(true); //Seta os elementos de tamanho fixo, ajudar a ganhar desempenho

        linearLayoutManager = new LinearLayoutManager(this); //Define como os dados são apresentados no recycler view
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //define como uma lista vertical

        recyclerView.setLayoutManager(linearLayoutManager); //configurando o recycler view com a especificação de layout

        adapter = new ItemCarrinhoAdapter(this,this,itensCarrinho);
        recyclerView.setAdapter(adapter);

        precoTotal = (TextView) findViewById(R.id.precoTotal);
        precoTotal.setText("Preço: R$ "+Double.toString(getPrecoTotal(itensCarrinho)));
    }

    private double getPrecoTotal(List<ItemVestuario> itens){
        double preco = 0;
        for (ItemVestuario vest:itens) {
            preco += vest.getPreco();
        }
        return preco;
    }

    public void comprarOnClick(View view){
        if(itensCarrinho.size()!=0) {
            ItemOng getOng;
            for (ItemVestuario vest : itensCarrinho) {
                getOng = ebazarDAO.getOng(DatabaseHelper.Ong.NOME, vest.getOng());
                ebazarDAO.changeValueOng(getOng, DatabaseHelper.Ong.VALOR_ARRECADADO,
                        String.valueOf(getOng.getValorArrecadado() + vest.getPreco()));
                ebazarDAO.RemoverBDVestuario(vest.getId());
            }
            Toast.makeText(this, "Compra realizada com sucesso", Toast.LENGTH_SHORT).show();
            this.finishAfterTransition();
            startActivity(new Intent(this, CarrinhoActivity.class));
        }else{
            Toast.makeText(this, "Carrinho vazio", Toast.LENGTH_SHORT).show();
        }
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
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(this).create();
            alertDialogBuilder.setMessage("Aplicativo desenvolvido por:\nMagdiel Campelo\nSyllas Rangel\nEloise Carvalho\n\n" +
                    "Futuras implementações:\n- Adicionar tile de informações sobre peça de roupa com fotos." +
                    "\n- Mais informações sobre ong" +
                    "\n- Orndenar listas com filtros\n");
            alertDialogBuilder.setButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialogBuilder.show();
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
            this.finishAfterTransition();
            startActivity(new Intent(this, CadastroOngActivity.class));
        } else if (id == R.id.listOng) {
            this.finishAfterTransition();
            startActivity(new Intent(this, ListaOngActivity.class));
        } else if (id == R.id.listVest){
            this.finishAfterTransition();
            startActivity(new Intent(this, ListaVestuarioActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        ebazarDAO.close();
        super.onDestroy();
    }

    private List<ItemVestuario> listCarrinhos(List<ItemVestuario> itensVest){
        List<ItemVestuario> carrinho = new ArrayList<ItemVestuario>();
        for (ItemVestuario vest:itensVest){
            if(vest.isCarrinho()){
                carrinho.add(vest);
            }
        }
        return carrinho;
    }

    @Override
    public void onDataSelected(View view, int position) {
        ItemVestuario selectedItem  = itensCarrinho.get(position);
        Toast.makeText(this, "Item removido(teste)", Toast.LENGTH_SHORT).show();
        ebazarDAO.changeValueVestuario(itensCarrinho.get(position),
                DatabaseHelper.Vestuario.CARRINHO, "FALSE");
        itensCarrinho.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
