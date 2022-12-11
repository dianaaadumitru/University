// See https://aka.ms/new-console-template for more information

using ConsoleApp1.Implementation;

namespace ConsoleApp1;

public static class Program
{
    private static readonly List<string> Urls = new List<string>
    {
        "www.cs.ubbcluj.ro/~motogna/LFTC", 
        "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/", 
        "www.cs.ubbcluj.ro/~forest",
    };

    static void Main(string[] args)
    {
        
        bool done = false;
        while (!done)
        {
            Console.WriteLine("1. Callbacks Method");
            Console.WriteLine("2. Tasks Method");
            Console.WriteLine("3. Async Await Method");
            Console.WriteLine("0. Exit");
            
            Console.Write(">>");

            string? choice = Console.ReadLine();
            
            switch (choice)
            {
                case "1":
                    CallbacksMethod.run(Urls);
                    break;
                case "2":
                    TaskMethod.run(Urls);
                    break;
                case "3":
                    AsyncMethod.run(Urls);
                    break;
                case "0":
                    done = true;
                    break;
                default:
                    Console.WriteLine("Invalid command!");
                    break;
            }
        }
    }
}