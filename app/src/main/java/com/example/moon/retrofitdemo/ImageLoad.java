package com.example.moon.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ImageLoad extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter mAdapter;
    List<ImageObject> imgaeList;
    GsonPlaceHolderApi gsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imgaeList = new ArrayList<>();

        List<ImageObject> imageObjectList = prepareMovieData();
        Log.i("MyTag", "onCreate: "+imageObjectList.size());



    }

    private List<ImageObject> prepareMovieData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gsonPlaceHolderApi = retrofit.create(GsonPlaceHolderApi.class);
        Call<List<ImageObject>> call = gsonPlaceHolderApi.getImage();
        call.enqueue(new Callback<List<ImageObject>>() {
            @Override
            public void onResponse(Response<List<ImageObject>> response, Retrofit retrofit) {
                if(!response.isSuccess()){
                    Toast.makeText(getApplicationContext(),"Failed to load images",Toast.LENGTH_SHORT).show();
                    return;
                }

                List<ImageObject> body = response.body();
                mAdapter = new MyAdapter(body);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

                Log.i("MyTag", "onResponse: "+body.size());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        return imgaeList;

    }
}
