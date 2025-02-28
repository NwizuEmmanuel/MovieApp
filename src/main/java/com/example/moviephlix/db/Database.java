package com.example.moviephlix.db;

import java.sql.*;

public class Database {

    final String url = "jdbc:mysql://localhost:3306/movie_db";
    final String user = "root";
    final String password = "";
    final String tableName = "movie_table";
    final int numCols = 6;

    final Connection connection;

    {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    checks if table exit, if not it creates one
    public Database(){
        String createTableQuery = "create table if not exists " + tableName +"(" +
                "id int primary key auto_increment," +
                "title varchar(255) not null," +
                "director varchar(255) not null," +
                "release_year int not null," +
                "runtime int not null," +
                "file_path text not null)";
        try {
            Statement statement = connection.createStatement();

            boolean tableExist = false;
            String checkTableQuery = "show tables like '" + tableName +"'";

            if(statement.executeQuery(checkTableQuery).next()){
                tableExist = true;
            }

            if (!tableExist){
                statement.executeUpdate(createTableQuery);
                System.out.println("Created table successfully");
            }else{
                System.out.println("Table already exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    use for insert data into table or db
    public void insertData(String title, String director, int releaseYear, int runtime, String filePath){
        String insertQuery = "insert into " + tableName +"(" +
                "title, director, release_year, runtime, file_path) values (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, director);
            preparedStatement.setInt(3, releaseYear);
            preparedStatement.setInt(4, runtime);
            preparedStatement.setString(5, filePath);

            int rowsInserted = preparedStatement.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("Rows inserted successfully");
            }else{
                System.out.println("Rows insertion failed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    use for delete data from table base on id assigned
    public void deleteData(int id){
        String deleteQuery = "delete from " + tableName + " where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0){
                System.out.println("Data deleted successfully.");
            }else{
                System.out.println("No Data found/deletion failed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    use for altering or updating data in table
    public void updateData(int updateId, String title, String director, int releaseYear, int runtime, String filePath){
        String updateQuery = "update " + tableName + " set title = ?, " +
                "director = ?, release_year = ?, " +
                "runtime = ?, file_path = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, director);
            preparedStatement.setInt(3, releaseYear);
            preparedStatement.setInt(4, runtime);
            preparedStatement.setString(5, filePath);
            preparedStatement.setInt(6, updateId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0){
                System.out.println("Data update successfully.");
            }else{
                System.out.println("Data not found or update failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    returns all data from table
    public String[][] queryData(){
        String selectQuery = "select * from " + tableName;
        String[][] items = new String[countData()][numCols];

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            int row = 0;
            int column = 0;
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                int releaseYear = resultSet.getInt("release_year");
                int runtime = resultSet.getInt("runtime");
                String filePath = resultSet.getString("file_path");

                items[row][column] = String.valueOf(id);
                column++;
                items[row][column] = title;
                column++;
                items[row][column] = director;
                column++;
                items[row][column] = String.valueOf(releaseYear);
                column++;
                items[row][column] = String.valueOf(runtime);
                column++;
                items[row][column] = filePath;
                row++;
                column = 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

//    return number of all rows in table
    public int countData(){
        int count = 0;

        String countQuery = "select count(*) as count from " + tableName;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(countQuery);
            if(resultSet.next()){
                count = resultSet.getInt("count");
                System.out.println("Number of rows: " + count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

//    returns number of data from table based on user search queries (title)
//    user is only meant to enter the title prefix
    public int countData(String title){
        int count = 0;

        String countQuery = "select count(*) as count from " + tableName + " where title like ?";

        try {
            PreparedStatement statement = connection.prepareStatement(countQuery);
            statement.setString(1, title+"%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                count = resultSet.getInt("count");
                System.out.println("Number of rows: " + count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

//    return data from table based id entered by user
    public String[] idSearch(int searchId){
        String[] items =new String[numCols];
        String searchIdQuery = "select * from " + tableName + " where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(searchIdQuery);
            preparedStatement.setInt(1, searchId);

            ResultSet resultSet = preparedStatement.executeQuery();

            int column = 0;
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                int releaseYear = resultSet.getInt("release_year");
                int runtime = resultSet.getInt("runtime");
                String filePath = resultSet.getString("file_path");

                items[column] = String.valueOf(id);
                column++;
                items[column] = title;
                column++;
                items[column] = director;
                column++;
                items[column] = String.valueOf(releaseYear);
                column++;
                items[column] = String.valueOf(runtime);
                column++;
                items[column] = filePath;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

//    returns data based on title prefix entered by user
    public String[][] titleSearch(String searchTitle){
        String searchQuery = "select * from " + tableName + " where title like ?";
        String[][] items = new String[countData(searchTitle)][numCols];

        try {
            PreparedStatement statement = connection.prepareStatement(searchQuery);
            statement.setString(1, searchTitle + "%");
            ResultSet resultSet = statement.executeQuery();

            int row = 0;
            int column = 0;
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                int releaseYear = resultSet.getInt("release_year");
                int runtime = resultSet.getInt("runtime");
                String filePath = resultSet.getString("file_path");

                items[row][column] = String.valueOf(id);
                column++;
                items[row][column] = title;
                column++;
                items[row][column] = director;
                column++;
                items[row][column] = String.valueOf(releaseYear);
                column++;
                items[row][column] = String.valueOf(runtime);
                column++;
                items[row][column] = filePath;
                row++;
                column = 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
