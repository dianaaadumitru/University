namespace sampleASP.Models
{
    public class SoftwareDeveloper
    {
        private String name;
        private String skills;

        public SoftwareDeveloper(string name, string skills)
        {
            this.name = name;
            this.skills = skills;
        }

        String getName()
        {
            return name;
        }

        String getSkills()
        {
            return skills;
        }

        public string toString()
        {
            return getName() + "-" + getSkills();
        }
    }
}
