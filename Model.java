import java.sql.*;

public class Model {
    static final String jDriver = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/buy";
    static final String dbUser = "root";
    static final String dbPass = "";
    Connection dbConnection;
    Statement statement;
    String [][] tableData;


     private String  s1 = "CREATE TABLE IF NOT EXISTS Buyers (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
            "firstname VARCHAR(30) NOT NULL," +
            "lastname VARCHAR(30) NOT NULL," +
            "adress VARCHAR(30) NOT NULL," +
            "credit_card_number VARCHAR(30) NOT NULL," +
            "bank_card_number VARCHAR(30) NOT NULL" +
            ");";

    private String s2 = "INSERT INTO Buyers VALUES " +
            "(1,'Vasilis','Ivanov','Sumy','3242','423252')," +
            "(2,'Igor','Petrov','Kiev','3453','342342')," +
            "(3,'Vladislav','Shepotin','Sumy','3242','423252')," +
            "(4,'Kolya','Ivanov','Sumy','3242','423252')," +
            "(5,'Emil','Babaev','Sumy','3242','423252')," +
            "(6,'Sasha','Sergeev','Sumy','3242','423252')" +
            ";";

    Model(){
        dbConnection = getDBConnection();
        try {
            statement = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDBConnection() {
        try {
            Class.forName(jDriver);
            dbConnection = DriverManager.getConnection(DB_URL, dbUser, dbPass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    public void makeFirstTable(){
        try {

            statement.execute(s1);
            //statement.execute(s2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String[][] getNamesSurnamesSorted(){
        String res = "";

        try{
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from Buyers order by firstname;");
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            tableData = new String[size][6];
            int i=0,j=0;
            while(rs.next()){
                tableData[i][j] = rs.getString("id"); j++;
                tableData[i][j] = rs.getString("firstname"); j++;
                tableData[i][j] = rs.getString("lastname"); j++;
                tableData[i][j] = rs.getString("adress"); j++;
                tableData[i][j] = rs.getString("credit_card_number"); j++;
                tableData[i][j] = rs.getString("bank_card_number"); j++;
                i++; j=0;
            }

        } catch (SQLException e){
            e.printStackTrace();}
        return tableData;
    }

    public String[][] getNamesSurnamesCreditInterval(int range1, int range2){
        if(range1 == (int)range1 && range2 == (int)range2)
        try{
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from Buyers WHERE credit_card_number BETWEEN " + range1 + " AND " + range2 + ";");
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            tableData = new String[size][6];
            int i=0,j=0;
            while(rs.next()){
                tableData[i][j] = rs.getString("id"); j++;
                tableData[i][j] = rs.getString("firstname"); j++;
                tableData[i][j] = rs.getString("lastname"); j++;
                tableData[i][j] = rs.getString("adress"); j++;
                tableData[i][j] = rs.getString("credit_card_number"); j++;
                tableData[i][j] = rs.getString("bank_card_number"); j++;
                i++; j=0;
            }

        } catch (SQLException e){
            e.printStackTrace();}
        return tableData;
    }

    public String[][] getSelectAll() {
            try {
                dbConnection = getDBConnection();
                statement = dbConnection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM Buyers;");
                rs.last();
                int size = rs.getRow();
                rs.beforeFirst();
                tableData = new String[size][6];
                int i=0,j=0;
                while(rs.next()){
                    tableData[i][j] = rs.getString("id"); j++;
                    tableData[i][j] = rs.getString("firstname"); j++;
                    tableData[i][j] = rs.getString("lastname"); j++;
                    tableData[i][j] = rs.getString("adress"); j++;
                    tableData[i][j] = rs.getString("credit_card_number"); j++;
                    tableData[i][j] = rs.getString("bank_card_number"); j++;
                    i++; j=0;
                }
            } catch (SQLException e) {
            }
        return tableData;
    }

    public void queryMake(String s) {
        if (s != null)
            try {
                statement.execute(s);
            } catch (SQLException e) {
                e.printStackTrace();}
    }


    void closeConn() {
        try {
            dbConnection = getDBConnection();
            dbConnection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

