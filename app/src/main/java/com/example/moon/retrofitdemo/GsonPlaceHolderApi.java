package com.example.moon.retrofitdemo;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;


public interface GsonPlaceHolderApi {

    @GET("Posts")
    Call<List<Posts>> getPosts();

    @GET("Posts/{user_id}")
    Call<Posts> getPost(@Path("user_id") int id);

    @GET("posts/1/comments")
    Call<Comment> getComment();
}
