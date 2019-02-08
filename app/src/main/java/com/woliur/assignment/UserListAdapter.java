package com.woliur.assignment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    Context context;
    ArrayList<User> userList;

    public UserListAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(userList.get(position).getName());
        viewHolder.tvAddress.setText(userList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPic;
        TextView tvName;
        TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPic = itemView.findViewById(R.id.iv_pic);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    User user = userList.get(getAdapterPosition());

                    Intent intent = new Intent(context, UserDetailsActivity.class);

                    intent.putExtra("pic", user.image);
                    intent.putExtra("name", user.name);
                    intent.putExtra("email", user.email);
                    intent.putExtra("address", user.address);
                    intent.putExtra("gender", user.gender);

                    context.startActivity(intent);
                }
            });

        }
    }
}
