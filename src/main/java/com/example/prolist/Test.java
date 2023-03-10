package com.example.prolist;

import java.sql.*;

public class Test implements DAO{
    public void query() throws ClassNotFoundException {
        String sql = "CREATE TABLE t(id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));";

        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection
                    (DB_URL, USER, PASS);

            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public static void main(String args[]) throws ClassNotFoundException {
        Test t = new Test();
        t.query();
    }
}
