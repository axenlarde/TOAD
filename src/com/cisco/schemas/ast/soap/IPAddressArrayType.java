
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPAddressArrayType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPAddressArrayType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IPAddrType" type="{http://schemas.cisco.com/ast/soap}IPAddrType"/>
 *         &lt;element name="Attribute" type="{http://schemas.cisco.com/ast/soap}AttributeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPAddressArrayType", propOrder = {
    "ip",
    "ipAddrType",
    "attribute"
})
public class IPAddressArrayType {

    @XmlElement(name = "IP", required = true)
    protected String ip;
    @XmlElement(name = "IPAddrType", required = true)
    protected IPAddrType ipAddrType;
    @XmlElement(name = "Attribute", required = true, nillable = true)
    protected AttributeType attribute;

    /**
     * Gets the value of the ip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIP() {
        return ip;
    }

    /**
     * Sets the value of the ip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIP(String value) {
        this.ip = value;
    }

    /**
     * Gets the value of the ipAddrType property.
     * 
     * @return
     *     possible object is
     *     {@link IPAddrType }
     *     
     */
    public IPAddrType getIPAddrType() {
        return ipAddrType;
    }

    /**
     * Sets the value of the ipAddrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPAddrType }
     *     
     */
    public void setIPAddrType(IPAddrType value) {
        this.ipAddrType = value;
    }

    /**
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link AttributeType }
     *     
     */
    public AttributeType getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeType }
     *     
     */
    public void setAttribute(AttributeType value) {
        this.attribute = value;
    }

}
