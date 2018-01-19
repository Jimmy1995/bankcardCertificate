package utils;


import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 *
 */
public class AdapterCDATA extends XmlAdapter<String, Serializable> {

    @Override
    public String marshal(Serializable arg0) throws Exception {
    	String str=arg0+"";
        return "<![CDATA[" + str + "]]>";
    }

    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }

}
