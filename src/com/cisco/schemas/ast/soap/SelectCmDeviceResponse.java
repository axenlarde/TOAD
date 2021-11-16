
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="selectCmDeviceReturn" type="{http://schemas.cisco.com/ast/soap}selectCmDeviceReturn"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "selectCmDeviceReturn"
})
@XmlRootElement(name = "selectCmDeviceResponse")
public class SelectCmDeviceResponse {

    @XmlElement(required = true)
    protected SelectCmDeviceReturn selectCmDeviceReturn;

    /**
     * Gets the value of the selectCmDeviceReturn property.
     * 
     * @return
     *     possible object is
     *     {@link SelectCmDeviceReturn }
     *     
     */
    public SelectCmDeviceReturn getSelectCmDeviceReturn() {
        return selectCmDeviceReturn;
    }

    /**
     * Sets the value of the selectCmDeviceReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectCmDeviceReturn }
     *     
     */
    public void setSelectCmDeviceReturn(SelectCmDeviceReturn value) {
        this.selectCmDeviceReturn = value;
    }

}
