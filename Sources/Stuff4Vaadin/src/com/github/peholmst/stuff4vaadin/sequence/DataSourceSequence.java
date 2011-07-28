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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.github.peholmst.stuff4vaadin.common.Interval;


/**
 * This is a {@link Sequence}-implementation that uses a database sequence as
 * backend and accesses it through JDBC. The SQL syntax used is at least
 * compatible with PostgreSQL and H2.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public abstract class DataSourceSequence extends Sequence {

	private static final long serialVersionUID = 3030379000632459832L;

	private static final Logger log = Logger.getLogger(DataSourceSequence.class.getName());

    private final String sequenceName;

    private final static String QUERY_NEXT_VALUE = "SELECT NEXTVAL('%s')";

    private final static String QUERY_CURRENT_VALUE = "SELECT CURRVAL('%s')";

    /**
     * Creates a new <code>DataSourceSequence</code> that uses the specified
     * database sequence.
     */
    public DataSourceSequence(String sequenceName) {
        assert sequenceName != null && !sequenceName.isEmpty() : "sequenceName must not be null nor empty";
        this.sequenceName = sequenceName;
    }

    /**
     * Returns the name of the sequence (never <code>null</code>).
     */
    public final String getSequenceName() {
        return sequenceName;
    }

    /**
     * Looks up the JDBC data source that is to be used for accessing the
     * sequence. If the data source cannot be found, an unchecked exception is
     * thrown.
     */
    protected abstract DataSource lookupDataSource();

    @Override
    protected Interval<Long> reserveSequenceValues() {
        log.log(Level.FINE, "Reserving sequence values");
        log.log(Level.FINE, "Using sequence name {0}", sequenceName);
        DataSource dataSource = lookupDataSource();
        try {
            Connection connection = dataSource.getConnection();
            long intervalStart = getCurrentSequenceValue(connection);
            if (intervalStart < 0) {
                /*
                 * If the sequence has just been created, the current value may
                 * be negative, in which case we have to fetch the next sequence
                 * value.
                 */
                intervalStart = getNextSequenceValue(connection);
            }
            long intervalEnd = getNextSequenceValue(connection) - 1;
            log.log(Level.FINE, "Reserved sequence values [{0}, {1}]", new Object[] {intervalStart,
                    intervalEnd});
            connection.close();
            return Interval.createClosedInterval(intervalStart, intervalEnd);
        } catch (SQLException e) {
            throw new RuntimeException("Could not reserve sequence values", e);
        }
    }

    private long getCurrentSequenceValue(Connection connection)
            throws SQLException {
        return queryForLong(
                String.format(QUERY_CURRENT_VALUE, getSequenceName()),
                connection);
    }

    private long getNextSequenceValue(Connection connection)
            throws SQLException {
        return queryForLong(String.format(QUERY_NEXT_VALUE, getSequenceName()),
                connection);
    }

    /**
     * Executes the given SQL query and assumes that the result will contain a
     * single long integer.
     * 
     * @param query
     *            the query to execute.
     * @param connection
     *            the connection to use for executing the query.
     * @return the query result.
     * @throws SQLException
     *             if something went wrong while executing the query or
     *             retrieving the result.
     */
    protected final long queryForLong(String query, Connection connection)
            throws SQLException {
        assert query != null : "query must not be null";
        assert connection != null : "connection must not be null";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        result.next();
        return result.getLong(1);
    }

}
