package com.nokia.nokiamessenger.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.nokia.nokiamessenger.BuildConfig;
import com.nokia.nokiamessenger.R;
import com.nokia.nokiamessenger.chat.data.ChatDataSource;
import com.nokia.nokiamessenger.chat.data.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChatActivityTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onResume_shouldAttachViewToPresenter() {
        final TestableChatActivity sut = createStartedSut();

        sut.onResume();

        verify(sut.getChatPresenter()).attachView(sut);
    }

    @Test
    public void onResume_shouldLoadData() {
        final TestableChatActivity sut = createStartedSut();

        sut.onResume();

        verify(sut.getChatPresenter()).loadData();
    }

    @Test
    public void onPause_shouldDetachViewFromPresenter() {
        final TestableChatActivity sut = createVisibleActivity();

        sut.onPause();

        verify(sut.getChatPresenter()).detachView();
    }

    @Test
    public void receivedNewMessage_shouldAddNewMessageViewToList() {
        final TestableChatActivity sut = createVisibleActivity();
        final Message testMessage = createMessage("test-message");

        final RecyclerView recyclerView = (RecyclerView) sut.findViewById(R.id.chat_recycler_view);
        final int preViewCount = recyclerView.getChildCount();

        sut.receivedNewMessage(testMessage);

        final int postViewCount = recyclerView.getChildCount();

        assertTrue(postViewCount > preViewCount);
    }

    @Test
    public void sendNewMessage_shouldCallPresenterWithNewMessage() {
        final TestableChatActivity sut = createVisibleActivity();

        final EditText messageInput = (EditText) sut.findViewById(R.id.chat_input);
        messageInput.setText("test-message");
        final Button messageSendButton = (Button) sut.findViewById(R.id.chat_input_send);
        messageSendButton.performClick();

        verify(sut.getChatPresenter()).sendMessage("test-message");

    }

    private TestableChatActivity createStartedSut() {
        return Robolectric.buildActivity(TestableChatActivity.class).create().start().get();
    }

    private TestableChatActivity createVisibleActivity() {
        return Robolectric.buildActivity(TestableChatActivity.class).setup().get();
    }

    private Message createMessage(String input) {
        return Message.fromInput(input);
    }

    static class TestableChatActivity extends ChatActivity {

        @NonNull
        @Override
        protected ChatDataSource createChatDataSource() {
            return Mockito.mock(ChatDataSource.class);
        }

        @NonNull
        @Override
        protected ChatPresenter createChatPresenter() {
            return Mockito.mock(ChatPresenter.class);
        }
    }
}
