package com.vnat.chatwithfirebase.Message.Adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.vnat.chatwithfirebase.Message.Model.Message;
import com.vnat.chatwithfirebase.R;

import java.util.List;

import butterknife.BindView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> list;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public MessageAdapter(List<Message> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message obj = list.get(position);

        holder.txtMessage.setText(obj.getMessage());
        holder.txtTime.setText(obj.getTime());

        if (auth.getCurrentUser() != null && obj.getSender() != null){
            if (obj.getSender().equals(auth.getCurrentUser().getEmail())){
                holder.imgAvatar.setVisibility(View.GONE);
                holder.layoutMessage.setGravity(Gravity.RIGHT);
                holder.cvMessage.setCardBackgroundColor(Color.BLUE);
                holder.txtMessage.setTextColor(Color.WHITE);
            }else {
                holder.imgAvatar.setVisibility(View.VISIBLE);
                holder.layoutMessage.setGravity(Gravity.LEFT);
                holder.cvMessage.setCardBackgroundColor(Color.WHITE);
                holder.txtMessage.setTextColor(Color.BLACK);

            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;

        @BindView(R.id.txtMessage)
        TextView txtMessage;

        @BindView(R.id.cvMessage)
        CardView cvMessage;

        @BindView(R.id.layoutMessage)
        LinearLayout layoutMessage;

        @BindView(R.id.txtTime)
        TextView txtTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}