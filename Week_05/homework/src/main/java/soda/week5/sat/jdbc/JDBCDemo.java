package soda.week5.sat.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDemo {

    public static void main(String[] args) throws SQLException {
        //mysql + jdbc, save, transaction, query
        JDBCDemo jdbcDemo = new JDBCDemo();
        jdbcDemo.tranSave();
        jdbcDemo.query();

        HikariConfig config = new HikariConfig("/jdbc.properties");
        HikariDataSource ds = new HikariDataSource(config);
        Connection conn = ds.getConnection();
        jdbcDemo.save(conn, "Alpha", 2);

    }


    /**
     * get mysql connection
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soda_test", "root", "12345678");
        return conn;
    }


    /**
     * execute transaction insert by jdbc connection
     *
     * @throws SQLException
     */
    public void tranSave() throws SQLException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);

            String sql1 = "insert into t_dog values (null, 'dog1', 1)";
            String sql2 = "insert into t_dog values (null, 'dog2', 2)";
            String sql3 = "insert into t_dog values (null, 'dog3', 3)";

            Statement statement = conn.createStatement();

            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
            statement.executeUpdate(sql3);

            conn.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            conn.rollback();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    /**
     * execute insert by connection which Hik-connection-pool
     *
     * @param conn
     * @param dogName
     * @param dogAge
     * @throws SQLException
     */
    public void save(Connection conn, String dogName, Integer dogAge) throws SQLException {
        Assert.notNull(dogName, "need dog name");
        Assert.notNull(dogAge, "need dog age");

        Statement statement = conn.createStatement();
        String sql = "insert into t_dog values (null, '" + dogName + "', " + dogAge + ")";
        int i = statement.executeUpdate(sql);
        System.out.printf("affected %d row", i);
    }

    public List<Dog> query() {
        List<Dog> ret = new ArrayList<>();

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from t_dog";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                Dog dog = new Dog(id, name, age);
                ret.add(dog);

                System.out.println(dog);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
