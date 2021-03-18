package com.example.thewrittensystem.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.example.thewrittensystem.R;

import java.io.ByteArrayOutputStream;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

public class canvas extends Fragment {

    private DrawableView drawableView;
    private DrawableViewConfig config = new DrawableViewConfig();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_canvas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawableView = view.findViewById(R.id.paintView);
        Button resetButton = view.findViewById(R.id.btnReset);
        Button undoButton = view.findViewById(R.id.btnUndo);
        Button doneButton = view.findViewById(R.id.btnDone);

        config.setStrokeColor(getResources().getColor(R.color.red));
        config.setStrokeWidth(30.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(1.0f);
        config.setCanvasHeight(1400);
        config.setCanvasWidth(1100);

        drawableView.setConfig(config);

        //Sets background image with letter
        drawableView.setBackgroundResource(R.drawable.bg);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.clear();
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.undo();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap drawnLetter = drawableView.obtainBitmap();
                Bitmap PNGCompress = codec(drawnLetter, Bitmap.CompressFormat.PNG, 100);
                //Verify PNGCompress with letter using ML API


                drawableView.clear();
            }
        });

    }

    //Function to Convert bitmap to given format
    private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);
        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}