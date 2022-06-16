package ro.ubb.sampleJSP.Domain;

public class Project {
    private int id;
    private int ProjectManagerID;
    private String name;
    private String description;
    private String members;

    public Project(int id, int projectManagerID, String name, String description, String members) {
        this.id = id;
        ProjectManagerID = projectManagerID;
        this.name = name;
        this.description = description;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectManagerID() {
        return ProjectManagerID;
    }

    public void setProjectManagerID(int projectManagerID) {
        ProjectManagerID = projectManagerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
