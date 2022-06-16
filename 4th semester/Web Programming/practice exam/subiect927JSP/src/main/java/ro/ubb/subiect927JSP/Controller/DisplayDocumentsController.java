package ro.ubb.subiect927JSP.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DisplayDocumentsController extends HttpServlet {
    public DisplayDocumentsController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("contains");

        request.getSession().setAttribute("containsText", text);

        response.sendRedirect("index.jsp");

    }
}
