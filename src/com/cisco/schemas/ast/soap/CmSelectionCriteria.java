
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CmSelectionCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CmSelectionCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaxReturnedDevices" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="DeviceClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Model" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SelectBy" type="{http://schemas.cisco.com/ast/soap}CmSelectBy"/>
 *         &lt;element name="SelectItems" type="{http://schemas.cisco.com/ast/soap}ArrayOfSelectItem"/>
 *         &lt;element name="Protocol" type="{http://schemas.cisco.com/ast/soap}ProtocolType"/>
 *         &lt;element name="DownloadStatus" type="{http://schemas.cisco.com/ast/soap}DeviceDownloadStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CmSelectionCriteria", propOrder = {
    "maxReturnedDevices",
    "deviceClass",
    "model",
    "status",
    "nodeName",
    "selectBy",
    "selectItems",
    "protocol",
    "downloadStatus"
})
public class CmSelectionCriteria {

    @XmlElement(name = "MaxReturnedDevices", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long maxReturnedDevices;
    @XmlElement(name = "DeviceClass", required = true, nillable = true)
    protected String deviceClass;
    @XmlElement(name = "Model", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long model;
    @XmlElement(name = "Status", required = true, nillable = true)
    protected String status;
    @XmlElement(name = "NodeName", required = true, nillable = true)
    protected String nodeName;
    @XmlElement(name = "SelectBy", required = true, nillable = true)
    protected CmSelectBy selectBy;
    @XmlElement(name = "SelectItems", required = true, nillable = true)
    protected ArrayOfSelectItem selectItems;
    @XmlElement(name = "Protocol", required = true, nillable = true)
    protected ProtocolType protocol;
    @XmlElement(name = "DownloadStatus", required = true, nillable = true)
    protected DeviceDownloadStatus downloadStatus;

    /**
     * Gets the value of the maxReturnedDevices property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxReturnedDevices() {
        return maxReturnedDevices;
    }

    /**
     * Sets the value of the maxReturnedDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxReturnedDevices(Long value) {
        this.maxReturnedDevices = value;
    }

    /**
     * Gets the value of the deviceClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceClass() {
        return deviceClass;
    }

    /**
     * Sets the value of the deviceClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceClass(String value) {
        this.deviceClass = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setModel(Long value) {
        this.model = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
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
     * Gets the value of the selectBy property.
     * 
     * @return
     *     possible object is
     *     {@link CmSelectBy }
     *     
     */
    public CmSelectBy getSelectBy() {
        return selectBy;
    }

    /**
     * Sets the value of the selectBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmSelectBy }
     *     
     */
    public void setSelectBy(CmSelectBy value) {
        this.selectBy = value;
    }

    /**
     * Gets the value of the selectItems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSelectItem }
     *     
     */
    public ArrayOfSelectItem getSelectItems() {
        return selectItems;
    }

    /**
     * Sets the value of the selectItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSelectItem }
     *     
     */
    public void setSelectItems(ArrayOfSelectItem value) {
        this.selectItems = value;
    }

    /**
     * Gets the value of the protocol property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolType }
     *     
     */
    public ProtocolType getProtocol() {
        return protocol;
    }

    /**
     * Sets the value of the protocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolType }
     *     
     */
    public void setProtocol(ProtocolType value) {
        this.protocol = value;
    }

    /**
     * Gets the value of the downloadStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceDownloadStatus }
     *     
     */
    public DeviceDownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    /**
     * Sets the value of the downloadStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceDownloadStatus }
     *     
     */
    public void setDownloadStatus(DeviceDownloadStatus value) {
        this.downloadStatus = value;
    }

}
