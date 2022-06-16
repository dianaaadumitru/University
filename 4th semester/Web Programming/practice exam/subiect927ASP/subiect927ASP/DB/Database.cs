using MySql.Data.MySqlClient;
using subiect927ASP.Models;

namespace subiect927ASP.DB
{
    public class Database
    {
        private MySqlConnection conn;

        public Database()
        {
            this.conn = new MySqlConnection();
            Console.WriteLine("hei from constructor");
            this.conn.ConnectionString = "server=localhost;uid=root;pwd=diana;database=subiect927;";
            conn.Open();
            Console.WriteLine("connection worked");
        }

        public Boolean addKeyword(string key, string value)
        {
            Boolean res = false;
            Console.WriteLine("hei before query");
            try
            {
                Console.WriteLine("hei from try");
                MySqlCommand cmd = new MySqlCommand("INSERT INTO keyword( keyword, value) VALUES (@key,@value)", conn);
                cmd.Parameters.AddWithValue("@key", key);
                cmd.Parameters.AddWithValue("@value", value);
                cmd.Prepare();
                MySqlDataReader reader = cmd.ExecuteReader();
                res = true;

                reader.Close();
                Console.WriteLine("hei after query");
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return res;
        }

        public List<string> displayDocuments (string title)
        {
            List<string> documents = new List<string>();
            try
            {
                MySqlCommand cmd = new MySqlCommand("SELECT * FROM document WHERE title like @title", conn);
                string contains = "%";
                contains += title;
                contains += "%";
                cmd.Parameters.AddWithValue("@title", contains);
                cmd.Prepare();

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    int id = myreader.GetInt32("id");
                    String titleQuery = myreader.GetString("title");
                    String listOfTemplates = myreader.GetString("listOfTemplates");
                    Document document = new Document(id, titleQuery, listOfTemplates);
                    Console.WriteLine(document.toString());
                    documents.Add(document.toString());
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return documents;
        }

    }
}
