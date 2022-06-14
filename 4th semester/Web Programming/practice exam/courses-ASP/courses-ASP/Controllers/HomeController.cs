using courses_ASP.Models;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using courses_ASP.Data_Abstraction_Layer;

namespace courses_ASP.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;



        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public int VerifyUser(string name)
        {
            DAL dal = new DAL();
            int id = dal.Authenticate(name);
            HttpContext.Session.SetString("id", id.ToString());
           // HttpSessionState.Session["id"] = id.ToString();
            return id;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}