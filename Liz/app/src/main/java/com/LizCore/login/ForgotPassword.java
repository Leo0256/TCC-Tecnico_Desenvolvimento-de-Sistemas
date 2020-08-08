package com.LizCore.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.LizCore.R;

import options.IntentActions;
import options.ServerConnection;
import options.TextActions;

public class ForgotPassword extends AppCompatActivity {

    public ServerConnection server;
    public IntentActions intent;
    public TextActions text;

    public ImageView mImage;
    public EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_forgot_password);
        server = new ServerConnection(this.getApplicationContext());
        intent = new IntentActions();
        text = new TextActions(this.getApplicationContext());

        mImage = findViewById(R.id.logo_small);
        mEmail = findViewById(R.id.email_recover);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> toPasswordRecover());
    }

    public void toPasswordRecover() {
        String strEmail = mEmail.getText().toString();
        if (strEmail.equals("")) {
            Toast.makeText(this.getApplicationContext(), "Informe um endereço de e-mail.", Toast.LENGTH_SHORT).show();
            mEmail.setBackgroundColor(getResources().getColor(R.color.red, null));
            text.setBackgroundInThreeSeconds(mEmail, getDrawable(R.drawable.bg_edit_text));

        } else {
            String id = server.checkEmail(strEmail);
            if (id != null) {
                String[] data = new String[]{
                        id,
                        strEmail
                };
                intent.newIntent(this, ChangePassword.class, data);
            } else {
                Toast.makeText(this.getApplicationContext(), "Endereço de e-mail desconhecido.", Toast.LENGTH_SHORT).show();
                mEmail.setBackgroundColor(getResources().getColor(R.color.red, null));
                text.setBackgroundInThreeSeconds(mEmail, getDrawable(R.drawable.bg_edit_text));
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        intent.newIntent(this, LoginActivity.class);
    }
}
