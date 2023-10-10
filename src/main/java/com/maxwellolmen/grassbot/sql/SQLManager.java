package com.maxwellolmen.grassbot.sql;

import java.sql.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class SQLManager {

    class ReverseComp implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }

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
        st.execute("CREATE TABLE IF NOT EXISTS grasscooldowns (id VARCHAR(32), timestamp BIGINT);");
        st.close();
    }

    public void verifyOpen() throws SQLException {
        if (connection.isClosed()) {
            connection = openConnection();
        }
    }

    public void saveGrassCounts(Map<Integer, List<String>> grassCounts) throws SQLException {
        verifyOpen();

        for (Map.Entry<Integer, List<String>> entry : grassCounts.entrySet()) {
            int count = entry.getKey();

            for (String id : entry.getValue()) {
                PreparedStatement pst = connection.prepareStatement("DELETE FROM grasscounts WHERE id=?;");
                pst.setString(1, id);
                pst.execute();
                pst.close();

                pst = connection.prepareStatement("INSERT INTO grasscounts (id, count) VALUES (?, ?);");
                pst.setString(1, id);
                pst.setInt(2, count);
                pst.execute();
                pst.close();
            }
        }
    }

    public void saveGrassCooldowns(Map<String, Long> grassCooldowns) throws SQLException {
        verifyOpen();

        for (Map.Entry<String, Long> entry : grassCooldowns.entrySet()) {
            PreparedStatement pst = connection.prepareStatement("DELETE FROM grasscooldowns WHERE id=?;");
            pst.setString(1, entry.getKey());
            pst.execute();
            pst.close();

            pst = connection.prepareStatement("INSERT INTO grasscooldowns (id, timestamp) VALUES (?, ?);");
            pst.setString(1, entry.getKey());
            pst.setLong(2, entry.getValue());
            pst.execute();
            pst.close();
        }
    }

    public Map<Integer, List<String>> getGrassCounts() throws SQLException {
        verifyOpen();

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT id, count FROM grasscounts;");
        Map<Integer, List<String>> grassCounts = new TreeMap<>(new ReverseComp());

        while (rs.next()) {
            int count = rs.getInt("count");
            String id = rs.getString("id");

            if (grassCounts.containsKey(count)) {
                grassCounts.get(count).add(id);
            } else {
                grassCounts.put(count, Arrays.asList(id));
            }
        }

        return grassCounts;
    }

    public Map<String, Long> getGrassCooldowns() throws SQLException {
        verifyOpen();

        Statement st = connection.createStatement();

        ResultSet rs = st.executeQuery("SELECT id, timestamp FROM grasscooldowns;");
        Map<String, Long> grassCooldowns = new HashMap<>();

        while (rs.next()) {
            grassCooldowns.put(rs.getString("id"), rs.getLong("timestamp"));
        }

        return grassCooldowns;
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