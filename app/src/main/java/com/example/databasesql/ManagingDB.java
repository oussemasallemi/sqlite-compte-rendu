package com.example.databasesql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagingDB extends AppCompatActivity {
    private ListView lv;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_db);
        lv = (ListView)findViewById(R.id.list_view);
        dataBase = new DataBase(this);
        viewData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String []items={"modif", "supp"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagingDB.this);
                builder.setTitle("Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which ==0){
                                    showUpdate(ManagingDB.this,lv,position);
                                }
                                else if (which == 1){
                                    delete(lv,position);
                                }
                            }
                        });
                builder.show();
            }
        });
    }
    public void viewData(){
        Cursor c = dataBase.getAllData();
        ArrayList<String> list = new ArrayList<>();

        if (c.getCount() == 0){
            Toast.makeText(ManagingDB.this, " base vide", Toast.LENGTH_SHORT).show();
        }
        else {
            while(c.moveToNext()){
                list.add(c.getString(0)+" "+c.getString(1)+ " " + c.getString(2)+ " " + c.getString(3));
                ListAdapter listAdapter = new ArrayAdapter<>(ManagingDB.this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                lv.setAdapter(listAdapter);
            }
        }
    }
    public void back(View view) {
        Intent intent2 = new Intent(ManagingDB.this, MainActivity.class);
        startActivity(intent2);
    }
    private void showUpdate(Activity ac, ListView lv, int p){
        Dialog dialog = new Dialog(ac);
        dialog.setContentView(R.layout.update_db);
        dialog.setTitle("Update");
        final EditText name = (EditText)dialog.findViewById(R.id.editTextTextPersonName2);
        final EditText mail = (EditText)dialog.findViewById(R.id.editTextTextEmailAddress2);
        final EditText phone = (EditText)dialog.findViewById(R.id.editTextPhone2);
        Button bt = (Button)dialog.findViewById(R.id.button);
        final String[] chaine = lv.getAdapter().getItem(p).toString().split(" ");
        name.setText(chaine[1]);
        mail.setText(chaine[2]);
        phone.setText(chaine[3]);
        /*int width = (int)(ac.getResources().getDisplayMetrics().widthPixels*0.9);
        int height = (int)(ac.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width, height);*/
        dialog.show();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = Integer.parseInt(chaine[0]);
                dataBase.update(name.getText().toString(), mail.getText().toString(), phone.getText().toString(),i);
                Toast.makeText(ManagingDB.this,"Mise a jour avec succes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagingDB.this, ManagingDB.class);
                startActivity(intent);
                viewData();
            }
        });
    }
    private void delete(ListView lv, int p){
        String[] chaine = lv.getAdapter().getItem(p).toString().split(" ");
        int i =Integer.parseInt(chaine[0]);
        dataBase.delete(i);
        Toast.makeText(this, "Suppression avec succes",Toast.LENGTH_SHORT).show();
        viewData();
    }
}