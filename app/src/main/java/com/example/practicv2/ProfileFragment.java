package com.example.practicv2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicv2.network.ApiHandler;
import com.example.practicv2.network.model.UserResponse;
import com.example.practicv2.service.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ApiService service = ApiHandler.getInstance().getService();
    private TextView userFullName;
    private TextView userEmail;
    private TextView userId;
    private String token;
    //private ImageView userAvatar;
    private SharedPreferences sp;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initUI(view);
        getUserData();
        return view;
    }

    private void initUI(View view){
        sp = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
    }

    private void getUserData(){
        AsyncTask.execute(() -> {
            service.getUserInfo(token).enqueue(new Callback<List<UserResponse>>() {
                @Override
                public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                    if(response.isSuccessful()){


                        userFullName = getView().findViewById(R.id.userFullName);
                        userEmail = getView().findViewById(R.id.userEmail);
                        userId = getView().findViewById(R.id.userId);
                        //userAvatar = getView().findViewById(R.id.userAvatar);
                        userFullName.setText(response.body().get(0).getFirstName() + " " + response.body().get(0).getLastName());
                        userEmail.setText(response.body().get(0).getEmail());
                        userId.setText(response.body().get(0).getUserId());
                    } else if(response.code() == 401)
                        Toast.makeText(getActivity().getApplicationContext(), "Произошла ошибка! Попробуйте позже!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity().getApplicationContext(), "Произошла ошибка! Попробуйте позже!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                    Log.d(TAG, t.getLocalizedMessage());
                    Toast.makeText(getActivity().getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}