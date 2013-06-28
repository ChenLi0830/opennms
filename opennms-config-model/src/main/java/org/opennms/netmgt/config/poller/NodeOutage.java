/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2.1</a>, using an XML
 * Schema.
 * $Id$
 */

package org.opennms.netmgt.config.poller;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.opennms.core.xml.ValidateUsing;

/**
 * Configuration of node-outage
 *  functionality
 * 
 * @version $Revision$ $Date$
 */

@XmlRootElement(name="node-outage")
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("all") public class NodeOutage implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Enable/disable node outage processing
     */
	@XmlAttribute(name="status", required=true)
    private java.lang.String _status;

    /**
     * Defines behavior of node outage processing when a
     *  service has changed status to DOWN and a critical service
     * is not
     *  defined. If "true", all remaining services on the interface
     * are
     *  polled.
     */
	@XmlAttribute(name="pollAllIfNoCriticalServiceDefined")
    private java.lang.String _pollAllIfNoCriticalServiceDefined;

    /**
     * Critical service. Defining a critical service greatly
     *  reduces the traffic generated by the poller when an
     * interface is DOWN.
     *  When an interface is DOWN only the critical service is
     * polled. If and
     *  when the critical service comes back UP then the
     * interface's other
     *  services are polled to determine their status. When an
     * interface is UP
     *  all services are polled as expected. If the critical
     * service goes DOWN,
     *  all services are considered to be DOWN and therefore the
     * interface is
     *  also considered DOWN.
     */
	@XmlElement(name="critical-service")
    private org.opennms.netmgt.config.poller.CriticalService _criticalService;


      //----------------/
     //- Constructors -/
    //----------------/

    public NodeOutage() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Overrides the java.lang.Object.equals method.
     * 
     * @param obj
     * @return true if the objects are equal.
     */
    @Override()
    public boolean equals(
            final java.lang.Object obj) {
        if ( this == obj )
            return true;
        
        if (obj instanceof NodeOutage) {
        
            NodeOutage temp = (NodeOutage)obj;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._pollAllIfNoCriticalServiceDefined != null) {
                if (temp._pollAllIfNoCriticalServiceDefined == null) return false;
                else if (!(this._pollAllIfNoCriticalServiceDefined.equals(temp._pollAllIfNoCriticalServiceDefined))) 
                    return false;
            }
            else if (temp._pollAllIfNoCriticalServiceDefined != null)
                return false;
            if (this._criticalService != null) {
                if (temp._criticalService == null) return false;
                else if (!(this._criticalService.equals(temp._criticalService))) 
                    return false;
            }
            else if (temp._criticalService != null)
                return false;
            return true;
        }
        return false;
    }

    /**
     * Returns the value of field 'criticalService'. The field
     * 'criticalService' has the following description: Critical
     * service. Defining a critical service greatly
     *  reduces the traffic generated by the poller when an
     * interface is DOWN.
     *  When an interface is DOWN only the critical service is
     * polled. If and
     *  when the critical service comes back UP then the
     * interface's other
     *  services are polled to determine their status. When an
     * interface is UP
     *  all services are polled as expected. If the critical
     * service goes DOWN,
     *  all services are considered to be DOWN and therefore the
     * interface is
     *  also considered DOWN.
     * 
     * @return the value of field 'CriticalService'.
     */
    public org.opennms.netmgt.config.poller.CriticalService getCriticalService(
    ) {
        return this._criticalService;
    }

    /**
     * Returns the value of field
     * 'pollAllIfNoCriticalServiceDefined'. The field
     * 'pollAllIfNoCriticalServiceDefined' has the following
     * description: Defines behavior of node outage processing when
     * a
     *  service has changed status to DOWN and a critical service
     * is not
     *  defined. If "true", all remaining services on the interface
     * are
     *  polled.
     * 
     * @return the value of field
     * 'PollAllIfNoCriticalServiceDefined'.
     */
    public java.lang.String getPollAllIfNoCriticalServiceDefined(
    ) {
        return this._pollAllIfNoCriticalServiceDefined == null ? "true" : _pollAllIfNoCriticalServiceDefined;
    }

    /**
     * Returns the value of field 'status'. The field 'status' has
     * the following description: Enable/disable node outage
     * processing
     * 
     * @return the value of field 'Status'.
     */
    public java.lang.String getStatus(
    ) {
        return this._status;
    }

    /**
     * Overrides the java.lang.Object.hashCode method.
     * <p>
     * The following steps came from <b>Effective Java Programming
     * Language Guide</b> by Joshua Bloch, Chapter 3
     * 
     * @return a hash code value for the object.
     */
    public int hashCode(
    ) {
        int result = 17;
        
        long tmp;
        if (_status != null) {
           result = 37 * result + _status.hashCode();
        }
        if (_pollAllIfNoCriticalServiceDefined != null) {
           result = 37 * result + _pollAllIfNoCriticalServiceDefined.hashCode();
        }
        if (_criticalService != null) {
           result = 37 * result + _criticalService.hashCode();
        }
        
        return result;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'criticalService'. The field
     * 'criticalService' has the following description: Critical
     * service. Defining a critical service greatly
     *  reduces the traffic generated by the poller when an
     * interface is DOWN.
     *  When an interface is DOWN only the critical service is
     * polled. If and
     *  when the critical service comes back UP then the
     * interface's other
     *  services are polled to determine their status. When an
     * interface is UP
     *  all services are polled as expected. If the critical
     * service goes DOWN,
     *  all services are considered to be DOWN and therefore the
     * interface is
     *  also considered DOWN.
     * 
     * @param criticalService the value of field 'criticalService'.
     */
    public void setCriticalService(
            final org.opennms.netmgt.config.poller.CriticalService criticalService) {
        this._criticalService = criticalService;
    }

    /**
     * Sets the value of field 'pollAllIfNoCriticalServiceDefined'.
     * The field 'pollAllIfNoCriticalServiceDefined' has the
     * following description: Defines behavior of node outage
     * processing when a
     *  service has changed status to DOWN and a critical service
     * is not
     *  defined. If "true", all remaining services on the interface
     * are
     *  polled.
     * 
     * @param pollAllIfNoCriticalServiceDefined the value of field
     * 'pollAllIfNoCriticalServiceDefined'.
     */
    public void setPollAllIfNoCriticalServiceDefined(
            final java.lang.String pollAllIfNoCriticalServiceDefined) {
        this._pollAllIfNoCriticalServiceDefined = pollAllIfNoCriticalServiceDefined;
    }

    /**
     * Sets the value of field 'status'. The field 'status' has the
     * following description: Enable/disable node outage processing
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(
            final java.lang.String status) {
        this._status = status;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * org.opennms.netmgt.config.poller.NodeOutage
     */
    public static org.opennms.netmgt.config.poller.NodeOutage unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.opennms.netmgt.config.poller.NodeOutage) Unmarshaller.unmarshal(org.opennms.netmgt.config.poller.NodeOutage.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
