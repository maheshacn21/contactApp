package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Hello extends AppCompatActivity {
    ActionBar actionBar;
    MyDBHelper myDBHelper;
    TextView displayName,displayNumber;
    EditText editName,editNumber;
    LinearLayout displayLayout1;
    LinearLayout displayLayout2;
    LinearLayout editLayout1;
    LinearLayout editLayout2;

    Button updateButton;

    Dialog dialog;
    int id;


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i= item.getItemId();
        switch (i){
            case R.id.editButton:
                updateButton=findViewById(R.id.updateButton);
                displayLayout1=findViewById(R.id.displayLayout1);
                displayLayout2=findViewById(R.id.displayLayout2);
                editLayout1=findViewById(R.id.editLayout1);
                editLayout2=findViewById(R.id.editLayout2);
                displayLayout1.setVisibility(displayLayout1.GONE);
                displayLayout2.setVisibility(displayLayout2.GONE);
                editLayout1.setVisibility(editLayout1.VISIBLE);
                editLayout2.setVisibility(editLayout2.VISIBLE);
                updateButton.setVisibility(updateButton.VISIBLE);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       String name=editName.getText().toString();
                       String number=editNumber.getText().toString();
                       int i=id;

                       myDBHelper=new MyDBHelper(Hello.this);
                       myDBHelper.update(name,number,i);
                       Intent intent=new Intent(Hello.this,MainActivity.class);
                       startActivity(intent);
                    }
                });
                break;

            case R.id.deleteButton:
                popUp();
                break;

            case R.id.shareButton:
                String s=displayName.getText().toString()+"\n"+displayNumber.getText().toString();
               Intent shareIntent=new Intent();
               shareIntent.setAction(Intent.ACTION_SEND);
               shareIntent.putExtra(Intent.EXTRA_TEXT,s);
               shareIntent.setType("text/plain");
               shareIntent=Intent.createChooser(shareIntent,"Share via: ");
               startActivity(shareIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        actionBar=getSupportActionBar();

        actionBar.setTitle("Contact");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        displayName=findViewById(R.id.displayName);
        displayNumber=findViewById(R.id.displayNumber);
        editName=findViewById(R.id.editName);
        editNumber=findViewById(R.id.editNumber);

        Intent intent=getIntent();
        id=intent.getIntExtra("GET_ID",0);

        myDBHelper=new MyDBHelper(this);
        Cursor cursor=myDBHelper.getDataById(id);

        System.out.println("Hello");
        while(cursor.moveToNext()){
            displayName.setText(cursor.getString(1));
            displayNumber.setText(cursor.getString(2));
            editName.setText(cursor.getString(1));
            editNumber.setText(cursor.getString(2));
        }


    }

    //************************----Action bar backButton----*******************
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void popUp(){
        Dialog dialog = new Dialog(Hello.this);
        dialog.setContentView(R.layout.dialog2_layout);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button okButton=dialog.findViewById(R.id.popupOkayButton);
        Button cancelButton=dialog.findViewById(R.id.popupCancelButton);
        dialog.show();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDBHelper.deleteById(id);
                Intent intent=new Intent(Hello.this,MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}