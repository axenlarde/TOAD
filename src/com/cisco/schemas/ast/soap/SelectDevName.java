
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectDevName complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectDevName">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DevName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectDevName", propOrder = {
    "devName"
})
public class SelectDevName {

    @XmlElement(name = "DevName", required = true, nillable = true)
    protected String devName;

    /**
     * Gets the value of the devName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevName() {
        return devName;
    }

    /**
     * Sets the value of the devName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevName(String value) {
        this.devName = value;
    }

}
