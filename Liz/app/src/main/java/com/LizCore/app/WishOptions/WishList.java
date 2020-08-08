package com.LizCore.app.WishOptions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.LizCore.R;
import com.LizCore.app.WishOptions.ListViewOptions.*;

import java.util.ArrayList;
import java.util.Objects;

import options.IntentActions;
import options.ServerConnection;

public class WishList extends Fragment {

    private static final String ID = "id";
    private String mId;

    private ServerConnection server;
    private IntentActions intent;

    private RecyclerView list;
    private AdapterRecyclerView adapter;
    private RecyclerView.LayoutManager manager;

    private ArrayList<ItemListView> items;


    public WishList() {}

    public static WishList newInstance(String id) {
        WishList fragment = new WishList();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ID);
        }
        server = new ServerConnection(Objects.requireNonNull(getActivity()).getApplicationContext());
        intent = new IntentActions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_fragment_wish_list, container, false);

        final Button add = view.findViewById(R.id.add_new);
        final Button search = view.findViewById(R.id.search_item);

        createRecyclerView();

        list = view.findViewById(R.id.list);
        list.setHasFixedSize(true);

        manager = new LinearLayoutManager(getContext());
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
                intent.newIntent(getActivity(), WishItem.class, data);
            }

            @Override
            public void onDeleteClick(int position) {
                delete(position);
            }
        });

        add.setOnClickListener(v ->
                intent.newIntent(getActivity(), WishItem.class));

        search.setOnClickListener(v ->
                intent.newIntent(getActivity(), WishSearch.class, mId));

        return view;
    }

    private void createRecyclerView(){
        items = new ArrayList<>();
        String[][] values = server.getList("lista_desejos", mId);

        if(!values[0][0].isEmpty()) {
            for (String[] item : values) {
                /* item[0] --> id
                 * item[1] --> name
                 * item[2] --> value
                 * item[3] --> image
                 * item[4] --> comment*/
                items.add(new ItemListView(Integer.parseInt(item[0]), item[1], "R$ " + item[2], Integer.parseInt(item[3]), item[4]));
            }
        }
    }

    private void delete(int position) {
        if (server.deleteListItem("lista_desejos", mId, items.get(position).getId())) {
            items.remove(position);
            adapter.notifyItemRemoved(position);

            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                    "Deletado.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                    "Erro ao deletar.", Toast.LENGTH_SHORT).show();
        }
    }
}
