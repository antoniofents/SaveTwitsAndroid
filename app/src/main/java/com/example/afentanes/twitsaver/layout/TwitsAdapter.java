package com.example.afentanes.twitsaver.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.afentanes.twitsaver.R;
import com.example.afentanes.twitsaver.Twit;

/**
 * Created by afentanes on 10/31/17.
 */

public class TwitsAdapter extends ArrayAdapter<Twit> {



    public TwitsAdapter(@NonNull Context context, @NonNull Twit[] objects) {
        super(context, R.layout.twit_desc, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(getContext());
        View customRowView = inflater.inflate(R.layout.twit_desc, parent, false);
        TextView author=  customRowView.findViewById(R.id.twit_autor) ;
        TextView twitText=  customRowView.findViewById(R.id.twit_text) ;
        Twit twit = this.getItem(position);
        author.setText(twit.author);
        twitText.setText(twit.text);

        return  customRowView;
    }
}
