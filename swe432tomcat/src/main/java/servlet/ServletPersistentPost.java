package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "ServletPersistentPost",
        urlPatterns = {"/ServletPersistentPost"}
)

public class ServletPersistentPost extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    String name = request.getParameter("submit");
    if (name != "submission") {
        out.println("<body style=\"text-align:center;\">");
        out.println("<h1>Homework 7</h1>");
        out.println("<h3>Group members: Raul Puza, Antonio Stockel, Matthew Lee</h3>");
        out.println("<div style=\"display:inline-block;width:400px;background-color:rgb(187,187,187);border-radius:5px;padding:10px0;margin:50px0;\">");
        out.println("<form method=\"post\" style=\"margin:20px0;width:50%;display:inline-block;\">");// action=\"https://cs.gmu.edu:8443/offutt/servlet/formHandler\">");
        out.println("<h4>Input multiple strings with a space in between</h4>");
        out.println("<textarea name=\"array\" id=\"array\" value=\"\" style=\"margin-top:10px;border:none;width:100%;height:100px;\"></textarea><br>");
        out.println("<button name=\"submit\" value=\"submission\" type=\"submit\" onClick=\"display()\" style=\"float:left;\">submit</button>");
        out.println("<button name=\"submit\" value=\"entries\"type=\"submit\" onClick=\"display()\" style=\"float:right;\">entries</button>");
        out.println("<button name=\"submit\" value=\"clear\"type=\"submit\" onClick=\"display()\" style=\"float:right;\">clear file</button>");
        out.println("</form>");
        out.println("<div id=\"error-message\" style=\"display:block;\">Incorrect input</div>");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Random String</p>");
            out.println("<input name=\"rand - string\" id=\"random - string\" type=\"text\" value=\"\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Random String (with replacement)</p>");
            out.println("<input name=\"random-string-replace\" id=\"random-string-replace\" type=\"text\" value=\"\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Random String (without replacement)</p>");
            out.println("<input name=\"random-string-replace-without\" id=\"random-string-replace-without\" type=\"text\" value=\"\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Sorted Array</p>");
            out.println("<input name=\"sorted-array\" id=\"sorted-array\" type=\"text\" value=\"\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Reverse Sorted Array</p>");
            out.println("<input name=\"reverse-sorted-array\" id=\"reverse-sorted-array\" type=\"text\" value=\"\">");
            out.println("</div>");
            out.println("</body>");
    } else {
            ServletContext servletContext = getServletContext();
    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/HelloPersistent");
    requestDispatcher.forward(request, response);
    }
    
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doGet(request, response);
  }
}
