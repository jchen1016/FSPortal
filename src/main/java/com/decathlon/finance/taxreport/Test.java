package com.decathlon.finance.taxreport;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Test {

    static List<String> filePath = new ArrayList<String>();
    static Map<String,String> m = new HashMap<String,String>();

    public static void initMap()
    {
        m.put("POSLogDateTime","TypeCode,Transaction");
        m.put("UnitID","TypeCode,RetailStore");
        m.put("Total","TypeCode,Sale|TotalType,TransactionGrandAmount");
    }

    public static void traverseFolder2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        filePath.add(file2.getAbsolutePath());
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }



    public static void joinXml() throws DocumentException, IOException {

        File originalFile = new File(filePath.get(0));
        SAXReader readerr = new SAXReader();
        Document outDoc = readerr.read(originalFile);
        Element r = outDoc.getRootElement();

        //ee.addText(je.getText());
        for(int i = 1 ;i<filePath.size(); i++)
        {
            Element ee = r.element("DataArea");
            File tempf = new File(filePath.get(i));
            SAXReader reader = new SAXReader();
            Document doc = reader.read(tempf);
            Element root = doc.getRootElement();
            Element e = root.element("DataArea");
            Element je = e.element("JournalEntry");
            ee.add((Element) je.clone());
        }
        XMLWriter writer = new XMLWriter(new FileWriter("D:\\TTTTTTTTTTTTTTTTTTTTTTT\\out.xml"));
        writer.write(outDoc);
        writer.close();
        //Element je = e.element("JournalEntry");

//        for (Iterator i = root.elementIterator("oxit:ApplicationArea"); i.hasNext(); ) {
//            foo = (Element) i.next();
//            int a =2 ;
//            System.out.print("车牌号码:" + foo.elementText("NO"));
//            System.out.println("车主地址:" + foo.elementText("ADDR"));
//        }
    }

    public static void showAllXMLKeyValue(String filepos) throws DocumentException {
        // 解析books.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new File("C:\\Users\\Chen.CNL-JMCXSQ2\\Desktop\\xxxxxmml.xml"));
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
           hasNext(bookStore);
    }

    public static void hasNext(Element e)
    {
        Iterator t  = e.elementIterator();
        if(t.hasNext())
        {
            while(t.hasNext())
            {
                hasNext((Element) t.next());
            }
        }
       else
        {

            if(!e.getStringValue().equals(""))
            {
                System.out.println("Key:" + e.getName() + "-------Value:" + e.getStringValue());
            }
        }

    }

    public  static  void main(String args[]) throws DocumentException, IOException {
//        try {
//            String file1 = "C:\\Users\\Chen.CNL-JMCXSQ2\\Desktop\\";
//            String file2 = "2015localaccount_GLentries.xls";
//            String vbFileName = ResourceUtils.getFile("C:\\Users\\Chen.CNL-JMCXSQ2\\Desktop\\RunVBA.vbs").getPath();
////            String [] cmd={"wscript",vbFileName,file1,file2};
//            String [] cmd={"wscript",vbFileName};
//            Runtime.getRuntime().exec(cmd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        String test = "aaatargetbbb";
//        String[] str = test.split("target");
//        System.out.println(str);

//        JACOBUtil emt = null;
//        try{
//            emt = new JACOBUtil(JACOBUtil.VISIBLE);
//            emt.openWorkbook("D:\\Test\\tttt.xlsm");
//            emt.runMacro(new Variant("GL_Convert"));
//            emt.closeWorkbook();
//        }
//        finally {
//            if(emt != null) {
//                emt.quit();
//            }
//        }

        //HuiYunCase
//        traverseFolder2("D:\\HuiYunCase\\ExactIn_March\\ExactIn_March");
//        joinXml();
//        System.out.println("finish!!!");
        //HuiYunCase

        showAllXMLKeyValue("TEST");

    }
}
