package com.alroy.automessager.ui.calllog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alroy.automessager.MainActivity;
import com.alroy.automessager.R;
import com.alroy.automessager.dbm;
import com.alroy.automessager.ui.home.HomeFragment;

public class CustomMessage extends AppCompatActivity{

    EditText message;
//    DBManager dbManager;
    dbm dbManager;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message);


    dbManager = new dbm(this);

        message = findViewById(R.id.et_messageId);

        recyclerView = findViewById(R.id.contacts_recyclerView);

    }


    public void submitMessage(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            myMessage();
            Intent intent = new Intent(CustomMessage.this, MainActivity.class);
            startActivity(intent);
        }

        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }




    public void myMessage() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }

//     String phoneNumber = ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.contact_name_number)).getText().toString();

//        String mes="maria is gay ";
//        Cursor cursor = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
//
//        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
//        cursor.moveToLast();
//        String phoneNumber =  cursor.getString(number);
//        String messageText = message.getText().toString();
//        Toast.makeText(this , phoneNumber, Toast.LENGTH_SHORT).show();
//
//
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(phoneNumber,null,mes,null,null);


        String messageText = message.getText().toString();
        dbManager.addmessage(messageText);
//        addMessage(messageText);
        if (dbManager.addmessage(messageText).equals("True")) {
            Cursor data = dbManager.getMessage();

            if (data.getCount() ==0) {

                    Toast.makeText(this, "db empty" , Toast.LENGTH_SHORT).show();
            } else
            while (data.moveToNext()) {
                String hello = data.getString(1);
                Toast.makeText(this, " done"+hello, Toast.LENGTH_SHORT).show();
            }

        }

//    public  void addMessage(String message){
//        boolean insertMessage = dbManager.addMessage(message);
//
//        if(insertMessage)
//            Toast.makeText(this ,"done", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this ,"not done", Toast.LENGTH_SHORT).show();
//
//    }
    }
}
