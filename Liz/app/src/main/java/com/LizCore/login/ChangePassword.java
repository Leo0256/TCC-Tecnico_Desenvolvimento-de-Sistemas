package com.LizCore.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import options.IntentActions;
import options.ServerConnection;
import options.TextActions;

import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import com.LizCore.R;

public class ChangePassword extends AppCompatActivity {

    public IntentActions intent;
    public TextActions text;
    private ServerConnection server;

    public EditText newPass, confPass;
    private String id, email;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_change_password);
        intent = new IntentActions();
        text = new TextActions(this.getApplicationContext());
        server = new ServerConnection(this.getApplicationContext());

        imageView = findViewById(R.id.logo_small);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            id = extra.getString("extra 0");
            email = extra.getString("extra 1");
        }

        newPass = findViewById(R.id.new_password);
        confPass = findViewById(R.id.confirm_password);

        text.onTouchPassword(newPass);
        text.onTouchPassword(confPass);
    }

    public void Changing(View v) {
        String Pass1 = newPass.getText().toString();
        String Pass2 = confPass.getText().toString();

        if (text.emptyEditText(newPass) && text.emptyEditText(confPass)) {
            if (!Pass1.equals(Pass2)) {
                Toast.makeText(getApplicationContext(), "Senhas diferentes", Toast.LENGTH_SHORT).show();
                confPass.setBackgroundColor(getResources().getColor(R.color.red, null));
                text.setBackgroundInThreeSeconds(confPass, getDrawable(R.drawable.bg_edit_text));

            } else if (server.changePassword(email, Pass1)) {
                Toast.makeText(getApplicationContext(), "Senha trocada", Toast.LENGTH_SHORT).show();
                intent.newIntent(this, LoginActivity.class, imageView);
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
        intent.newIntent(this, LoginActivity.class);
    }
}
