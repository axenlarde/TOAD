
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectAppItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectAppItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AppItem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectAppItem", propOrder = {
    "appItem"
})
public class SelectAppItem {

    @XmlElement(name = "AppItem", required = true, nillable = true)
    protected String appItem;

    /**
     * Gets the value of the appItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppItem() {
        return appItem;
    }

    /**
     * Sets the value of the appItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppItem(String value) {
        this.appItem = value;
    }

}
