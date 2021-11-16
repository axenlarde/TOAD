
package com.cisco.schemas.ast.soap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "RISService70", targetNamespace = "http://schemas.cisco.com/ast/soap", wsdlLocation = "/wsdl/10/RISService70.wsdl")
public class RISService70
    extends Service
{

    private final static URL RISSERVICE70_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.cisco.schemas.ast.soap.RISService70 .class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.cisco.schemas.ast.soap.RISService70 .class.getResource("/wsdl/10/");
            url = new URL(baseUrl, "RISService70.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: './wsdl/10/RISService70.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        RISSERVICE70_WSDL_LOCATION = url;
    }

    public RISService70(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RISService70() {
        super(RISSERVICE70_WSDL_LOCATION, new QName("http://schemas.cisco.com/ast/soap", "RISService70"));
    }

    /**
     * 
     * @return
     *     returns RisPortType
     */
    @WebEndpoint(name = "RisPort70")
    public RisPortType getRisPort70() {
        return super.getPort(new QName("http://schemas.cisco.com/ast/soap", "RisPort70"), RisPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RisPortType
     */
    @WebEndpoint(name = "RisPort70")
    public RisPortType getRisPort70(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.cisco.com/ast/soap", "RisPort70"), RisPortType.class, features);
    }

}
