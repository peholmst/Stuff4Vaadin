/*
 * Copyright (c) 2011 Petter Holmström
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.peholmst.stuff4vaadin.sequence;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case for {@link DataSourceSequence}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class DataSourceSequenceTest {

    private static JdbcDataSource dataSource;

    static final String SEQUENCE_NAME = "mySequence";

    @BeforeClass
    public static void setUpDataSource() throws Exception {
    	// Change logging settings so that debug messages are shown in the console
    	final ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.FINEST);
        Logger.getLogger("").addHandler(ch);
    	Logger.getLogger("").setLevel(Level.FINEST);

    	dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP SEQUENCE IF EXISTS " + SEQUENCE_NAME);
        statement.execute("CREATE SEQUENCE " + SEQUENCE_NAME
                + " START WITH 1 INCREMENT BY 10");
        statement.close();
        connection.close();
    }

    @SuppressWarnings("serial")
	static class SequenceUnderTest extends DataSourceSequence {

        public SequenceUnderTest() {
            super("mySequence");
        }

        @Override
        protected DataSource lookupDataSource() {
            return dataSource;
        }
    }

    @Test
    public void initialReservationByConstructor() {
        SequenceUnderTest seq = new SequenceUnderTest();
        assertEquals(1L, seq.getNextValue());
    }

    @Test
    public void loopThroughNextValuesUntilRangeRunsOut() {
        SequenceUnderTest seq = new SequenceUnderTest();
        long oldValue = seq.getNextValue();
        for (int i = 0; i < 10; ++i) {
            long newValue = seq.getNextValue();
            assertEquals(oldValue + 1, newValue);
            oldValue = newValue;
        }
    }

}
