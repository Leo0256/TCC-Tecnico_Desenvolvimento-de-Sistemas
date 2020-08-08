package com.LizCore.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LizCore.R;

import java.util.Objects;

import options.ServerConnection;
import options.TextActions;

public class Begin extends Fragment {
    private static final String ID = "id";
    private String mId;

    private ServerConnection server;
    private TextActions textActions;

    public Begin() {}

    public static Begin newInstance(String id) {
        Begin fragment = new Begin();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ID);
        }
        server = new ServerConnection(Objects.requireNonNull(getActivity()));
        textActions = new TextActions(Objects.requireNonNull(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_fragment_begin, container, false);

        textActions.setProgress(view,String.valueOf(mId));

        return view;
    }
}
