package com.example.thewrittensystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.thewrittensystem.R;

public class grid extends Fragment {

    GridView gridView;
    TextView textView;
    String[] list;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        gridView = view.findViewById(R.id.gridViewCharacters);
        textView = view.findViewById(R.id.textView);

        Bundle bundle = this.getArguments();
        String data = bundle.getString("key");
        list = bundle.getStringArray("characters");
        textView.setText(data);

        CustomAdapter obj = new CustomAdapter(list,getActivity());
        gridView.setAdapter(obj);
        gridView.setOnItemClickListener((parent, view1, position, id) -> navController.navigate(R.id.action_grid_to_canvas));
        return view;
    }
    public class CustomAdapter extends BaseAdapter {
        private final String[] txtCollections;
        private Context context;

        public CustomAdapter(String[] txtCollections, Context context) {
            this.txtCollections = txtCollections;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.row_items, null);

            TextView textCollections = view.findViewById(R.id.txtCollections);

            textCollections.setText(txtCollections[position]);
            return view;
        }
    }

}