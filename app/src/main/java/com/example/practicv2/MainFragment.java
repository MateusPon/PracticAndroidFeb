package com.example.practicv2;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.practicv2.network.ApiHandler;
import com.example.practicv2.network.ErrorUtils;
import com.example.practicv2.network.adapters.MoviesAdapter;
import com.example.practicv2.network.model.MoviesResponse;
import com.example.practicv2.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    private ApiService service = ApiHandler.getInstance().getService();
    private MoviesAdapter listAdapter;
    private List<MoviesResponse> movieList = new ArrayList<>();
    private RecyclerView recyclerView;

    public MainFragment() {

    }

    public static MainFragment newInstance(String param1, String param2) {
        return new MainFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main,container,false);
       getMovies();
       InitUI(view);
       return view;
    }
    private void InitUI (View view) {recyclerView = view.findViewById(R.id.recycleView);}
    private void getMovies(){
        AsyncTask.execute(() ->{
            service.getMovies("new").enqueue(new Callback<List<MoviesResponse>>() {
                @Override
                public void onResponse(Call<List<MoviesResponse>> call, Response<List<MoviesResponse>> response) {
                    if(response.isSuccessful()){
                        movieList = response.body();
                        listAdapter = new MoviesAdapter(movieList, getContext());
                        SnapHelper snapHelper = new PagerSnapHelper();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(listAdapter);
                        snapHelper.attachToRecyclerView(recyclerView);
                    } else if (response.code() == 400){
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getContext(), "Не удалось получить информацию о фильмах", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<MoviesResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}