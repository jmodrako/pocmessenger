package com.nokia.nokiamessenger.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nokia.nokiamessenger.R;
import com.nokia.nokiamessenger.chat.adapter.ChatListAdapter;
import com.nokia.nokiamessenger.chat.data.ChatDataSource;
import com.nokia.nokiamessenger.chat.data.ChatDataSourceImplementation;
import com.nokia.nokiamessenger.chat.data.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatActivity extends AppCompatActivity implements ChatView {

    private ChatPresenter chatPresenter;
    private ChatListAdapter listAdapter;
    private EditText messageInput;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setupMessageInput();
        setupRecyclerView();
        setupToolbar();

        final ChatDataSource chatDataSource = createChatDataSource();
        chatPresenter = new ChatPresenter(chatDataSource);
    }

    @NonNull
    protected ChatDataSource createChatDataSource() {
        return new ChatDataSourceImplementation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatPresenter.attachView(this);

        // Schedule loading for tests, library initialization, network request, etc.
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Actual data loading.
                chatPresenter.loadData();
            }
        }, TimeUnit.SECONDS.toMillis(5));
    }

    @Override
    protected void onPause() {
        chatPresenter.detachView();
        super.onPause();
    }

    @Override
    public void receivedInitialMessages(final List<Message> messages) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupListWithInitialMessages(messages);
            }
        });
    }

    @Override
    public void receivedNewMessage(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleNewMessage(message);
            }
        });
    }

    @Override
    public void onMessageSent(final Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleNewMessage(message);
                Toast.makeText(ChatActivity.this, "Message has been sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWrongMessageInput() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChatActivity.this, "Wrong message input.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupMessageInput() {
        messageInput = (EditText) findViewById(R.id.chat_input);

        final Button messageSendButton = (Button) findViewById(R.id.chat_input_send);
        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatPresenter.sendMessage(messageInput.getText().toString());
                messageInput.setText("");
                messageInput.clearFocus();
            }
        });
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);

        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        listAdapter = new ChatListAdapter(this);
        recyclerView.setAdapter(listAdapter);
    }

    private void setupListWithInitialMessages(List<Message> messages) {
        listAdapter.updateMessages(messages);
        listAdapter.notifyDataSetChanged();
    }

    private void handleNewMessage(Message message) {
        final int appendedIndex = listAdapter.appendMessage(message);
        listAdapter.notifyItemInserted(appendedIndex);
        recyclerView.scrollToPosition(listAdapter.getItemCount() - 1);
    }
}
