package com.LizCore.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.LizCore.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import options.DatePickerFragment;
import options.ServerConnection;
import options.TextActions;


public class Profile extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String ID = "id";

    public Context context;

    private String mId;

    private ImageView image;
    public Button button;
    private TextInputLayout mName, mEmail, mPass, mGen;
    private Button mDate;
    private String[] data;


    private ServerConnection server;
    public TextActions text;

    public Profile() {
    }

    public static Profile newInstance(String data) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ID, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ID);
        }
        context = Objects.requireNonNull(getActivity()).getApplicationContext();
        server = new ServerConnection(Objects.requireNonNull(getActivity()).getApplicationContext());
        text = new TextActions(Objects.requireNonNull(getActivity()).getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_fragment_profile, container, false);

        image = view.findViewById(R.id.profile_image);
        button = view.findViewById(R.id.button);

        mName = view.findViewById(R.id.name);
        mEmail = view.findViewById(R.id.email);
        mPass = view.findViewById(R.id.password);
        mGen = view.findViewById(R.id.gen);
        mDate = view.findViewById(R.id.date);

        //text.dateFormat(mDate);

        mDate.setOnClickListener(v -> {
            DialogFragment date = new DatePickerFragment(Profile.this);
            assert getFragmentManager() != null;
            date.show(getFragmentManager(), "date picker");
        });

        data = new String[6];

        setProfile();

        image.setOnClickListener(v -> {
            //
            Toast.makeText(context, "mudar imagem", Toast.LENGTH_SHORT).show();
            //
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(mName) && validate(mEmail) && validate(mPass)) {


                    data[0] = mName.getEditText().getText().toString().trim();
                    data[1] = mPass.getEditText().getText().toString().trim();
                    data[2] = mEmail.getEditText().getText().toString().trim();
                    data[3] = mGen.getEditText().getText().toString().trim();
                    data[4] = mDate.getText().toString().trim();
                    data[5] = mId;

                    if (server.changeUser(data)) {
                        Toast.makeText(context, "Alterações salvas", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Erro ao salvar as alterações", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private boolean validate(TextInputLayout inputLayout) {
        String textInput = inputLayout.getEditText().getText().toString().trim();

        if (textInput.isEmpty()) {
            inputLayout.setError("Campo vazio");
            return false;
        } else {
            inputLayout.setError(null);
            //inputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private void setProfile() {
        String[] data = server.getUserData(mId);
        if (!data[0].isEmpty()) {
            mName.getEditText().setText(data[1]);
            mPass.getEditText().setText(data[2]);
            mEmail.getEditText().setText(data[3]);
            mGen.getEditText().setText(data[4]);
            mDate.setText(data[5]);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String dateSelect = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        mDate.setText(dateSelect);
    }
}
