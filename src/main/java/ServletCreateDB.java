import DataBase.DataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletCreateDB extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        int id;
        DataBase db = new DataBase();
        db.CreateDataBase();
        StringBuilder sb = db.showDataBase();
        String dbName = db.existDataBase();

//        if (sb == null)
//        {
//            sb = new StringBuilder("ERROR");
//        }

        PrintWriter writer = response.getWriter();
        writer.println("<html><head></head><body> DataBases on Server (jdbc:mysql://localhost:3306/) -> </br> " + sb + "</br>");
        if (dbName == null)
        {
            dbName = "DataBase nor exist";
        }
        else
        {
            dbName = "DataBase " + dbName + " exist" ;
        }
        writer.println(dbName + "</br>");
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
