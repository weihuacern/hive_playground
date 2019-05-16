package com.HiveClient;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Random;

import org.apache.hive.jdbc.HiveDriver;

public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    /**
     * Generate a random string.
     *
     * @param random the random number generator.
     * @param characters the characters for generating string.
     * @param length the length of the generated string.
     * @return
     */
    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }

        //replace "hive" here with the name of the user the queries should run as
        Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "scott", "tiger");
        //Connection con = DriverManager.getConnection("jdbc:hive2://35.235.75.246:10000/default", "scott", "tiger");
        Statement stmt = con.createStatement();
        //String tableName = "testHiveDriverTable";
        String tableName = "huaTest001";
        stmt.execute(String.format("drop table if exists %s", tableName));
        stmt.execute(String.format("create table %s (key int, value string)", tableName));

        // show tables
        String sql = String.format("show tables '%s'", tableName);
        System.out.println(String.format("Running: %s", sql));
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }
        // describe table
        sql = String.format("describe %s", tableName);
        System.out.println(String.format("Running: %s", sql));
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }

        HiveJdbcClient hc = new HiveJdbcClient();
        //batch insert
        for (int i = 0; i < 10; i++) {
            String rs = hc.generateString(new Random(), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890", 10);
            // load data into table
            sql = String.format("insert into table %s values (%d, '%s')",
                                tableName,
                                i, rs);
            System.out.println(String.format("Running: %s", sql));
            stmt.execute(sql);
        }

        // select * query
        sql = String.format("select * from %s", tableName);
        System.out.println(String.format("Running: %s", sql));
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.format("%s\t%s", String.valueOf(res.getInt(1)), res.getString(2)));
        }

        // regular hive query
        sql = String.format("select count(1) from %s", tableName);
        System.out.println(String.format("Running: %s", sql));
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }
    }
}
