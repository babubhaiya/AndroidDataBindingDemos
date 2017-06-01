package com.napolean.nonomanews.data.remote;


import com.napolean.nonomanews.data.model.Comment;
import com.napolean.nonomanews.data.model.Post;
import com.napolean.nonomanews.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NonomaNewsService {

    String ENDPOINT = "https://hacker-news.firebaseio.com";

    /**
     * Return a list of the latest post IDs.
     */
    @GET("/v0/topstories.json")
    Call<List<Long>> getTopStories();


    /**
     * Return a list of a users post IDs.
     */
    @GET("/v0/user/{user}.json")
    Call<User> getUser(@Path("user") String user);

    /**
     * Return story item.
     */
    @GET("/v0/item/{itemId}.json")
    Call<Post> getStoryItem(@Path("itemId") String itemId);

    /**
     * Returns a comment item.
     */
    @GET("/v0/item/{itemId}.json")
    Call<Comment> getCommentItem(@Path("itemId") String itemId);

}
