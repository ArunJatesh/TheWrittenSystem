package com.example.thewrittensystem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.thewrittensystem.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("The Written System");

        NavController navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph()).build();

        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);




        //TextView textView = (TextView) findViewById(R.id.textview);

        //Typeface typeface = ResourcesCompat.getFont(this, R.font.mukta_malar_regular);

        //textView.setText("வணக்கம்");

        //textView.setTypeface(typeface);
    }
}