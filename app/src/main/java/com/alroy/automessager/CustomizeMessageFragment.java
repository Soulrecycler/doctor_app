package com.alroy.automessager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomizeMessageFragment extends Fragment {

    FloatingActionButton fab;




    public CustomizeMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_customize_message, container, false);

        fab = root.findViewById(R.id.fab);

        fab.setVisibility(View.INVISIBLE);

        return  root;
    }


}
