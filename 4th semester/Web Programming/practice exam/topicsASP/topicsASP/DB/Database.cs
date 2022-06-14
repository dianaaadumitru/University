using MySql.Data.MySqlClient;
using topicsASP.Models;

namespace topicsASP.DB
{
    public class Database
    {
        private MySqlConnection conn;

        public Database()
        {
            this.conn = new MySqlConnection();
            this.conn.ConnectionString = "server=localhost;uid=root;pwd=diana;database=topics;";
            conn.Open();
        }

        public List<String> getPosts()
        {
            List<String> posts = new List<String>();
            try
            {
                MySqlCommand cmd = new MySqlCommand("select * from posts", conn);

               cmd.Prepare();

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    int id = myreader.GetInt32("id");
                    int topicId = myreader.GetInt32("topicId");
                    int date = myreader.GetInt32("date");
                    String user = myreader.GetString("user");
                    String text = myreader.GetString("text");
                    Post post = new Post(id, user, topicId, text, date);
                    Console.WriteLine(post.toString());
                    posts.Add(post.toString());
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return posts;
        }

        public int topicExists(string name)
        {
            try
            {
                Console.WriteLine("before topic..." + name);
                MySqlCommand cmd = new MySqlCommand("SELECT id from topics where topicname=@name", conn);
                cmd.Parameters.AddWithValue("@name", name);
                
                cmd.Prepare();
                MySqlDataReader myreader = cmd.ExecuteReader();
                Console.WriteLine("query executed");
                if (myreader.Read())
                {
                    Console.WriteLine(myreader.GetInt32("id"));
                    int id = myreader.GetInt32("id");
                    myreader.Close();
                    return id;
                }

                Console.WriteLine("after topicExist try...");

                myreader.Close();


            }
            catch (MySqlException ex)
            {

                Console.Write(ex.Message);

            }
            //Console.WriteLine("after update...");
            return 0;
        }

        public Boolean addTopic(string topicName)
        {
            try
            {
                Console.WriteLine("before add...");
                MySqlCommand cmd = new MySqlCommand("INSERT INTO topics(topicname) VALUES (@topicName)", conn);
                cmd.Parameters.AddWithValue("@topicName", topicName);
                Console.WriteLine("before prepare...");

                cmd.Prepare();
                Console.WriteLine("after prepare...");
                MySqlDataReader myreader = cmd.ExecuteReader();

                Console.WriteLine("after try...");

                myreader.Close();


            }
            catch (MySqlException ex)
            {

                Console.Write(ex.Message);
                return false;
            }
            return true;
        }

        public Boolean updatePost(int id, string user, int topicId, string text, int date)
        {
            Console.WriteLine("id: " + id + "user: " + user);
            try
            {
                Console.WriteLine("before update...");
                MySqlCommand cmd = new MySqlCommand("UPDATE posts SET user=@user,topicId=@topicId,text=@text,date=@date WHERE id=@id", conn);
                cmd.Parameters.AddWithValue("@user", user);
                cmd.Parameters.AddWithValue("@topicId", topicId);
                cmd.Parameters.AddWithValue("@text", text);
                cmd.Parameters.AddWithValue("@date", date);
                cmd.Parameters.AddWithValue("@id", id);


                cmd.Prepare();
                Console.WriteLine(cmd);
                MySqlDataReader myreader = cmd.ExecuteReader();

                Console.WriteLine("after try...");

                myreader.Close();


            }
            catch (MySqlException ex)
            {

                Console.Write(ex.Message);
                return false;
            }
            Console.WriteLine("after update...");
            return true;
        }


        public Boolean addPost(string user, string topic, string text, int date)
        {
            Console.WriteLine(" topic: " + topic + " user: " + user + " text " + text +" date " + date);

            Console.WriteLine("before topic..." + topic);
            int topicId = topicExists(topic);
            if (topicId == 0)
            {
                addTopic(topic);
                topicId = topicExists(topic);
            }
            

            Console.WriteLine("topic id from add " + topicId);
            try
            {
                Console.WriteLine("before add...");
                MySqlCommand cmd = new MySqlCommand("INSERT INTO posts(user, topicId, text, date) VALUES (@user,@topicId,@text,@date)", conn);
                cmd.Parameters.AddWithValue("@user", user);
                cmd.Parameters.AddWithValue("@topicId", topicId);
                cmd.Parameters.AddWithValue("@text", text);
                cmd.Parameters.AddWithValue("@date", date);
                Console.WriteLine("before prepare...");

                cmd.Prepare();
                Console.WriteLine("after prepare...");
                MySqlDataReader myreader = cmd.ExecuteReader();

                Console.WriteLine("after try...");

                myreader.Close();


            }
            catch (MySqlException ex)
            {

                Console.Write(ex.Message);
                return false;
            }
            return true;
        }


    }
}
