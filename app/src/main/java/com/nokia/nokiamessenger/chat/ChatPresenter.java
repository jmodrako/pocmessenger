package com.nokia.nokiamessenger.chat;

import com.nokia.nokiamessenger.BasePresenter;
import com.nokia.nokiamessenger.chat.data.ChatDataSource;
import com.nokia.nokiamessenger.chat.data.Message;

import java.util.List;

class ChatPresenter extends BasePresenter<ChatView> implements ChatDataSource.OnMessagesListener {

    private final ChatDataSource chatDataSource;

    ChatPresenter(ChatDataSource chatDataSource) {
        this.chatDataSource = chatDataSource;
    }

    void loadData() {
        chatDataSource.registerOnMessagesListener(this);
        chatDataSource.loadLastMessages();
    }

    void sendMessage(String messageInput) {
        if (messageInput != null && messageInput.length() > 0) {
            final Message message = Message.fromInput(messageInput);
            chatDataSource.sendMessage(message);

            view().onMessageSent(message);
        } else {
            view().onWrongMessageInput();
        }
    }

    @Override
    public void onInitialMessages(List<Message> messages) {
        view().receivedInitialMessages(messages);
    }

    @Override
    public void onNewMessage(Message message) {
        view().receivedNewMessage(message);
    }

    @Override
    public void detachView() {
        chatDataSource.unregisterOnMessagesListener();
        super.detachView();
    }

    @Override
    protected ChatView emptyView() {
        return ChatView.EMPTY_VIEW;
    }
}
