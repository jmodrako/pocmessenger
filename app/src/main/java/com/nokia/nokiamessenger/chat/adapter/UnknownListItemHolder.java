package com.nokia.nokiamessenger.chat.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nokia.nokiamessenger.R;
import com.nokia.nokiamessenger.chat.data.Message;

class UnknownListItemHolder extends BaseListItemViewHolder {

    private TextView labelView;

    UnknownListItemHolder(View itemView) {
        super(itemView);
        labelView = (TextView) itemView.findViewById(R.id.list_item_unknown_label);
    }

    @Override
    public void withMessage(@NonNull Message message) {
        labelView.setText(String.format("Unsupported type: %s", message.getContent().getType()));
    }
}
