
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtocolType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProtocolType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Any"/>
 *     &lt;enumeration value="SCCP"/>
 *     &lt;enumeration value="SIP"/>
 *     &lt;enumeration value="Unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProtocolType")
@XmlEnum
public enum ProtocolType {

    @XmlEnumValue("Any")
    ANY("Any"),
    SCCP("SCCP"),
    SIP("SIP"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown");
    private final String value;

    ProtocolType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProtocolType fromValue(String v) {
        for (ProtocolType c: ProtocolType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
