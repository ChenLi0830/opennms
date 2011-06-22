/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *     along with OpenNMS(R).  If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information contact: 
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.netmgt.dao.support;

import java.util.ArrayList;
import java.util.List;

import org.opennms.core.utils.ReplaceAllOperation;
import org.opennms.core.utils.ReplaceFirstOperation;
import org.opennms.core.utils.StringReplaceOperation;
import org.opennms.netmgt.config.collector.CollectionResource;
import org.opennms.netmgt.config.datacollection.Parameter;

/**
 * <p>SiblingColumnStorageStrategy class.</p>
 *
 * @author <a href="mailto:jeffg@opennms.org">Jeff Gehlbach</a>
 * @author <a href="mailto:agalue@opennms.org">Alejandro Galue</a>
 */
public class SiblingColumnStorageStrategy extends IndexStorageStrategy {
    private static final String PARAM_SIBLING_COLUMN_NAME = "sibling-column-name";
    private String m_siblingColumnName;

    private static final String PARAM_REPLACE_FIRST = "replace-first";
    private static final String PARAM_REPLACE_ALL = "replace-all";
    private List<StringReplaceOperation> m_replaceOps;

    /**
     * <p>Constructor for SiblingColumnStorageStrategy.</p>
     */
    public SiblingColumnStorageStrategy() {
        super();
        m_replaceOps = new ArrayList<StringReplaceOperation>();
    }
    
    /** {@inheritDoc} */
    @Override
    public String getResourceNameFromIndex(CollectionResource resource) {
        StringAttributeVisitor visitor = new StringAttributeVisitor(m_siblingColumnName);
        resource.visit(visitor);
        String value = (visitor.getValue() != null ? visitor.getValue() : resource.getInstance());
        
        // First remove all non-US-ASCII characters and turn all forward slashes into dashes 
        String name = value.replaceAll("[^\\x00-\\x7F]", "").replaceAll("/", "-");
        
        // Then perform all replacement operations specified in the parameters
        for (StringReplaceOperation op : m_replaceOps) {
            log().debug("Doing string replacement on instance name '" + name + "' using " + op);
            name = op.replace(name);
        }

        log().debug("Inbound instance name was '" + resource.getInstance() + "', outbound was '" + ("".equals(name) ? resource.getInstance() : name) + "'");
        return ("".equals(name) ? resource.getInstance() : name);
    }
    
    /** {@inheritDoc} */
    @Override
    public void setParameters(List<Parameter> parameterCollection) {
        if (parameterCollection == null) {
            log().fatal("Got a null parameter list, but need one containing a '" + PARAM_SIBLING_COLUMN_NAME + "' parameter.");
            throw new RuntimeException("Got a null parameter list, but need one containing a '" + PARAM_SIBLING_COLUMN_NAME + "' parameter.");
        }
        
        for (Parameter param : parameterCollection) {
            if (PARAM_SIBLING_COLUMN_NAME.equals(param.getKey())) {
                m_siblingColumnName = param.getValue();
            } else if (PARAM_REPLACE_FIRST.equals(param.getKey())) {
                m_replaceOps.add(new ReplaceFirstOperation(param.getValue()));
            } else if (PARAM_REPLACE_ALL.equals(param.getKey())) {
                m_replaceOps.add(new ReplaceAllOperation(param.getValue()));
            } else {
                log().warn("Encountered unsupported parameter key=\"" + param.getKey() + "\". Can accept: " + PARAM_SIBLING_COLUMN_NAME + ", " + PARAM_REPLACE_FIRST + ", " + PARAM_REPLACE_ALL);
            }
        }
        
        if (m_siblingColumnName == null) {
            log().error("The provided parameter list must contain a '" + PARAM_SIBLING_COLUMN_NAME + "' parameter.");
            throw new RuntimeException("The provided parameter list must contain a '" + PARAM_SIBLING_COLUMN_NAME + "' parameter.");
        }
    }
}
