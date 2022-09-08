package com.example.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {
    ArrayList<ContactModel> contactList;
    Context context;

    //*********-----Passing filtered list using search----***************
    public void setilFteredList(ArrayList<ContactModel> contactList){
        this.contactList=contactList;
        notifyDataSetChanged();
    }

    RecyclerAdapter(Context context,ArrayList<ContactModel> contactList){
        this.context=context;
        this.contactList=contactList;

    }

    public int getId(int position){
        return contactList.get(position).getId();
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.row_model,parent,false);
        viewHolder viewHolder=new viewHolder(v,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(contactList.get(position).getName().toString());
        holder.iconText.setText(((contactList.get(position).getName()).charAt(0)+""));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    private OnIteMClickListener mListener;
    public interface OnIteMClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnIteMClickListener listener){
        mListener=listener;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView iconText,name;
        public viewHolder(@NonNull View itemView,OnIteMClickListener listener) {
            super(itemView);

            iconText= itemView.findViewById(R.id.iconText);
            name=itemView.findViewById(R.id.contactName);

            //********-----click listener on name----****************
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
