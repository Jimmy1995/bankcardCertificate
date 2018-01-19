package utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;


/**
 *
 */
public class XMLUtils {
 
    @SuppressWarnings("unchecked")
	public static <T> T parserString(String content, Class<T> c) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(content);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException ex) {
           ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
	public static String toString(Object object, Class c) {
        try {
            JAXBContext jc = JAXBContext.newInstance(c);
            Marshaller m = jc.createMarshaller();
            StringWriter writer = new StringWriter();
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty("com.sun.xml.internal.bind.characterEscapeHandler", new CharacterEscapeHandler() {
            	 public void escape(char[] ac, int i, int j, boolean flag,
                         Writer writer) throws IOException {
                     writer.write(ac, i, j);
                 }
            });

            m.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
