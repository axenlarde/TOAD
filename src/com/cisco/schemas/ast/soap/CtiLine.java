
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtiLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CtiLine">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DirNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LineStatus" type="{http://schemas.cisco.com/ast/soap}CtiStatus"/>
 *         &lt;element name="LineStatusReason" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="LineTimeStamp" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtiLine", propOrder = {
    "dirNumber",
    "lineStatus",
    "lineStatusReason",
    "lineTimeStamp"
})
public class CtiLine {

    @XmlElement(name = "DirNumber", required = true, nillable = true)
    protected String dirNumber;
    @XmlElement(name = "LineStatus", required = true, nillable = true)
    protected CtiStatus lineStatus;
    @XmlElement(name = "LineStatusReason", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long lineStatusReason;
    @XmlElement(name = "LineTimeStamp", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long lineTimeStamp;

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
     * Gets the value of the lineStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CtiStatus }
     *     
     */
    public CtiStatus getLineStatus() {
        return lineStatus;
    }

    /**
     * Sets the value of the lineStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtiStatus }
     *     
     */
    public void setLineStatus(CtiStatus value) {
        this.lineStatus = value;
    }

    /**
     * Gets the value of the lineStatusReason property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLineStatusReason() {
        return lineStatusReason;
    }

    /**
     * Sets the value of the lineStatusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLineStatusReason(Long value) {
        this.lineStatusReason = value;
    }

    /**
     * Gets the value of the lineTimeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLineTimeStamp() {
        return lineTimeStamp;
    }

    /**
     * Sets the value of the lineTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLineTimeStamp(Long value) {
        this.lineTimeStamp = value;
    }

}
