
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectCmDeviceResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectCmDeviceResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalDevicesFound" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="CmNodes" type="{http://schemas.cisco.com/ast/soap}ArrayOfCmNode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectCmDeviceResult", propOrder = {
    "totalDevicesFound",
    "cmNodes"
})
public class SelectCmDeviceResult {

    @XmlElement(name = "TotalDevicesFound", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long totalDevicesFound;
    @XmlElement(name = "CmNodes", required = true, nillable = true)
    protected ArrayOfCmNode cmNodes;

    /**
     * Gets the value of the totalDevicesFound property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalDevicesFound() {
        return totalDevicesFound;
    }

    /**
     * Sets the value of the totalDevicesFound property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalDevicesFound(Long value) {
        this.totalDevicesFound = value;
    }

    /**
     * Gets the value of the cmNodes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCmNode }
     *     
     */
    public ArrayOfCmNode getCmNodes() {
        return cmNodes;
    }

    /**
     * Sets the value of the cmNodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCmNode }
     *     
     */
    public void setCmNodes(ArrayOfCmNode value) {
        this.cmNodes = value;
    }

}
