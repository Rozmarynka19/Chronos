package com.example.chronosapp.ui.itemList;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.chronosapp.R;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AddBillActivity extends AppCompatActivity implements AddBillBackgroundTaskListener {

    private String listID, billName, receiverName, bankAccountNumber, paymentTitle, amount, desc,
            paymentDeadline, paymentDate, paymentTime;
    private TextInputEditText billNameEdit, receiverNameEdit, bankAccountNumberEdit,
            paymentTitleEdit, amountEdit, descriptionEdit, paymentDateEdit, paymentTimeEdit;

    private static final int SCAN_QR = 1;
    private static final int SCAN_OCR = 2;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private LinearLayout bckArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        Intent details = getIntent();
        listID =  details.getStringExtra("listid");
        Toast.makeText(this, listID, Toast.LENGTH_SHORT).show();
        initDatePicker();
        initTimePicker();
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
        paymentDateEdit = findViewById(R.id.paymentDateEdit);
        paymentDateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
                else
                    datePickerDialog.hide();
            }
        });
        paymentTimeEdit = findViewById(R.id.paymentTimeEdit);
        paymentTimeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    timePickerDialog.show();
                else
                    timePickerDialog.hide();
            }
        });
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = createTime(hourOfDay, minute);
                paymentTimeEdit.setText(time);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hour = cal.HOUR_OF_DAY;
        int minute = cal.MINUTE;

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, false);

    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = createDate(day, month, year);
                paymentDateEdit.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String createDate(int day, int month, int year)
    {
        String picked_day = String.valueOf(day);
        if(picked_day.length() == 1)
            picked_day = "0" + picked_day;

        String picked_month = String.valueOf(month);
        if(picked_month.length() == 1)
            picked_month = "0" + picked_month;

        return picked_day + "-" + picked_month + "-" + year;
    }

    private String createTime(int hour, int minute){

        String picked_hour = String.valueOf(hour);
        if(picked_hour.length() == 1)
            picked_hour = "0" + picked_hour;

        String picked_minute = String.valueOf(minute);
        if(picked_minute.length() == 1)
            picked_minute = "0" + picked_minute;

        return picked_hour + ":" + picked_minute;
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
                Toast.makeText(this, "Unrecognized format", Toast.LENGTH_LONG).show();
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
                            //paymentDeadlineEdit.setText(it.next());
                            String[] splittedDeadline = it.next().split(" ");
                            if(splittedDeadline.length > 0) {
                                paymentDateEdit.setText(splittedDeadline[0]);
                                paymentTimeEdit.setText(splittedDeadline[1]);
                            }
                    }
                }
            } catch (NoSuchElementException e) {
                it.hasNext();
            }
        }
    }

    private int check_errors() {
        int errors = 0;
        if(billName.isEmpty() || receiverName.isEmpty() || bankAccountNumber.isEmpty() || paymentTitle.isEmpty() || amount.isEmpty()) {
            if(billName.isEmpty()) {
                billNameEdit.setError("Bill name is required");
                errors += 1;
            }
            if(receiverName.isEmpty()) {
                receiverNameEdit.setError("Receiver name is required");
                errors += 1;
            }
            if(bankAccountNumber.isEmpty()) {
                bankAccountNumberEdit.setError("Bank account number is required");
                errors += 1;
            }
            else if(bankAccountNumber.length() < 26){
                bankAccountNumberEdit.setError("Correct bank account number must contain 26 digits.");
                bankAccountNumberEdit.requestFocus();
                errors += 1;
            }
            if(paymentTitle.isEmpty()) {
                paymentTitleEdit.setError("Payment title is required");
                errors += 1;
            }
            if(amount.isEmpty()) {
                amountEdit.setError("Amount is required");
                errors += 1;
            }

        }
        return errors;
    }

    public void sendNewBillToDb(View view) {
        //[] = {listid, itemname, itemtype, billRecipient, billRecipientBankAccount,
        // billTransferTitle, billAmount, billDesc, billDeadline}

        billName = billNameEdit.getText().toString().trim();
        receiverName = receiverNameEdit.getText().toString().trim();
        bankAccountNumber = bankAccountNumberEdit.getText().toString().trim();
        paymentTitle = paymentTitleEdit.getText().toString().trim();
        amount = amountEdit.getText().toString().trim();
        Toast.makeText(this,amount,Toast.LENGTH_LONG).show();
        desc = descriptionEdit.getText().toString().trim();
        paymentDate = paymentDateEdit.getText().toString();
        paymentTime = paymentTimeEdit.getText().toString();

        if(check_errors() > 0) {
            return;
        }

        paymentDeadline = "";
        paymentDeadline = paymentDeadline.concat(paymentDate).concat(" ").concat(paymentTime);

/*        if(billName.isEmpty()){
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
        }*/


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