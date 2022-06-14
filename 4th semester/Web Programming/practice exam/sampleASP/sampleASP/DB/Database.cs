using MySql.Data.MySqlClient;
using sampleASP.Models;

namespace sampleASP.DB
{
    public class Database
    {
        private MySqlConnection conn;
       
        public Database()
        {
            this.conn = new MySqlConnection();
            this.conn.ConnectionString = "server=localhost;uid=root;pwd=diana;database=sample;";
            conn.Open();
        }

        public List<String> getProjects()
        {
            List<String> projects = new List<String>();
            try
            {
                MySqlCommand cmd = new MySqlCommand("select * from project", conn);
                cmd.Prepare();

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    int id = myreader.GetInt32("id");
                    int projectManagerId = myreader.GetInt32("projectManagerId");
                    String name = myreader.GetString("name");
                    String description = myreader.GetString("description");
                    String members = myreader.GetString("members");
                    Project project = new Project(id, projectManagerId, name, description, members);
                    
                    projects.Add(project.toString());
                }
                myreader.Close();

            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return projects;
        }


        public List<String> getUserProjects(string username)
        {
            List<String> projects = new List<String>();
            try
            {
                MySqlCommand cmd = new MySqlCommand("select * from project", conn);
                cmd.Prepare();

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    String name = myreader.GetString("name");
                    String members = myreader.GetString("members");
                    if (members.Contains(username))
                    { 
                        projects.Add(name); 
                    }
                }
                myreader.Close();

            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return projects;
        }

        public List<String> getDevelopers()
        {
            List<String> developers = new List<String>();
            try
            {
                MySqlCommand cmd = new MySqlCommand("select * from softwaredeveloper", conn);
                cmd.Prepare();

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    String name = myreader.GetString("name");
                    String members = myreader.GetString("skills");
                    SoftwareDeveloper developer = new SoftwareDeveloper(name, members);

                    developers.Add(developer.toString());
                }
                myreader.Close();

            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return developers;
        }
    }

}
