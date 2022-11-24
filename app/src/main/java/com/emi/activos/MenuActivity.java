package com.emi.activos;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emi.activos.Adapter.ListAdapter;
import com.emi.activos.Adapter.ListElement;
import com.emi.activos.recyclerviewBell.reciclerDb;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MenuActivity extends AppCompatActivity {

    public static List<ListElement> elements;
    private CardView qrgalery;
    private CardView showsaves;

    private String ssid;
    private String password;
    private String cifrado;
    private reciclerDb develData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        qrgalery = findViewById(R.id.qrgalery);
        showsaves = findViewById(R.id.database);

        develData = new reciclerDb(getApplicationContext());

        qrgalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType( android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(pickIntent, 111);
            }
        });
        showsaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent datab = new Intent(MenuActivity.this, QrScannnedActivity.class);
                startActivity(datab);
            }
        });


    }
    public void scanMarginScanner(View view) {
        ScanOptions options = new ScanOptions();
        options.setOrientationLocked(true);
        options.setPrompt("Dirija la camara a un Qr valido");
        options.setCaptureActivity(QrActivity.class);
        barcodeLauncher.launch(options);
    }
    public void showdata(View view){
        Intent intent = new Intent(MenuActivity.this, QrScannnedActivity.class);
        startActivity(intent);

    }
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Escaneo Cancelado");
                        Toast.makeText(MenuActivity.this, "Escaneo Cancelado", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Asigne los permisos de camara");
                        Toast.makeText(MenuActivity.this, "Asigne los permisos de camara", Toast.LENGTH_LONG).show();
                    }
                } else {

                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setTitle("Wifi");

                        String[] parts = result.getContents().split(";");

                        String[] partsSsid = parts[0].split((":"));
                        String[] partsSsid2 = parts[1].split((":"));
                        String[] partsSsid3 = parts[2].split((":"));

                        ssid = partsSsid[2];
                        password = partsSsid3[1];
                        cifrado = partsSsid2[1];
                        builder.setMessage("SSID: " + ssid + "\nPASSWORD: " + password + "\nCIFRADO: " + cifrado);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                        try {

                            develData.agregarWifi(ssid, password, cifrado);
                            Toast.makeText(MenuActivity.this, "Guardado con exito..", Toast.LENGTH_LONG).show();
                        } catch (Exception er) {
                            Toast.makeText(MenuActivity.this, "Este wifi ya a sido agregado..", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(MenuActivity.this, "Este qr no pertenece a uno de WIFI..", Toast.LENGTH_LONG).show();
                    }

                }
            });

    private void LoadDataToApi(String usernamed, String passwordd, String cifradod){

        elements = new ArrayList<>();
        elements.add(new ListElement(usernamed, passwordd, cifradod));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //the case is because you might be handling multiple request codes here
            case 111:
                if(data == null || data.getData()==null) {
                    Log.e("TAG", "The uri is null, probably the user cancelled the image selection process using the back button.");
                    return;
                }
                Uri uri = data.getData();
                try
                {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap == null)
                    {
                        Log.e("TAG", "uri is not a bitmap," + uri.toString());
                        return;
                    }
                    int width = bitmap.getWidth(), height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    bitmap.recycle();
                    bitmap = null;
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    try
                    {
                        Result result = reader.decode(bBitmap);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setTitle("Wifi");


                        String[] parts = result.toString().split(";");

                        String[] partsSsid = parts[0].split((":"));
                        String[] partsSsid2 = parts[1].split((":"));
                        String[] partsSsid3 = parts[2].split((":"));

                        ssid = partsSsid[2];
                        password = partsSsid3[1];
                        cifrado = partsSsid2[1];

                        builder.setMessage("SSID: " + ssid + "\nPASSWORD: "+password+"\nCIFRADO: "+cifrado);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                        try {

                            develData.agregarWifi(ssid, password, cifrado);
                            Toast.makeText(MenuActivity.this, "Guardado con exito..", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Toast.makeText(MenuActivity.this, "Este wifi ya a sido agregado..", Toast.LENGTH_LONG).show();
                        }


                    }
                    catch (NotFoundException e)
                    {
                        Log.e("TAG", "decode exception", e);
                    }
                }
                catch (FileNotFoundException e)
                {
                    Log.e("TAG", "can not open file" + uri.toString(), e);
                }
                break;
        }
    }
}