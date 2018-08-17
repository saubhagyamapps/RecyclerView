package com.example.android.recyclerview.retrofit;


import com.example.android.recyclerview.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("selected_AuthorData")
    Call<ResponseModel> getResponce(@Field("author_id") String author_id);

}
