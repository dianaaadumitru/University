using Microsoft.AspNetCore.Mvc;
using sampleASP.DB;

namespace sampleASP.Controllers
{
    public class MainController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public List<String> getProjects()
        {
            Database db = new Database();
            return db.getProjects();
        }

        public List<String> getUserProjects()
        {
            Database db = new Database();
            string username = HttpContext.Session.GetString("username");
            return db.getUserProjects(username);
        }

        public List<String> getDevelopers()
        {
            Database db = new Database();
            return db.getDevelopers();
        }
    }
}
