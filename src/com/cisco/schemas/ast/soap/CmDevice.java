
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CmDevice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CmDevice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DirNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceClass" type="{http://schemas.cisco.com/ast/soap}DeviceClass"/>
 *         &lt;element name="Model" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Product" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="BoxProduct" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Httpd" type="{http://schemas.cisco.com/ast/soap}CmDevHttpd"/>
 *         &lt;element name="RegistrationAttempts" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="IsCtiControllable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LoginUserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://schemas.cisco.com/ast/soap}CmDevRegStat"/>
 *         &lt;element name="StatusReason" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="PerfMonObject" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="DChannel" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="H323Trunk" type="{http://schemas.cisco.com/ast/soap}H323Trunk"/>
 *         &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Protocol" type="{http://schemas.cisco.com/ast/soap}ProtocolType"/>
 *         &lt;element name="NumOfLines" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="LinesStatus" type="{http://schemas.cisco.com/ast/soap}ArrayOfCmDevSingleLineStatus"/>
 *         &lt;element name="ActiveLoadID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InactiveLoadID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DownloadStatus" type="{http://schemas.cisco.com/ast/soap}DeviceDownloadStatus"/>
 *         &lt;element name="DownloadFailureReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DownloadServer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IPAddress" type="{http://schemas.cisco.com/ast/soap}ArrayOfIPAddressArrayType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CmDevice", propOrder = {
    "name",
    "dirNumber",
    "deviceClass",
    "model",
    "product",
    "boxProduct",
    "httpd",
    "registrationAttempts",
    "isCtiControllable",
    "loginUserId",
    "status",
    "statusReason",
    "perfMonObject",
    "dChannel",
    "description",
    "h323Trunk",
    "timeStamp",
    "protocol",
    "numOfLines",
    "linesStatus",
    "activeLoadID",
    "inactiveLoadID",
    "downloadStatus",
    "downloadFailureReason",
    "downloadServer",
    "ipAddress"
})
public class CmDevice {

    @XmlElement(name = "Name", required = true, nillable = true)
    protected String name;
    @XmlElement(name = "DirNumber", required = true, nillable = true)
    protected String dirNumber;
    @XmlElement(name = "DeviceClass", required = true, nillable = true)
    protected DeviceClass deviceClass;
    @XmlElement(name = "Model", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long model;
    @XmlElement(name = "Product", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long product;
    @XmlElement(name = "BoxProduct", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long boxProduct;
    @XmlElement(name = "Httpd", required = true, nillable = true)
    protected CmDevHttpd httpd;
    @XmlElement(name = "RegistrationAttempts", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long registrationAttempts;
    @XmlElement(name = "IsCtiControllable")
    protected boolean isCtiControllable;
    @XmlElement(name = "LoginUserId", required = true, nillable = true)
    protected String loginUserId;
    @XmlElement(name = "Status", required = true, nillable = true)
    protected CmDevRegStat status;
    @XmlElement(name = "StatusReason", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long statusReason;
    @XmlElement(name = "PerfMonObject", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long perfMonObject;
    @XmlElement(name = "DChannel", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long dChannel;
    @XmlElement(name = "Description", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "H323Trunk", required = true, nillable = true)
    protected H323Trunk h323Trunk;
    @XmlElement(name = "TimeStamp", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long timeStamp;
    @XmlElement(name = "Protocol", required = true, nillable = true)
    protected ProtocolType protocol;
    @XmlElement(name = "NumOfLines", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long numOfLines;
    @XmlElement(name = "LinesStatus", required = true, nillable = true)
    protected ArrayOfCmDevSingleLineStatus linesStatus;
    @XmlElement(name = "ActiveLoadID", required = true, nillable = true)
    protected String activeLoadID;
    @XmlElement(name = "InactiveLoadID", required = true, nillable = true)
    protected String inactiveLoadID;
    @XmlElement(name = "DownloadStatus", required = true, nillable = true)
    protected DeviceDownloadStatus downloadStatus;
    @XmlElement(name = "DownloadFailureReason", required = true, nillable = true)
    protected String downloadFailureReason;
    @XmlElement(name = "DownloadServer", required = true, nillable = true)
    protected String downloadServer;
    @XmlElement(name = "IPAddress", required = true)
    protected ArrayOfIPAddressArrayType ipAddress;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the dirNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirNumber() {
        return dirNumber;
    }

    /**
     * Sets the value of the dirNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirNumber(String value) {
        this.dirNumber = value;
    }

    /**
     * Gets the value of the deviceClass property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceClass }
     *     
     */
    public DeviceClass getDeviceClass() {
        return deviceClass;
    }

    /**
     * Sets the value of the deviceClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceClass }
     *     
     */
    public void setDeviceClass(DeviceClass value) {
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
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProduct(Long value) {
        this.product = value;
    }

    /**
     * Gets the value of the boxProduct property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBoxProduct() {
        return boxProduct;
    }

    /**
     * Sets the value of the boxProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBoxProduct(Long value) {
        this.boxProduct = value;
    }

    /**
     * Gets the value of the httpd property.
     * 
     * @return
     *     possible object is
     *     {@link CmDevHttpd }
     *     
     */
    public CmDevHttpd getHttpd() {
        return httpd;
    }

    /**
     * Sets the value of the httpd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmDevHttpd }
     *     
     */
    public void setHttpd(CmDevHttpd value) {
        this.httpd = value;
    }

    /**
     * Gets the value of the registrationAttempts property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRegistrationAttempts() {
        return registrationAttempts;
    }

    /**
     * Sets the value of the registrationAttempts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRegistrationAttempts(Long value) {
        this.registrationAttempts = value;
    }

    /**
     * Gets the value of the isCtiControllable property.
     * 
     */
    public boolean isIsCtiControllable() {
        return isCtiControllable;
    }

    /**
     * Sets the value of the isCtiControllable property.
     * 
     */
    public void setIsCtiControllable(boolean value) {
        this.isCtiControllable = value;
    }

    /**
     * Gets the value of the loginUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginUserId() {
        return loginUserId;
    }

    /**
     * Sets the value of the loginUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginUserId(String value) {
        this.loginUserId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link CmDevRegStat }
     *     
     */
    public CmDevRegStat getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmDevRegStat }
     *     
     */
    public void setStatus(CmDevRegStat value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusReason property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStatusReason() {
        return statusReason;
    }

    /**
     * Sets the value of the statusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStatusReason(Long value) {
        this.statusReason = value;
    }

    /**
     * Gets the value of the perfMonObject property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPerfMonObject() {
        return perfMonObject;
    }

    /**
     * Sets the value of the perfMonObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPerfMonObject(Long value) {
        this.perfMonObject = value;
    }

    /**
     * Gets the value of the dChannel property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDChannel() {
        return dChannel;
    }

    /**
     * Sets the value of the dChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDChannel(Long value) {
        this.dChannel = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the h323Trunk property.
     * 
     * @return
     *     possible object is
     *     {@link H323Trunk }
     *     
     */
    public H323Trunk getH323Trunk() {
        return h323Trunk;
    }

    /**
     * Sets the value of the h323Trunk property.
     * 
     * @param value
     *     allowed object is
     *     {@link H323Trunk }
     *     
     */
    public void setH323Trunk(H323Trunk value) {
        this.h323Trunk = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTimeStamp(Long value) {
        this.timeStamp = value;
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
     * Gets the value of the numOfLines property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumOfLines() {
        return numOfLines;
    }

    /**
     * Sets the value of the numOfLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumOfLines(Long value) {
        this.numOfLines = value;
    }

    /**
     * Gets the value of the linesStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCmDevSingleLineStatus }
     *     
     */
    public ArrayOfCmDevSingleLineStatus getLinesStatus() {
        return linesStatus;
    }

    /**
     * Sets the value of the linesStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCmDevSingleLineStatus }
     *     
     */
    public void setLinesStatus(ArrayOfCmDevSingleLineStatus value) {
        this.linesStatus = value;
    }

    /**
     * Gets the value of the activeLoadID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveLoadID() {
        return activeLoadID;
    }

    /**
     * Sets the value of the activeLoadID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveLoadID(String value) {
        this.activeLoadID = value;
    }

    /**
     * Gets the value of the inactiveLoadID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInactiveLoadID() {
        return inactiveLoadID;
    }

    /**
     * Sets the value of the inactiveLoadID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInactiveLoadID(String value) {
        this.inactiveLoadID = value;
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

    /**
     * Gets the value of the downloadFailureReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadFailureReason() {
        return downloadFailureReason;
    }

    /**
     * Sets the value of the downloadFailureReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadFailureReason(String value) {
        this.downloadFailureReason = value;
    }

    /**
     * Gets the value of the downloadServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadServer() {
        return downloadServer;
    }

    /**
     * Sets the value of the downloadServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadServer(String value) {
        this.downloadServer = value;
    }

    /**
     * Gets the value of the ipAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfIPAddressArrayType }
     *     
     */
    public ArrayOfIPAddressArrayType getIPAddress() {
        return ipAddress;
    }

    /**
     * Sets the value of the ipAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfIPAddressArrayType }
     *     
     */
    public void setIPAddress(ArrayOfIPAddressArrayType value) {
        this.ipAddress = value;
    }

}
