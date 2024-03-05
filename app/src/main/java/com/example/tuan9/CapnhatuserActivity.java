package com.example.tuan9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuan9.model.User;

public class CapnhatuserActivity extends AppCompatActivity {

    EditText edtMaCN,edtTenCN,edtPhoneCN;
    Button btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhatuser);


        addControls();
        xuLyCapNhat();
        xuLyDelete();
    }

    private void xuLyDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CapnhatuserActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int kq=MainActivity.database.delete("tblUser","maU=?", new String[]{edtMaCN.getText().toString()});
                        if(kq>0) finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void xuLyCapNhat() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("tenU",edtTenCN.getText().toString());
                values.put("phoneU",edtPhoneCN.getText().toString());
                int kq=MainActivity.database.update("tblUser",values,"maU=?",new String[]{edtMaCN.getText().toString()});
                if (kq>0)
                    finish();
                else
                    Toast.makeText(CapnhatuserActivity.this,"Update new record Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        edtMaCN=findViewById(R.id.edtMaCN);
        edtTenCN=findViewById(R.id.edtTenCN);
        edtPhoneCN=findViewById(R.id.edtPhoneCN);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);


        Intent intent=getIntent();
        User u= (User) intent.getSerializableExtra("u");
        edtMaCN.setText(u.getMaU().toString());
        edtTenCN.setText(u.getTenU().toString());
        edtPhoneCN.setText(u.getPhoneU().toString());
        edtMaCN.setEnabled(false);
    }
}