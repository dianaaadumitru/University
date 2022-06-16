package ro.ubb.subiect927JSP.Domain;

public class Document {
    private int id;
    private String title;
    private String listOfTemplates;

    public Document(int id, String title, String listOfTemplates) {
        this.id = id;
        this.title = title;
        this.listOfTemplates = listOfTemplates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListOfTemplates() {
        return listOfTemplates;
    }

    public void setListOfTemplates(String listOfTemplates) {
        this.listOfTemplates = listOfTemplates;
    }
}
