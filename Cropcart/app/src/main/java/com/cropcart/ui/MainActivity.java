package com.cropcart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cropcart.R;
import com.cropcart.fragments.ConsumerMainFrag;
import com.cropcart.fragments.FarmerMainFrag;
import com.cropcart.fragments.MyFarm;
import com.cropcart.fragments.OrderItem;
import com.cropcart.fragments.PostCommodity;
import com.cropcart.preferences.SharedPref;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        SharedPref pref = new SharedPref(MainActivity.this);
        if (!pref.issignedin())
            startActivity(new Intent(MainActivity.this, Signup.class));
        if (pref.isfarmer()) {
            FarmerMainFrag frag = new FarmerMainFrag();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).add(R.id.container, frag).commit();
        } else {
            ConsumerMainFrag frag = new ConsumerMainFrag();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).add(R.id.container, frag).commit();
        }
    }

    public void postCommodity() {
        PostCommodity frag = new PostCommodity();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.container, frag).commit();

    }


    public void myfarm() {
        MyFarm frag = new MyFarm();
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.container, frag).commit();

    }

    public void orderitem(String s) {
        OrderItem frag = new OrderItem();
        Bundle bundle = new Bundle();
        bundle.putString("data", s);
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.container, frag).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPref pref = new SharedPref(MainActivity.this);
                pref.setIssignedin(false);
                pref.setUserid("0");
                startActivity(new Intent(MainActivity.this, Signup.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
