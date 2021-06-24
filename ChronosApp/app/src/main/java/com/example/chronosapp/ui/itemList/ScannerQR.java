package com.example.chronosapp.ui.itemList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.chronosapp.R;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

public class ScannerQR extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Context context;
    private TextView textView;

    String[] permission = {
            Manifest.permission.CAMERA
    };
    int PERM_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        textView = findViewById(R.id.textView);
        scannerView = findViewById(R.id.surfaceView);
        //if(scannerView==null)
            //Toast.makeText(this,"null found!",Toast.LENGTH_SHORT).show();
        mCodeScanner = new CodeScanner(this, scannerView);
        context = this;
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result.getText());
                        String codedData = result.getText();
                        Intent intent = new Intent();
                        intent.putExtra("codedData",codedData);
                        setResult(RESULT_OK,intent);
                        mCodeScanner.stopPreview();
                        mCodeScanner.releaseResources();
                        ((Activity)context).finish();
                    }
                });
            }
        });
//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestCamera();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCodeScanner.stopPreview();
        mCodeScanner.releaseResources();
    }

    private boolean checkPermissions()
    {
        List<String> permissionsList = new ArrayList<>();
        for(String perm: permission)
            if(ContextCompat.checkSelfPermission(getApplicationContext(), perm) != PackageManager.PERMISSION_GRANTED)
                permissionsList.add(perm);
        if(!permissionsList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]), PERM_CODE);
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCamera();
    }

    private void requestCamera() {
        if(checkPermissions())
            mCodeScanner.startPreview();
    }

}