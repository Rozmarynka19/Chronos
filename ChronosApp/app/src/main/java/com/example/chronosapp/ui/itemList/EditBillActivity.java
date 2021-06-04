package com.example.chronosapp.ui.itemList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Hashtable;


public class EditBillActivity extends AppCompatActivity implements EditBillBackgroundTaskListener,
                                                                    GetBillBackgroundTaskListener{

    private String itemID, billName, receiverName, bankAccountNumber, paymentTitle, amount, desc,
            paymentDeadline;
    private TextInputEditText billNameEdit, receiverNameEdit, bankAccountNumberEdit,
            paymentTitleEdit, amountEdit, descriptionEdit, paymentDeadlineEdit;
    private Button editBillButton;

    private static final int SCAN_QR = 1;

    private LinearLayout bckArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        Intent details = getIntent();
        itemID =  details.getStringExtra("itemid");
        billName =  details.getStringExtra("itemName");
        Log.d("EditBillActivity - itemID", itemID);
        Log.d("EditBillActivity - billName", billName);

        billNameEdit = findViewById(R.id.billNameEdit);
        receiverNameEdit = findViewById(R.id.reciverNameEdit);
        bankAccountNumberEdit = findViewById(R.id.bankAccountNumberEdit);
        paymentTitleEdit = findViewById(R.id.paymentTitleEdit);
        amountEdit = findViewById(R.id.amountEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        paymentDeadlineEdit = findViewById(R.id.paymentDeadlineEdit);
        editBillButton = findViewById(R.id.addBillButton);
        editBillButton.setText("Edit Bill");

        bckArrow = findViewById(R.id.go_back);

        bckArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getBill(itemID);
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
                String amount = leftSideComma+"."+rightSideComma;
                amountEdit.setText(amount);
            }
            if(!splittedCodedData[4].equals("")) receiverNameEdit.setText(splittedCodedData[4]);
            if(!splittedCodedData[5].equals("")) paymentTitleEdit.setText(splittedCodedData[5]);
        }
    }

    public void sendNewBillToDb(View view) {
        //[] = {itemid, itemname, billRecipient, billRecipientBankAccount,
        // billTransferTitle, billAmount, billDesc, billDeadline}

        billName = billNameEdit.getText().toString();
        receiverName = receiverNameEdit.getText().toString();
        bankAccountNumber = bankAccountNumberEdit.getText().toString();
        paymentTitle = paymentTitleEdit.getText().toString();
        amount = amountEdit.getText().toString();
//        Toast.makeText(this,amount,Toast.LENGTH_LONG).show();
        desc = descriptionEdit.getText().toString();
        paymentDeadline = paymentDeadlineEdit.getText().toString();

        EditBillBackgroundTask editBillBackgroundTask = new EditBillBackgroundTask(this);
        editBillBackgroundTask.execute(itemID, billName, receiverName,
                bankAccountNumber, paymentTitle, amount, desc, paymentDeadline);
    }

    @Override
    public void refreshListOfItems() {
        setResult(RESULT_OK,new Intent());
        this.finish();
    }

    private void getBill(String itemID)
    {
        GetBillBackgroundTask getBillBackgroundTask = new GetBillBackgroundTask(this);
        getBillBackgroundTask.execute(itemID);
    }

    @Override
    public void getBillDetails(Hashtable<String, String> tableOfBillDetails) {
        Log.d("getBillDetails - recipient", tableOfBillDetails.get("Bill_Recipient"));
        Log.d("getBillDetails - bank account", tableOfBillDetails.get("Bill_RecipientsBankAccount"));
        Log.d("getBillDetails - title", tableOfBillDetails.get("Bill_TransferTitle"));
        Log.d("getBillDetails - amount", tableOfBillDetails.get("Bill_Amount"));
        Log.d("getBillDetails - desc", tableOfBillDetails.get("Bill_Desc"));
        Log.d("getBillDetails - deadline", tableOfBillDetails.get("Bill_Deadline"));

        billNameEdit.setText(billName);
        receiverNameEdit.setText(tableOfBillDetails.get("Bill_Recipient"));
        bankAccountNumberEdit.setText(tableOfBillDetails.get("Bill_RecipientsBankAccount"));
        paymentTitleEdit.setText(tableOfBillDetails.get("Bill_TransferTitle"));
        amountEdit.setText(tableOfBillDetails.get("Bill_Amount"));
        descriptionEdit.setText(tableOfBillDetails.get("Bill_Desc"));
        paymentDeadlineEdit.setText(tableOfBillDetails.get("Bill_Deadline"));
    }
}