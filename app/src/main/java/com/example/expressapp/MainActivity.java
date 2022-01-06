package com.example.expressapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //private EditText et_email_login,et_password_login;
    private TextInputLayout til_correo,til_password;
    private Button btn_login,btn_registrarme;
    private ProgressDialog mProgressBar;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        til_correo = (TextInputLayout) findViewById(R.id.txt_Correo_Login);
        til_password = (TextInputLayout) findViewById(R.id.txt_Password_Login1);
       // et_email_login = (EditText) findViewById(R.id.txt_Email_Login);
        //et_password_login = (EditText) findViewById(R.id.txt_Password_Login);

        btn_login = (Button) findViewById(R.id.btn_Login);
        btn_registrarme = (Button) findViewById(R.id.btn_Registrarme);


        btn_registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,Activity_Registrar.class);
                startActivity(intent1);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mProgressBar = new ProgressDialog(MainActivity.this);
    }

    public void verificar(){
        String s_correo = til_correo.getEditText().getText().toString();
        String s_password  = til_password.getEditText().getText().toString();
        //String s_email = et_email_login.getText().toString();
        //String s_pass = et_password_login.getText().toString();
        if(s_correo.isEmpty() || !s_correo.contains("@")){
            showError(til_correo, "Correo no Valido");
        }if(s_password.isEmpty() || s_password.length() < 6){
            showError(til_password,"Contraseña Invalida");
        }else {
            mProgressBar.setTitle("Iniciando");
            mProgressBar.setMessage("Iniciando, Espere un Momento");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();


            mAuth.signInWithEmailAndPassword(s_correo,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgressBar.dismiss();
                        Intent inten3 = new Intent(MainActivity.this,Activity_Inicio.class);
                        inten3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(inten3);
                    }else {
                        mProgressBar.dismiss();
                        Toast.makeText(getApplicationContext(),"No se pudo Iniciar", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Correo y/o Contraseña Incorrectos",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void showError(TextInputLayout input, String s){
        input.setError(s);
        input.requestFocus();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(MainActivity.this,Activity_Inicio.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

}