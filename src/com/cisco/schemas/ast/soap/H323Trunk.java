
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for H323Trunk complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="H323Trunk">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfigName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TechPrefix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Zone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RemoteCmServer1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RemoteCmServer2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RemoteCmServer3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AltGkList" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActiveGk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CallSignalAddr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RasAddr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "H323Trunk", propOrder = {
    "configName",
    "techPrefix",
    "zone",
    "remoteCmServer1",
    "remoteCmServer2",
    "remoteCmServer3",
    "altGkList",
    "activeGk",
    "callSignalAddr",
    "rasAddr"
})
public class H323Trunk {

    @XmlElement(name = "ConfigName", required = true, nillable = true)
    protected String configName;
    @XmlElement(name = "TechPrefix", required = true, nillable = true)
    protected String techPrefix;
    @XmlElement(name = "Zone", required = true, nillable = true)
    protected String zone;
    @XmlElement(name = "RemoteCmServer1", required = true, nillable = true)
    protected String remoteCmServer1;
    @XmlElement(name = "RemoteCmServer2", required = true, nillable = true)
    protected String remoteCmServer2;
    @XmlElement(name = "RemoteCmServer3", required = true, nillable = true)
    protected String remoteCmServer3;
    @XmlElement(name = "AltGkList", required = true, nillable = true)
    protected String altGkList;
    @XmlElement(name = "ActiveGk", required = true, nillable = true)
    protected String activeGk;
    @XmlElement(name = "CallSignalAddr", required = true, nillable = true)
    protected String callSignalAddr;
    @XmlElement(name = "RasAddr", required = true, nillable = true)
    protected String rasAddr;

    /**
     * Gets the value of the configName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * Sets the value of the configName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigName(String value) {
        this.configName = value;
    }

    /**
     * Gets the value of the techPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechPrefix() {
        return techPrefix;
    }

    /**
     * Sets the value of the techPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechPrefix(String value) {
        this.techPrefix = value;
    }

    /**
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZone(String value) {
        this.zone = value;
    }

    /**
     * Gets the value of the remoteCmServer1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteCmServer1() {
        return remoteCmServer1;
    }

    /**
     * Sets the value of the remoteCmServer1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteCmServer1(String value) {
        this.remoteCmServer1 = value;
    }

    /**
     * Gets the value of the remoteCmServer2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteCmServer2() {
        return remoteCmServer2;
    }

    /**
     * Sets the value of the remoteCmServer2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteCmServer2(String value) {
        this.remoteCmServer2 = value;
    }

    /**
     * Gets the value of the remoteCmServer3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteCmServer3() {
        return remoteCmServer3;
    }

    /**
     * Sets the value of the remoteCmServer3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteCmServer3(String value) {
        this.remoteCmServer3 = value;
    }

    /**
     * Gets the value of the altGkList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltGkList() {
        return altGkList;
    }

    /**
     * Sets the value of the altGkList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltGkList(String value) {
        this.altGkList = value;
    }

    /**
     * Gets the value of the activeGk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveGk() {
        return activeGk;
    }

    /**
     * Sets the value of the activeGk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveGk(String value) {
        this.activeGk = value;
    }

    /**
     * Gets the value of the callSignalAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallSignalAddr() {
        return callSignalAddr;
    }

    /**
     * Sets the value of the callSignalAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallSignalAddr(String value) {
        this.callSignalAddr = value;
    }

    /**
     * Gets the value of the rasAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRasAddr() {
        return rasAddr;
    }

    /**
     * Sets the value of the rasAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRasAddr(String value) {
        this.rasAddr = value;
    }

}
