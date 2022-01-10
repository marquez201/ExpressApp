package com.example.expressapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Menu_Principal extends AppCompatActivity {
    public TextView tv_correo, tv_id;
    private ImageView iv_grua;
    Toolbar toolbar;
    //ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        tv_correo = (TextView) findViewById(R.id.textView_email);
        tv_id = (TextView) findViewById(R.id.textView_ID);
        iv_grua = (ImageView) findViewById(R.id.imageView_grua);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        tv_correo.setText(user.getEmail());
        tv_id.setText(user.getUid());


        iv_grua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_gruas = new Intent(Activity_Menu_Principal.this,MapsActivity.class);
                startActivity(intent_gruas);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal_1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menu_datos){
            Intent intent_datos = new Intent(Activity_Menu_Principal.this,Activity_Datos.class);
            startActivity(intent_datos);
        } else if (id == R.id.menu_confi){

        } else if (id == R.id.menu_acerca){
            Intent intent_noso = new Intent(Activity_Menu_Principal.this,Activity_Nosotros.class);
            startActivity(intent_noso);
        } else if (id == R.id.menu_cerrar){
            mAuth.signOut();
            Intent intent = new Intent(Activity_Menu_Principal.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}