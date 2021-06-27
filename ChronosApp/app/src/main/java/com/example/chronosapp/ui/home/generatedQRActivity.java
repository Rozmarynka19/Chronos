package com.example.chronosapp.ui.home;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class generatedQRActivity extends AppCompatActivity {
    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generated_qr);

        qrCodeIV = findViewById(R.id.idIVQrcode);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimen = Math.min(width, height);
        dimen = dimen * 3 / 4;

        String account_name = getIntent().getStringExtra("Account_Name");
        String account_number = getIntent().getStringExtra("Account_Number");

        String toQR = "";
       // ?|kod kraju (PL)|nr konta odbiorcy|kwota(bez przecinków)|nazwa odbiorcy|tytuł|?(np. nr ewidencyjny klienta)|?(waluta: PLN)|
        toQR = toQR.concat("?|").concat("|").concat(account_number).concat("|").concat("|").concat(account_name).concat("|").concat("|?").concat("|?").concat("|");
        System.out.println("QR CODE: " + toQR);

        qrgEncoder = new QRGEncoder(toQR, null, QRGContents.Type.TEXT, dimen);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }

    }
}
