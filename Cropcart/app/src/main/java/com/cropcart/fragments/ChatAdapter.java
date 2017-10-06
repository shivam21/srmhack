package com.cropcart.fragments;

/**
 * Created by BHUSRI on 10/6/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cropcart.R;
import com.cropcart.preferences.SharedPref;
import com.cropcart.ui.MainActivity;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.ArrayList;
import java.util.List;

final class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<String> texts = new ArrayList<>();
    private ArrayList<CropChatVar> arrayList;
    private String myuserid;
    private boolean isusermale;
    private boolean isblindsuccess;
    private String profilepicid, myprofilepicid;
    private MainActivity context;


    public ChatAdapter(MainActivity blindChat, final ArrayList<CropChatVar> arrayList, boolean isblindsuccess, String profilepicid) {
        SharedPref pref = new SharedPref(blindChat);
        myuserid = pref.getUserid();
        context = blindChat;
        this.arrayList = arrayList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == 0) {
            final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ChatViewHolderme(layoutInflater.inflate(R.layout.adapterchatme, parent, false));
        } else {
            final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ChatViewHolder(layoutInflater.inflate(R.layout.adapter_chat, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CropChatVar obj = arrayList.get(position);

        if (obj.getUserid().equals(myuserid)) {
            ChatViewHolderme holderme = (ChatViewHolderme) holder;
            holderme.time.setReferenceTime(Long.parseLong(obj.getTime()));
            holderme.textView.setText(obj.getMessage());
        } else {
            ChatViewHolder holder1 = (ChatViewHolder) holder;
            holder1.time.setReferenceTime(Long.parseLong(obj.getTime()));
            holder1.textView.setText(obj.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        CropChatVar obj = arrayList.get(position);
        if (obj.getUserid().equals(myuserid)) {
            return 0;
        }
        return 1;

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ChatViewHolderme extends RecyclerView.ViewHolder {

        private RelativeTimeTextView time;
        private TextView textView;

        public ChatViewHolderme(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            textView = itemView.findViewById(R.id.adapter_chat_text_view);
        }

    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private RelativeTimeTextView time;
        private TextView textView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            textView = itemView.findViewById(R.id.adapter_chat_text_view);
        }

    }
}

