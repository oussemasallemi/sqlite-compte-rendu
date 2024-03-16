package com.example.databasesql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button ajouter, view;
    private EditText nom, mail, tel;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ajouter = (Button)findViewById(R.id.add_btn);
        view = (Button)findViewById(R.id.view_btn);
        nom = (EditText)findViewById(R.id.editTextTextPersonName);
        mail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        tel = (EditText)findViewById(R.id.editTextPhone);
        db = new DataBase(this);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDb();
            }
        });
    }

    protected void addDb(){
        if (!nom.getText().toString().equalsIgnoreCase("") &&
                !mail.getText().toString().equalsIgnoreCase("") &&
                !tel.getText().toString().equalsIgnoreCase(""))
        {
            Boolean inserted = db.insertData(nom.getText().toString(), mail.getText().toString(), phone.getText().toString());
            if (inserted){
                Toast.makeText(MainActivity.this, "OK",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "echec",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(MainActivity.this, "faire tout le remplissage", Toast.LENGTH_SHORT).show();
        }
    }


    public void viewDb(View view) {
        Intent intent = new Intent(this, ManagingDB.class);
        startActivity(intent);
    }

}