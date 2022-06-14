namespace topicsASP.Models
{
    public class Post
    {
        private int id;
        private String user;
        private int topicId;
        private String text;
        private int date;

        public Post(int id, string user, int topicId, string text, int date)
        {
            this.id = id;
            this.user = user;
            this.topicId = topicId;
            this.text = text;
            this.date = date;
        }

        int getId()
        {
            return id;
        }

        int getTopicId() { return topicId; }

        int getDate() { return date; }

        String getUser() { return user; }

        String getText() { return text; }

        public string toString()
        {
            return getId() + " " + getUser() + " " + getTopicId() + " " + getText() + " " + getDate();
        }
    }
}
