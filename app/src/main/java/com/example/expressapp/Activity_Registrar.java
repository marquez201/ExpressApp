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

public class Activity_Registrar extends AppCompatActivity {
    public TextInputLayout til_nombre_r,til_correo_r,til_password_r,til_confirm_pass_r;
    private Button btn_registrar;
    private ProgressDialog mProgressBar;
    FirebaseAuth mAuth;
    public String s_nombre = "";
    public String s_email = "";
    public String s_password = "";
    public String s_confirm_pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        til_nombre_r = (TextInputLayout) findViewById(R.id.txt_Nombre_R);
        til_correo_r = (TextInputLayout) findViewById(R.id.txt_Correo_Regis);
        til_password_r = (TextInputLayout) findViewById(R.id.txt_Password_R);
        til_confirm_pass_r = (TextInputLayout) findViewById(R.id.txt_Confirmar_Pass_R);
        btn_registrar = (Button) findViewById(R.id.btn_Registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCredenciales();
            }
        });//Fin de Boton Registrar

        mAuth = FirebaseAuth.getInstance();
        mProgressBar = new ProgressDialog(Activity_Registrar.this);
    }//Fin del onCreate

    public void verificarCredenciales(){
        s_nombre = til_nombre_r.getEditText().getText().toString();
        s_email = til_correo_r.getEditText().getText().toString();
        s_password = til_password_r.getEditText().getText().toString();
        s_confirm_pass = til_confirm_pass_r.getEditText().getText().toString();
        if(s_nombre.isEmpty() || s_nombre.length() < 5){
            showError(til_nombre_r,"Nombre no Valido");
        }if(s_email.isEmpty() || !s_email.contains("@")){
            showError(til_correo_r,"Correo no Valido");
        }if(s_password.isEmpty() || s_password.length() < 6){
            showError(til_password_r,"La Contraseña debe tener mas de 6 Caracteres");
        }if(s_confirm_pass.isEmpty() || !s_confirm_pass.equals(s_password)){
            showError(til_confirm_pass_r,"Contraseña no Valida, No Coincide");
        }else {
            mProgressBar.setTitle("Procesando Registro");
            mProgressBar.setMessage("Registrando, Espere un Momento");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();

            mAuth.createUserWithEmailAndPassword(s_email,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgressBar.dismiss();
                        Intent intent = new Intent(Activity_Registrar.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Registro Exitoso",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"No se pudo Registrar",Toast.LENGTH_SHORT).show();
                    }
                }
            });//Fin del metodo

        }
    }//Fin del Verificar

    private void showError(TextInputLayout input, String s){
        input.setError(s);
        input.requestFocus();
    }
}