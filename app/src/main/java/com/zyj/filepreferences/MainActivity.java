package com.zyj.filepreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zyj.filepreferences.lib.FilePreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilePreferences.put( this , "abc" , "哈哈哈") ;
        String s = (String) FilePreferences.get( this , "abc" );
        Toast.makeText(this, "" + s , Toast.LENGTH_SHORT).show();
    }
}
