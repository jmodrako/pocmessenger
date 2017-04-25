package com.nokia.nokiamessenger.chat.data;

import java.util.List;

public interface ChatDataSource {

    interface OnMessagesListener {

        void onInitialMessages(List<Message> messages);

        void onNewMessage(Message message);
    }

    void sendMessage(Message message);

    void loadLastMessages();

    void registerOnMessagesListener(OnMessagesListener listener);

    void unregisterOnMessagesListener();
}
