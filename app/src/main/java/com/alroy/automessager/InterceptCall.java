package com.alroy.automessager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.alroy.automessager.ui.calllog.CustomMessage;

public class InterceptCall extends BroadcastReceiver {
    private static int i=-1;
    dbm dbManager;
    MainActivity m = new MainActivity();
    String message;


    CustomMessage customMessage = new CustomMessage();
    @Override
    public void onReceive(Context context, Intent intent) {

        dbManager = new dbm(context);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(null, new String[]{Manifest.permission.READ_CALL_LOG}, 1);

        }

        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                m.flag=1;
                Toast.makeText(context ,"You are getting a call", Toast.LENGTH_SHORT).show();


            }

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Toast.makeText(context ,"You have picked the call you MORON", Toast.LENGTH_SHORT).show();
            }

            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
                Toast.makeText(context, "The call has ended/Call cut", Toast.LENGTH_SHORT).show();

                    if (m.flag==1) {

                        Cursor data = dbManager.getMessage();

                        if (data.getCount() ==0) {

                            Toast.makeText(context, "db empty" , Toast.LENGTH_SHORT).show();
                        } else
                            while (data.moveToNext()) {
                                 message = data.getString(1);
                                Toast.makeText(context, " done"+message, Toast.LENGTH_SHORT).show();

                            }
//                        String mes = "doc is born";
                        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                                null, null, null, null);

                        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                        cursor.moveToLast();
                        String phoneNumber = cursor.getString(number);


                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                        m.flag= 0;
//     String phoneNumber = ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.contact_name_number)).getText().toString();
                    }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
