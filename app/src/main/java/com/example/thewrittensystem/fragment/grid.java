package com.example.thewrittensystem.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thewrittensystem.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class grid extends Fragment {

    GridView gridView;
    TextView textView;
    ArrayList<characters> list;
    NavController navController;
    AlertDialog.Builder builder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AtomicReference<View> view = new AtomicReference<>(inflater.inflate(R.layout.fragment_grid, container, false));
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


        // Get a reference to the ListView, and attach the adapter to the listView.
        gridView = view.get().findViewById(R.id.gridViewCharacters);
        textView = view.get().findViewById(R.id.textView);

        Bundle bundle = this.getArguments();
        String data = bundle.getString("key");
        list = (ArrayList<characters>) bundle.getSerializable("characters");
        textView.setText(data);

        CustomAdapter obj = new CustomAdapter(list, getActivity());
        gridView.setAdapter(obj);
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            if(position>=1 && position<=5)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("char", list.get(position));
                navController.navigate(R.id.action_grid_to_canvas, bundle1);
            }
            else
            {
                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
                view.set(layoutInflater.inflate(R.layout.alert_dialog, null));
                ImageView img = view.get().findViewById(R.id.image);
                TextView txt = view.get().findViewById(R.id.achieved);
                img.setImageResource(R.drawable.amaze);
                txt.setText("This feature is still under development, Stay tuned!");

                builder.setView(view.get())
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
        return view.get();
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
