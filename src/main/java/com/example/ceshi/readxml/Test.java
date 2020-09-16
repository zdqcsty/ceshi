/*
package com.example.ceshi.readxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();

        File f = new File("E:\\study_workSpace\\ceshi\\src\\main\\resources\\book.xml");
        Document doc = dBuilder.parse(f);

        Element root = doc.getDocumentElement();
        // 输出根节点的名字，bookstore
//        System.out.println(root.getTagName());
        // 获得所有的book节点
        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            //获得第i个book节点
            Node child = children.item(i);
            //用来存储第i个节点的内容
            Book book = new Book();
            if (child instanceof Element) {
                Element childElement = (Element) child;
//                int bookId = Integer.parseInt(childElement.getAttribute("id"));
//                System.out.println(bookId);
                NodeList bookAttrbuteList = childElement.getChildNodes();
                // 循环遍历book节点的各个子节点，如name,author...
                for (int j = 0; j < bookAttrbuteList.getLength(); j++) {
                    Node bookAttrbute = bookAttrbuteList.item(j);
                    if (bookAttrbute instanceof Element) {
                        String content = bookAttrbute.getTextContent().trim();
                        System.out.println(
                                ((Element) bookAttrbute).getTagName() + ":" + content);
//                        bookAttrbuteContent.add(content);
                    }
                }
                */
/*book.setName(bookAttrbuteContent.get(0));
                book.setAuthor(bookAttrbuteContent.get(1));
                book.setYear(Integer.parseInt(bookAttrbuteContent.get(2)));
                book.setPrice(Integer.parseInt(bookAttrbuteContent.get(3)));
                books.add(book);*//*

            }
        }
    }


}
*/
