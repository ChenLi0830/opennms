//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2008 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.                                                            
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//       
// For more information contact: 
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//

package org.opennms.netmgt.poller;

import org.opennms.netmgt.config.collector.CollectionAttribute;
import org.opennms.netmgt.config.collector.CollectionAttributeType;
import org.opennms.netmgt.config.collector.CollectionResource;
import org.opennms.netmgt.config.collector.CollectionSetVisitor;
import org.opennms.netmgt.config.collector.Persister;
import org.opennms.netmgt.config.collector.ServiceParameters;

/**
 * <p>LatencyCollectionAttribute class.</p>
 *
 * @author <a href="mailto:agalue@opennms.org">Alejandro Galue</a>
 * @version $Id: $
 */
public class LatencyCollectionAttribute implements CollectionAttribute {

    private LatencyCollectionResource m_resource;
    private Double m_value;
    private String m_name;
    
    /**
     * <p>Constructor for LatencyCollectionAttribute.</p>
     *
     * @param resource a {@link org.opennms.netmgt.poller.LatencyCollectionResource} object.
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.Double} object.
     */
    public LatencyCollectionAttribute(LatencyCollectionResource resource, String name, Double value) {
        super();
        m_resource = resource;
        m_name = name;
        m_value = value;
    }

    /**
     * <p>getAttributeType</p>
     *
     * @return a {@link org.opennms.netmgt.config.collector.CollectionAttributeType} object.
     */
    public CollectionAttributeType getAttributeType() {
        return null;
    }

    /**
     * <p>getName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return m_name;
    }

    /**
     * <p>getNumericValue</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNumericValue() {
        return m_value.toString();
    }

    /**
     * <p>getResource</p>
     *
     * @return a {@link org.opennms.netmgt.config.collector.CollectionResource} object.
     */
    public CollectionResource getResource() {
        return m_resource;
    }

    /**
     * <p>getStringValue</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStringValue() {
        return null;
    }

    /**
     * <p>getType</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getType() {
        return "gauge";
    }

    /** {@inheritDoc} */
    public boolean shouldPersist(ServiceParameters params) {
        return true;
    }

    /** {@inheritDoc} */
    public void storeAttribute(Persister persister) {
    }

    /** {@inheritDoc} */
    public void visit(CollectionSetVisitor visitor) {
    }

}
