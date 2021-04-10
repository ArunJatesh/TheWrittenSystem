package com.example.thewrittensystem.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thewrittensystem.R;
import com.example.thewrittensystem.ml.MobileNetV2;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

public class canvas extends Fragment {

    int counter =0;

    private DrawableView drawableView;
    characters list;
    TextView txtView ;

    private DrawableViewConfig config = new DrawableViewConfig();
    AlertDialog.Builder builder;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        return inflater.inflate(R.layout.fragment_canvas, container, false);

    }
    //
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtView = view.findViewById(R.id.letter);
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
        list = (characters) getArguments().getSerializable("char");
        characters currentCharacter = list;

        txtView.setText(currentCharacter.getTxt());


        drawableView.setBackgroundResource(currentCharacter.getmImageResourceId());


        drawableView.setConfig(config);

        //Sets background image with letter

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

                    //Bitmap drawnLetter = BitmapFactory.decodeResource(getResources(),R.drawable.test034);
                    Bitmap drawnLetter = drawableView.obtainBitmap();
                    drawnLetter.setHasAlpha(true);
                    Bitmap newBitmap = Bitmap.createBitmap(drawnLetter.getWidth(), drawnLetter.getHeight(), drawnLetter.getConfig());
                    Canvas canvasbn = new Canvas(newBitmap);
                    canvasbn.drawColor(Color.WHITE);
                    canvasbn.drawBitmap(drawnLetter, 0, 0, null);

                    try{
                        String path = Environment.getExternalStorageDirectory().toString();
                        OutputStream fOut = null;

                        File file = new File(path, "drawing"+counter+".png"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                        fOut = new FileOutputStream(file);
                        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush(); // Not really required
                        fOut.close(); // do not forget to close the stream
                        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                        counter+=1;

                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    int modelInputSize = 0;

                    modelInputSize = 4 * 224 * 224 * 3;

                    Bitmap resizedImage = Bitmap.createScaledBitmap(newBitmap,224,224,true);

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

                    double max = arr[0];
                    int index =0;

                    for(int i=1;i<35;i++) {
                        if(arr[i]>max)
                        {
                            max = arr[i];
                            index = i;
                        }
                    }
                    modelF.close();

                    builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
                    View view = layoutInflater.inflate(R.layout.alert_dialog,null);
                    ImageView img = view.findViewById(R.id.image);
                    TextView txt = view.findViewById(R.id.achieved);
                    int id = Integer.valueOf(list.getId());
                    if(id == index)
                    {
                        img.setImageResource(R.drawable.happy);
                        txt.setText("You did it!");
                        builder.setView(view)
                                .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        navController.popBackStack();
                                    }
                                })
                                .setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else
                    {
                        img.setImageResource(R.drawable.sad);
                        txt.setText("You can do better!");
                        builder.setView(view)
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Go back", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        navController.popBackStack();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
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