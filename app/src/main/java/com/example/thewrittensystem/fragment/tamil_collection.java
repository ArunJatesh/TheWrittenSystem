package com.example.thewrittensystem.fragment;

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

import com.example.thewrittensystem.adapter.CollectionAdapter;
import com.example.thewrittensystem.R;

import java.util.Arrays;
import java.util.List;

public class tamil_collection extends Fragment {
    RecyclerView recyclerView;
    private CollectionAdapter.RecyclerViewClickListener listener;
    NavController navController;
    String[] collections = {"Word1", "Word2", "Word3", "Word4"};
    String[] word1 = {"B", "U", "N", "D","L","E"};
    String[] word2 = {"V", "I", "E", "W"};
    String[] word3 = {"T", "A", "M", "I","L"};
    String[] word4 = {"F", "R", "A", "G","M","E","N","T","S"};
    List<String[]> category = Arrays.asList(word1, word2, word3, word4);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
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
            Log.d("imagedata","Position clicked: "+ position);
            Bundle bundle = new Bundle();
            bundle.putString("key", collections[position]);
            bundle.putStringArray("characters", category.get(position));

            grid fragment = new grid();
            fragment.setArguments(bundle);
            navController.navigate(R.id.action_tamil_collection_to_grid, bundle);
        };

    }



}
