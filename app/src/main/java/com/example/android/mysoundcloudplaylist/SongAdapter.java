package com.example.android.mysoundcloudplaylist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {
    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            if(getItem(position).getSongName() == null)
            {
                //for ArtistFragment
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.fragment_artist, parent, false);
            }
            else
            {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.fragment_song, parent, false);
            }
        }

        if(getItem(position).getSongName() == null)
        {
            //for ArtistFragment
            TextView textView = listItemView.findViewById(R.id.artist_name);
            textView.setText(getItem(position).getArtistName());
        }
        else
        {
            TextView songName, artistName;
            songName = listItemView.findViewById(R.id.song_name);
            artistName = listItemView.findViewById(R.id.artist_name_song);
            songName.setText(getItem(position).getSongName());
            artistName.setText(getItem(position).getArtistName());
        }
        return listItemView;
    }
}