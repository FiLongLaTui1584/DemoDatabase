package com.example.tuan9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tuan9.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String DATABASE_NAME="mydatabase5";
    public String DB_SUFFUX_PATH="/databases/";

    public static SQLiteDatabase database=null;

    ListView lvUser;
    Button btnOpen;
    ArrayAdapter<User> adapterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();
        addControls();

        addEvent();
        xulyCapNhat();
    }

    private void xulyCapNhat() {
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user=adapterUser.getItem(position);
                Intent intent=new Intent(MainActivity.this,CapnhatuserActivity.class);
                intent.putExtra("u",user );
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void addEvent() {
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ThemuserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.rawQuery("select * from tblUser",null);
        adapterUser.clear();
        while (cursor.moveToNext()){
            String ma=cursor.getString(0);
            String ten=cursor.getString(1);
            String phone=cursor.getString(2);
            User u=new User(ma,ten,phone);
            adapterUser.add(u);
        }
        cursor.close();
    }

    private void addControls() {
        btnOpen=findViewById(R.id.btnOpenNew);
        lvUser=findViewById(R.id.lvUser);
        adapterUser=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvUser.setAdapter(adapterUser);
    }


    private void processCopy() {
        try {
            File file=getDatabasePath(DATABASE_NAME);
            if(!file.exists())
                copyDatabaseFromAsset();
            Toast.makeText(this,"Copy Database Successful",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(this,"Copy Database Fail",Toast.LENGTH_SHORT).show();
        }
    }

    private void copyDatabaseFromAsset() {
        try{
            InputStream inputFile=getAssets().open(DATABASE_NAME);
            String outputFileName=getDatabasePath();
            File file=new File(getApplicationInfo().dataDir+DB_SUFFUX_PATH);
            if(!file.exists())
                file.mkdir();
            OutputStream outFile=new FileOutputStream(outputFileName);
            byte[]buffer=new byte[1024];
            int length;
            while((length= inputFile.read(buffer))>0)outFile.write(buffer,0,length);
            outFile.flush();
            outFile.close();
            inputFile.close();
        }
        catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }
    public String getDatabasePath(){
        return getApplicationInfo().dataDir+DB_SUFFUX_PATH+DATABASE_NAME;
    }
}