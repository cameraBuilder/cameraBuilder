package com.hackathon.camerabuilder.api;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackathon.camerabuilder.api.model.BaseResponse;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Repository {

    private OkHttpClient client;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public Repository(SharedPreferences sharedPreferences, OkHttpClient client) {
        this.client = client;
        gson = new Gson();
        this.sharedPreferences = sharedPreferences;

    }

    public  void test(final NetworkCallBack<String> callBack) {

        Request request  = new Request.Builder()
                .url(ApiHelper.host)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(), BaseResponse.class);
                if (response.code() == 200) {
                    callBack.onSuccess(null, baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }

        });
    }
    }
