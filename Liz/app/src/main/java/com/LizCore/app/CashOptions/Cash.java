package com.LizCore.app.CashOptions;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LizCore.R;

import java.util.Objects;

import options.IntentActions;
import options.ServerConnection;
import options.TextActions;


public class Cash extends Fragment {

    private static final String ID = "data";
    private String mId;

    private TextView salary;

    private ServerConnection server;
    private TextActions textActions;
    private IntentActions intent;

    private TextView list;

    public Cash() {
    }

    public static Cash newInstance(String id) {
        Cash fragment = new Cash();
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
        intent = new IntentActions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_fragment_cash, container, false);

        salary = view.findViewById(R.id.salary);
        list = view.findViewById(R.id.account_list);

        setList();
        setLeftover(view);

        (view.findViewById(R.id.add)).setOnClickListener(v ->
                intent.newIntent(getActivity(), CashItem.class));

        (view.findViewById(R.id.search)).setOnClickListener(v ->
                intent.newIntent(getActivity(), CashSearch.class, mId));

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setLeftover(View v) {
        textActions.setProgress(v, mId);
        salary.setText(String.format("R$ %s", server.getRealTotal(mId)));
    }

    @SuppressLint("SetTextI18n")
    private void setList() {
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("");

        ForegroundColorSpan fcsRed;
        String[][] values = server.getList("conta", mId);

        if (!values[0][0].isEmpty()) {

            int count = 0;
            for (String[] item : values) {
                //item[1] --> name
                //item[2] --> value

                String value = "- R$" + item[2];
                String text = " (" + item[1] + ")\n";

                fcsRed = new ForegroundColorSpan(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.red, null));
                ssBuilder.append(value).append(text)
                        .setSpan(fcsRed, count, ssBuilder.length() - text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                count = ssBuilder.length();
            }

            list.setText(ssBuilder);
        }
    }
}
