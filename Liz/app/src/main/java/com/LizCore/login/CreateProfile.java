package com.LizCore.login;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import android.os.Bundle;

import options.IntentActions;
import options.ServerConnection;
import options.TextActions;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;

import android.widget.Toast;

//---------------------------------------//

import com.LizCore.R;

public class CreateProfile extends AppCompatActivity {

    public IntentActions intent;
    public TextActions text;
    public ServerConnection server;

    public ImageView imageView;
    public Button button;
    public EditText[] data = new EditText[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_create_profile);
        intent = new IntentActions();
        text = new TextActions(this.getApplicationContext());
        server = new ServerConnection(this.getApplicationContext());

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.logo_small);

        data[0] = findViewById(R.id.name);
        data[1] = findViewById(R.id.password);
        data[2] = findViewById(R.id.email);
        data[3] = findViewById(R.id.gen);
        data[4] = findViewById(R.id.date);

        data[5] = findViewById(R.id.confirm_password);

        /*Verifica apenas se os campos 'Nome', 'Email', 'Senha' e 'Confirme a Senha' estão vazios,
          já que 'Gênero' e 'Nascimento' são opcionais.*/
        for (int x = 0; x < 6; x++) {
            data[x].addTextChangedListener(setTextWatcher);
        }

        text.onTouchPassword(data[1]);//Password
        text.onTouchPassword(data[5]);//ConfPassword

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_account();
            }
        });

    }

    public void create_account() {

        if (distinctPass(data[1]/* Senha */, data[5]/* Confirmação de Senha */)) {
            String[] values = new String[5];

            for (int x = 0; x < data.length - 1; x++) {
                values[x] = data[x].getText().toString();
            }
            if (server.setUser(values)) {
                Toast.makeText(getApplicationContext(), "Conta criada com sucesso.", Toast.LENGTH_SHORT).show();
                intent.newIntent(this, LoginActivity.class, imageView);
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao criar uma nova conta.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean distinctPass(@NotNull EditText pass1, @NotNull EditText pass2) {

        if (!pass1.getText().toString().equals(pass2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Senhas diferentes", Toast.LENGTH_SHORT).show();
            pass2.setBackgroundColor(getResources().getColor(R.color.red, null));
            text.setBackgroundInThreeSeconds(pass2, getDrawable(R.drawable.bg_edit_text));
            return false;

        } else
            return true;
    }

    private final TextWatcher setTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String[] strings = new String[6];
            for (int x = 0; x < 6; x++) {
                strings[x] = data[x].getText().toString().trim();
            }

            if (!strings[0].isEmpty() && !strings[1].isEmpty() && !strings[2].isEmpty()
                    && !strings[5].isEmpty())
                setButtonBackground(true);

            else
                setButtonBackground(false);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void setButtonBackground(boolean enable) {
        if (enable) {
            button.setEnabled(true);
            button.setBackgroundColor(getApplicationContext().getColor(R.color.shadow_purple));
            button.setTextColor(getApplicationContext().getColor(R.color.white));

        } else {
            button.setEnabled(false);
            button.setBackground(getApplicationContext().getDrawable(R.drawable.bg_btn));
            button.setTextColor(getApplicationContext().getColor(R.color.background_medium));
        }
    }

    @Override
    public void finish() {
        super.finish();
        intent.newIntent(this, LoginActivity.class);
    }
}
