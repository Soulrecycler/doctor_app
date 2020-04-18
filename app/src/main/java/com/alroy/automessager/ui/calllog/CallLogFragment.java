package com.alroy.automessager.ui.calllog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alroy.automessager.CallsAdapter;
import com.alroy.automessager.CallsModel;
import com.alroy.automessager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class CallLogFragment extends Fragment implements CallsAdapter.OnNoteListener {

    private CallLogViewModel callLogViewModel;
    private RecyclerView recyclerView;
    public String ii;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        callLogViewModel =
                ViewModelProviders.of(this).get(CallLogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calls, container, false);

        callLogViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });



        recyclerView = root.findViewById(R.id.contacts_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        CallsAdapter adapter = new CallsAdapter(getContext(), getCallLog(), this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }

    public List<CallsModel> getCallLog() {

        List<CallsModel> list = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);


        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            String strDateFormat;

            Date currentDate = new Date();   // Current System Date and time is assigned to objDate
            Date objCurrentDate = new Date(Long.valueOf(cursor.getString(date)));


            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            Calendar yest = Calendar.getInstance();

            cal1.setTime(currentDate);
            cal2.setTime(objCurrentDate);
            yest.setTime(currentDate);

            yest.add(Calendar.DATE, -1);


            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

            boolean yesterday = yest.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);


            if (sameDay) {
                strDateFormat = "hh:mm:ss a "; //Date format is Specified
                Date objDate = new Date(Long.valueOf(cursor.getString(date))); // Current System Date and time is assigned to objDate

                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); //Date format string is passed as an argument to the Date format object


                String callType = cursor.getString(type);
                String dir = null;
                int dirCode = Integer.parseInt(callType);
                switch (dirCode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "Outgoing Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate), dir + " ,", R.drawable.ic_call_made_24px));

                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "Incoming Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate), dir + " ,", R.drawable.ic_call_received_24px));

                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "Missed Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate), dir + " ,", R.drawable.ic_call_missed_24px));

                        break;
                }


            } else if (yesterday) {
                strDateFormat = "hh:mm a  "; //Date format is Specified
                Date objDate = new Date(Long.valueOf(cursor.getString(date))); // Current System Date and time is assigned to objDate

                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); //Date format string is passed as an argument to the Date format object

                String callType = cursor.getString(type);
                String dir = null;
                int dirCode = Integer.parseInt(callType);
                switch (dirCode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "Outgoing Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate) + "Yesterday", dir + " ,", R.drawable.ic_call_made_24px));
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "Incoming Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate) + "Yesterday", dir + " ,", R.drawable.ic_call_received_24px));
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "Missed Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate) + "Yesterday", dir + " ,", R.drawable.ic_call_missed_24px));

                        break;
                }


            } else {
                strDateFormat = "hh:mm a , dd-MMM-yy"; //Date format is Specified
                Date objDate = new Date(Long.valueOf(cursor.getString(date))); // Current System Date and time is assigned to objDate

                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); //Date format string is passed as an argument to the Date format object

                String callType = cursor.getString(type);
                String dir = null;
                int dirCode = Integer.parseInt(callType);
                switch (dirCode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "Outgoing Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate), dir + " ,", R.drawable.ic_call_made_24px));
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "Incoming Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate), dir + " ,", R.drawable.ic_call_received_24px));
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "Missed Call";
                        list.add(new CallsModel(cursor.getString(number), objSDF.format(objDate), dir + " ,", R.drawable.ic_call_missed_24px));
                        break;
                }


            }


        }
        cursor.close();
        Collections.reverse(list);


//        CustomMessage customMessage = new CustomMessage();
//        customMessage.myMessage(title1);

        return list;
    }


    @Override
    public void onNoteClick(int position, View view) {
//       getCallLog().get(position);

        String title1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.contact_name_number)).getText().toString();

//        ii= ((TextView) recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.contact_name_number)).getText().toString();


        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber="tel:" + title1;
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);

    }

}