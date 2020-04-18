package com.alroy.automessager.ui.calllog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alroy.automessager.DBManager;
import com.alroy.automessager.R;

public class CustomMessage extends AppCompatActivity{

    EditText message;
    DBManager dbManager;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message);


    dbManager = new DBManager(this);

        message = findViewById(R.id.et_messageId);

        recyclerView = findViewById(R.id.contacts_recyclerView);

    }


    public void submitMessage(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            myMessage();

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
        addMessage(messageText);
//       if( dbManager.addMessage(messageText).equals("true")){
//           Cursor data = dbManager.getMessage();
//       String hello= data.getString(1);
//           Toast.makeText(this ,"done"+ hello, Toast.LENGTH_SHORT).show();
//
//       }
//       else
//           Toast.makeText(this ,"not done", Toast.LENGTH_SHORT).show();





    }

    public  void addMessage(String message){
        boolean insertMessage = dbManager.addMessage(message);

        if(insertMessage)
            Toast.makeText(this ,"done", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this ,"not done", Toast.LENGTH_SHORT).show();

    }

}
