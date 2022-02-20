package com.example.keycloak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccessToken();
            }
        });
    }

    public void getAccessToken(){
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        String password = pass.getText().toString();
        String username = user.getText().toString();

        Call<AccessToken> call = service.getAccessToken("Login", "password", "Mkkg0bFlHHsLYym9PMIkO981iKSMBVyB", "openid", username, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if(response.isSuccessful()){
                    AccessToken accessToken = response.body();
                    Intent intent = new Intent(loginActivity.this,MenuActivity.class);
                    loginActivity.this.startActivity(intent);
                } else{
                    Toast.makeText(loginActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(loginActivity.this,"Error failure :"+t,Toast.LENGTH_LONG).show();
            }
        });
    }
}