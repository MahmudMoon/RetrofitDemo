package com.example.moon.retrofitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.POST;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    GsonPlaceHolderApi gsonPlaceHolderApi;
    EditText et_userID_,et_title,et_body;
    Button btn_submit,btn_update,btn_delete,btn_load_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text_view);
        et_userID_ = (EditText)findViewById(R.id.userID);
        et_title =(EditText)findViewById(R.id.title);
        et_body = (EditText)findViewById(R.id.body);
        btn_submit = (Button)findViewById(R.id.submit);
        btn_update = (Button)findViewById(R.id.update);
        btn_delete = (Button)findViewById(R.id.delete);
        btn_load_images = (Button)findViewById(R.id.imageLoad);

        btn_load_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ImageLoad.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gsonPlaceHolderApi = retrofit.create(GsonPlaceHolderApi.class);
       // getAllPosts();
       // getSinglePost();
       // getSingleComment();
        //getSingleUserPost();
        //checkMultipleParameters();
        getMultipleUsersData();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = Integer.parseInt(et_userID_.getText().toString().trim());
                String title_ =et_title.getText().toString().trim();
                String body_ = et_body.getText().toString().trim();

                Posts posts = new Posts(userId,title_,body_);
                //Call<Posts> call = gsonPlaceHolderApi.storePost(posts);
                Call<Posts> call = gsonPlaceHolderApi.storePost(userId,title_,body_);
                call.enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Response<Posts> response, Retrofit retrofit) {
                        if(!response.isSuccess()){
                            textView.setText("Failed to store Data");
                            return;
                        }

                        Posts post = response.body();
                        StringBuffer stringBuffer = new StringBuffer();
                        int code = response.code();
                        stringBuffer.append("Code : " + code + "\n");
                        int userId = post.getUserId();
                        stringBuffer.append("User Id : " + userId + "\n");
                        int id = post.getId();
                        stringBuffer.append("Id : " + id + "\n");
                        String title = post.getTitle();
                        stringBuffer.append("Title : " + title + "\n");
                        String body = post.getBody();
                        stringBuffer.append("Body : " + body + "\n");
                        stringBuffer.append("\n");
                        textView.setText(stringBuffer.toString());
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = Integer.parseInt(et_userID_.getText().toString().trim());
                String title_ =et_title.getText().toString().trim();
                String body_ = et_body.getText().toString().trim();
                Posts posts = new Posts(userId,title_,body_);

              //  Call<Posts> call = gsonPlaceHolderApi.patchPost(5,posts); //update only the fields given , use null if don't want to update
                Call<Posts> call = gsonPlaceHolderApi.putPost(5,posts); // update the old posts with the newly given one
                call.enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Response<Posts> response, Retrofit retrofit) {
                        if(!response.isSuccess()){
                            textView.setText("Data Not Found");
                            return;
                        }

                        Posts post = response.body();
                        StringBuffer stringBuffer = new StringBuffer();
                        int code = response.code();
                        stringBuffer.append("Code : " + code + "\n");
                        int userId = post.getUserId();
                        stringBuffer.append("User Id : " + userId + "\n");
                        int id = post.getId();
                        stringBuffer.append("Id : " + id + "\n");
                        String title = post.getTitle();
                        stringBuffer.append("Title : " + title + "\n");
                        String body = post.getBody();
                        stringBuffer.append("Body : " + body + "\n");
                        stringBuffer.append("\n");
                        textView.setText(stringBuffer.toString());

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(et_userID_.getText().toString().trim());
                Call<Void> call = gsonPlaceHolderApi.deletePost(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Response<Void> response, Retrofit retrofit) {
                        if(!response.isSuccess()){
                            textView.setText("FAILED TO DELETE");
                            return;
                        }

                        int code = response.code();
                        textView.setText("Code : " + code);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });


    }

    private void getMultipleUsersData() {
        Call<List<Posts>> calls = gsonPlaceHolderApi.getMultipleUsersData(new int[]{1,5});
        calls.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Response<List<Posts>> response, Retrofit retrofit) {
                if(!response.isSuccess()){
                    textView.setText("Data Not Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                List<Posts> posts = response.body();
                for(Posts post:posts) {
                    int userId = post.getUserId();
                    stringBuffer.append("User Id : " + userId + "\n");
                    int id = post.getId();
                    stringBuffer.append("Id : " + id + "\n");
                    String title = post.getTitle();
                    stringBuffer.append("Title : " + title + "\n");
                    String body = post.getBody();
                    stringBuffer.append("Body : " + body + "\n");
                    stringBuffer.append("\n");
                }

                textView.setText(stringBuffer.toString());

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void checkMultipleParameters() {
        Call<List<Posts>> call = gsonPlaceHolderApi.getSinglePostWithMultipleParameters(1,1);
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Response<List<Posts>> response, Retrofit retrofit) {
                if(!response.isSuccess()){
                    textView.setText("Data Not Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                List<Posts> posts = response.body();
                for(Posts post:posts) {
                    int userId = post.getUserId();
                    stringBuffer.append("User Id : " + userId + "\n");
                    int id = post.getId();
                    stringBuffer.append("Id : " + id + "\n");
                    String title = post.getTitle();
                    stringBuffer.append("Title : " + title + "\n");
                    String body = post.getBody();
                    stringBuffer.append("Body : " + body + "\n");
                    stringBuffer.append("\n");
                }
                textView.setText(stringBuffer.toString());

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void getSingleUserPost() {
        Call<List<Posts>> call = gsonPlaceHolderApi.getSingleUserPost(1);
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Response<List<Posts>> response, Retrofit retrofit) {
                if(!response.isSuccess()){
                    textView.setText("Data Not Found");
                    return;
                }

                List<Posts> posts = response.body();
                StringBuffer stringBuffer = new StringBuffer();
                for(Posts post:posts){
                    int userId = post.getUserId();
                    stringBuffer.append("User Id : " +userId + "\n");
                    int id = post.getId();
                    stringBuffer.append("Id : " + id + "\n");
                    String title = post.getTitle();
                    stringBuffer.append("Title : " + title + "\n" );
                    String body = post.getBody();
                    stringBuffer.append("Body : " + body + "\n");
                    stringBuffer.append("\n");
                }
                textView.setText(stringBuffer.toString());

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void getSingleComment() {
        Call<List<Comment>> comment = gsonPlaceHolderApi.getComment();
        comment.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Response<List<Comment>> response, Retrofit retrofit) {
                if(!response.isSuccess()){
                    textView.setText("Failed to load data");
                    return;
                }

                List<Comment> comments = response.body();
                StringBuilder stringBuffer = new StringBuilder();
                for(Comment comment1:comments) {
                    int postId = comment1.getPostId();
                    int id = comment1.getId();
                    String name = comment1.getName();
                    String email = comment1.getEmail();
                    String body = comment1.getBody();
                    stringBuffer.append(id);
                    stringBuffer.append("\n");
                    stringBuffer.append(postId);
                    stringBuffer.append("\n");
                    stringBuffer.append(name);
                    stringBuffer.append("\n");
                    stringBuffer.append(email);
                    stringBuffer.append("\n");
                    stringBuffer.append(body);
                    stringBuffer.append("\n");
                }
                textView.setText(stringBuffer.toString());

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
