package com.example.moon.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    GsonPlaceHolderApi gsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text_view);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gsonPlaceHolderApi = retrofit.create(GsonPlaceHolderApi.class);
       // getAllPosts();
        //getSinglePost();
        getSingleComment();

    }

    private void getSingleComment() {
        Call<Comment> comment = gsonPlaceHolderApi.getComment();
        comment.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Response<Comment> response, Retrofit retrofit) {
                if(!response.isSuccess()){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void getSinglePost() {
        Call<Posts> post = gsonPlaceHolderApi.getPost(10);
        post.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Response<Posts> response, Retrofit retrofit) {
                if(!response.isSuccess()){
                    textView.setText("Failed to load data");
                    return;
                }
                Posts post = response.body();
                StringBuffer stringBuffer = new StringBuffer();

                int id = post.getId();
                stringBuffer.append(id);
                stringBuffer.append("\n");
                int userId = post.getUserId();
                stringBuffer.append(userId);
                stringBuffer.append("\n");
                String title = post.getTitle();
                stringBuffer.append(title);
                stringBuffer.append("\n");
                String body = post.getBody();
                stringBuffer.append(body);
                stringBuffer.append("\n");
                textView.setText(stringBuffer.toString());

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void getAllPosts(){
       Call<List<Posts>> posts = gsonPlaceHolderApi.getPosts();
       posts.enqueue(new Callback<List<Posts>>() {
           @Override
           public void onResponse(Response<List<Posts>> response, Retrofit retrofit) {
               if(!response.isSuccess()){
                   textView.setText(response.code());
                   return;
               }

               List<Posts> all_posts = response.body();
               StringBuffer stringBuffer = new StringBuffer();
               for(Posts post:all_posts){
                   int id = post.getId();
                   stringBuffer.append(id);
                   stringBuffer.append("\n");
                   int userId = post.getUserId();
                   stringBuffer.append(userId);
                   stringBuffer.append("\n");
                   String title = post.getTitle();
                   stringBuffer.append(title);
                   stringBuffer.append("\n");
                   String body = post.getBody();
                   stringBuffer.append(body);
                   stringBuffer.append("\n");
               }
               textView.setText(stringBuffer.toString());
           }

           @Override
           public void onFailure(Throwable t) {
               textView.setText(t.getMessage());
           }
       });

   }
}
