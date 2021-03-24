// From "Professional Java Server Programming", Patzer et al.,

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;
import java.util.Enumeration;

@WebServlet(
        name = "Attribute",
        urlPatterns = {"/attributeServlet"}
)

public class attributeServlet extends HttpServlet
{
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
   // Get session object
   HttpSession session = request.getSession();

   String name   = request.getParameter("attrib_name");
   String value  = request.getParameter("attrib_value");
   String car    = request.getParameter("attrib_car");
   String carValue = request.getParameter("attrib_carValue");
   String remove = request.getParameter("attrib_remove");
   String action = request.getParameter("action");

   if (remove != null && remove.equals("on"))
   {
      session.removeAttribute(name);
   }
   else
   {
      if ((name != null && name.length() > 0) && (value != null && value.length() > 0) && (car != null && car.length() > 0) && (carValue != null && carValue.length() > 0))
      {
         session.setAttribute(name, value);
         session.setAttribute(car, carValue);
      }

   }

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();

     // Called from the invalidate button, kill the session.
      // Get session object
      HttpSession session = request.getSession();
      session.invalidate();

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      if (action != null && action.equals("invalidate"))
   {
      out.println("<html>");
      out.println("<head>");
      out.println(" <title>Session lifecycle</title>");
      out.println("</head>");
      out.println("");
      out.println("<body>");

      out.println("<p>Your session has been invalidated.</P>");

      // Create a link so the user can create a new session.
      // The link will have a parameter builtin
      String lifeCycleURL = "/offutt/servlet/sessionLifeCycle";
      out.println("<a href=\"https://java-servlet-assignment.herokuapp.com/attributeServlet?action=newSession\">");
      out.println("Create new session</A>");

      out.println("</body>");
      out.println("</html>");
      out.close();
   } else {
           out.println("<html>");
           // no-cache lets the page reload by clicking on the reload link
           out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
           out.println("<head>");
           out.println(" <title>Session lifecycle</title>");
           out.println("</head>");
           out.println("");

           out.println("<body>");
           out.println("<h1><center>Session attributes</center></h1>");

           out.println("Enter name and value and car of an attribute");

           // String url = response.encodeURL ("offutt/servlet/attributeServlet");
           String url = response.encodeURL("attributeServlet");
           out.println("<form action=\"" + url + "\" method=\"GET\">");
           out.println(" Name: ");
           out.println(" <input type=\"text\" size=\"10\" name=\"attrib_name\">");

           out.println(" Value: ");
           out.println(" <input type=\"text\" size=\"10\" name=\"attrib_value\">");

           out.println(" Car: ");
           out.println(" <input type=\"text\" size=\"10\" name=\"attrib_car\">");

           out.println(" Car value: ");
           out.println(" <input type=\"text\" size=\"10\" name=\"attrib_carValue\">");

           out.println(" <br><input type=\"checkbox\" name=\"attrib_remove\">Remove");
           out.println(" <input type=\"submit\" name=\"update\" value=\"Update\">");
           out.println("</form>");
           out.println("<hr>");

           out.print  ("<br><br><a href=\"https://java-servlet-assignment.herokuapp.com/attributeServlet?action=invalidate\">");
           out.println("Invalidate the session</a>");
           out.print  ("<br><a href=\"https://java-servlet-assignment.herokuapp.com/attributeServlet\">");
           out.println("Reload this page</a>");

           out.println("Attributes in this session:");
   }
   Enumeration e = session.getAttributeNames();
   while (e.hasMoreElements())
   {
      String att_name  = (String) e.nextElement();
      String att_value = (String) session.getAttribute(att_name);
      String att_car   = (String) e.nextElement();
      String att_carValue = (String) session.getAttribute(att_car);

      out.print  ("<br><b>Name:</b> ");
      out.println(att_name);
      out.print  ("<br><b>Value:</b> ");
      out.println(att_value);
      out.print  ("<br><b>Car:</b> ");
      out.println(att_car);
      out.print  ("<br><b>Car value:</b> ");
      out.println(att_carValue);
   } //end while

   out.println("</body>");
   out.println("</html>");
   out.close();
} // End doGet
} //End  SessionLifeCycle
