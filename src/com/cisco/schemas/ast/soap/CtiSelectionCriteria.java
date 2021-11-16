
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtiSelectionCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtiSelectionCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaxReturnedItems" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="CtiMgrClass" type="{http://schemas.cisco.com/ast/soap}CtiMgrClass"/>
 *         &lt;element name="Status" type="{http://schemas.cisco.com/ast/soap}CtiStatus"/>
 *         &lt;element name="NodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SelectAppBy" type="{http://schemas.cisco.com/ast/soap}CtiSelectAppBy"/>
 *         &lt;element name="AppItems" type="{http://schemas.cisco.com/ast/soap}ArrayOfSelectAppItem"/>
 *         &lt;element name="DevNames" type="{http://schemas.cisco.com/ast/soap}ArrayOfSelectDevName"/>
 *         &lt;element name="DirNumbers" type="{http://schemas.cisco.com/ast/soap}ArrayOfSelectDirNumber"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtiSelectionCriteria", propOrder = {
    "maxReturnedItems",
    "ctiMgrClass",
    "status",
    "nodeName",
    "selectAppBy",
    "appItems",
    "devNames",
    "dirNumbers"
})
public class CtiSelectionCriteria {

    @XmlElement(name = "MaxReturnedItems", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long maxReturnedItems;
    @XmlElement(name = "CtiMgrClass", required = true, nillable = true)
    protected CtiMgrClass ctiMgrClass;
    @XmlElement(name = "Status", required = true, nillable = true)
    protected CtiStatus status;
    @XmlElement(name = "NodeName", required = true, nillable = true)
    protected String nodeName;
    @XmlElement(name = "SelectAppBy", required = true, nillable = true)
    protected CtiSelectAppBy selectAppBy;
    @XmlElement(name = "AppItems", required = true, nillable = true)
    protected ArrayOfSelectAppItem appItems;
    @XmlElement(name = "DevNames", required = true, nillable = true)
    protected ArrayOfSelectDevName devNames;
    @XmlElement(name = "DirNumbers", required = true, nillable = true)
    protected ArrayOfSelectDirNumber dirNumbers;

    /**
     * Gets the value of the maxReturnedItems property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxReturnedItems() {
        return maxReturnedItems;
    }

    /**
     * Sets the value of the maxReturnedItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxReturnedItems(Long value) {
        this.maxReturnedItems = value;
    }

    /**
     * Gets the value of the ctiMgrClass property.
     * 
     * @return
     *     possible object is
     *     {@link CtiMgrClass }
     *     
     */
    public CtiMgrClass getCtiMgrClass() {
        return ctiMgrClass;
    }

    /**
     * Sets the value of the ctiMgrClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiMgrClass }
     *     
     */
    public void setCtiMgrClass(CtiMgrClass value) {
        this.ctiMgrClass = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link CtiStatus }
     *     
     */
    public CtiStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiStatus }
     *     
     */
    public void setStatus(CtiStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the nodeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets the value of the nodeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

    /**
     * Gets the value of the selectAppBy property.
     * 
     * @return
     *     possible object is
     *     {@link CtiSelectAppBy }
     *     
     */
    public CtiSelectAppBy getSelectAppBy() {
        return selectAppBy;
    }

    /**
     * Sets the value of the selectAppBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiSelectAppBy }
     *     
     */
    public void setSelectAppBy(CtiSelectAppBy value) {
        this.selectAppBy = value;
    }

    /**
     * Gets the value of the appItems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSelectAppItem }
     *     
     */
    public ArrayOfSelectAppItem getAppItems() {
        return appItems;
    }

    /**
     * Sets the value of the appItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSelectAppItem }
     *     
     */
    public void setAppItems(ArrayOfSelectAppItem value) {
        this.appItems = value;
    }

    /**
     * Gets the value of the devNames property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSelectDevName }
     *     
     */
    public ArrayOfSelectDevName getDevNames() {
        return devNames;
    }

    /**
     * Sets the value of the devNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSelectDevName }
     *     
     */
    public void setDevNames(ArrayOfSelectDevName value) {
        this.devNames = value;
    }

    /**
     * Gets the value of the dirNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSelectDirNumber }
     *     
     */
    public ArrayOfSelectDirNumber getDirNumbers() {
        return dirNumbers;
    }

    /**
     * Sets the value of the dirNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSelectDirNumber }
     *     
     */
    public void setDirNumbers(ArrayOfSelectDirNumber value) {
        this.dirNumbers = value;
    }

}
