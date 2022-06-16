package ro.ubb.sampleJSP.Domain;

public class UserToAdd {
    private String user;
    private String project;

    public UserToAdd(String user, String project) {
        this.user = user;
        this.project = project;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
