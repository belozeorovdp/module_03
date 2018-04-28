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

//        if (sb == null)
//        {
//            sb = new StringBuilder("ERROR");
//        }

        PrintWriter writer = response.getWriter();
        writer.println("<html><head></head><body>" + sb + "</body></html>");
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
