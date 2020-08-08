package com.LizCore.app.AnnotationOptions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.LizCore.R;
import com.LizCore.app.AnnotationOptions.ListViewOptions.ItemListView;
import com.LizCore.app.Menu;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import options.IntentActions;
import options.ServerConnection;

public class AnnotationItem extends AppCompatActivity {

    private ServerConnection server;
    private IntentActions intent;

    private String mId;
    private TextView txtId;

    private EditText edtContent;
    private TextInputLayout edtName;
    private TextView txtDate;

    private Button btnSave;
    private ImageButton btnDelete;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_interface_annotation_item);
        server = new ServerConnection(this.getApplicationContext());
        intent = new IntentActions();

        txtId = findViewById(R.id.id_item);
        edtName = findViewById(R.id.name_item);
        edtContent = findViewById(R.id.content_item);
        txtDate = findViewById(R.id.date_item);

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
                        edtName.getEditText().setText(item.getName());

                        String date = String.format("%s %s", txtDate.getText(), item.getDate());
                        txtDate.setText(date);

                        edtContent.setText(item.getContent());
                    }
                }

                btnSave.setForeground(getResources().getDrawable(R.drawable.ic_upload_black, null));

            } else {
                btnSave.setForeground(getResources().getDrawable(R.drawable.ic_add_black, null));
            }
        }

        btnSave.setOnClickListener(v ->
                save(btnSave.getForeground() == getResources().getDrawable(R.drawable.ic_upload_black, null)));

        btnDelete.setOnClickListener(v -> delete(txtId.getText().toString()));

        (findViewById(R.id.arrow_back)).setOnClickListener(v ->
                intent.newIntent(AnnotationItem.this, Menu.class));
    }

    private ItemListView getItem() {
        String[][] values = server.getList("anotacao", mId);
        ItemListView response = null;

        if (!values[0][0].isEmpty()) {
            for (String[] item : values) {
                /* item[0] --> id
                 * item[1] --> name
                 * item[2] --> content
                 * item[3] --> date*/
                if (txtId.getText().toString().equals(item[0])) {
                    response = new ItemListView(Integer.parseInt(item[0]), item[1], item[2], item[3]);
                }
            }
        }
        return response;
    }

    private void delete(String id) {
        if (server.deleteListItem("anotacao", mId, id)) {
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

        String date = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(Calendar.getInstance().getTime());
        String[] data = new String[]{
                txtId.getText().toString(),
                ID,
                Objects.requireNonNull(edtName.getEditText()).getText().toString(),
                edtContent.getText().toString(),
                date
        };

        if (server.addListItem("anotacao", mId, data)) {
            if(save) {
                Toast.makeText(getApplicationContext(), "Salvo nas lista.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Alterações salvas.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
