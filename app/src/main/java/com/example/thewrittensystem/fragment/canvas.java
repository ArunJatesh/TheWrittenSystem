package com.example.thewrittensystem.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.thewrittensystem.R;
import com.example.thewrittensystem.ml.MobileNetV2;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


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
//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawableView = view.findViewById(R.id.paintView);
        Button resetButton = view.findViewById(R.id.btnReset);
        Button undoButton = view.findViewById(R.id.btnUndo);
        Button doneButton = view.findViewById(R.id.btnDone);

        config.setStrokeColor(getResources().getColor(R.color.black));
        config.setStrokeWidth(30.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(1.0f);
        config.setCanvasHeight(1400);
        config.setCanvasWidth(1100);

        drawableView.setConfig(config);

        //Sets background image with letter
        drawableView.setBackgroundResource(R.drawable.test);

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
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                try {

                    Bitmap drawnLetter = BitmapFactory.decodeResource(getResources(),R.drawable.test0);
                    //Bitmap drawnLetter = drawableView.obtainBitmap();

                    int modelInputSize = 0;

                    modelInputSize = 4 * 224 * 224 * 3;

                    Bitmap resizedImage = Bitmap.createScaledBitmap(drawnLetter,224,224,true);

                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1,224,224,3}, DataType.FLOAT32);

                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(modelInputSize);

                    byteBuffer.order(ByteOrder.nativeOrder());

                    int[] pixels = new int[224*224];

                    resizedImage.getPixels(pixels,0,resizedImage.getWidth(),0,0,resizedImage.getWidth(),resizedImage.getHeight());

                    for(int pixelValue : pixels)
                    {
                        float r = ((pixelValue >> 16) & 0xFF);
                        float g = ((pixelValue >> 8) & 0xFF) ;
                        float b = (pixelValue & 0xFF) ;

                        byteBuffer.putFloat(r);
                        byteBuffer.putFloat(g);
                        byteBuffer.putFloat(b);
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    MobileNetV2 modelF = MobileNetV2.newInstance(getActivity().getApplicationContext());
                    MobileNetV2.Outputs outputs = modelF.process(inputFeature0);

                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    @org.checkerframework.checker.nullness.qual.NonNull float[] arr = outputFeature0.getFloatArray();

                    float max = arr[0];
                    int index =0;

                    for(int i=1;i<35;i++) {
                        System.out.println("Value : " + arr[i] + "\t" + "Index : " + i + "\n");

                        if(arr[i]>max)
                        {
                            max = arr[i];
                            index = i;
                        }
                    }

                    System.out.println("Value : " + max + "\t" + "Index : " + index );


                    modelF.close();
                }
                catch (Exception e) {
                    // TODO Handle the exception
                    e.printStackTrace();
                }
                drawableView.clear();
            }
        });

    }
}