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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroVestuarioActivity extends AppCompatActivity {
    int cont = 0;
    private Uri fileUri;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vestuario);

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





}