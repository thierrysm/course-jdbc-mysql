package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.*;

public class Program {

    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;
        try {
            conn = DB.getConnection();

            conn.setAutoCommit(false);

            st = conn.createStatement();

            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3000 WHERE DepartmentId = 2");

            System.out.println(rows1 + " rows1");
            System.out.println(rows2 + " rows2");

            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transaction rollback caused by: " + e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Error try to rollback caused by: " + e.getMessage());
            }
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
