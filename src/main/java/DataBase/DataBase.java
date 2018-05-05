package DataBase;

import com.google.gson.JsonObject;
import org.json.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase
{
    private String url      = "jdbc:mysql://localhost:3306/";
    private String nameDB   = "exseldb";
    private String user     = "root";
    private String password = "dima";
    private String fullUrlForDB;

    public DataBase()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        //fullUrlForDB = url + nameDB + urlSufix;
        //fullUrlForDB = url + nameDB;
        fullUrlForDB = url;//  + nameDB;
    }

    public void CreateDataBase()
    {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement())
        {
            // CREATE DATABASE DBName;
            // CREATE DATABASE IF NOT EXISTS DBName;
            int Result = stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nameDB + ";");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public StringBuilder showDataBase()
    {
        String  query = "SHOW DATABASES;";
        StringBuilder sb = new StringBuilder();
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDB, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();
            rs.last();
            int resnum = rs.getRow();
            int j = 0;
            rs.beforeFirst();
            while(rs.next())
            {
                if (j < resnum - 1)
                {
                    for (int i = 1; i <= columns; i++)
                    {
                        sb.append(rs.getString(i) + ",\t");
                    }
                    j++;
                }
                else
                {
                    for (int i = 1; i <= columns; i++)
                    {
                        sb.append(rs.getString(i) + "\t");
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return sb;

    }

    public String existDataBase()
    {
        String  query = "SHOW DATABASES;";
        String result = null;
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDB, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();
            while(rs.next())
            {
                for (int i = 1; i <= columns; i++)
                {
                    if (rs.getString(i).equals(nameDB))
                    {
                      result = rs.getString(i);
                    }
                }
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    // SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'DBName'
    public static void SELECTQuery(String nameBD, String query)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameBD, "root", "dima");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();
            while(rs.next())
            {
                for (int i = 1; i <= columns; i++)
                {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println();
            rs.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public  void createTable(String nameTable, int columns)
    {
        String namePrefix = "Column_";
        String partQuery = " id INT NOT NULL AUTO_INCREMENT, ";
        String typeColumn = " VARCHAR (255)";
        for (int i = 0; i < columns; i++)
        {
            if (i < columns - 1)
            {
                partQuery += namePrefix + i + " " + typeColumn + ",";
            }
            else if (i < columns)
            {
                partQuery += namePrefix + i + " " + typeColumn + ", PRIMARY KEY (id)";
            }
        }
        String query = "CREATE TABLE " + nameTable + " (" + partQuery + ");";

        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement();
            )
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // -------------
    public void plusData(String nameTable, String data)
    {
        String namePrefix = "Column_";
        String[] subStr = data.split(",");
        int length = subStr.length;
        String partQuery = "INSERT INTO " + nameTable + " (";
        for (int i = 0; i < length; i++)
        {
            if (i < length - 1)
            {
                partQuery += namePrefix + i + ",";
            }
            else if (i < length)
            {
                partQuery += namePrefix + i + ") ";
            }
        }
        partQuery  += " VALUES (";
        for (int i = 0; i < length; i++)
        {
            if (i < length - 1)
            {
                partQuery += "'" + subStr[i] + "'" + ", ";
            }
            else
            {
                partQuery += "'" + subStr[i] + "'" +  "); ";
            }
        }
        System.out.println(partQuery);
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
            )
        {
            statement.executeUpdate(partQuery);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void plusDataRows(String nameTable, String data)
    {
        int count = countColums(nameTable);
        String namePrefix = "Column_" + count;
        String[] subStr = data.split(",");
        int length = subStr.length;
        plusColumn(nameTable, namePrefix);
        for (int i = 0; i < length; i++)
        {
            plusValue(nameTable, namePrefix, subStr[i]);
        }
    }

    private int countColums(String nameTable)
    {
        // SHOW COLUMNS FROM disk
        String  query = "SHOW COLUMNS FROM " + nameTable + ";";
        int result = 0;
        try ( Connection connection = DriverManager.getConnection(url + nameDB, user, password);
              Statement statement = connection.createStatement();
            )
        {
            ResultSet rs = statement.executeQuery(query);
            result = rs.getInt(0);
//            int columns = rs.getMetaData().getColumnCount();
//            while(rs.next())
//            {
//                for (int i = 0; i <= columns; i++)
//                {
//                        result = rs.getInt(i);
//
//                }
//            }
            rs.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    // ALTER TABLE table_name ADD field_name parameters
    private void plusColumn(String nameTable, String nameColumn)
    {
        String  query = "ALTER TABLE " + nameTable + " ADD " + nameColumn + " TEXT;";
        System.out.println(query);
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    // INSERT INTO employee(id, name) VALUES(200, 'Jason');
    private void plusValue(String nameTable, String nameColumn, String Value)
    {
        String  query = "INSERT INTO " + nameTable + "(" + nameColumn + ") " + "VALUES('" + Value + "');";
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void dropTable(String nameTable)
    {
        String query = "DROP TABLE " + nameTable + ";";
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void dropRow(String nameTable, int id)
    {
        String query = "DELETE FROM " + nameTable + " WHERE id = " + id + ";";
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // ALTER TABLE tbl_Country DROP COLUMN IsDeleted;
    public void dropColumn(String nameTable, String nameColumn)
    {
        String query = "ALTER TABLE " + nameTable + " DROP COLUMN " + nameColumn + ";";
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private ArrayList<String> idArray = new ArrayList<>();
    public ArrayList<JsonObject> getJson(String nameTable)
    {

        ArrayList<JsonObject> result = new ArrayList<>();
        ArrayList<String> nameColumns = new ArrayList<>();

        String query = "SELECT * FROM " + nameTable + ";";
        try (Connection connection = DriverManager.getConnection(url + nameDB, user, password);
             Statement statement = connection.createStatement()
            )
        {
            ResultSet resultSet = statement.executeQuery(query);
            int count = resultSet.getMetaData().getColumnCount();
            for(int i = 1; i <= count; i++)
            {
                nameColumns.add(resultSet.getMetaData().getColumnName(i));
            }
            while (resultSet.next())
            {
                JsonObject jsonObject = new JsonObject();
                for(int i = 1; i <= count; i++)
                {
                    if(i == 1)
                    {
                        idArray.add(resultSet.getString(i));
                    }
                    else
                    {
                        String key = nameColumns.get(i - 1);
                        String value = resultSet.getString(i);
                        jsonObject.addProperty(key, value);
                        System.out.println(key);
                        System.out.println(value);
                    }
                }
                result.add(jsonObject);
            }
            resultSet.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public  ArrayList<String> getArrayId()
    {
        return idArray;
    }

}


