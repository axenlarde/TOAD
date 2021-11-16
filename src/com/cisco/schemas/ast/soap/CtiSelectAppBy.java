
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CtiSelectAppBy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CtiSelectAppBy">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AppId"/>
 *     &lt;enumeration value="AppIPV4Address"/>
 *     &lt;enumeration value="AppIPV6Address"/>
 *     &lt;enumeration value="UserId"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CtiSelectAppBy")
@XmlEnum
public enum CtiSelectAppBy {

    @XmlEnumValue("AppId")
    APP_ID("AppId"),
    @XmlEnumValue("AppIPV4Address")
    APP_IPV_4_ADDRESS("AppIPV4Address"),
    @XmlEnumValue("AppIPV6Address")
    APP_IPV_6_ADDRESS("AppIPV6Address"),
    @XmlEnumValue("UserId")
    USER_ID("UserId");
    private final String value;

    CtiSelectAppBy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CtiSelectAppBy fromValue(String v) {
        for (CtiSelectAppBy c: CtiSelectAppBy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
