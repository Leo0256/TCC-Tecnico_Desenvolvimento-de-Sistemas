package com.LizCore.app.WishOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.LizCore.R;
import com.LizCore.app.Menu;
import com.LizCore.app.WishOptions.ListViewOptions.AdapterRecyclerView;
import com.LizCore.app.WishOptions.ListViewOptions.ItemListView;

import java.util.ArrayList;
import java.util.Objects;

import options.IntentActions;
import options.ServerConnection;

public class WishSearch extends AppCompatActivity {

    private IntentActions intent;
    private ServerConnection server;

    private String mId;

    private RecyclerView list;
    private AdapterRecyclerView adapter;
    private RecyclerView.LayoutManager manager;

    private ArrayList<ItemListView> items;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_interface_item_search);
        server = new ServerConnection(this.getApplicationContext());
        intent = new IntentActions();
        search = findViewById(R.id.search_bar);
        items = new ArrayList<>();

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                mId = Objects.requireNonNull(extra.getString("extra"));
            }
        }

        createRecyclerView();

        list = findViewById(R.id.search_list);
        list.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        adapter = new AdapterRecyclerView(items);

        list.setLayoutManager(manager);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String[] data = new String[]{
                        mId,
                        items.get(position).getId()
                };
                intent.newIntent(WishSearch.this, WishItem.class, data);
            }

            @Override
            public void onDeleteClick(int position) {
                delete(position);
            }
        });

        findViewById(R.id.arrow_back).setOnClickListener(v ->
            intent.newIntent(WishSearch.this, Menu.class));

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    private void createRecyclerView() {
        items = new ArrayList<>();
        String[][] values = server.getList("lista_desejos", mId);

        if (!values[0][0].isEmpty()) {
            for (String[] item : values) {
                /* item[0] --> id
                 * item[1] --> name
                 * item[2] --> value
                 * item[3] --> image
                 * item[4] --> comment*/
                items.add(new ItemListView(Integer.parseInt(item[0]), item[1],
                        "R$ " + item[2], Integer.parseInt(item[3]), item[4]));
            }
        }
    }

    private void delete(int position) {
        if (server.deleteListItem("lista_desejos", mId, items.get(position).getId())) {
            items.remove(position);
            adapter.notifyItemRemoved(position);

            Toast.makeText(getApplicationContext(),"Deletado.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Erro ao deletar.", Toast.LENGTH_SHORT).show();
        }
    }
}
