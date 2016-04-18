package com.example.syllas.e_bazar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroVestuarioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int cont = 0;
    private Uri fileUri;
    private String selectedImagePath;
    

    private Spinner spnTipo,spnTamanho, spnOngIndicada;
    private List<String> tamanhos = new ArrayList<String>();
    private List<String> tipos = new ArrayList<String>();
    private List<String> NomesOng = new ArrayList<String>();
    private String tipo,tamanho,ong;

    private EbazarDAO bazarDAO; //Objeto que faz as operações no banco de dados
    private List<ItemOng> itemOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vestuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bazarDAO = new EbazarDAO(this); //passando o contexto para o bd

        //cria dados fakes (Apagar depois)
        itemOng = new ArrayList<ItemOng>();
        for(int i = 0; i<10;i++){
            ItemOng ong = new ItemOng(); //item do vetor
            //adiciona informações
            ong.setCidade("Bla bla");
            ong.setId(""+i+"");
            ong.setImg("");
            ong.setIntuito("");
            ong.setNome("Ong "+i+" :)");
            ong.setUF("");
            itemOng.add(ong); //adiciona item ao vetor
        }


        bazarDAO.InserirBDOng(itemOng);

        itemOng.clear();
        NomesOng.clear();

        itemOng = bazarDAO.listarOng();
        for (int i = 0; i<itemOng.size(); i++ ){
            NomesOng.add(itemOng.get(i).getNome());
        }



        //Adicionando Nomes no ArrayList
        tipos.add("Blusa");
        tipos.add("Calça");
        tipos.add("Calçado");
        tipos.add("Meia");
        tipos.add("Saia");
        tipos.add("Vestido");
        tipos.add("Outro");


        tamanhos.add("Selecione tipo");




        //Identifica o Spinner no layout
        spnTipo = (Spinner) findViewById(R.id.spnTipo);
        spnTamanho = (Spinner) findViewById(R.id.spnTamanho);
        spnOngIndicada = (Spinner) findViewById(R.id.spnOng);
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList tipos
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnTipo.setAdapter(spinnerArrayAdapter);

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList Tamanhos
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tamanhos);
        ArrayAdapter<String> spinnerArrayAdapter2 = arrayAdapter2;
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnTamanho.setAdapter(spinnerArrayAdapter2);

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList Tamanhos
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, NomesOng);
        ArrayAdapter<String> spinnerArrayAdapter3 = arrayAdapter3;
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnOngIndicada.setAdapter(spinnerArrayAdapter3);

        //Método do Spinner para capturar o item selecionado
        spnTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                tipo = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                Toast.makeText(CadastroVestuarioActivity.this, "Tipo Selecionado: " + tipo, Toast.LENGTH_LONG).show();

                if (tipo== "Calçado"){
                    tamanhos.clear();
                    tamanhos.add("30");
                    tamanhos.add("32");
                    tamanhos.add("34");
                    tamanhos.add("36");
                    tamanhos.add("37");
                    tamanhos.add("38");
                    tamanhos.add("39");
                    tamanhos.add("40");
                    tamanhos.add("Outro");
                }else if(tipo == "Blusa" || tipo == "Meia"|| tipo=="Vestido"){
                    tamanhos.clear();
                    tamanhos.add("PP");
                    tamanhos.add("P");
                    tamanhos.add("M");
                    tamanhos.add("G");
                    tamanhos.add("GG");
                    tamanhos.add("Outro");
                }else if(tipo == "Calça" || tipo == "Saia" ){
                    tamanhos.clear();
                    tamanhos.add("34");
                    tamanhos.add("36");
                    tamanhos.add("38");
                    tamanhos.add("40");
                    tamanhos.add("42");
                    tamanhos.add("44");
                    tamanhos.add("46");
                    tamanhos.add("48");
                    tamanhos.add("Outro");

                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Método do Spinner para capturar o item selecionado
        spnTamanho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                tamanho = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                Toast.makeText(CadastroVestuarioActivity.this, "Tamanho Selecionado: " + tamanho, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Método do Spinner para capturar o item selecionado
        spnOngIndicada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                ong = parent.getItemAtPosition(posicao).toString();
                //imprime um Toast na tela com o nome que foi selecionado
                Toast.makeText(CadastroVestuarioActivity.this, "Ong Selecionada: " + ong, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //Abre a camera nativa do smartphone
    public void tirarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
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
        if (requestCode == 0) {//imagem da camera
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap img = (Bitmap) bundle.get("data");//Pega a imagem.
                    cont += 1;
                    switch (cont) {
                        case 1:
                            ImageView iv1 = (ImageView) findViewById(R.id.ivFotoTirada1);
                            iv1.setImageBitmap(img);

                            break;
                        case 2:
                            ImageView iv2 = (ImageView) findViewById(R.id.ivFotoTirada2);
                            iv2.setImageBitmap(img);
                            break;
                        case 3:
                            ImageView iv3 = (ImageView) findViewById(R.id.ivFotoTirada3);
                            iv3.setImageBitmap(img);
                            break;
                        default:
                            Toast toast = Toast.makeText(getApplicationContext(), "Número máximo de imagens atingito", Toast.LENGTH_SHORT);
                            toast.show();
                            break;
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "A captura foi cancelada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "A câmera foi fechada", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 1234){
            if (resultCode==RESULT_OK){
                //imagem veio da galeria
                Uri uriImagemGaleria = data.getData();

                //CASO SEJA NECESSÁRIO O PATH (BUGADO)
                /*String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uriImagemGaleria,projection,null,null,null);
                if( cursor != null ){
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    selectedImagePath = cursor.getString(column_index);
                    Toast.makeText(getApplicationContext(), "caminho "+column_index, Toast.LENGTH_SHORT).show();
                }else{
                    selectedImagePath = uriImagemGaleria.getPath();
                    Toast.makeText(getApplicationContext(), "Entrou no else "+selectedImagePath, Toast.LENGTH_SHORT).show();
                }

               Bitmap imgGalery = BitmapFactory.decodeFile(selectedImagePath);*/

                cont += 1;
                switch (cont) {
                    case 1:
                        ImageView iv1 = (ImageView) findViewById(R.id.ivFotoTirada1);
                        iv1.setImageURI(uriImagemGaleria);
                        break;
                    case 2:
                        ImageView iv2 = (ImageView) findViewById(R.id.ivFotoTirada2);
                        iv2.setImageURI(uriImagemGaleria);
                        break;
                    case 3:
                        ImageView iv3 = (ImageView) findViewById(R.id.ivFotoTirada3);
                        iv3.setImageURI(uriImagemGaleria);
                        break;
                    default:
                        Toast toast = Toast.makeText(getApplicationContext(), "Número máximo de imagens atingito", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                }

            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.listVest) {
            startActivity(new Intent(this, ListaVestuarioActivity.class));
        } else if (id == R.id.cadastroOng) {

        } else if (id == R.id.listOng) {
            startActivity(new Intent(this, ListaOngActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }



}