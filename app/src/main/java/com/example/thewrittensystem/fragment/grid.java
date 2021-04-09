package com.example.thewrittensystem.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thewrittensystem.R;

import java.io.Serializable;
import java.util.ArrayList;

public class grid extends Fragment {

    GridView gridView;
    TextView textView;
    ArrayList<characters> list;
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


        // Get a reference to the ListView, and attach the adapter to the listView.
        gridView = view.findViewById(R.id.gridViewCharacters);
        textView = view.findViewById(R.id.textView);

        Bundle bundle = this.getArguments();
        String data = bundle.getString("key");
        list = (ArrayList<characters>) bundle.getSerializable("characters");
        textView.setText(data);

        CustomAdapter obj = new CustomAdapter(list, getActivity());
        gridView.setAdapter(obj);
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("char",  list.get(position));

            navController.navigate(R.id.action_grid_to_canvas, bundle1);

        });
        return view;
    }

    public class CustomAdapter extends BaseAdapter {
        private final ArrayList<characters> txtCollections;
        private Context context;

        public CustomAdapter(ArrayList<characters> txtCollections, Context context) {
            this.txtCollections = txtCollections;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public characters getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.row_items, parent, false);
            }

            characters currentCharacter = list.get(position);

            TextView textCollections = listItemView.findViewById(R.id.txtCollections);

            textCollections.setText(currentCharacter.getTxt());

            return listItemView;
        }
       /* View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fragment_canvas, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        characters currentCharacter = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView =  listItemView.findViewById(R.id.letter);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentCharacter.getTxt());

        // Find the TextView in the list_item.xml layout with the ID version_number

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = listItemView.findViewById(R.id.paintView);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        iconView.setImageResource(currentCharacter.getImg());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }*/

    }


}
