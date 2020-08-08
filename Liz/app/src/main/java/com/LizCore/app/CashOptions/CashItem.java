package com.LizCore.app.CashOptions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LizCore.R;
import com.LizCore.app.CashOptions.ListViewOptions.ItemListView;
import com.LizCore.app.Menu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import options.IntentActions;
import options.ServerConnection;

public class CashItem extends AppCompatActivity {
    private ServerConnection server;
    private IntentActions intent;

    private String mId;
    private TextView txtId;

    private TextInputLayout mName, mValue;
    private EditText edtName, edtValue;

    private Button btnSave;
    private ImageButton btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_interface_cash_item);
        server = new ServerConnection(this.getApplicationContext());
        intent = new IntentActions();

        txtId = findViewById(R.id.id_item);
        mName = findViewById(R.id.name_item);
        mValue = findViewById(R.id.value_item);

        edtName = mName.getEditText();
        edtValue = mValue.getEditText();

        btnSave = findViewById(R.id.button);
        btnDelete = findViewById(R.id.delete_item);

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();

            if (extra != null) {
                //id do usuario
                mId = extra.getString("extra 0");
                //id do item
                txtId.setText(extra.getString("extra 1"));

                ArrayList<ItemListView> values = new ArrayList<>();
                values.add(getItem());

                for (ItemListView item : values) {
                    if (item.getId().equals(txtId.getText().toString())) {
                        txtId.setText(item.getId());
                        edtName.setText(item.getName());
                        edtValue.setText(item.getValue());
                    }
                }
                btnSave.setForeground(getResources().getDrawable(R.drawable.ic_upload_black, null));

            } else {
                btnSave.setForeground(getResources().getDrawable(R.drawable.ic_add_black, null));
            }

            btnSave.setOnClickListener(v -> {
                if (validate(mName) && validate(mValue)) {
                    save(btnSave.getForeground() == getResources().getDrawable(R.drawable.ic_upload_black, null));
                }
            });

            btnDelete.setOnClickListener(v -> delete(txtId.getText().toString()));

            //Retorna ao menu
            (findViewById(R.id.arrow_back)).setOnClickListener(v ->
                    intent.newIntent(CashItem.this, Menu.class));
        }
    }

    private ItemListView getItem() {
        String[][] values = server.getList("saldo", mId);
        ItemListView response = null;

        if (!values[0][0].isEmpty()) {
            for (String[] item : values) {
                /* item[0] --> id
                 * item[1] --> name
                 * item[2] --> value*/
                if (txtId.getText().toString().equals(item[0])) {
                    response = new ItemListView(item[0], item[1], item[2]);
                }
            }
        }

        return response;
    }

    private boolean validate(TextInputLayout inputLayout) {
        String textInput = Objects.requireNonNull(inputLayout.getEditText()).getText().toString().trim();

        if (textInput.isEmpty()) {
            inputLayout.setError("Campo vazio");
            return false;
        } else {
            inputLayout.setError(null);
            return true;
        }
    }

    private void delete(String id) {
        if (server.deleteListItem("saldo", mId, id)) {
            Toast.makeText(getApplicationContext(), "Deletado.", Toast.LENGTH_SHORT).show();
            intent.newIntent(this, Menu.class);
        }else{
            Toast.makeText(getApplicationContext(), "Erro ao deletar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void save(boolean save) {
        String ID;
        if(save) {
            ID = mId;
        }else{
            ID = "";
        }

        String[] data = new String[]{
                txtId.getText().toString(),
                ID,
                edtName.getText().toString(),
                edtValue.getText().toString(),
        };

        if (server.addListItem("saldo", mId, data)) {
            if(save) {
                Toast.makeText(getApplicationContext(), "Salvo nas lista.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Alterações salvas.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
