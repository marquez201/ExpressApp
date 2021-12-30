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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Registrar extends AppCompatActivity {
    private EditText et_nombre,et_email,et_password,et_confirm_pass;
    private Button btn_registrar;
    private ProgressDialog mProgressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        et_nombre = (EditText) findViewById(R.id.txt_Nombre);
        et_email = (EditText) findViewById(R.id.txt_Email);
        et_password = (EditText) findViewById(R.id.txt_Password);
        et_confirm_pass = (EditText) findViewById(R.id.txt_Confirmd_Password);
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
        String snombre = et_nombre.getText().toString();
        String semail = et_email.getText().toString();
        String spassword = et_password.getText().toString();
        String sconfirmpass = et_confirm_pass.getText().toString();

        if(snombre.isEmpty() || snombre.length() < 5){
            showError(et_nombre,"Nombre no Valido");
        }else if(semail.isEmpty() || !semail.contains("@")){
            showError(et_email,"Email no Valido");
        }else if(spassword.isEmpty() || spassword.length() < 6){
            showError(et_password,"La Contraseña debe tener mas de 6 Caracteres");
        }else if(sconfirmpass.isEmpty() || !sconfirmpass.equals(spassword)){
            showError(et_confirm_pass,"Contraseña no Valida, No Coincide");
        }else {
            mProgressBar.setTitle("Procesando Registro");
            mProgressBar.setMessage("Registrando, Espere un Momento");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();

            mAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}