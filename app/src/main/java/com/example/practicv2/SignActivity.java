package com.example.practicv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicv2.data.DataManager;
import com.example.practicv2.network.ApiHandler;
import com.example.practicv2.network.ErrorUtils;
import com.example.practicv2.network.model.SignInBody;
import com.example.practicv2.network.model.SignInResponse;
import com.example.practicv2.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends AppCompatActivity {

    private EditText mEmailInput;
    private EditText mPasswordInput;
    private Button mSignInButton;
    private Button mSignUpButton;

    private ApiService service = ApiHandler.getInstance().getService();

    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        InitUI();
    }

    private void InitUI(){
        mEmailInput = findViewById(R.id.email);
        mPasswordInput = findViewById(R.id.password_input);

        mSignInButton = findViewById(R.id.btn_sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignInButtonClick();
            }
        });

        mSignUpButton = findViewById(R.id.btn_sign_up);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignActivity.this,SignUp.class));
            }
        });
    }

    private void handleSignInButtonClick(){
        AsyncTask.execute(()->{
            service.doSignInRequest(getSignInData()).enqueue(new Callback<SignInResponse>() {
                @Override
                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                    if (response.isSuccessful()){
                        sp = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", response.body().getToken());
                        editor.apply();

                        DataManager.setToken(response.body().getToken());
                        startActivity(new Intent(SignActivity.this, MainActivity.class));
                    } else if (response.code() == 400) {
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getApplicationContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка! Попробуйте позже", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SignInResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private SignInBody getSignInData() {
        return new SignInBody(mEmailInput.getText().toString(), mPasswordInput.getText().toString());
    }

}