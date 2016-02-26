package com.company;
import java.sql.*;
/**
 * Created by nathaniel on 1/21/16.
 */
public class SQLiteHelper {
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ROTORSETTINGS.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE ROTORSETTINGS" +
                    "(ROTORORDER           TEXT," +
                    "SETTING            INT," +
                    "FINGERPRINT        TEXT)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
