import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletCreateTable extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException
    {
        int id;
        String nameTable = request.getParameter("name");
        String columns   = request.getParameter("columns");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head></head><body>" + "name: " + nameTable + "</br>" + "columns:" + columns + "</body></html>");
        writer.flush();
        writer.close();

//        DataBaseManipulation dbManager = new DataBaseManipulation();
//        RequestDispatcher requestDispatcher = null;
//
//        //if (!dbManager.isAlreadyExists("name", name) || !dbManager.isAlreadyExists("email", email))
//        {
//            id = dbManager.addUserToDB(name, email, password);
//
//            response.addCookie(new Cookie("user_id", String.valueOf(id)));
//
//            requestDispatcher = request.getRequestDispatcher("index.jsp");
//            requestDispatcher.forward(request, response);
//        }
//        else
//        {
//            requestDispatcher = request.getRequestDispatcher("form.html");
//            requestDispatcher.forward(request, response);
//        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }


}
