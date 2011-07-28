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

import javax.naming.Context;
import javax.naming.InitialContext;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case for {@link JndiDataSourceSequence}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class JndiDataSourceSequenceTest {

    static final String DATASOURCE_JNDI_NAME = "java:/comp/env/jdbc/testDataSource";
    static final String SEQUENCE_NAME = "mySequence";

    @BeforeClass
    public static void setUpJndi() throws Exception {
        /*
         * Parts copied from: http://blogs.sun.com/randystuph/entry/
         * injecting_jndi_datasources_for_junit
         */

        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:/comp");
        ic.createSubcontext("java:/comp/env");
        ic.createSubcontext("java:/comp/env/jdbc");

        // Construct DataSource
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        ic.bind(DATASOURCE_JNDI_NAME, dataSource);

        // Create sequence
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP SEQUENCE IF EXISTS " + SEQUENCE_NAME);
        statement.execute("CREATE SEQUENCE " + SEQUENCE_NAME
                + " START WITH 1 INCREMENT BY 10");
        statement.close();
    }

    @Test
    public void initialReservationByConstructor() {
        JndiDataSourceSequence seq = new JndiDataSourceSequence(SEQUENCE_NAME,
                DATASOURCE_JNDI_NAME);
        assertEquals(1L, seq.getNextValue());
    }

    @Test
    public void loopThroughNextValuesUntilRangeRunsOut() {
        JndiDataSourceSequence seq = new JndiDataSourceSequence(SEQUENCE_NAME,
                DATASOURCE_JNDI_NAME);
        long oldValue = seq.getNextValue();
        for (int i = 0; i < 10; ++i) {
            long newValue = seq.getNextValue();
            assertEquals(oldValue + 1, newValue);
            oldValue = newValue;
        }
    }

}
