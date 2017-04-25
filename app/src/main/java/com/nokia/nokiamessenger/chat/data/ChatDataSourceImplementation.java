package com.nokia.nokiamessenger.chat.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nokia.nokiamessenger.chat.data.Message.Content.ContentType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChatDataSourceImplementation implements ChatDataSource {

    @Nullable
    private ChatDataSource.OnMessagesListener onMessagesListener;

    @Nullable
    private Runnable workerRunnable;

    @Nullable
    private Handler workerHandler;

    @Override
    public void sendMessage(Message message) {
        // TODO Send to server...
    }

    @Override
    public void loadLastMessages() {
        final List<Message> lastMessages = Arrays.asList(
                Message.fromOther("Jacek", "Modrakowski", "Czołem Nokia!", ContentType.TEXT),
                Message.fromOther("Jan", "Kowalski", "A gdyby tu szła Wasza Matka?", ContentType.IMAGE),
                Message.fromOther("Karol", "Nowak", "Sprawozdanie Klubu Tęcza.", ContentType.FILE),
                Message.fromOther("Aniela", "de Molen", "Przyszłam i jestem.", ContentType.TEXT)
        );

        if (onMessagesListener != null) {
            onMessagesListener.onInitialMessages(lastMessages);
        }

        final HandlerThread worker = new HandlerThread("worker-chat");
        worker.start();

        workerHandler = new Handler(worker.getLooper());

        workerRunnable = new Runnable() {
            @Override
            public void run() {
                final int messagesCount = lastMessages.size();
                final int randomIndex = new Random().nextInt(messagesCount);

                onMessagesListener.onNewMessage(lastMessages.get(randomIndex));

                workerHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(7));
            }
        };

        workerHandler.postDelayed(workerRunnable, TimeUnit.SECONDS.toMillis(7));
    }

    @Override
    public void registerOnMessagesListener(@NonNull OnMessagesListener listener) {
        onMessagesListener = listener;
    }

    @Override
    public void unregisterOnMessagesListener() {
        if (workerHandler != null) {
            workerHandler.removeCallbacks(workerRunnable);
        }

        onMessagesListener = null;
    }
}
