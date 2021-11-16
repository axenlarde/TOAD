
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtiMgrClass.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CtiMgrClass">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Provider"/>
 *     &lt;enumeration value="Device"/>
 *     &lt;enumeration value="Line"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CtiMgrClass")
@XmlEnum
public enum CtiMgrClass {

    @XmlEnumValue("Provider")
    PROVIDER("Provider"),
    @XmlEnumValue("Device")
    DEVICE("Device"),
    @XmlEnumValue("Line")
    LINE("Line");
    private final String value;

    CtiMgrClass(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CtiMgrClass fromValue(String v) {
        for (CtiMgrClass c: CtiMgrClass.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
