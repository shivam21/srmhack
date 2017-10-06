package com.cropcart.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cropcart.R;
import com.cropcart.fragments.LandDetails;
import com.cropcart.fragments.Loginfrag;
import com.cropcart.fragments.RegisterUser;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class Signup extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Loginfrag frag = new Loginfrag();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).add(R.id.container, frag).commit();
    }

    public void registerUser() {
        RegisterUser frag = new RegisterUser();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.container, frag).commit();
    }

    public void farmaerlanddetails(String response) {
        LandDetails frag = new LandDetails();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.container, frag).commit();

    }
}
