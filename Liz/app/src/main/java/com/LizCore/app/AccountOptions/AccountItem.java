package com.LizCore.app.AccountOptions;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.LizCore.R;
import com.LizCore.app.AccountOptions.ListViewOptions.ItemListView;
import com.LizCore.app.Menu;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import options.DatePickerFragment;
import options.IntentActions;
import options.ServerConnection;

public class AccountItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ServerConnection server;
    private IntentActions intent;

    private String mId;

    private TextView txtId;
    private TextInputLayout txtName, txtValue;

    private Button btnDate, btnSave;
    private ImageButton btnDelete;

    public EditText edtName, edtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_interface_account_item);
        server = new ServerConnection(this.getApplicationContext());
        intent = new IntentActions();

        txtId = findViewById(R.id.id_item);
        txtName = findViewById(R.id.name_item);
        txtValue = findViewById(R.id.value_item);
        btnDate = findViewById(R.id.date_item);

        edtName = txtName.getEditText();
        edtValue = txtValue.getEditText();

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
                    if (item.getId().contentEquals(txtId.getText())) {
                        edtName.setText(item.getName());
                        edtValue.setText(item.getValue());
                        btnDate.setText(item.getDate());
                    }
                }
                btnSave.setForeground(getResources().getDrawable(R.drawable.ic_upload_black, null));

            } else {
                btnSave.setForeground(getResources().getDrawable(R.drawable.ic_add_black, null));
            }
        }

        btnDate.setOnClickListener(v -> {
            DialogFragment date = new DatePickerFragment(AccountItem.this);
            date.show(getSupportFragmentManager(), "date picker");
        });

        btnSave.setOnClickListener(v -> {
            if (validate(txtName) && validate(txtValue)) {
                save(btnSave.getForeground() == getResources().getDrawable(R.drawable.ic_upload_black, null));
            }
        });

        btnDelete.setOnClickListener(v -> delete(txtId.getText().toString()));

        (findViewById(R.id.arrow_back)).setOnClickListener(v ->
                intent.newIntent(AccountItem.this, Menu.class));
    }

    private ItemListView getItem() {
        String[][] values = server.getList("conta", mId);
        ItemListView response = null;

        if (!values[0][0].isEmpty()) {
            for (String[] item : values) {
                /* item[0] --> id
                 * item[1] --> name
                 * item[2] --> value
                 * item[3] --> date*/
                if (txtId.getText().toString().equals(item[0])) {
                    response = new ItemListView(Integer.parseInt(item[0]), item[1],item[2], item[3]);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String dateSelect = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        btnDate.setText(dateSelect);
    }

    private void delete(String id) {
        if (server.deleteListItem("conta", mId, id)) {
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
                edtValue.getText().toString()
        };

        if (server.addListItem("conta", mId, data)) {
            if(save) {
                Toast.makeText(getApplicationContext(), "Salvo nas lista.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Alterações salvas.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
