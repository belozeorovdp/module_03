import DataBase.DataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletDropTable extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String nameTable = request.getParameter("name");
        DataBase db = new DataBase();
        db.dropTable(nameTable);
        PrintWriter writer = response.getWriter();
        writer.println("<html><head></head><body>");
        writer.println("<a href=\"/index.jsp\">Back</a> </br>");
        writer.println("</body></html>");
        writer.flush();
        writer.close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }
}
