package ro.ubb.posts.Controller;

import ro.ubb.posts.DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class AddController extends HttpServlet {
    public AddController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("username");
        String topicName = request.getParameter("topicNameAdd");
        String text = request.getParameter("textAdd");

        Date d=new Date();
        int date = d.getYear() + 1900;

        DBManager dbManager = new DBManager();

        if(dbManager.addPost(user, topicName, text, date)) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('post added');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('error');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        }
    }
}
