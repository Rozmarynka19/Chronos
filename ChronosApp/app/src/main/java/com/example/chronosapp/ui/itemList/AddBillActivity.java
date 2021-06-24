package com.example.chronosapp.ui.itemList;

import android.content.Intent;
import android.os.Bundle;

import com.example.chronosapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.gms.common.util.NumberUtils;
import com.google.zxing.common.StringUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AddBillActivity extends AppCompatActivity implements AddBillBackgroundTaskListener {

    private String listID, billName, receiverName, bankAccountNumber, paymentTitle, amount, desc,
            paymentDeadline;
    private TextInputEditText billNameEdit, receiverNameEdit, bankAccountNumberEdit,
            paymentTitleEdit, amountEdit, descriptionEdit, paymentDeadlineEdit;

    private static final int SCAN_QR = 1;
    private static final int SCAN_OCR = 2;

    private LinearLayout bckArrow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        Intent details = getIntent();
        listID =  details.getStringExtra("listid");
        //Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();

        bckArrow = findViewById(R.id.go_back);

        bckArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        billNameEdit = findViewById(R.id.billNameEdit);
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
        startActivityForResult(new Intent(getApplicationContext(), ScannerOCR.class),SCAN_OCR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_QR && resultCode == RESULT_OK) {
            String codedData = data.getStringExtra("codedData");

            if (codedData == null)
                return;

            String[] splittedCodedData = codedData.split("\\|");
            if (splittedCodedData.length == 1) {
                //Toast.makeText(this, "Unrecognized format", Toast.LENGTH_LONG).show();
                return;
            }
            if (!splittedCodedData[2].equals(""))
                bankAccountNumberEdit.setText(splittedCodedData[2]);
            if (!splittedCodedData[3].equals("")) {
                String plainAmount = splittedCodedData[3];
                int plainAmountLen = plainAmount.length();
                String leftSideComma = plainAmount.substring(0, plainAmountLen - 2);
                String rightSideComma = plainAmount.substring(plainAmountLen - 2, plainAmountLen);
                String amount = leftSideComma + "." + rightSideComma;
                amountEdit.setText(amount);
            }
            if (!splittedCodedData[4].equals("")) receiverNameEdit.setText(splittedCodedData[4]);
            if (!splittedCodedData[5].equals("")) paymentTitleEdit.setText(splittedCodedData[5]);
        }
        else if (requestCode == SCAN_OCR && resultCode == RESULT_OK) {
            String codedData = data.getStringExtra("codedData");
            if (codedData == null) {
                return;
            }


            String[] splittedCodedData = codedData.split("\n");

            List<String> wordList = Arrays.asList(splittedCodedData);
            Iterator<String> it = wordList.listIterator();
            try {
                while (it.hasNext()) {
                    String currentLine = it.next();
                    switch (currentLine) {
                        case "Nazwa odbiorcy":
                            receiverNameEdit.setText(it.next());
                            break;
                        case "Na rachunek":
                            bankAccountNumberEdit.setText(it.next());
                            break;
                        case "Kwota":
                            amountEdit.setText(it.next());
                            break;
                        case "Tytuł przelewu":
                            paymentTitleEdit.setText(it.next());
                            break;
                        case "Opis przelewu":
                            descriptionEdit.setText(it.next());
                            break;
                        case "Data operacji":
                            paymentDeadlineEdit.setText(it.next());
                    }
                }
            } catch (NoSuchElementException e) {
                it.hasNext();
            }
        }
    }

    public void sendNewBillToDb(View view) {
        //[] = {listid, itemname, itemtype, billRecipient, billRecipientBankAccount,
        // billTransferTitle, billAmount, billDesc, billDeadline}

        billName = billNameEdit.getText().toString().trim();
        receiverName = receiverNameEdit.getText().toString().trim();
        bankAccountNumber = bankAccountNumberEdit.getText().toString().trim();
        paymentTitle = paymentTitleEdit.getText().toString().trim();
        amount = amountEdit.getText().toString().trim();
        //Toast.makeText(this,amount,Toast.LENGTH_LONG).show();
        desc = descriptionEdit.getText().toString().trim();
        paymentDeadline = paymentDeadlineEdit.getText().toString().trim();

        if(billName.isEmpty()){
            billNameEdit.setError("Bill name is required");
            billNameEdit.requestFocus();
            return;
        }

        if(receiverName.isEmpty()){
            receiverNameEdit.setError("Receiver name is required");
            receiverNameEdit.requestFocus();
            return;
        }

        if(bankAccountNumber.isEmpty()){
            bankAccountNumberEdit.setError("Bank account number is required");
            bankAccountNumberEdit.requestFocus();
            return;
        }

        if(bankAccountNumber.length() < 26){
            bankAccountNumberEdit.setError("Correct bank account number must contain 26 digits.");
            bankAccountNumberEdit.requestFocus();
            return;
        }

        if(paymentTitle.isEmpty()){
            paymentTitleEdit.setError("Payment title is required");
            paymentTitleEdit.requestFocus();
            return;
        }

        if(amount.isEmpty()){
            amountEdit.setError("Amount is required");
            amountEdit.requestFocus();
            return;
        }


        if(billName.isEmpty()){
            billNameEdit.setError("Bill name is required");
            billNameEdit.requestFocus();
            return;
        }
        if(receiverName.isEmpty()){
            receiverNameEdit.setError("Receiver name is required");
            receiverNameEdit.requestFocus();
            return;
        }
        if(bankAccountNumber.isEmpty()){
            bankAccountNumberEdit.setError("Bank account number is required");
            bankAccountNumberEdit.requestFocus();
            return;
        }
        if(paymentTitle.isEmpty()){
            paymentTitleEdit.setError("Payment title is required");
            paymentTitleEdit.requestFocus();
            return;
        }
        if(amount.isEmpty()){
            amountEdit.setError("Amount is required");
            amountEdit.requestFocus();
            return;
        }


        AddBillBackgroundTask addBillBackgroundTask = new AddBillBackgroundTask(this);
        addBillBackgroundTask.execute(listID, billName, ItemTypes.Bill.toString(), receiverName,
                bankAccountNumber, paymentTitle, amount, desc, paymentDeadline);
    }

    @Override
    public void refreshListOfItems() {
        setResult(RESULT_OK,new Intent());
        this.finish();
    }
}