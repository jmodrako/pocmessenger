package com.nokia.nokiamessenger.chat.data;

import android.support.annotation.NonNull;

public class Message {

    private boolean isMine;
    private final Author author;
    private final Content content;

    static Message fromOther(String authorFirstName, String authorLastName,
                             String contentData, Content.ContentType contentType) {
        return new Message(authorFirstName, authorLastName, contentData, contentType, false);
    }

    public static Message fromInput(String inputMessage) {
        return new Message("Jacek", "Beny", inputMessage, Content.ContentType.TEXT, true);
    }

    private Message(String authorFirstName, String authorLastName,
                    String contentData, Content.ContentType contentType, boolean isMine) {
        this.isMine = isMine;
        this.author = new Author(authorFirstName, authorLastName);
        this.content = new Content(contentData, contentType);
    }

    public boolean isMine() {
        return isMine;
    }

    @NonNull
    public Author getAuthor() {
        return author;
    }

    @NonNull
    public Content getContent() {
        return content;
    }

    public static class Author {
        private final String firstName;
        private final String lastName;

        public Author(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    public static class Content {

        public enum ContentType {
            TEXT, FILE, IMAGE
        }

        private final String content;
        private final ContentType type;

        public Content(String content, ContentType type) {
            this.content = content;
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public ContentType getType() {
            return type;
        }
    }
}
