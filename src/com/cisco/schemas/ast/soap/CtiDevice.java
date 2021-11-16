
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtiDevice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtiDevice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AppControlsMedia" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeviceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceStatus" type="{http://schemas.cisco.com/ast/soap}CtiStatus"/>
 *         &lt;element name="DeviceStatusReason" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="DeviceTimeStamp" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtiDevice", propOrder = {
    "appControlsMedia",
    "deviceName",
    "deviceStatus",
    "deviceStatusReason",
    "deviceTimeStamp"
})
public class CtiDevice {

    @XmlElement(name = "AppControlsMedia", required = true, type = Boolean.class, nillable = true)
    protected Boolean appControlsMedia;
    @XmlElement(name = "DeviceName", required = true, nillable = true)
    protected String deviceName;
    @XmlElement(name = "DeviceStatus", required = true, nillable = true)
    protected CtiStatus deviceStatus;
    @XmlElement(name = "DeviceStatusReason", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long deviceStatusReason;
    @XmlElement(name = "DeviceTimeStamp", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long deviceTimeStamp;

    /**
     * Gets the value of the appControlsMedia property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAppControlsMedia() {
        return appControlsMedia;
    }

    /**
     * Sets the value of the appControlsMedia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAppControlsMedia(Boolean value) {
        this.appControlsMedia = value;
    }

    /**
     * Gets the value of the deviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Sets the value of the deviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceName(String value) {
        this.deviceName = value;
    }

    /**
     * Gets the value of the deviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CtiStatus }
     *     
     */
    public CtiStatus getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Sets the value of the deviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiStatus }
     *     
     */
    public void setDeviceStatus(CtiStatus value) {
        this.deviceStatus = value;
    }

    /**
     * Gets the value of the deviceStatusReason property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDeviceStatusReason() {
        return deviceStatusReason;
    }

    /**
     * Sets the value of the deviceStatusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDeviceStatusReason(Long value) {
        this.deviceStatusReason = value;
    }

    /**
     * Gets the value of the deviceTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDeviceTimeStamp() {
        return deviceTimeStamp;
    }

    /**
     * Sets the value of the deviceTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDeviceTimeStamp(Long value) {
        this.deviceTimeStamp = value;
    }

}
