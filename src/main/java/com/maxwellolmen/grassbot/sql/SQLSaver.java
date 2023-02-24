package com.maxwellolmen.grassbot.sql;

import java.sql.SQLException;

public interface SQLSaver {
    public void autosave() throws SQLException;
}
