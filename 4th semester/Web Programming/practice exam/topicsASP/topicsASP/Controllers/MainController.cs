using Microsoft.AspNetCore.Mvc;
using topicsASP.DB;
using topicsASP.Models;

namespace topicsASP.Controllers
{
    public class MainController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public List<String> GetPosts()
        {
            Database db = new Database();
            return db.getPosts();
        }

        public Boolean updatePost(int id, int topicId, string text)
        {
            Database db = new Database();
            string user = HttpContext.Session.GetString("username");
            DateTime dtNow;
            dtNow = DateTime.Today;
            int currentDate = dtNow.Year;
            return db.updatePost(id, user, topicId, text, currentDate);
        }

        public Boolean addPost( string topic, string text)
        {
            Database db = new Database();
            string user = HttpContext.Session.GetString("username");
            DateTime dtNow;
            dtNow = DateTime.Today;
            int currentDate = dtNow.Year;
            return db.addPost(user, topic, text, currentDate);
        }
    }
}
