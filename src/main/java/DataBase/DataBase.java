package DataBase;

import java.sql.*;

public class DataBase
{
    private String url      = "jdbc:mysql://localhost:3306/";
    private String nameDB   = "exseldb";
    private String user     = "root";
    private String password = "dima";
    private String urlSufix = "?verifyServerCertificate=false&useSSL=true";
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
        fullUrlForDB = url + nameDB;
    }

    public void CreateDataBase()
    {
        //Connection connection = null;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement())
        {
//            connection = DriverManager.getConnection(url, user, password);
            // Statement stmt = null;
            // stmt = connection.createStatement();
            int Result = stmt.executeUpdate("CREATE DATABASE " + nameDB + ";");

//            stmt.close();
//            connection.close();
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
            while(rs.next())
            {
                for (int i = 1; i <= columns; i++)
                {
                    sb.append(rs.getString(i) + "\t");
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

    // SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'DBName'
    public static void SELECTQuery(String nameBD, String query)
    {
        // nameBD = FileManipulation.getLastNamePathDirectoryOrFile(nameBD);
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

}


