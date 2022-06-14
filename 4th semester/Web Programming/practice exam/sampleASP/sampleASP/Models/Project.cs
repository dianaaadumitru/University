namespace sampleASP.Models
{
    public class Project
    {
        private int id;
        private int projectManagerId;
        private String name;
        private String description;
        private String members;

        public Project(int id, int projectManagerId, string name, string description, string members)
        {
            this.id = id;
            this.projectManagerId = projectManagerId;
            this.name = name;
            this.description = description;
            this.members = members;
        }
        int getId()
        {
            return id;
        }

        int getprojectManagerId()
        {
            return projectManagerId;
        }

        String getName()
        {
            return name;
        }

        String getDescription()
        {
            return description;
        }

        String getMembers()
        {
            return members;
        }

        public string toString()
        {
            return getId() + " " + getprojectManagerId() + " " + getName() + " " + getDescription() + " " + getMembers();
        }
    }
}
