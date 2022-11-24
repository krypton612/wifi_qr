package com.emi.activos;

import android.view.View;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QrActivity extends CaptureActivity {
    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_qrscan);

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        return (DecoratedBarcodeView)findViewById(R.id.dimentions_camera);
    }
}
