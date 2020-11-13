package com.vnat.chatwithfirebase.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vnat.chatwithfirebase.Message.View.MessageActivity;
import com.vnat.chatwithfirebase.User.Model.User;
import com.vnat.chatwithfirebase.R;
import com.vnat.chatwithfirebase.User.View.UserActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> list;

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final User obj = list.get(position);

        holder.txtName.setText(obj.getName());

        if (obj.getAvatarUrl().equals("default")){
            holder.imgAvatar.setImageResource(R.drawable.ic_user);
        }else{
            Glide.with(context)
                    .load(obj.getAvatarUrl())
                    .error(R.drawable.ic_user)
                    .into(holder.imgAvatar);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("uid", obj.getUid());
                intent.putExtra("fullName", obj.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;

        @BindView(R.id.txtName)
        TextView txtName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}