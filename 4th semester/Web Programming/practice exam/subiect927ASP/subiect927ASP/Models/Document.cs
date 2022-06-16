namespace subiect927ASP.Models
{
    public class Document
    {
        private int id;
        private String title;
        private String listOfTemplates;

        public Document(int id, string title, string listOfTemplates)
        {
            this.id = id;
            this.title = title;
            this.listOfTemplates = listOfTemplates;
        }

        public int getId()
        {
            return id;
        }

        public String getTitle()
        {
            return title;
        }

        public String getListOfTemplates()
        {
            return listOfTemplates;
        }

        public string toString()
        {
            return getId() + " " + getTitle() + " " + getListOfTemplates();
        } 
    }
}
