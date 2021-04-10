package com.example.thewrittensystem.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thewrittensystem.adapter.CollectionAdapter;
import com.example.thewrittensystem.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class tamil_collection extends Fragment {
    RecyclerView recyclerView;
    private CollectionAdapter.RecyclerViewClickListener listener;
    NavController navController;
    ArrayList<characters> UyirArrayList = new ArrayList<characters>();
    AlertDialog.Builder builder;

    String[] collections = {"Uyir Ezhuthukkal\nஉயிர் எழுத்துக்கள்", "Mei Ezhuthukkal\nமெய் எழுத்துக்கள்", "Uyirmei Ezhuthukkal\nஉயிர்மெய் எழுத்துக்கள்", "Aayudha Ezhuthu\nஆயுத எழுத்து"};
    String[] uyir = {"அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஐ", "ஒ", "ஓ", "ஔ"};
    String[] mei = {"க்", "ங்", "ச்", "ஞ்", "ட்", "ண்", "த்", "ந்", "ப்", "ம்", "ய்", "ர்", "ல்", "வ்", "ழ்", "ள்", "ற்", "ன்"};
    String[] uyirmei = {"க", "கா", "கி", "கீ", "கு", "கூ", "கெ", "கே",	"கை", "கொ", "கோ", "கௌ",
            "ங", "ஙா", "ஙி", "ஙீ", "ஙு", "ஙூ", "ஙெ", "ஙே", "ஙை", "ஙொ", "ஙோ", "ஙௌ",
            "ச", "சா", "சி", "சீ", "சு",	"சூ", "செ",	"சே", "சை", "சொ",	"சோ", "சௌ",
            "ஞ",	"ஞா",	"ஞி",	"ஞீ",	"ஞு",	"ஞூ",	"ஞெ",	"ஞே",	"ஞை",	"ஞொ",	"ஞோ",	"ஞௌ",
            "ட",	"டா",	"டி",	"டீ",	"டு",	"டூ",	"டெ",	"டே",	"டை",	"டொ",	"டோ",	"டௌ",
            "ண",	"ணா",	"ணி",	"ணீ",	"ணு",	"ணூ",	"ணெ",	"ணே",	"ணை",	"ணொ",	"ணோ",	"ணௌ",
            "த", "தா", "தி",	"தீ",	"து",	"தூ",	"தெ",	"தே",	"தை",	"தொ",	"தோ",	"தௌ",
            "ந", "நா",	"நி",	"நீ",	"நு",	"நூ",	"நெ",	"நே",	"நை",	"நொ",	"நோ",	"நௌ",
            "ப",	"பா",	"பி",	"பீ",	"பு",	"பூ",	"பெ",	"பே",	"பை",	"பொ",	"போ",	"பௌ",
            "ம",	"மா",	"மி",	"மீ",	"மு",	"மூ",	"மெ",	"மே",	"மை",	"மொ",	"மோ",	"மௌ",
            "ய",	"யா",	"யி",	"யீ",	"யு",	"யூ",	"யெ",	"யே", 	"யை",	"யொ",	"யோ",	"யௌ",
            "ர",	"ரா",	"ரி",	"ரீ",	"ரு",	"ரூ",	"ரெ",	"ரே",	"ரை",	"ரொ",	"ரோ",	"ரௌ",
            "ல", "லா",	"லி",	"லீ",	"லு",	"லூ",	"லெ",	"லே",	"லை",	"லொ",	"லோ",	"லௌ",
            "வ",	"வா",	"வி",	"வீ",	"வு",	"வூ",	"வெ",	"வே",	"வை",	"வொ",	"வோ",	"வௌ",
            "ழ", "ழா",	"ழி", "ழீ",	"ழு",	"ழூ",	"ழெ",	"ழே",	"ழை",	"ழொ",	"ழோ",	"ழௌ",
            "ள", "ளா",	"ளி",	"ளீ",	"ளு",	"ளூ",	"ளெ",	"ளே",	"ளை",	"ளொ",	"ளோ",	"ளௌ",
            "ற", "றா",	"றி",	"றீ",	"று",	"றூ",	"றெ",	"றே",	"றை",	"றொ",	"றோ",	"றௌ",
            "ன",	"னா",	"னி",	"னீ",	"னு",	"னூ",	"னெ",	"னே",	"னை",	"னொ",	"னோ",	"னௌ"};
    String[] aaidham = {"ஃ"};
    List<ArrayList<characters>> category = Collections.singletonList(UyirArrayList);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        UyirArrayList.add(new characters("அ","0",R.drawable.u0));
        UyirArrayList.add(new characters("ஆ","1",R.drawable.u1));
        UyirArrayList.add(new characters("இ","2",R.drawable.u2));
        UyirArrayList.add(new characters("ஈ","3",R.drawable.u3));
        UyirArrayList.add(new characters("உ","4",R.drawable.u4));
        UyirArrayList.add(new characters("ஊ","5",R.drawable.u5));
        UyirArrayList.add(new characters("எ","6",R.drawable.u6));
        UyirArrayList.add(new characters("ஏ","7",R.drawable.u7));
        UyirArrayList.add(new characters("ஐ","8",R.drawable.u8));
        UyirArrayList.add(new characters("ஒ","9",R.drawable.u9));
        UyirArrayList.add(new characters("ஓ","10",R.drawable.u10));
        UyirArrayList.add(new characters("ஔ","11",R.drawable.u11));
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


        // Get a reference to the ListView, and attach the adapter to the listView.

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tamil_collection, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setAdapter();
        return view;
    }
    public void setAdapter(){
        setOnClickListener();
        CollectionAdapter adapter = new CollectionAdapter(collections,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void setOnClickListener(){
        listener = (v, position) -> {
            if(position == 0)
            {
                Log.d("imagedata", "Position clicked: " + position);
                Bundle bundle = new Bundle();
                bundle.putString("key", collections[position]);
                bundle.putSerializable("characters", category.get(position));
                grid fragment = new grid();
                fragment.setArguments(bundle);
                navController.navigate(R.id.action_tamil_collection_to_grid, bundle);
            }
            else
            {
                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.alert_dialog,null);
                ImageView img = view.findViewById(R.id.image);
                TextView txt = view.findViewById(R.id.achieved);
                img.setImageResource(R.drawable.amaze);
                txt.setText("This feature is still under development, Stay tuned!");

                builder.setView(view)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };

    }



}
