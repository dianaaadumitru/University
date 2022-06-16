package ro.ubb.posts.Domain;

public class Post {
    private int id;
    private String user;
    private int topicId;
    private String text;
    private int date;

    public Post() {
    }

    public Post(int id, String user, int topicId, String text, int date) {
        this.id = id;
        this.user = user;
        this.topicId = topicId;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
