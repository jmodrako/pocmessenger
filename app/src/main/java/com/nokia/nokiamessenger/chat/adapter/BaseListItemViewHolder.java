package com.nokia.nokiamessenger.chat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.nokia.nokiamessenger.chat.data.Message;

abstract class BaseListItemViewHolder extends ViewHolder {
    BaseListItemViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void withMessage(@NonNull Message message);
}
