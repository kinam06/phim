package com.example.film.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.R;
import com.example.film.database.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.SeatAdapterVH> {

    private Context context;
    private List<User> list = new ArrayList<>();

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null);
        return new SeatAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapterVH holder, int position) {
        int pos = holder.getAdapterPosition();
        User user = list.get(pos);
        holder.name.setText("Username: " + user.getUsername());
        holder.createDate.setText("Create date: " + user.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SeatAdapterVH extends RecyclerView.ViewHolder {

        TextView name, createDate;
        View itemView;

        public SeatAdapterVH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            createDate = itemView.findViewById(R.id.createDate);
            this.itemView = itemView;
        }
    }

}