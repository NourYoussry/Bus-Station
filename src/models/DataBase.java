package models;

import java.sql.*;

public class DataBase {

    private Connection con;

    public DataBase() throws Exception {
        initConnection();
    }

    public void initConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_station", "root", "admin");
    }

    public void closeConnection() throws Exception {
        con.close();
    }

    public ResultSet Query(String statement) throws Exception {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(statement);
        return rs;
    }

    /**
     * Provides facility to filter MySQL tables. The method expects its first
     * argument to be the table name. The second argument is a two dimensional
     * array of strings that represents an array of pairs, the first item is
     * the column name and the second item is the column value that will be
     * used to filter the table with.
     * <p>
     * Example: Consider a table called employees that has two columns, namely,
     * name and id. Say that we want to filter this table based on the name of
     * the employee and his id.
     * <p>
     * String[][] filters = {
     * {"name", "Amr"},  ---------> {"col_name", "col_value"}
     * {"id"  , "4691"}
     * };
     * <p>
     * ResultSet rs = filterTableBy("employees", filters);
     * <p>
     * After executing this snippet, the ResultSet rs would contain all the rows
     * of table employees that have their name column equal to Amr and their id
     * column equal to 4691.
     *
     * @param tableName
     * @param filters
     * @return
     * @throws java.lang.Exception
     */
    public ResultSet filterTableBy(String tableName, String[][] filters) throws Exception {
        int nosRows = filters.length;

        String stmt = "Select * FROM " + tableName + " WHERE ";
        for (String[] row : filters) {
            stmt += row[0] + " = \"" + row[1] + "\"";
            if (nosRows > 1) {
                stmt += " AND ";
                nosRows--;
            }
        }
        stmt += ";";
        return Query(stmt);
    }

    /**
     * Provides facility to update MySQL table records (rows). The method
     * expects its first argument to be the table name, the second argument is
     * a two dimensional array of strings that represents an array of pairs, the
     * first item in the pair is the column name you want to change and the second
     * item is the new column value, the third argument is a two dimensional
     * array of strings that represents an array of pairs, the first item is
     * the column name and the second item is the column value that will be
     * used to filter the table with (conditions that specify which rows would
     * actually be updated).
     * <p>
     * Example: Consider a table called employees that has two columns, namely,
     * name and id. Say that we want to modify all records that have its name
     * equals Amr, for example we want to change its id.
     * <p>
     * String[][] values = {
     * {"id", "1359"}    ---------> {"col_name", "new_col_value"}
     * };
     * <p>
     * String[][] filters = {
     * {"name", "Amr"},  ---------> {"col_name", "col_value"}
     * };
     * <p>
     * int numberOfRecordsAffected = updateRecord("employees", values, filters);
     * <p>
     * After executing this snippet, all the rows of table employees that had
     * their name column equal to Amr would have their id updated to 1359.
     *
     * @param tableName
     * @param values
     * @param filters
     * @return
     * @throws java.lang.Exception
     */
    public int updateRecords(String tableName, String[][] values, String[][] filters) throws Exception {
        int numberOfFilters = filters.length;
        int numberOfValues = values.length;

        String query = "UPDATE " + tableName + " SET ";

        // append the values
        for (String[] colValue : values) {
            query += colValue[0] + " = \"" + colValue[1] + "\"";
            if (numberOfValues > 1) {
                query += ", ";
                numberOfValues--;
            }
        }

        query += " WHERE ";

        // append the filters
        for (String[] filter : filters) {
            query += filter[0] + " = \"" + filter[1] + "\"";
            if (numberOfFilters > 1) {
                query += " AND ";
                numberOfFilters--;
            }
        }
        query += ";";

        // execute the query and return the number of records affected
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query);
    }

    /**
     * Same syntax as filterTableBy but deletes.
     *
     * @param tableName
     * @param filters
     * @return
     * @throws SQLException
     */
    public int deleteRecords(String tableName, String[][] filters) throws SQLException {
        int numberOfFilters = filters.length;

        String query = "DELETE FROM " + tableName + " WHERE ";
        for (String[] filter : filters) {
            query += filter[0] + " = \"" + filter[1] + "\"";
            if (numberOfFilters > 1) {
                query += " AND ";
                numberOfFilters--;
            }
        }
        query += ";";

        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query);
    }

    /**
     * Returns a ResultSet containing all the records of table tableName.
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public ResultSet getRecords(String tableName) throws Exception {
        String query = "SELECT * FROM " + tableName + ";";
        return Query(query);
    }

    /**
     * Provides facility to add records to MySQL tables. The method expects its
     * first argument to be the table name. The second argument is a two dimensional
     * array of strings that represents an array of pairs, the first item is
     * the column name and the second item is the column value.
     * <p>
     * Example: Consider a table called employees that has two columns, namely,
     * name and id. Say that we want to add a new record to this table.
     * <p>
     * String[][] values = {
     * {"name", "Amr"},  ---------> {"col_name", "col_value"}
     * {"id"  , "4691"}
     * };
     * <p>
     * insertRecord("employees", values);
     * <p>
     * After executing this snippet, the employees table would contain a new
     * record with the given values.
     *
     * @param tableName
     * @param values
     * @return
     * @throws java.lang.Exception
     */
    public int insertRecord(String tableName, String[][] values) throws Exception {
        int valuesNumber = values.length;
        int aux = valuesNumber;

        String query = "INSERT INTO " + tableName + " (";

        for (String[] value : values) {
            query += value[0];
            if (aux-- > 1) query += ", ";
        }

        query += ") VALUES (";

        for (String[] value : values) {
            query += "\"" + value[1] + "\"";
            if (valuesNumber-- > 1) query += ", ";
        }

        query += ");";

        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query);
    }
}