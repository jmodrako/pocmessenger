package com.nokia.nokiamessenger.chat.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nokia.nokiamessenger.R;
import com.nokia.nokiamessenger.chat.data.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<BaseListItemViewHolder> {

    private final static int VIEW_TEXT = 0;
    private final static int VIEW_IMAGE = 1;
    private final static int VIEW_FILE = 2;
    private final static int VIEW_UNKNOWN = 3;

    private final List<Message> data = new ArrayList<>();

    private final LayoutInflater layoutInflater;
    private final int mineMessageColor;

    public ChatListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mineMessageColor = ContextCompat.getColor(context, R.color.primary_text_05);
    }

    @Override
    public int getItemViewType(int position) {
        switch (data.get(position).getContent().getType()) {
            case TEXT:
                return VIEW_TEXT;
            case FILE:
                return VIEW_FILE;
            case IMAGE:
                return VIEW_IMAGE;
            default:
                return VIEW_UNKNOWN;
        }
    }

    @Override
    public BaseListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TEXT:
                return new TextListItemHolder(layoutInflater.inflate(
                        R.layout.list_item_text, parent, false));
            default:
                return new UnknownListItemHolder(layoutInflater.inflate(
                        R.layout.list_item_unknown, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseListItemViewHolder holder, int position) {
        final Message message = data.get(position);
        holder.withMessage(message);

        if (message.isMine()) {
            holder.itemView.setBackgroundColor(mineMessageColor);
        } else {
            holder.itemView.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateMessages(List<Message> messages) {
        data.addAll(messages);
    }

    public int appendMessage(Message message) {
        final int sizeBeforeAdd = data.size();
        data.add(message);

        return sizeBeforeAdd;
    }
}
