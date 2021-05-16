package com.example.chronosapp.ui.itemList;

import android.content.Intent;
import android.os.Bundle;

import com.example.chronosapp.R;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;


public class AddBillActivity extends AppCompatActivity {

    private String listID;
    private TextInputEditText receiverNameEdit,
            bankAccountNumberEdit,
            paymentTitleEdit,
            amountEdit,
            descriptionEdit,
            paymentDeadlineEdit;

    private static final int SCAN_QR = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        Intent details = getIntent();
        listID =  details.getStringExtra("listid");
        Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();

        receiverNameEdit = findViewById(R.id.reciverNameEdit);
        bankAccountNumberEdit = findViewById(R.id.bankAccountNumberEdit);
        paymentTitleEdit = findViewById(R.id.paymentTitleEdit);
        amountEdit = findViewById(R.id.amountEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        paymentDeadlineEdit = findViewById(R.id.paymentDeadlineEdit);
    }

    public void onClickScanQR(View view){
        startActivityForResult(new Intent(getApplicationContext(), ScannerQR.class),SCAN_QR);
    }

    public void onClickScanOCR(View view) {
        //TODO: implement scanning document with OCR
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SCAN_QR && resultCode == RESULT_OK)
        {
            String codedData = data.getStringExtra("codedData");

            if(codedData == null)
                return;

            String[] splittedCodedData = codedData.split("\\|");
            if(splittedCodedData.length == 1)
            {
                Toast.makeText(this,"Unrecognized format",Toast.LENGTH_LONG).show();
                return;
            }
            if(!splittedCodedData[2].equals("")) bankAccountNumberEdit.setText(splittedCodedData[2]);
            if(!splittedCodedData[3].equals(""))
            {
                String plainAmount = splittedCodedData[3];
                int plainAmountLen = plainAmount.length();
                String leftSideComma = plainAmount.substring(0,plainAmountLen-2);
                String rightSideComma = plainAmount.substring(plainAmountLen-2,plainAmountLen);
                String amount = leftSideComma+","+rightSideComma;
                amountEdit.setText(amount);
            }
            if(!splittedCodedData[4].equals("")) receiverNameEdit.setText(splittedCodedData[4]);
            if(!splittedCodedData[5].equals("")) paymentTitleEdit.setText(splittedCodedData[5]);
        }
    }
}