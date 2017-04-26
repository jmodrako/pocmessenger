package com.nokia.nokiamessenger.chat.adapter;

import com.nokia.nokiamessenger.chat.data.Message;

public interface OnChatListItemClickListener {
    void onChatListItemClick(int position, Message clickedMessage);
}
