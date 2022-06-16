package ro.ubb.sampleJSP.Controller;

import ro.ubb.sampleJSP.DB.DBManager;
import ro.ubb.sampleJSP.Domain.UserToAdd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ConfirmAddController extends HttpServlet {
    public ConfirmAddController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<UserToAdd> usersToProjects = (ArrayList<UserToAdd>) request.getSession().getAttribute("partialAdd");

        System.out.println("user to project from final controller: " + usersToProjects.size());

        DBManager dbManager = new DBManager();
        int ok = dbManager.addUserToProjects(usersToProjects);
        System.out.println("return from function" + ok);
        if (ok == 0) {
            List<UserToAdd> userToAdd = new ArrayList<>();
            request.getSession().setAttribute("partialAdd", userToAdd);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('user does not exist in db');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        } else if(ok == 1) {
            List<UserToAdd> userToAdd = new ArrayList<>();
            request.getSession().setAttribute("partialAdd", userToAdd);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('user added');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        }else {
            List<UserToAdd> userToAdd = new ArrayList<>();
            request.getSession().setAttribute("partialAdd", userToAdd);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('error');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        }
    }
}
