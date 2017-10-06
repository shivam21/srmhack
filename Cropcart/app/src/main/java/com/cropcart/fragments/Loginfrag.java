package com.cropcart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cropcart.R;
import com.cropcart.preferences.SharedPref;
import com.cropcart.ui.MainActivity;
import com.cropcart.ui.Signup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class Loginfrag extends Fragment implements View.OnClickListener {
    private static final String MYTAG = "LOGINFRAG";
    private TextView title;
    private EditText phone, password;
    private Button signin;
    private TextView register;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        bindviews(v);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        return v;
    }

    private void bindviews(View v) {
        title = v.findViewById(R.id.title);
        phone = v.findViewById(R.id.phone);
        password = v.findViewById(R.id.password);
        signin = v.findViewById(R.id.signin);
        register = v.findViewById(R.id.signup);
        title.setText(Html.fromHtml("Crop" + "<font color='#00BFFF'>cart</font>"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                String phonenumber = phone.getText().toString();
                String pass = password.getText().toString();
                if (phonenumber.length() != 10)
                    phone.setError("Invalid phone");
                if (pass.length() == 0)
                    password.setError("Enter password");
                if (phonenumber.length() == 10 && pass.length() > 0)
                    CheckStatus(phonenumber, pass);
                break;
            case R.id.signup:
                ((Signup) getActivity()).registerUser();
                break;
        }
    }

    private void CheckStatus(final String phonenumber, final String pass) {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.domain + Urls.checkloginstatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(MYTAG, "onResponse: " + response);
                if (response.equals("0")) {
                    if (getView() != null)
                        Snackbar.make(getView(), "Login Failed", Snackbar.LENGTH_LONG).show();
                } else {
                    SharedPref pref = new SharedPref(getActivity());
                    pref.setIssignedin(true);
                    pref.setUserid(response);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phonenumber);
                map.put("pass", pass);
                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
