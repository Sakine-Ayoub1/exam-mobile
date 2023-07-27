package com.example.sakine_ayoub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void login(View view) {
        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();

        // Vérifiez si les champs de connexion ne sont pas vides
        if (!login.isEmpty() && !password.isEmpty()) {
            // Enregistrez les informations de connexion dans les préférences partagées
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("login", login);
            editor.putString("password", password);
            editor.apply();

            // Passez à l'écran principal de l'application (ou effectuez l'action souhaitée après l'authentification réussie)
            // Remplacez MainActivity.class par le nom de votre activité principale
            startActivity(new Intent(this, MainActivity.class));
            finish(); // Pour fermer l'écran d'authentification et empêcher le retour en arrière
        } else {
            Toast.makeText(this, "Veuillez saisir le login et le mot de passe", Toast.LENGTH_SHORT).show();
        }
    }
}
