package com.napolean.nonomanews.data.model;

import android.view.View;
import android.widget.Toast;

import java.util.List;

public class User {

    public String about;
    public String id;
    public long karma;
    public List<Long> submitted;

    private View.OnClickListener userClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
        }
    };


    public View.OnClickListener getUserClickListener() {
        return userClickListener;
    }
}
