using Microsoft.AspNetCore.Mvc;
using subiect927ASP.DB;
using subiect927ASP.Models;
using System.Diagnostics;

namespace subiect927ASP.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index()
        {
            return View();
        }

        public Boolean addKeyword(string key, string value)
        {
            Console.WriteLine("hei from controller");
            Database db = new Database();
            return db.addKeyword(key, value);
        }

        public List<String> displayDocuments(string title)
        {

            Database db = new Database();
            return db.displayDocuments(title);
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