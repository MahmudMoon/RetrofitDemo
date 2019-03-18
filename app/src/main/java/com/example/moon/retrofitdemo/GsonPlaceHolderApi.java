package com.example.moon.retrofitdemo;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;


public interface GsonPlaceHolderApi {

    @GET("Posts")
    Call<List<Posts>> getPosts();

    @GET("Posts/{user_id}")
    Call<Posts> getPost(@Path("user_id") int id);

    @GET("posts/1/comments")
    Call<List<Comment>> getComment();

    @GET("posts")
    Call<List<Posts>> getSingleUserPost(@Query("userId") int userId);

    @GET("posts")
    Call<List<Posts>> getSinglePostWithMultipleParameters(@Query("userId") int userId,
                                                          @Query("id") int id);

//    @GET("posts")
//    Call<List<Posts>> getMultipleUsersData(@Query("userId") int userId,
//                                           @Query("userId") int userId2);

    @GET("posts")
    Call<List<Posts>> getMultipleUsersData(@Query("userId") int[] userId);

    @POST("posts")
    Call<Posts> storePost(@Body Posts posts);

    @FormUrlEncoded
    @POST("posts")
    Call<Posts> storePost(@Field( "userId") int userId,
                          @Field("title") String title,
                          @Field("body") String body
                 );
    @PUT("posts/{id}")
    Call<Posts> putPost(@Path("id") int id,@Body Posts posts);

    @PATCH("posts/{id}")
    Call<Posts> patchPost(@Path("id") int id, @Body Posts posts);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @GET("photos")
    Call<List<ImageObject>> getImage();

}
