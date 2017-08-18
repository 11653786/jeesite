package com.thinkgem.jeesite.third.wx;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xml������
 * @author miklchen
 *
 */
public class XMLUtil {

	/**
	 * ����xml,���ص�һ��Ԫ�ؼ�ֵ�ԡ�����һ��Ԫ�����ӽڵ㣬��˽ڵ��ֵ���ӽڵ��xml��ݡ�
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();

		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}

			m.put(k, v);
		}

		in.close();

		return m;
	}

	/**
     * ��ȡ�ӽ���xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

	/**
	 * ��ȡxml�����ַ�
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws JDOMException 
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String)doc.getProperty("encoding");
	}


	/**
	 * 根据map生成对应的XML
	 * @param param
	 * @return String
	 */
	public static String getXmlByMap(Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		if(param != null && param.size() > 0) {
			sb.append("<xml>");
			for (String key : param.keySet()) {
				sb.append("<" + key + ">");
				sb.append(param.get(key));
				sb.append("</" + key + ">");
			}
			sb.append("</xml>");
		}

		return sb.toString();
	}
	
}
