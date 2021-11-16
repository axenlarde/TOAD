
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CmSingleLineStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CmSingleLineStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Any"/>
 *     &lt;enumeration value="Registered"/>
 *     &lt;enumeration value="UnRegistered"/>
 *     &lt;enumeration value="Rejected"/>
 *     &lt;enumeration value="Unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CmSingleLineStatus")
@XmlEnum
public enum CmSingleLineStatus {

    @XmlEnumValue("Any")
    ANY("Any"),
    @XmlEnumValue("Registered")
    REGISTERED("Registered"),
    @XmlEnumValue("UnRegistered")
    UN_REGISTERED("UnRegistered"),
    @XmlEnumValue("Rejected")
    REJECTED("Rejected"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown");
    private final String value;

    CmSingleLineStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CmSingleLineStatus fromValue(String v) {
        for (CmSingleLineStatus c: CmSingleLineStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
