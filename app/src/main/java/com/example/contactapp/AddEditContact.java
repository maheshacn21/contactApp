package com.example.contactapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddEditContact extends AppCompatActivity {

    private FloatingActionButton addButtonDB;
    private EditText contactName,contactNumber;

    private String name,number;
    ActionBar actionBar;
    MyDBHelper myDBHelper;

    MainActivity main=new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        myDBHelper=new MyDBHelper(this);

        actionBar=getSupportActionBar();

        actionBar.setTitle("Add Contact");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        addButtonDB=findViewById(R.id.addButtonDb);
        contactName=findViewById(R.id.contactName);
        contactNumber=findViewById(R.id.contactNumber);

        addButtonDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    //***************-----Adding contact to DB-----***************************
    public void saveData(){
        name=contactName.getText().toString();
        number=contactNumber.getText().toString();

        if(!name.isEmpty()&&!number.isEmpty()){
            Boolean flag=main.checkNum(number);
            if(flag){
                System.out.println("number already exists");
                popUp();
            }
            else {
                myDBHelper.addContact(name,number);
                contactName.setText("");
                contactNumber.setText("");
                Intent intent=new Intent(AddEditContact.this,MainActivity.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "Please provide contact details", Toast.LENGTH_SHORT).show();
        }


    }

    //************************----Action bar backButton----*******************
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //******************-----Dialog popUp----**********************************
    public void popUp(){
        Dialog dialog = new Dialog(AddEditContact.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button dialogButton=dialog.findViewById(R.id.dialogButton);
        dialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}