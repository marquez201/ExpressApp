package com.example.expressapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expressapp.Fragments.DatosFragment;
import com.example.expressapp.Fragments.MainFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public TextView tv_correo, tv_id;

    FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        tv_correo = (TextView) findViewById(R.id.txt_Correo_Usuario);
        tv_id = (TextView) findViewById(R.id.txt_ID_Usuario);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();




        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_nav_host_fragment_content_principal,new MainFragment());
        fragmentTransaction.commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        drawerLayout.closeDrawer(GravityCompat.START);

        if(item.getItemId() == R.id.nav_home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_nav_host_fragment_content_principal,new DatosFragment());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.nav_servicio_gruas){
            Intent intent_gruas = new Intent(Activity_Inicio.this,MapsActivity.class);
            startActivity(intent_gruas);
        }
        if (item.getItemId() == R.id.nav_servicio_gas){
            Intent intent_gas = new Intent(Activity_Inicio.this,MapsActivity.class);
            startActivity(intent_gas);
        }
        if (item.getItemId() == R.id.nav_servicio_gasolina){
            Intent intent_gasolina = new Intent(Activity_Inicio.this,MapsActivity.class);
            startActivity(intent_gasolina);
        }
        if(item.getItemId() == R.id.nav_eliminar_cuenta){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential("manuel@gmail.com","yosoymanuel");
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent_eliminar = new Intent(Activity_Inicio.this,MainActivity.class);
                                intent_eliminar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent_eliminar);
                            }else {
                                Toast.makeText(getApplicationContext(),"No se pudo Eliminar" + task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        if(item.getItemId() == R.id.nav_cerrar_sesion){
            mAuth.signOut();
            Intent intent = new Intent(Activity_Inicio.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return false;
    }
}