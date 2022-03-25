package com.darksoul.chatapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darksoul.chatapp.LoginActivity;
import com.darksoul.chatapp.MainActivity;
import com.darksoul.chatapp.Models.SearchModel;
import com.darksoul.chatapp.Models.UserModel;
import com.darksoul.chatapp.Network.Api.SendRequestApi;
import com.darksoul.chatapp.Network.Api.UserAuthApi;
import com.darksoul.chatapp.Network.Api.UserSearchApi;
import com.darksoul.chatapp.Network.RetrofitClient;
import com.darksoul.chatapp.ProfileActivity;
import com.darksoul.chatapp.R;
import com.darksoul.chatapp.SessionManager.Sessions;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText Search_name;
    private Button sendReq;
    private UserSearchApi userSearchApi;
    private SendRequestApi sendRequestApi;
    private LinearLayout searchLayout,SearchLayout2;
    private TextView profileName;
    private SearchModel data;
    private Button addFriend,cancelReq;

    public FriendsFragment() {
    }

    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //init Api
        Retrofit retrofit = RetrofitClient.getInstance();
        userSearchApi = retrofit.create(UserSearchApi.class);
        sendRequestApi=retrofit.create(SendRequestApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        //getting user uid......
        Sessions sessions=new Sessions(getContext(),Sessions.SESSION_USER);
        String uid=sessions.getUID();

        //init view............
        Search_name = view.findViewById(R.id.text_search);
        searchLayout = view.findViewById(R.id.search_friend);
        SearchLayout2=view.findViewById(R.id.search_not_found);
        profileName = view.findViewById(R.id.profile_namee);
        addFriend=view.findViewById(R.id.add_friend);
        cancelReq=view.findViewById(R.id.cancel_friend);


        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("from", uid);
                map.put("to", data.getUserID());
                Call<String> call = sendRequestApi.sendRequest(map);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200){
                            String result=response.body();
                            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                        }else if(response.code() == 402){
                            String result=response.body();
                            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        Search_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String searchWord = Search_name.getText().toString();
                    if (searchWord.length() != 0) {
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.]+\\.+[a-z]+";
                        if (searchWord.matches(emailPattern)) {
                            EmailSearch(searchWord);
                        } else {
                            PhoneSearch(searchWord);
                        }
                    }
                    return false;
                }
                return false;
            }

            private void PhoneSearch(String searchWord) {
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", searchWord);
                Call<SearchModel> call = userSearchApi.phoneSearch(map);
                call.enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        if (response.code() == 200) {
                            data = response.body();
                            if(uid.equals(data.getUserID())){
                                SearchLayout2.setVisibility(View.VISIBLE);
                            }else{
                                searchLayout.setVisibility(View.VISIBLE);
                                profileName.setText(data.getName());
                                addFriend.setVisibility(View.VISIBLE);
                            }

                        }else if(response.code() == 201){
                            data = response.body();
                            if(uid.equals(data.getUserID())){
                                SearchLayout2.setVisibility(View.VISIBLE);
                            }else{
                                searchLayout.setVisibility(View.VISIBLE);
                                profileName.setText(data.getName());
                                cancelReq.setVisibility(View.VISIBLE);
                            }
                        }else if (response.code() == 402) {
                            Toast.makeText(getContext(), "Database error !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            SearchLayout2.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            private void EmailSearch(String searchWord) {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", searchWord);
                map.put("uid",uid);
                Call<SearchModel> call = userSearchApi.emailSearch(map);
                call.enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        if (response.code() == 200) {
                            data = response.body();
                            if(uid.equals(data.getUserID())){
                                SearchLayout2.setVisibility(View.VISIBLE);
                            }else{
                                searchLayout.setVisibility(View.VISIBLE);
                                profileName.setText(data.getName());
                                addFriend.setVisibility(View.VISIBLE);
                            }
                        }else if(response.code() == 201){
                            data = response.body();
                            if(uid.equals(data.getUserID())){
                                SearchLayout2.setVisibility(View.VISIBLE);
                            }else{
                                searchLayout.setVisibility(View.VISIBLE);
                                profileName.setText(data.getName());
                                cancelReq.setVisibility(View.VISIBLE);
                            }
                        }else if (response.code() == 402) {
                            Toast.makeText(getContext(), "Database error !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            SearchLayout2.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });




        return view;
    }


}