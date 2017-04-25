package com.nokia.nokiamessenger.chat.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.nokia.nokiamessenger.R;
import com.nokia.nokiamessenger.chat.data.Message;

class TextListItemHolder extends BaseListItemViewHolder {

    private TextView authorView;
    private TextView contentView;

    TextListItemHolder(View itemView) {
        super(itemView);
        authorView = (TextView) itemView.findViewById(R.id.list_item_text_author);
        contentView = (TextView) itemView.findViewById(R.id.list_item_text_content);
    }

    @Override
    public void withMessage(@NonNull Message message) {
        authorView.setText(message.getAuthor().getFirstName());
        contentView.setText(message.getContent().getContent());
    }
}
