//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2017.05.18 ʱ�� 09:13:04 AM CST 
//


package site.blmdz.webs;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the site.blmdz.webs package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: site.blmdz.webs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTestRequest }
     * 
     */
    public GetTestRequest createGetTestRequest() {
        return new GetTestRequest();
    }

    /**
     * Create an instance of {@link GetTestResponse }
     * 
     */
    public GetTestResponse createGetTestResponse() {
        return new GetTestResponse();
    }

}
