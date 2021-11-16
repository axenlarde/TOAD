
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CmSelectBy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CmSelectBy">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Name"/>
 *     &lt;enumeration value="IPV4Address"/>
 *     &lt;enumeration value="IPV6Address"/>
 *     &lt;enumeration value="DirNumber"/>
 *     &lt;enumeration value="Description"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CmSelectBy")
@XmlEnum
public enum CmSelectBy {

    @XmlEnumValue("Name")
    NAME("Name"),
    @XmlEnumValue("IPV4Address")
    IPV_4_ADDRESS("IPV4Address"),
    @XmlEnumValue("IPV6Address")
    IPV_6_ADDRESS("IPV6Address"),
    @XmlEnumValue("DirNumber")
    DIR_NUMBER("DirNumber"),
    @XmlEnumValue("Description")
    DESCRIPTION("Description");
    private final String value;

    CmSelectBy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CmSelectBy fromValue(String v) {
        for (CmSelectBy c: CmSelectBy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
