package com.decathlon.finance.taxreport.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class StringUtil {
    public static int compareCode(String str1, String str2)
    {
        int i = -1;
        String[] sa1= str1.split("");
        String[] sa2= str2.split("");
        for(int y = 0 ;y<sa1.length; )
        {
            if(sa2.length-1 < y)
            {
                break;
            }
            i = Integer.parseInt(sa1[y])-Integer.parseInt(sa2[y]);
            if(i == 0)
            {
                y++;
            }
            else
            {
                y=sa1.length;
            }
        }
        return i;
    }

    public static String txt2String(File file)
    {
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
