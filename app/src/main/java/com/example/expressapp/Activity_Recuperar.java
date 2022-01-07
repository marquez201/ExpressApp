package com.example.expressapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Recuperar extends AppCompatActivity {
    private TextInputLayout til_email_recup;
    private Button btn_enviar;
    FirebaseAuth mAuth;
    private ProgressDialog mProgressBar;
    private String s_correo_recu = til_email_recup.getEditText().getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        mAuth = FirebaseAuth.getInstance();
        til_email_recup = (TextInputLayout) findViewById(R.id.txt_Recuperar);
        btn_enviar = (Button) findViewById(R.id.btn_Enviar_Email);
        mProgressBar = new ProgressDialog(Activity_Recuperar.this);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!s_correo_recu.isEmpty()){
                    mProgressBar.setMessage("Espere un momento");
                    mProgressBar.setCanceledOnTouchOutside(false);
                    mProgressBar.show();
                    Recuperar_Pass();
                }else {
                    //showError(til_email_recup,"Correo Invalido");
                    Toast.makeText(Activity_Recuperar.this,"Debe ingresar el correo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Recuperar_Pass(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(s_correo_recu).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Activity_Recuperar.this,"Se ha enviado el correo para restablecer contraseña",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Activity_Recuperar.this,"No se pudo enviar el correo",Toast.LENGTH_SHORT).show();
                }

                mProgressBar.dismiss();
            }
        });
    }


    public void showError(TextInputLayout input, String s){
        input.setError(s);
        input.requestFocus();
    }
}