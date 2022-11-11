package com.prakharsharma.imusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.Main_layout1);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        listView = findViewById(R.id.listview);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ArrayList<File> mysongs = fetchsongs(Environment.getExternalStorageDirectory());
                        String [] data = new String[mysongs.size()];
                        for(int i = 0 ; i< mysongs.size();i++){
                            data[i] = mysongs.get(i).getName().replace(".mp3","");
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String >
                                (MainActivity.this, android.R.layout.simple_list_item_1,data){
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text = (TextView) view.findViewById(android.R.id.text1);
                                text.setTextColor(Color.BLACK);
                                return view;
                            }

                        };


                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(MainActivity.this,individualsongs.class);
                                String senddata = listView.getItemAtPosition(position).toString();
                                intent.putExtra("song",mysongs);
                                intent.putExtra("current",senddata);
                                intent.putExtra("position",position);
                                startActivity(intent);


                            }
                        });

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymain,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.setting:
                Intent intent = new Intent(getApplicationContext(), settingo.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    public ArrayList<File> fetchsongs(File file){

        ArrayList arrayList = new ArrayList();
        File [] songs = file.listFiles();
        if(songs != null){
            for(File myfile : songs){
                if(!myfile.isHidden() && myfile.isDirectory()){
                    arrayList.addAll(fetchsongs(myfile));
                }
                else{
                    if(!myfile.getName().startsWith(".") && !myfile.getName().startsWith("Call") && myfile.getName().endsWith(".mp3")){
                        arrayList.add(myfile);
                    }
                }

            }
        }

        return arrayList;

    }


}