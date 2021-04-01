// Simple example servlet from slides
package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

// The @WebServletannotation is used to declare a servlet
@WebServlet(
        name = "HelloPersistent",
        urlPatterns = {"/HelloPersistent"}
)

public class HelloPersistent extends HttpServlet // Inheriting from HttpServlet makes this a servlet
{

    static String RESOURCE_FILE = "entries.txt";
    static final String VALUE_SEPARATOR = " ";
    boolean flag = false;
    int globalRandom = 0;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html"); // Tells the web container what we're sending back
        PrintWriter out = response.getWriter(); // Make it appear as if we're "writing" to the browser window
        printHead(out);
        printBody(out, "", "", "", "", "", "");
        printTail(out);
    }  // end doGet()

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String text = request.getParameter("array");
        String button = request.getParameter("submit");
        String error = "";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(button.equals("entries")) {
            printHead(out);
            printResponseBody(out, RESOURCE_FILE);
            printTail(out);
        } else if(button.equals("clear")){
            FileWriter fwOb = new FileWriter(RESOURCE_FILE, false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
            printHead(out);
            printResponseBody(out, null);
            printTail(out);
        }
        else {


            String[] array = eraseDuplicates(convertToArray(text));

            if (array == null) {
                error = "<li>Text is required</li>";
                text = "";
            }
            checkHasTag(array);
            String randomString1 = randomString(array);
            String randomString2 = randomString(array);
            String randomStringWithoutReplacement = randomStringWithoutReplacement(array);
            String[] sortedArray = sortArray(array);
            String[] reverseSortedArray = reverseSortedArray(array);


            if (error.length() == 0) {
                PrintWriter entriesPrintWriter =
                        new PrintWriter(new FileWriter(RESOURCE_FILE, true), true);
                entriesPrintWriter.println(text);
                entriesPrintWriter.close();

                printHead(out);
                printBody(out, text, randomString1, randomString2, randomStringWithoutReplacement, concatString(sortedArray), concatString(reverseSortedArray));
                printTail(out);

            }
        }
    }

    private void printHead(PrintWriter out){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet example</title>");
        out.println("</head>");
    }
    private void printBody(PrintWriter out, String text,String box1, String box2, String box3, String box4, String box5){
        out.println("<body style=\"text-align:center;\">");
        out.println("<h1>Homework 7</h1>");
        out.println("<h3>Group members: Raul Puza, Antonio Stockel, Matthew Lee</h3>");
        out.println("<div style=\"display:inline-block;width:400px;background-color:rgb(187,187,187);border-radius:5px;padding:10px0;margin:50px0;\">");
        out.println("<form method=\"post\" style=\"margin:20px0;width:50%;display:inline-block;\">");// action=\"https://cs.gmu.edu:8443/offutt/servlet/formHandler\">");
        out.println("<h4>Input multiple strings with a space in between</h4>");
        out.println("<textarea name=\"array\" id=\"array\" value=\""+ text +"\" style=\"margin-top:10px;border:none;width:100%;height:100px;\"></textarea><br>");
        out.println("<button name=\"submit\" value=\"submission\" type=\"submit\" onClick=\"display()\" style=\"float:left;\">submit</button>");
        out.println("<button name=\"submit\" value=\"entries\"type=\"submit\" onClick=\"display()\" style=\"float:right;\">entries</button>");
        out.println("<button name=\"submit\" value=\"clear\"type=\"submit\" onClick=\"display()\" style=\"float:right;\">clear file</button>");
        out.println("</form>");

        if(flag == false){
            out.println("<div id=\"error-message\" style=\"display:none;\">Incorrect input</div>");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Random String</p>");
            out.println("<input name=\"rand - string\" id=\"random - string\" type=\"text\" value=\""+ box1+ "\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Random String (with replacement)</p>");
            out.println("<input name=\"random-string-replace\" id=\"random-string-replace\" type=\"text\" value=\"" + box2 + "\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Random String (without replacement)</p>");
            out.println("<input name=\"random-string-replace-without\" id=\"random-string-replace-without\" type=\"text\" value=\""+ box3 + "\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Sorted Array</p>");
            out.println("<input name=\"sorted-array\" id=\"sorted-array\" type=\"text\" value=\""+ box4 + "\">");
            out.println("</div>");
            out.println("<br>");
            out.println("<div>");
            out.println("<p>Reverse Sorted Array</p>");
            out.println("<input name=\"reverse-sorted-array\" id=\"reverse-sorted-array\" type=\"text\" value=\""+ box5 +"\">");
            out.println("</div>");
            out.println("</body>");
        }
        else {
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
        }
    }

    /** *****************************************************
     *  Prints the <BODY> of the HTML page
     ********************************************************* */
    private void printResponseBody (PrintWriter out, String resourcePath){
        out.println("<body>");

        out.println("<p>");
        out.println(
                "Persistent Text");
        out.println("</p>");
        out.println("<p>");
        out.println("");
        out.println(" <table>");

        try {
            out.println("  <tr>");
            out.println("   <th>Text</th>");
            out.println("  </tr>");
            File file = new File(resourcePath);
            if(!file.exists() || file == null){
                out.println("  <tr>");
                out.println("   <td>No entries persisted yet.</td>");
                out.println("  </tr>");
                return;
            }

            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //String []  entry= line.split(VALUE_SEPARATOR);
                out.println("  <tr>");
                /*for(String value: entry){
                    out.println("   <td>"+value+"</td>");
                }
                 */
                out.println("   <td>"+ line +"</td>");
                out.println("  </tr>");
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        out.println(" </table>");
        out.println("");
        out.println("</p>");
        out.println("</body>");
    }

    private void printTail(PrintWriter out){
        out.println("");
        out.println("</html>");
    }


    /**********************************/
    private String[] convertToArray(String input) {
        String[] result = input.split(" ");
        return result;
    }

    private String randomString(String[] input) {
        int random = new Random().nextInt(input.length);
        globalRandom = random;
        return input[random];
    }

    private String randomStringWithoutReplacement(String[] input) {
        int random = new Random().nextInt(input.length);
        while (random == globalRandom) {
            random = new Random().nextInt(input.length);
        }
        return input[random];
    }

    private String[] sortArray(String[] input) {
        Arrays.sort(input);
        return input;
    }

    private String[] reverseSortedArray(String[] input) {
        String[] result = new String[input.length];
        int j = input.length - 1;
        for (int i = 0; i < input.length; i++) {
            result[i] = input[j--];
        }
        return result;
    }

    private String concatString (String [] array){
        String result = array[0];
        for(int i = 1; i < array.length; i++){
            result = result + " " + array[i];
        }
        return  result;
    }
    private String[] eraseDuplicates (String[] array) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < array.length; i++) {
            if(result.contains(array[i]) == false){
                result.add(array[i]);
            }
        }
        String[] ar = new String[result.size()];
        for(int i =0; i < result.size(); i++){
            ar[i] = result.get(i);
        }
        return ar;
    }
    public void checkHasTag(String[] str) {
        String pattern = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
        Pattern p = Pattern.compile(pattern);
        for (int i = 0; i < str.length; i++) {
            Matcher m = p.matcher(str[i]);
            if (m.find() == true){
                flag = true;
                return;
            }
        }
        flag = false;
    }
}  // end Hello
