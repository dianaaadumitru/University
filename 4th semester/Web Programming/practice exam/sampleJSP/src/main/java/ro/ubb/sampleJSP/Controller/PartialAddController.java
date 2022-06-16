package ro.ubb.sampleJSP.Controller;

import ro.ubb.sampleJSP.Domain.UserToAdd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartialAddController extends HttpServlet {
    private List<UserToAdd> usersToProjects;
    public PartialAddController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter("userAdd");
        String project = request.getParameter("projectAdd");

        usersToProjects = (ArrayList<UserToAdd>) request.getSession().getAttribute("partialAdd");

        usersToProjects.add(new UserToAdd(user, project));

        System.out.println("userToProject size: " + usersToProjects.size());

        request.getSession().setAttribute("partialAdd", usersToProjects);

        response.sendRedirect("user.jsp");

    }
}
