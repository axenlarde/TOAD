
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CmNode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CmNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReturnCode" type="{http://schemas.cisco.com/ast/soap}RisReturnCode"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NoChange" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CmDevices" type="{http://schemas.cisco.com/ast/soap}ArrayOfCmDevice"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CmNode", propOrder = {
    "returnCode",
    "name",
    "noChange",
    "cmDevices"
})
public class CmNode {

    @XmlElement(name = "ReturnCode", required = true, nillable = true)
    protected RisReturnCode returnCode;
    @XmlElement(name = "Name", required = true, nillable = true)
    protected String name;
    @XmlElement(name = "NoChange")
    protected boolean noChange;
    @XmlElement(name = "CmDevices", required = true, nillable = true)
    protected ArrayOfCmDevice cmDevices;

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link RisReturnCode }
     *     
     */
    public RisReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RisReturnCode }
     *     
     */
    public void setReturnCode(RisReturnCode value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the noChange property.
     * 
     */
    public boolean isNoChange() {
        return noChange;
    }

    /**
     * Sets the value of the noChange property.
     * 
     */
    public void setNoChange(boolean value) {
        this.noChange = value;
    }

    /**
     * Gets the value of the cmDevices property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCmDevice }
     *     
     */
    public ArrayOfCmDevice getCmDevices() {
        return cmDevices;
    }

    /**
     * Sets the value of the cmDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCmDevice }
     *     
     */
    public void setCmDevices(ArrayOfCmDevice value) {
        this.cmDevices = value;
    }

}
