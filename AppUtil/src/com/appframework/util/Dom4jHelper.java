/**
 * http://www.jq-school.com
 */
package com.appframework.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author advance
 *
 */
public class Dom4jHelper {
    /**
     *  Ω‚Œˆurl xmlŒƒµµ
     * @param url
     * @return
     * @throws DocumentException
     */
    public Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }
    /**
     * ±È¿˙Ω‚ŒˆŒƒµµ
     * @param document
     */
    public void treeWalk(Document document) {
        treeWalk( document.getRootElement() );
    }
    /**
     * ±È¿˙Ω‚Œˆ‘™Àÿ
     * @param element
     */
    public void treeWalk(Element element) {
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            if ( node instanceof Element ) {
                treeWalk( (Element) node );
            }
            else {
                // ¥¶¿Ì....
            }
        }
    }

    /**
     * Ω‚ŒˆŒƒº˛£¨ªÒµ√∏˘‘™Àÿ
     * @param xmlPath
     * @param encoding
     * @return
     * @throws Exception
     */
    public static Element parse(String xmlPath,String encoding)throws Exception{
        //Œƒº˛ «∑Ò¥Ê‘⁄
        File file = new File(xmlPath);
        if(!file.exists()){
            throw new Exception("’“≤ªµΩxmlŒƒº˛£∫"+xmlPath);
        }

        //Ω‚Œˆ
        SAXReader reader = new SAXReader(false);
        Document doc = reader.read(new FileInputStream(file),encoding);
        Element root = doc.getRootElement();
        return root;
    }

    /**
     * ±£¥ÊŒƒµµ
     * @param doc
     * @param xmlPath
     * @param encoding
     * @throws Exception
     */
    public static void save(Document doc,String xmlPath,String encoding)throws Exception{
        OutputFormat format=OutputFormat.createPrettyPrint();
        format.setEncoding(encoding);
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(xmlPath),encoding),format);
        writer.write(doc);
        writer.flush();
        writer.close();
    }
    /**
     * –ﬁ∏ƒxmlƒ≥Ω⁄µ„µƒ÷µ
     * @param inputXml ‘≠xmlŒƒº˛
     * @param nodes “™–ﬁ∏ƒµƒΩ⁄µ„
     * @param attributename  Ù–‘√˚≥∆
     * @param value –¬÷µ
     * @param outXml  ‰≥ˆŒƒº˛¬∑æ∂º∞Œƒº˛√˚ »Áπ˚ ‰≥ˆŒƒº˛Œ™null£¨‘Úƒ¨»œŒ™‘≠xmlŒƒº˛
     */
    public static void modifyDocument(File inputXml, String nodes, String attributename, String value, String outXml) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputXml);
            List list = document.selectNodes(nodes);
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Attribute attribute = (Attribute) iter.next();
                if (attribute.getName().equals(attributename))
                    attribute.setValue(value);
            }
            XMLWriter output;
            if (outXml != null){ //÷∏∂® ‰≥ˆŒƒº˛
                output = new XMLWriter(new FileWriter(new File(outXml)));
            }else{ // ‰≥ˆŒƒº˛Œ™‘≠Œƒº˛
                output = new XMLWriter(new FileWriter(inputXml));
            }
            output.write(document);
            output.close();
        }

        catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * xml◊™ªªŒ™◊÷∑˚¥Æ
     * @param doc
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String toString(Document doc,String encoding)throws Exception{
        OutputFormat format=OutputFormat.createPrettyPrint();
        format.setEncoding(encoding);
        ByteArrayOutputStream byteOS=new ByteArrayOutputStream();
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(byteOS,encoding),format);
        writer.write(doc);
        writer.flush();
        writer.close();
        writer=null;

        return byteOS.toString(encoding);
    }
    /**
     * ◊÷∑˚¥Æ◊™ªªŒ™Document
     * @param text
     * @return
     * @throws DocumentException
     */
    public static Document str2Document(String text) throws DocumentException{
        Document document = DocumentHelper.parseText(text);
        return document;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
