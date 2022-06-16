namespace subiect927ASP.Models
{
    public class Keyword
    {
        private String key;
        private String value;

        public Keyword(string key, string value)
        {
            this.key = key;
            this.value = value;
        }

        public String getKey()
        {
            return key;
        }

        public String getValue()
        {
            return value;
        }

        public String toString()
        {
            return getKey() + " " + getValue();
        }
    }
}
