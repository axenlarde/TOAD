
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RisReturnCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RisReturnCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Ok"/>
 *     &lt;enumeration value="NotFound"/>
 *     &lt;enumeration value="InvalidRequest"/>
 *     &lt;enumeration value="InternalError"/>
 *     &lt;enumeration value="NodeNotResponding"/>
 *     &lt;enumeration value="InvalidNodeName"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RisReturnCode")
@XmlEnum
public enum RisReturnCode {

    @XmlEnumValue("Ok")
    OK("Ok"),
    @XmlEnumValue("NotFound")
    NOT_FOUND("NotFound"),
    @XmlEnumValue("InvalidRequest")
    INVALID_REQUEST("InvalidRequest"),
    @XmlEnumValue("InternalError")
    INTERNAL_ERROR("InternalError"),
    @XmlEnumValue("NodeNotResponding")
    NODE_NOT_RESPONDING("NodeNotResponding"),
    @XmlEnumValue("InvalidNodeName")
    INVALID_NODE_NAME("InvalidNodeName");
    private final String value;

    RisReturnCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RisReturnCode fromValue(String v) {
        for (RisReturnCode c: RisReturnCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
