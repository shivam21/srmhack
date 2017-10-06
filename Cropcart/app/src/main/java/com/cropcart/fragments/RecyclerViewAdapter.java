package com.cropcart.fragments;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.cropcart.R;
import com.cropcart.ui.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<JSONObject> arrayList;
    private MainActivity mainActivity;

    public RecyclerViewAdapter(MainActivity context, ArrayList<JSONObject> arrayList) {
        inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        mainActivity = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View v = inflater.inflate(R.layout.categories, parent, false);
                return new MyCategories(v);
            default:
                View v2 = inflater.inflate(R.layout.commodityitem, parent, false);
                return new MyHelper(v2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            JSONObject obj = arrayList.get(position - 1);
            MyHelper myHelper = (MyHelper) holder;
            try {
                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(String.format("%s%s%s.jpg", Urls.domain, Urls.imagepath, obj.getString("picid")), ((MyHelper) holder).imageView);
                myHelper.price.setText("\u20B9" + obj.getString("price") + " per kg");
                myHelper.name.setText(obj.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            default:
                return 1;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyHelper extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView price, name;

        public MyHelper(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.orderitem(arrayList.get(getAdapterPosition()).toString());
                }
            });
        }
    }

    class MyCategories extends RecyclerView.ViewHolder {

        public MyCategories(View itemView) {
            super(itemView);
        }
    }

}
