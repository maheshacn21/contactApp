package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;
    static ArrayList<ContactModel> contactList;
    static ArrayList<ContactModel> filteredArrayList;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    MyDBHelper myDBHelper;
    Hello hello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper=new MyDBHelper(this);
        contactList=myDBHelper.fetchContact();

        addButton=findViewById(R.id.addbtn);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddEditContact.class);
                startActivity(intent);
            }
        });

        recyclerView=findViewById(R.id.contactRecyclerView);
        recyclerAdapter=new RecyclerAdapter(MainActivity.this,contactList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnClickListener(new RecyclerAdapter.OnIteMClickListener() {
            @Override
            public void onItemClick(int position) {
               int id=recyclerAdapter.getId(position);
               Intent intent=new Intent(MainActivity.this,Hello.class);
               intent.putExtra("GET_ID",id);
               startActivity(intent);
            }
        });
    }

    //***********----logic to check phone number present in db or not-----************
    Boolean flag;
    public Boolean checkNum(String number){
        if(contactList.size()>0){
            for(int i=0;i<contactList.size();i++){
                if(contactList.get(i).getPhone_no().equals(number)){
                    flag=true;
                    break;
                }
                else{
                    flag=false;
                }
            }
        }
        else{
            flag=false;
        }

       return flag;
    }


    //*************----Search bar----******************************
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem search=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) search.getActionView();
        searchView.setQueryHint("Search contact..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //**********----Search Filter logic----*************************
    private void filteredList(String newText) {
        filteredArrayList=new ArrayList<>();
        int count=0;
        if(!newText.isEmpty()){
            for(ContactModel data:contactList){
                for(int i=0;i<newText.length();i++){
                    if(data.getName().length()>=newText.length()&&data.getName().toLowerCase().charAt(i)==newText.toLowerCase().charAt(i))
                        count++;
                }
                if(newText.length()==count){
                    filteredArrayList.add(data);
                }
                count=0;
            }
        }
        else{
            filteredArrayList=contactList;
        }


        if(filteredArrayList.isEmpty()){
            Toast.makeText(this, "no contact found", Toast.LENGTH_SHORT).show();
        }
        else{
            recyclerAdapter.setilFteredList(filteredArrayList);
        }
    }

}