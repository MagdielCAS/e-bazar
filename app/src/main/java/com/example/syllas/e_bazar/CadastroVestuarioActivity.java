package com.example.syllas.e_bazar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
    private String selectedImagePath;
    

    private Spinner spnTipo,spnTamanho, spnOngIndicada;
    private List<String> tamanhos = new ArrayList<String>();
    private List<String> tipos = new ArrayList<String>();
    private List<String> NomesOng = new ArrayList<String>();
    private String tipo,tamanho,ong;


    private EbazarDAO bazarDAO; //Objeto que faz as operações no banco de dados
    private List<ItemOng> itemOng;

    @TargetApi(Build.VERSION_CODES.M)
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(shouldAskPermission()) {
            String[] perms = {"android.permission. WRITE_EXTERNAL_STORAGE"};

            int permsRequestCode = 200;

            requestPermissions(perms, permsRequestCode);
        }

    }


    //Cadastra um vestuario
    public void cadastraVestuario(View view){
        ItemVestuario item = new ItemVestuario();
        EditText etNome = (EditText) findViewById(R.id.etTituloVestuario);
        EditText etCor = (EditText) findViewById(R.id.etCorVestuario);
        EditText etPreco = (EditText) findViewById(R.id.etPrecoVestuario);
        RatingBar rtBar = (RatingBar) findViewById(R.id.ratingbarVest);
        ImageView iv1 = (ImageView) findViewById(R.id.ivFotoTirada1);
        ImageView iv2 = (ImageView) findViewById(R.id.ivFotoTirada2);
        ImageView iv3 = (ImageView) findViewById(R.id.ivFotoTirada3);
        ArrayList<Drawable> fotos = new ArrayList<Drawable>();


        item.setNome(etNome.getText().toString());
        item.setTipo(tipo,pegarIdTipo(tipo));
        item.setTamanho(tamanho);
        item.setOng(ong);
        item.setCor(etCor.getText().toString());
        item.setEstadoConservacao(rtBar.getRating());
        fotos.add(iv1.getDrawable());
        fotos.add(iv2.getDrawable());
        fotos.add(iv3.getDrawable());
        item.setPreco(Double.parseDouble(etPreco.getText().toString()));
        item.setFotos(fotos);
        item.setCarrinho(false);

        bazarDAO.InserirBDVestuario(item);

        startActivity(new Intent(this, ListaVestuarioActivity.class));
    }

    public int pegarIdTipo(String tipo){
        switch (tipo){
            case "Calça":
                return 1;
            case "Calçado":
                return 2;
            case "Meia":
                return 3;
            case "Blusa":
                return 4;
            case "Vestido":
                return 5;
            case "Saia":
                return 6;
            default:
                return -1;
        }
    }


    //Abre a camera nativa do smartphone
    public void tirarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(intent, 0);
        }
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
                    cont += 1;
                    switch (cont) {
                        case 1:
                            ImageView iv1 = (ImageView) findViewById(R.id.ivFotoTirada1);
                            iv1.setImageDrawable(Drawable.createFromPath(selectedImagePath));

                            break;
                        case 2:
                            ImageView iv2 = (ImageView) findViewById(R.id.ivFotoTirada2);
                            iv2.setImageDrawable(Drawable.createFromPath(selectedImagePath));
                            break;
                        case 3:
                            ImageView iv3 = (ImageView) findViewById(R.id.ivFotoTirada3);
                            iv3.setImageDrawable(Drawable.createFromPath(selectedImagePath));
                            break;
                        default:
                            Toast toast = Toast.makeText(getApplicationContext(), "Número máximo de imagens atingito", Toast.LENGTH_SHORT);
                            toast.show();
                            break;
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
                selectedImagePath = getRealPathFromURI_API19(getApplicationContext(),uriImagemGaleria);
                Log.i("Caminho da imagem ",selectedImagePath);

                cont += 1;
                switch (cont) {
                    case 1:
                        ImageView iv1 = (ImageView) findViewById(R.id.ivFotoTirada1);
                        iv1.setImageDrawable(Drawable.createFromPath(selectedImagePath));
                        break;
                    case 2:
                        ImageView iv2 = (ImageView) findViewById(R.id.ivFotoTirada2);
                        iv2.setImageDrawable(Drawable.createFromPath(selectedImagePath));
                        break;
                    case 3:
                        ImageView iv3 = (ImageView) findViewById(R.id.ivFotoTirada3);
                        iv3.setImageDrawable(Drawable.createFromPath(selectedImagePath));
                        break;
                    default:
                        Toast toast = Toast.makeText(getApplicationContext(), "Número máximo de imagens atingito", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.listVest) {
            this.finishAfterTransition();
            startActivity(new Intent(this, ListaVestuarioActivity.class));
        } else if (id == R.id.cadastroOng) {
            startActivity(new Intent(this, CadastroOngActivity.class));
        } else if (id == R.id.listOng) {
            this.finishAfterTransition();
            startActivity(new Intent(this, ListaOngActivity.class));
        }else if(id == R.id.carrinho){
            this.finishAfterTransition();
            startActivity(new Intent(this, CarrinhoActivity.class));
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

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;

                break;

        }

    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir.getPath(), imageFileName + ".png");

        // Save a file: path for use with ACTION_VIEW intents
        selectedImagePath = image.getAbsolutePath();
        return image;
    }




    private boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);

    }



}