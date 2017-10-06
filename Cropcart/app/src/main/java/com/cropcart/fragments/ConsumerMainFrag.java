package com.cropcart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cropcart.R;
import com.cropcart.preferences.SharedPref;
import com.cropcart.ui.MainActivity;
import com.cropcart.utils.EndlessScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Cache.*;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class ConsumerMainFrag extends Fragment {
    private RecyclerView recyclerView;
    private int currentpage = 0;
    private RecyclerViewAdapter adapter;
    private int fetchhcount;
    private ArrayList<JSONObject> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.consumerfrag, container, false);
        binsviews(v);
        fetchcommodities();
        return v;
    }

    private void fetchcommodities() {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.domain + Urls.getcommodities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        arrayList.add(obj);
                    }
                    adapter.notifyDataSetChanged();
                    fetchhcount = fetchhcount + 10;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                SharedPref pref = new SharedPref(getActivity());
                map.put("fetchcount", "0");
                map.put("userid", pref.getUserid());
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    //  Log.d(TAG, "parseNetworkResponse: " + response.data.toString());
                    Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(jsonString, cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }


            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void setRequest() {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.domain + Urls.getcommodities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        arrayList.add(obj);
                    }
                    adapter.notifyDataSetChanged();
                    fetchhcount = fetchhcount + 10;
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                SharedPref pref = new SharedPref(getActivity());
                map.put("fetchcount", String.valueOf(fetchhcount));
                map.put("userid", pref.getUserid());
                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void binsviews(View v) {
        recyclerView = v.findViewById(R.id.recyclerview);
        fetchhcount = 0;
        currentpage = 0;
        adapter = new RecyclerViewAdapter((MainActivity) getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollListener(mLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {


                if (currentpage != page) {
                    //   Log.d(TAG, "onLoadMore: " + page + " " + totalItemsCount);
                    setRequest();
                    currentpage++;
                }
            }
        });

    }
}
