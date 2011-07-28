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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * This is a {@link DataSourceSequence} that uses JNDI to lookup the data
 * source.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class JndiDataSourceSequence extends DataSourceSequence {

	private static final long serialVersionUID = -6324463206509516233L;

	private final static Logger log = Logger.getLogger(JndiDataSourceSequence.class.getName());

    private final String jndiName;

    /**
     * Creates a new <code>JndiDataSourceSequence</code>.
     * 
     * @param sequenceName
     *            the name of the database sequence.
     * @param jndiName
     *            the JNDI name of the data source.
     */
    public JndiDataSourceSequence(String sequenceName, String jndiName) {
        super(sequenceName);
        assert jndiName != null && !jndiName.isEmpty() : "jndiName must not be null nor empty";
        this.jndiName = jndiName;
    }

    @Override
    protected DataSource lookupDataSource() {
        log.log(Level.INFO, "Looking up data source with JNDI name {0}", getJndiName());
        try {
            return (DataSource) new InitialContext().lookup(getJndiName());
        } catch (NamingException e) {
            log.log(Level.SEVERE, "Could not find data source", e);
            throw new IllegalStateException("Could not find data source", e);
        }
    }

    /**
     * Returns the JNDI name of the data source.
     */
    public final String getJndiName() {
        return jndiName;
    }

}
