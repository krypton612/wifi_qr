package com.emi.activos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.emi.activos.Adapter.ListAdapter;
import com.emi.activos.recyclerviewBell.reciclerDb;

public class QrScannnedActivity extends AppCompatActivity {

    public static ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scannned);
        reciclerDb databaseInfo = new reciclerDb(getApplicationContext());


        listAdapter = new ListAdapter(databaseInfo.mostrar(), this);

        RecyclerView recyclerView = findViewById(R.id.activo_escaneado);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);


    }
}