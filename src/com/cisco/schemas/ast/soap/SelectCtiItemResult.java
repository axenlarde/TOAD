
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectCtiItemResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectCtiItemResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalItemsFound" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="CtiNodes" type="{http://schemas.cisco.com/ast/soap}ArrayOfCtiNode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectCtiItemResult", propOrder = {
    "totalItemsFound",
    "ctiNodes"
})
public class SelectCtiItemResult {

    @XmlElement(name = "TotalItemsFound", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long totalItemsFound;
    @XmlElement(name = "CtiNodes", required = true, nillable = true)
    protected ArrayOfCtiNode ctiNodes;

    /**
     * Gets the value of the totalItemsFound property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalItemsFound() {
        return totalItemsFound;
    }

    /**
     * Sets the value of the totalItemsFound property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalItemsFound(Long value) {
        this.totalItemsFound = value;
    }

    /**
     * Gets the value of the ctiNodes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCtiNode }
     *     
     */
    public ArrayOfCtiNode getCtiNodes() {
        return ctiNodes;
    }

    /**
     * Sets the value of the ctiNodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCtiNode }
     *     
     */
    public void setCtiNodes(ArrayOfCtiNode value) {
        this.ctiNodes = value;
    }

}
