package com.example.amoeba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class PersistenceTest {
    @Test
    void testRecordWin(){
        Persistence.ensureDbFileDeletedForTests();
        Persistence p = new Persistence();
        p.recordWin(\"Alice\");
        p.recordWin(\"Bob\");
        p.recordWin(\"Alice\");
        // open DB
        try(java.sql.Connection c = java.sql.DriverManager.getConnection(\"jdbc:sqlite:database.db\")){
            try(java.sql.Statement s = c.createStatement()){
                try(java.sql.ResultSet rs = s.executeQuery(\"SELECT wins FROM players WHERE name='Alice'\")){
                    assertTrue(rs.next());
                    assertEquals(2, rs.getInt(1));
                }
            }
        } catch(Exception e){ fail(e); }
        new File(\"database.db\").delete();
    }
}
