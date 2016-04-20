package com.example.syllas.e_bazar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CadastroOngActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private EbazarDAO bazarDAO; //Objeto que faz as operações no banco de dados
    private List<ItemOng> itemOng;
    private List<String> ufs = new ArrayList<String>();
    private String uf;

    private String selectedImagePath = "";




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


        ufs.add("AC");
        ufs.add("AL");
        ufs.add("AM");
        ufs.add("AP");
        ufs.add("BA");
        ufs.add("CE");
        ufs.add("DF");
        ufs.add("ES");
        ufs.add("GO");
        ufs.add("MA");
        ufs.add("MG");
        ufs.add("MS");
        ufs.add("MT");
        ufs.add("PA");
        ufs.add("PB");
        ufs.add("PE");
        ufs.add("PI");
        ufs.add("PR");
        ufs.add("RJ");
        ufs.add("RN");
        ufs.add("RO");
        ufs.add("RR");
        ufs.add("RS");
        ufs.add("SC");
        ufs.add("SE");
        ufs.add("SP");
        ufs.add("TO");


        Spinner spnUFOng = (Spinner) findViewById(R.id.spnUFOng);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList tipos
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ufs);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnUFOng.setAdapter(spinnerArrayAdapter);

        //Método do Spinner para capturar o item selecionado
        spnUFOng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                uf = parent.getItemAtPosition(posicao).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //Abre a galeria do smartphone
    public void abrirGaleria(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {//imagem da camera
            if (resultCode == RESULT_OK) {

                //imagem veio da galeria
                Uri uriImagemGaleria = data.getData();
                selectedImagePath = getRealPathFromURI_API19(getApplicationContext(), uriImagemGaleria);
                Log.i("Caminho da imagem ", selectedImagePath);

                ImageView iv1 = (ImageView) findViewById(R.id.ivLogoOng);
                iv1.setImageDrawable(Drawable.createFromPath(selectedImagePath));
            }
        }
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    //Cadastra uma ong
    public void cadastraOng(View view){
        ItemOng item = new ItemOng();
        EditText etNome = (EditText) findViewById(R.id.etNomeOng);
        EditText etIntuito = (EditText) findViewById(R.id.etIntuitoOng);
        EditText etCidade = (EditText) findViewById(R.id.etCidadeOng);

        if(!etNome.getText().toString().equals("") && !etCidade.getText().toString().equals("")
                && !etIntuito.getText().toString().equals("")) {

            item.setNome(etNome.getText().toString());
            item.setIntuito(etIntuito.getText().toString());
            item.setCidade(etCidade.getText().toString());
            item.setUF(uf);
            item.setImg(selectedImagePath);
            item.setValorArrecadado(0);

            bazarDAO.InserirBDOng(item);

            startActivity(new Intent(this, ListaOngActivity.class));
        }else{
            Toast.makeText(this, "Preencha todos os campo",Toast.LENGTH_SHORT).show();
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
