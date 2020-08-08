package com.LizCore.app.AnnotationOptions;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LizCore.R;
import com.LizCore.app.AnnotationOptions.ListViewOptions.AdapterRecyclerView;
import com.LizCore.app.AnnotationOptions.ListViewOptions.ItemListView;

import java.util.ArrayList;
import java.util.Objects;

import options.IntentActions;
import options.ServerConnection;

public class AnnotationSearch extends AppCompatActivity {

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
                intent.newIntent(AnnotationSearch.this, AnnotationItem.class, data);
            }

            @Override
            public void onDeleteClick(int position) {
                delete(position);
            }
        });

        findViewById(R.id.arrow_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.LizCore.app.Menu menuActivity = new com.LizCore.app.Menu();
                intent.newIntent(AnnotationSearch.this, menuActivity.getClass());
            }
        });

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

    private void delete(int position) {
        items.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void createRecyclerView() {
        items = new ArrayList<>();
        String[][] values = server.getList("anotacao", mId);

        if (!values[0][0].isEmpty()) {
            for (String[] item : values) {
                /* item[0] --> id
                 * item[1] --> name
                 * item[2] --> content
                 * item[3] --> date*/
                if (item[2].length() > 100) {
                    StringBuilder builder = new StringBuilder();
                    builder.ensureCapacity(100);
                    builder.append(item[2].subSequence(0, 100)).append("\t[...]");

                    items.add(new ItemListView(Integer.parseInt(item[0]), item[1], builder.toString(), item[3]));
                } else {
                    items.add(new ItemListView(Integer.parseInt(item[0]), item[1], item[2], item[3]));
                }
            }
        }
    }
}
