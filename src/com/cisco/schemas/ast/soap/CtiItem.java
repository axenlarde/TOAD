
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtiItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtiItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AppId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AppIpAddr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AppIpv6Addr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AppStatus" type="{http://schemas.cisco.com/ast/soap}CtiStatus"/>
 *         &lt;element name="AppStatusReason" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="AppTimeStamp" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="CtiDevice" type="{http://schemas.cisco.com/ast/soap}CtiDevice"/>
 *         &lt;element name="CtiLine" type="{http://schemas.cisco.com/ast/soap}CtiLine"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtiItem", propOrder = {
    "appId",
    "userId",
    "appIpAddr",
    "appIpv6Addr",
    "appStatus",
    "appStatusReason",
    "appTimeStamp",
    "ctiDevice",
    "ctiLine"
})
public class CtiItem {

    @XmlElement(name = "AppId", required = true, nillable = true)
    protected String appId;
    @XmlElement(name = "UserId", required = true, nillable = true)
    protected String userId;
    @XmlElement(name = "AppIpAddr", required = true, nillable = true)
    protected String appIpAddr;
    @XmlElement(name = "AppIpv6Addr", required = true, nillable = true)
    protected String appIpv6Addr;
    @XmlElement(name = "AppStatus", required = true, nillable = true)
    protected CtiStatus appStatus;
    @XmlElement(name = "AppStatusReason", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long appStatusReason;
    @XmlElement(name = "AppTimeStamp", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long appTimeStamp;
    @XmlElement(name = "CtiDevice", required = true, nillable = true)
    protected CtiDevice ctiDevice;
    @XmlElement(name = "CtiLine", required = true, nillable = true)
    protected CtiLine ctiLine;

    /**
     * Gets the value of the appId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Sets the value of the appId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppId(String value) {
        this.appId = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the appIpAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppIpAddr() {
        return appIpAddr;
    }

    /**
     * Sets the value of the appIpAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppIpAddr(String value) {
        this.appIpAddr = value;
    }

    /**
     * Gets the value of the appIpv6Addr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppIpv6Addr() {
        return appIpv6Addr;
    }

    /**
     * Sets the value of the appIpv6Addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppIpv6Addr(String value) {
        this.appIpv6Addr = value;
    }

    /**
     * Gets the value of the appStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CtiStatus }
     *     
     */
    public CtiStatus getAppStatus() {
        return appStatus;
    }

    /**
     * Sets the value of the appStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiStatus }
     *     
     */
    public void setAppStatus(CtiStatus value) {
        this.appStatus = value;
    }

    /**
     * Gets the value of the appStatusReason property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAppStatusReason() {
        return appStatusReason;
    }

    /**
     * Sets the value of the appStatusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAppStatusReason(Long value) {
        this.appStatusReason = value;
    }

    /**
     * Gets the value of the appTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAppTimeStamp() {
        return appTimeStamp;
    }

    /**
     * Sets the value of the appTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAppTimeStamp(Long value) {
        this.appTimeStamp = value;
    }

    /**
     * Gets the value of the ctiDevice property.
     * 
     * @return
     *     possible object is
     *     {@link CtiDevice }
     *     
     */
    public CtiDevice getCtiDevice() {
        return ctiDevice;
    }

    /**
     * Sets the value of the ctiDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiDevice }
     *     
     */
    public void setCtiDevice(CtiDevice value) {
        this.ctiDevice = value;
    }

    /**
     * Gets the value of the ctiLine property.
     * 
     * @return
     *     possible object is
     *     {@link CtiLine }
     *     
     */
    public CtiLine getCtiLine() {
        return ctiLine;
    }

    /**
     * Sets the value of the ctiLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiLine }
     *     
     */
    public void setCtiLine(CtiLine value) {
        this.ctiLine = value;
    }

}
