
package com.cisco.schemas.ast.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeviceClass.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DeviceClass">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Any"/>
 *     &lt;enumeration value="Phone"/>
 *     &lt;enumeration value="Gateway"/>
 *     &lt;enumeration value="H323"/>
 *     &lt;enumeration value="Cti"/>
 *     &lt;enumeration value="VoiceMail"/>
 *     &lt;enumeration value="MediaResources"/>
 *     &lt;enumeration value="SIPTrunk"/>
 *     &lt;enumeration value="HuntList"/>
 *     &lt;enumeration value="Unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DeviceClass")
@XmlEnum
public enum DeviceClass {

    @XmlEnumValue("Any")
    ANY("Any"),
    @XmlEnumValue("Phone")
    PHONE("Phone"),
    @XmlEnumValue("Gateway")
    GATEWAY("Gateway"),
    @XmlEnumValue("H323")
    H_323("H323"),
    @XmlEnumValue("Cti")
    CTI("Cti"),
    @XmlEnumValue("VoiceMail")
    VOICE_MAIL("VoiceMail"),
    @XmlEnumValue("MediaResources")
    MEDIA_RESOURCES("MediaResources"),
    @XmlEnumValue("SIPTrunk")
    SIP_TRUNK("SIPTrunk"),
    @XmlEnumValue("HuntList")
    HUNT_LIST("HuntList"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown");
    private final String value;

    DeviceClass(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeviceClass fromValue(String v) {
        for (DeviceClass c: DeviceClass.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
