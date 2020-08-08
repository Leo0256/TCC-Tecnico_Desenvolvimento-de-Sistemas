package com.LizCore.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.LizCore.R;
import com.LizCore.app.Menu;

import options.IntentActions;
import options.TextActions;
import options.ServerConnection;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final LoadingActivity lastActivity = new LoadingActivity();

    public IntentActions actions;
    public TextActions text;
    private ServerConnection server;

    protected EditText Edt_login, Edt_pass;
    private String Str_login, Str_pass;
    private Button btnEnter;
    TextView txtForgotPass, txtCreateAccount;

    public ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);
        lastActivity.finishActivity(0);

        actions = new IntentActions();
        text = new TextActions(this.getApplicationContext());
        server = new ServerConnection(this.getApplicationContext());

        btnEnter = findViewById(R.id.button);
        txtForgotPass = findViewById(R.id.forgot_password);
        txtCreateAccount = findViewById(R.id.create_account);

        Edt_login = findViewById(R.id.login);
        Edt_pass = findViewById(R.id.password);

        imageView = findViewById(R.id.logo_medium);

        text.onTouchPassword(Edt_pass);

        Edt_login.addTextChangedListener(setTextWatcher);
        Edt_pass.addTextChangedListener(setTextWatcher);

        //Realiza o login.
        btnEnter.setOnClickListener(v -> {

            String id = server.makeLogin(Str_login, Str_pass);
            if (id != null) {
                actions.newIntent(LoginActivity.this, Menu.class, id);
                finishAfterTransition();
            } else {
                Toast.makeText(getApplicationContext(), "Usuário desconhecido", Toast.LENGTH_SHORT).show();
            }
        });

        txtForgotPass.setOnClickListener(mListener(ForgotPassword.class));
        txtCreateAccount.setOnClickListener(mListener(CreateProfile.class));
    }

    private final TextWatcher setTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String input_login = Edt_login.getText().toString().toLowerCase().trim();
            String input_pass = Edt_pass.getText().toString().trim();

            if (!input_login.isEmpty() && !input_pass.isEmpty()) {
                Str_login = input_login;
                Str_pass = input_pass;

                btnEnter.setEnabled(true);
                btnEnter.setBackgroundColor(getApplicationContext().getColor(R.color.shadow_purple));
                btnEnter.setTextColor(getApplicationContext().getColor(R.color.white));

            } else {
                btnEnter.setEnabled(false);
                btnEnter.setBackground(getApplicationContext().getDrawable(R.drawable.bg_btn));
                btnEnter.setTextColor(getApplicationContext().getColor(R.color.background_medium));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //Método responsável por chamar a classe informada "destiny"
    private View.OnClickListener mListener(final Class destiny) {
        return v -> actions.newIntent(LoginActivity.this, destiny, imageView);
    }

    @Override
    public void finish() {
        super.finish();
    }
}