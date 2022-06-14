using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;


namespace courses_ASP.Data_Abstraction_Layer
{
    public class DAL{
        private MySqlConnection conn;

        public DAL()
        {
            this.conn = new MySqlConnection();
            this.conn.ConnectionString = "server=localhost;uid=root;pwd=diana;database=courses;";
            conn.Open();
        }

        public int Authenticate(string name)
        {
            try
            {
                MySqlCommand cmd = new MySqlCommand("select id from persons P where P.name=@name", conn);
                cmd.Parameters.AddWithValue("@name", name);
                cmd.Prepare();
                MySqlDataReader myreader = cmd.ExecuteReader();

                if (myreader.Read())
                {
                    return myreader.GetInt32("id");
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return -1;
        }

        public String GetParticipants(string courseName){
            try { 
                MySqlCommand query = new MySqlCommand("SELECT participants FROM courses WHERE coursename = @name", conn);
                query.Parameters.AddWithValue("@name", courseName);
                query.Prepare();
                MySqlDataReader myreader = query.ExecuteReader();

                if (myreader.Read())
                {
                    return myreader.GetString("participants");
                }
                myreader.Close();
            }
            catch (MySqlException ex) {
                Console.Write(ex.Message);
            }
            return null;

            
        }

        public List<String> getCourses(string student)
        {
            List<String> courses = new List<String>();

            try
            {
                MySqlCommand cmd = new MySqlCommand("select * from courses", conn);
                cmd.Prepare();
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    string participants = myreader.GetString("participants");
                    string course = myreader.GetString("coursename");
                    if (participants.Contains(student))
                    {
                        courses.Add(course);
                    }
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return courses;
        }

        public List<String> getProfCourses(int id)
        {
            List<String> courses = new List<String>();
            try
            {
                MySqlCommand cmd = new MySqlCommand("select * from courses where professorId = @id", conn);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.Prepare();
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    string course = myreader.GetString("coursename");
                    courses.Add(course);
                }
                myreader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }
            return courses;
        }

        public Boolean addGrade(string courseName, string studentName, string grade)
        {
            string participants = "";
            string grades = "";
            try
            {
                MySqlCommand cmd = new MySqlCommand("SELECT * FROM courses WHERE coursename=@courseName", conn);
                cmd.Parameters.AddWithValue("@courseName", courseName);
                Console.Write("course: ", courseName);
                cmd.Prepare();
                MySqlDataReader myreader = cmd.ExecuteReader();

                if (myreader.Read())
                {
                    Console.WriteLine("got here");
                    participants = myreader.GetString("participants");
                    grades = myreader.GetString("grades");
                    if (participants.Contains(studentName))
                    {
                        string[] arrayParticipants = participants.Split(",");
                        string[] arrayGrades = grades.Split(",");
                        participants = "";
                        grades = "";
                        int i;
                        for(i = 0; i < arrayParticipants.Length - 1; i++)
                        {
                            if (arrayParticipants[i] == studentName)
                            {
                                arrayGrades[i] = grade;
                             }
                            participants += arrayParticipants[i];
                            participants += ",";
                            grades += arrayGrades[i];
                            grades += ",";
                        }

                        if (arrayParticipants[i] == studentName)
                        {
                            arrayGrades[i] = grade;
                        }
                        participants += arrayParticipants[i];
                        grades += arrayGrades[i];
                    } else
                    {
                        participants += ",";
                        participants += studentName;
                        grades += ",";
                        grades += grade;
                    }
                }
               
                myreader.Close();

                try
                {
                    MySqlCommand cmd2 = new MySqlCommand("UPDATE courses SET participants=@participants,grades=@grades WHERE coursename=@courseName", conn);
                    cmd2.Parameters.AddWithValue("@participants", participants);
                    cmd2.Parameters.AddWithValue("@grades", grades);
                    cmd2.Parameters.AddWithValue("@courseName", courseName);
                    cmd2.Prepare();
                    MySqlDataReader myreader2 = cmd2.ExecuteReader();

                    myreader2.Close();
                }
                catch (MySqlException ex)
                {
                    Console.Write(ex.Message);
                    return false;
                }
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
