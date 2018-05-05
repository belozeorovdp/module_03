import DataBase.DataBase;

import com.google.gson.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ServletProcessing extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String nameTable = request.getParameter("name");

        DataBase db = new DataBase();
        ArrayList<JsonObject> jsonArray = db.getJson(nameTable);

         PrintWriter writer = response.getWriter();
        writer.println("<html><head></head><body>");

        for(int i = 0; i < jsonArray.size(); i++)
        {
            if(jsonArray.size() == 1)
            {
                writer.print("{" + db.getArrayId().get(i) + ":");
                writer.print(jsonArray.get(i) + "}");
                break;
            }
            else if(i == 0)
            {
                writer.print("{" + db.getArrayId().get(i) + ":");
                writer.print(jsonArray.get(i) + ", ");
            }
            else if (i == jsonArray.size() - 1)
            {
                writer.print(db.getArrayId().get(i) +  ":");
                writer.print(jsonArray.get(i) + "}");
            } else {
                writer.print(db.getArrayId().get(i) + ":");
                writer.print(jsonArray.get(i) + ", ");
            }

        }
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
