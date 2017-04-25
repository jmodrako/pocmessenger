package com.nokia.nokiamessenger.chat;

import com.nokia.nokiamessenger.chat.data.Message;

import java.util.List;

interface ChatView {
    void receivedInitialMessages(List<Message> messages);

    void receivedNewMessage(Message message);

    void onMessageSent(Message message);

    void onWrongMessageInput();

    ChatView EMPTY_VIEW = new ChatView() {
        @Override
        public void receivedInitialMessages(List<Message> messages) {

        }

        @Override
        public void receivedNewMessage(Message message) {

        }

        @Override
        public void onMessageSent(Message message) {

        }

        @Override
        public void onWrongMessageInput() {

        }
    };
}
