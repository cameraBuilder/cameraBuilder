package com.hackathon.camerabuilder.api;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackathon.camerabuilder.api.model.BaseResponse;
import com.hackathon.camerabuilder.api.model.Camera;
import com.hackathon.camerabuilder.api.model.Flash;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.UserInfo;
import com.hackathon.camerabuilder.ui.Adapter;
import com.hackathon.camerabuilder.api.model.Lens;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.hackathon.camerabuilder.api.ApiHelper.ADD_TO_BAG;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_ADAPTER;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_ALL_ADAPTERS;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_ALL_CAMS;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_ALL_FLASHES;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_ALL_LENSES;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_CAM;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_FLASH;
import static com.hackathon.camerabuilder.api.ApiHelper.GET_LENS;
import static com.hackathon.camerabuilder.api.ApiHelper.LOGIN;
import static com.hackathon.camerabuilder.api.ApiHelper.REGISTER;


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

    public void setUserInfo(String userInfo) {
        sharedPreferences.edit().putString("USER_INFO", userInfo).apply();
    }

    public void logout() {
        sharedPreferences.edit().remove("USER_INFO").apply();
    }

    public UserInfo getUserInfo() {
        String userString = sharedPreferences.getString("USER_INFO","");
        if (TextUtils.isEmpty(userString)) {
            return null;
        }
        return gson.fromJson(userString, UserInfo.class);
    }


    public <T> void login(String email, String password, final NetworkCallBack<UserInfo> callBack) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        Request request  = new Request.Builder()
                .url(LOGIN)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<UserInfo> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),
                        TypeToken.getParameterized(BaseResponse.class, UserInfo.class).getType());
                if (response.code() == 200 || response.code() == 201) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }

        });
    }

    public  void register(String email, String password, String userName, final NetworkCallBack<UserInfo> callBack) {

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("username", userName)
                .build();

        Request request  = new Request.Builder()
                .url(REGISTER)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<UserInfo> baseResponse = gson.fromJson(response.body().string(),BaseResponse.class);
                if (response.code() == 200) {
                    callBack.onSuccess(null, baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }


    public  void getAllCams(final NetworkCallBack<ArrayList<Camera>> callBack) {
        Request request  = new Request.Builder()
                .url(GET_ALL_CAMS)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<ArrayList<Camera>> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),new TypeToken<BaseResponse<ArrayList<Camera>>>(){}.getType());

                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }

    public  void getAllLenses(final NetworkCallBack<ArrayList<Lens>> callBack) {

        Request request  = new Request.Builder()
                .url(GET_ALL_LENSES)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<ArrayList<Lens>> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),new TypeToken<BaseResponse<ArrayList<Lens>>>(){}.getType());

                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }

    public  void getAllAdapters(final NetworkCallBack<ArrayList<Adapter>> callBack) {

        Request request  = new Request.Builder()
                .url(GET_ALL_ADAPTERS)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<ArrayList<Adapter>> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(), new TypeToken<BaseResponse<ArrayList<Adapter>>>(){}.getType());
                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });

    }


    public  void getAllFlashes(final NetworkCallBack<ArrayList<Flash>> callBack) {

        Request request  = new Request.Builder()
                .url(GET_ALL_FLASHES)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<ArrayList<Flash>> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(), new TypeToken<BaseResponse<ArrayList<Flash>>>(){}.getType());
                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }


    public  void getFlash(int flashID,final NetworkCallBack<Flash> callBack) {

        Request request  = new Request.Builder()
                .url(GET_FLASH + "/" + flashID)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<Flash> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(), new TypeToken<BaseResponse<Flash>>(){}.getType());
                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }


    public  void getAdapter(int adapterID, final NetworkCallBack<Adapter> callBack) {
        Request request  = new Request.Builder()
                .url(GET_ADAPTER)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<Adapter> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),new TypeToken<BaseResponse<Adapter>>(){}.getType());
                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }

    public  void getLens(int lensID, final NetworkCallBack<Lens> callBack) {
        Request request  = new Request.Builder()
                .url(GET_LENS + "/" + lensID)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<Lens> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),new TypeToken<BaseResponse<ArrayList<Lens>>>(){}.getType());

                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }


    public  void getCam(int camID,final NetworkCallBack<Camera> callBack) {
        Request request  = new Request.Builder()
                .url(GET_CAM +"/"+camID)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse<Camera> baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),new TypeToken<Camera>(){}.getType());
                if (response.code() == 200) {
                    callBack.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }

    public  void addToBag(String productId, String productTag, final NetworkCallBack callBack) {

        RequestBody requestBody = new FormBody.Builder()
                .add("productId", productId)
                .add("productTag", productTag)
                .build();

        Request request  = new Request.Builder()
                .url(ADD_TO_BAG)
                .addHeader("Authorization ", getUserInfo().getToken())
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                BaseResponse baseResponse = gson.fromJson(Objects.requireNonNull(response.body()).string(),BaseResponse.class);
                if (response.code() == 200) {
                    callBack.onSuccess(null, baseResponse.getMessage());
                    return;
                }
                callBack.onError(baseResponse.getMessage());
            }
        });
    }

    }
