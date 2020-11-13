package com.vnat.chatwithfirebase.Message.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vnat.chatwithfirebase.Message.Model.Message;
import com.vnat.chatwithfirebase.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> list;

    private FirebaseUser mUser;
    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;

    public MessageAdapter(List<Message> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_LEFT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message obj = list.get(position);

        holder.txtMessage.setText(obj.getMessage());
        holder.txtTime.setText(obj.getTime());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (list.get(position).getSender().equals(mUser.getUid())){
            return TYPE_RIGHT;
        }else{
            return TYPE_LEFT;
        }
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;

        @BindView(R.id.txtMessage)
        TextView txtMessage;

        @BindView(R.id.txtTime)
        TextView txtTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}