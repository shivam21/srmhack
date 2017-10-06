package com.cropcart.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class RegisterUser extends Fragment {
    private EditText name, password, phone, pin, state, district, tehsil, village;
    private TextView next;
    private RadioGroup usergender, useroccupation;
    private int ismale = 1, isyes = 1;
    private String TAG = "REGISTER";
    private ProgressDialog dialog;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.registeruser, container, false);
        bindviews(v);
        pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    dialog = ProgressDialog.show(getActivity(), "",
                            "Loading. Please wait...", true);
                    getAddress(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String userphone = phone.getText().toString();
                String userpass = password.getText().toString();
                String userpin = pin.getText().toString();
                String userstate = state.getText().toString();
                String userdistrict = district.getText().toString();
                String usertehsil = tehsil.getText().toString();
                String uservillage = village.getText().toString();
                RadioButton button1 = v.findViewById(usergender.getCheckedRadioButtonId());
                RadioButton button2 = v.findViewById(useroccupation.getCheckedRadioButtonId());
                String mygender = button1.getText().toString();
                String myoccupation = button2.getText().toString();
                if (mygender.equals("Male")) {
                    ismale = 1;
                } else {
                    ismale = 0;
                }
                if (myoccupation.equals("Yes")) {
                    isyes = 1;
                } else {
                    isyes = 0;
                }
                if (username.length() == 0)
                    name.setError("Enter name");
                if (userpass.length() == 0)
                    password.setError("Enter password");
                if (userphone.length() == 0) {
                    phone.setError("Enter phone");
                } else if (userphone.length() != 10)
                    phone.setError("Invalid phone");
                if (username.length() > 0 && userpass.length() > 0 && userphone.length() == 10) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("username", username);
                    map.put("userphone", userphone);
                    map.put("userpass", userpass);
                    map.put("userpin", userpin);
                    map.put("userstate", userstate);
                    map.put("userdistrict", userdistrict);
                    map.put("usertehsil", usertehsil);
                    map.put("uservillage", uservillage);
                    map.put("ismale", String.valueOf(ismale));
                    map.put("isfarmer", String.valueOf(isyes));
                    saveuser(map);
                }

            }
        });
        return v;
    }

    private void saveuser(final HashMap<String, String> map) {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.domain + Urls.saveuser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.equals("0")) {
                    Toast.makeText(getActivity(), "Phone number already registered", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPref pref = new SharedPref(getActivity());
                    pref.setUserid(response);
                    pref.setIssignedin(true);
                    if (isyes == 1) {
                        pref.setIsfarmer(true);
                        ((Signup) getActivity()).farmaerlanddetails(response);
                    } else {
                        pref.setIsfarmer(false);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void bindviews(View v) {
        name = v.findViewById(R.id.name);
        password = v.findViewById(R.id.pass);
        phone = v.findViewById(R.id.phone);
        pin = v.findViewById(R.id.pin);
        state = v.findViewById(R.id.state);
        district = v.findViewById(R.id.district);
        tehsil = v.findViewById(R.id.tehsil);
        village = v.findViewById(R.id.village);
        next = v.findViewById(R.id.next);
        usergender = v.findViewById(R.id.gender);
        useroccupation = v.findViewById(R.id.useroccupation);
    }

    public void getAddress(String pin) {
        StringRequest request = new StringRequest(Request.Method.GET, Urls.fetchaddress + pin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("records");
                    JSONObject obj2 = array.getJSONObject(0);
                    state.setText(obj2.getString("statename"));
                    district.setText(obj2.getString("districtname"));
                    tehsil.setText(obj2.getString("related_suboffice"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
