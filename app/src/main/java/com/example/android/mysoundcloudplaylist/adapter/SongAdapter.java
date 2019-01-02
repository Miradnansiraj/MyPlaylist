package com.example.android.mysoundcloudplaylist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.mysoundcloudplaylist.R;
import com.example.android.mysoundcloudplaylist.Song;
import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {
    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    static class ViewHolder {
        private TextView artistName;
        private TextView songName;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            if(getItem(position).getSongName() == null)
            {
                //for ArtistFragment
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.fragment_artist, parent, false);
                holder.artistName = convertView.findViewById(R.id.artist_name);
                convertView.setTag(holder);
            }
            else
            {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.fragment_song, parent, false);
                holder.artistName = convertView.findViewById(R.id.artist_name_song);
                holder.songName = convertView.findViewById(R.id.song_name);
                convertView.setTag(holder);
            }
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(getItem(position).getSongName() == null)
        {
            //for ArtistFragment
            holder.artistName.setText(getItem(position).getArtistName());
        }
        else
        {
            //for SongFragment

            holder.songName.setText(getItem(position).getSongName());
            holder.artistName.setText(getItem(position).getArtistName());
        }
        return convertView;
    }
}