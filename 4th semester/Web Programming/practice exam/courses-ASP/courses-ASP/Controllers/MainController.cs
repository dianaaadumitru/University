using courses_ASP.Data_Abstraction_Layer;
using Microsoft.AspNetCore.Mvc;

namespace courses_ASP.Controllers
{
    public class MainController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public String GetParticipants(string courseName)
        {
            DAL dal = new DAL();

            return dal.GetParticipants(courseName);
        }

        public List<String> getCourses(string student)
        {
            DAL dal = new DAL();

            return dal.getCourses(student);
        }

        public List<String> getProfCourses()
        {
            DAL dal = new DAL();
            string idString = HttpContext.Session.GetString("id");
            int id = int.Parse(idString);
            return dal.getProfCourses(id);
        }

        public Boolean addGrade(string courseName, string studentName, string grade)
        {
            DAL dal = new DAL();
            return dal.addGrade(courseName, studentName, grade);
        }
    }
}
