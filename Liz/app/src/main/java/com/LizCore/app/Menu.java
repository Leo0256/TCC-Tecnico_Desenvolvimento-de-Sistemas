package com.LizCore.app;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.LizCore.R;
import com.LizCore.app.AccountOptions.Accounts;
import com.LizCore.app.AnnotationOptions.Annotations;
import com.LizCore.app.CashOptions.Cash;
import com.LizCore.app.WishOptions.WishList;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import options.ServerConnection;
import options.TextActions;

public class Menu extends AppCompatActivity {

    private ServerConnection server;
    private TextActions textActions;
    private Fragment open;
    private DrawerLayout layout;

    private int mId;
    public static final String PREFERENCES = "save";
    public static final String centerId = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu);

        server = new ServerConnection(this.getApplicationContext());
        textActions = new TextActions(this.getApplicationContext());

        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                mId = Integer.parseInt(Objects.requireNonNull(extra.getString("extra")));
                saveData();

            } else {
                mId = getId();
            }
        }

        fragTransaction(Begin.newInstance(String.valueOf(mId)));

        layout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(setNavigationItem());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layout, R.string.open, R.string.close);

        layout.addDrawerListener(toggle);
        toggle.syncState();


        findViewById(R.id.drawer_button).setOnClickListener(v ->
                layout.openDrawer(GravityCompat.START));

    }

    private NavigationView.OnNavigationItemSelectedListener setNavigationItem() {
        return item -> {
            {
                switch (item.getItemId()) {
                    //Tela de Início
                    case R.id.begin:
                        fragTransaction(Begin.newInstance(String.valueOf(mId)));
                        break;

                    //Tela do Saldo
                    case R.id.cash:
                        fragTransaction(Cash.newInstance(String.valueOf(mId)));
                        break;

                    //Tela das Contas
                    case R.id.accounts:
                        fragTransaction(Accounts.newInstance(String.valueOf(mId)));
                        break;

                    //Tela da Lista de Desejos
                    case R.id.wish_list:
                        fragTransaction(WishList.newInstance(String.valueOf(mId)));
                        break;

                    //Tela das Anotações
                    case R.id.annotations:
                        fragTransaction(Annotations.newInstance(String.valueOf(mId)));
                        break;

                    //Tela de Perfil
                    case R.id.option_profile:
                        fragTransaction(Profile.newInstance(String.valueOf(mId)));
                        break;

                    //Adicionais
                    case R.id.option_other:
                        fragTransaction(Other.newInstance(""));
                        break;
                }

                layout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    //Executa a transição de fragmentos
    public void fragTransaction(Fragment fragment) {
        if (open != fragment) {
            if (open != null) {
                closeFragment(open);
            }
            open = fragment;
            openFragment(open);
        }
    }

    //Abre fragmentos
    public void openFragment(Fragment fragment) {
        //manager
        getSupportFragmentManager()
                //transaction
                .beginTransaction()
                //animação customizada
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
						R.anim.slide_in_right, R.anim.slide_out_left)
                //fragmento empilhados
                .addToBackStack(null).add(R.id.fragment_layout, fragment, "")
                //inicia
                .commit();
    }

    //Fecha o fragmento aberto
    public void closeFragment(Fragment fragment) {
        //manager
        getSupportFragmentManager()
                //transaction
                .beginTransaction()
                //animação customizada
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
						R.anim.slide_in_right, R.anim.slide_out_left)
                //remoção
                .remove(fragment)
                //inicia
                .commit();
    }

    public void saveData() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(centerId, mId);
        editor.apply();
    }

    private int getId() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        return prefs.getInt(centerId, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resource, menu);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        this.isFinishing();
    }
}

