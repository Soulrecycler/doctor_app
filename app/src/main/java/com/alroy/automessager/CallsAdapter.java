package com.alroy.automessager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alroy.automessager.ui.calllog.CustomMessage;

import java.util.List;

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private Context mcontext;
    private List<CallsModel> mcallsModelList;
    private OnNoteListener mOnNoteListener;

    public CallsAdapter(Context context , List<CallsModel> listCalls, OnNoteListener onNoteListener) {
        mcontext = context;
        mcallsModelList = listCalls;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mcontext);

        View view = layoutInflater.inflate(R.layout.single_contact_item, parent,false);

       return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView number ,date,type;
        int resource = mcallsModelList.get(position).getCallTypeLogo();


        number = holder.number;
        date = holder.date;
        type = holder.typeOfCall;


        holder.setProductImage(resource);
        number.setText(mcallsModelList.get(position).getNumber());
        date.setText(mcallsModelList.get(position).getDate());
        type.setText(mcallsModelList.get(position).getTypeOfCall());






    }

    @Override
    public int getItemCount() {
        return mcallsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView number ,date,typeOfCall;
        private ImageView callTypeLoog;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            number = itemView.findViewById(R.id.contact_name_number);
            date = itemView.findViewById(R.id.call_date);
            typeOfCall = itemView.findViewById(R.id.type_of_call);
            callTypeLoog = itemView.findViewById(R.id.call_type_icon);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        private void setProductImage(int resource)
        {
            callTypeLoog.setImageResource(resource);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition(),itemView);
        }
    }

    public  interface OnNoteListener {
        void onNoteClick(int position, View view);
    }





}
