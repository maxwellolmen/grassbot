package com.maxwellolmen.grassbot.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SQLManager {
    
    private Connection connection;

    private ArrayList<SQLSaver> savers;

    public void init() {
        savers = new ArrayList<>();

        try {
            connection = openConnection();

            initTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws SQLException {
        Properties props = new Properties();

        props.put("user", "root");
        props.put("password", "#!monakeR!477");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/grassbot", props);
    }

    public void initTables() throws SQLException {
        Statement st = connection.createStatement();
        st.execute("CREATE TABLE IF NOT EXISTS grasscounts (id VARCHAR(32), count INT(8));");
        st.close();
    }

    public void verifyOpen() throws SQLException {
        if (connection.isClosed()) {
            connection = openConnection();
        }
    }

    public void saveGrassCounts(Map<String, Integer> grassCounts) throws SQLException {
        verifyOpen();

        for (Map.Entry<String, Integer> entry : grassCounts.entrySet()) {
            PreparedStatement pst = connection.prepareStatement("DELETE FROM grasscounts WHERE id=?;");
            pst.setString(1, entry.getKey());
            pst.execute();
            pst.close();

            pst = connection.prepareStatement("INSERT INTO grasscounts (id, count) VALUES (?, ?);");
            pst.setString(1, entry.getKey());
            pst.setInt(2, entry.getValue());
            pst.execute();
            pst.close();
        }
    }

    public Map<String, Integer> getGrassCounts() throws SQLException {
        verifyOpen();

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT id, count FROM grasscounts;");
        Map<String, Integer> grassCounts = new HashMap<>();

        while (rs.next()) {
            grassCounts.put(rs.getString("id"), rs.getInt("count"));
        }

        return grassCounts;
    }

    public String[] getTopGrassCounts() throws SQLException {
        verifyOpen();

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT id FROM grasscounts ORDER BY count DESC LIMIT 10;");
        String[] ids = new String[10];

        int i = 0;
        while (rs.next()) {
            ids[i] = rs.getString(1);
            i++;
        }

        return ids;
    }

    public void addSaver(SQLSaver saver) {
        savers.add(saver);
    }

    public void autosave() {
        System.out.println("Autosaving SQL...");

        for (SQLSaver saver : savers) {
            try {
                System.out.println("Saving " + saver.getClass().getName() + "...");
                saver.autosave();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}