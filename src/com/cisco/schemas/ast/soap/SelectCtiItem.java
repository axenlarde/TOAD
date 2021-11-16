
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
 *         &lt;element name="StateInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CtiSelectionCriteria" type="{http://schemas.cisco.com/ast/soap}CtiSelectionCriteria"/>
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
    "stateInfo",
    "ctiSelectionCriteria"
})
@XmlRootElement(name = "selectCtiItem")
public class SelectCtiItem {

    @XmlElement(name = "StateInfo", required = true)
    protected String stateInfo;
    @XmlElement(name = "CtiSelectionCriteria", required = true)
    protected CtiSelectionCriteria ctiSelectionCriteria;

    /**
     * Gets the value of the stateInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * Sets the value of the stateInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateInfo(String value) {
        this.stateInfo = value;
    }

    /**
     * Gets the value of the ctiSelectionCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link CtiSelectionCriteria }
     *     
     */
    public CtiSelectionCriteria getCtiSelectionCriteria() {
        return ctiSelectionCriteria;
    }

    /**
     * Sets the value of the ctiSelectionCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiSelectionCriteria }
     *     
     */
    public void setCtiSelectionCriteria(CtiSelectionCriteria value) {
        this.ctiSelectionCriteria = value;
    }

}
