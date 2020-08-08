package com.LizCore.app;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LizCore.R;

import java.util.Objects;

public class Other extends Fragment {

    private static final String DATA = "data";

    private String mData;

    public TextView text;

    public Other() {
    }

    public static Other newInstance(String data) {
        Other fragment = new Other();
        Bundle args = new Bundle();
        args.putString(DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = getArguments().getString(DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_fragment_other, container, false);

        TextView app_description = view.findViewById(R.id.app_description);

        String text = Objects.requireNonNull(getActivity()).getResources().getString(R.string.app_description);

        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getActivity().getResources().getColor(R.color.purple, null));
        ss.setSpan(colorSpan, 6, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        app_description.setText(ss);

        return view;
    }
}
