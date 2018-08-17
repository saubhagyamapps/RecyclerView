package com.example.android.recyclerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.recyclerview.R;
import com.example.android.recyclerview.adepter.ProjectListAdepter;
import com.example.android.recyclerview.model.ResponseModel;
import com.example.android.recyclerview.retrofit.ApiClient;
import com.example.android.recyclerview.retrofit.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    ApiInterface apiService;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView txtDatanotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intl();
    }

    private void intl() {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        txtDatanotFound = (TextView) findViewById(R.id.txtDataNotFound);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getData();
    }

    private void getData() {
        try {
            Call<ResponseModel> modelCall = apiService.getResponce("2");
            modelCall.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    Gson mGson = new Gson();
                    String result = mGson.toJson(response);
                    Log.e("MainActivity ", result);

                    if (response.body().getData().size() == 0) {
                        txtDatanotFound.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("Size of getdata", response.body().getData().size() + "");
                        mAdapter = new ProjectListAdepter(response.body().getData(), getApplicationContext());
                        mRecyclerView.setHasFixedSize(true);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }
}
