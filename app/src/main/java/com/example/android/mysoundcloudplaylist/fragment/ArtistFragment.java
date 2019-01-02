package com.example.android.mysoundcloudplaylist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.mysoundcloudplaylist.ArtistSongActivity;
import com.example.android.mysoundcloudplaylist.R;
import com.example.android.mysoundcloudplaylist.Song;
import com.example.android.mysoundcloudplaylist.adapter.SongAdapter;

import java.util.ArrayList;

public class ArtistFragment extends Fragment {
    public ArtistFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_list, container, false);

        final ArrayList<Song> artists = new ArrayList<Song>();
        artists.add(new Song("88rising"));
        artists.add(new Song("Aruna & Rameses B"));
        artists.add(new Song("Alex Ferro Catas"));
        artists.add(new Song("Bad Computer"));
        artists.add(new Song("Bazzi"));
        artists.add(new Song("Bumkey"));
        artists.add(new Song("Childish Gambino"));

        SongAdapter adapter = new SongAdapter(getActivity(), artists);
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ArtistSongActivity.class);
                intent.putExtra("ARTISTNAME", artists.get(i).getArtistName());
                startActivity(intent);
            }
        });

        return view;
    }
}
