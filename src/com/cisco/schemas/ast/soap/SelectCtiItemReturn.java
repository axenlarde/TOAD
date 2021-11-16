
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for selectCtiItemReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="selectCtiItemReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SelectCtiItemResult" type="{http://schemas.cisco.com/ast/soap}SelectCtiItemResult"/>
 *         &lt;element name="StateInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "selectCtiItemReturn", propOrder = {
    "selectCtiItemResult",
    "stateInfo"
})
public class SelectCtiItemReturn {

    @XmlElement(name = "SelectCtiItemResult", required = true)
    protected SelectCtiItemResult selectCtiItemResult;
    @XmlElement(name = "StateInfo", required = true)
    protected String stateInfo;

    /**
     * Gets the value of the selectCtiItemResult property.
     * 
     * @return
     *     possible object is
     *     {@link SelectCtiItemResult }
     *     
     */
    public SelectCtiItemResult getSelectCtiItemResult() {
        return selectCtiItemResult;
    }

    /**
     * Sets the value of the selectCtiItemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectCtiItemResult }
     *     
     */
    public void setSelectCtiItemResult(SelectCtiItemResult value) {
        this.selectCtiItemResult = value;
    }

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

}
