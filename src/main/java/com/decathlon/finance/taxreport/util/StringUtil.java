package com.decathlon.finance.taxreport.util;

public class StringUtil {
    public static int compareCode(String str1, String str2)
    {
        int i = -1;
        String[] sa1= str1.split("");
        String[] sa2= str2.split("");
        if(str1.equals("901460"))
        {
            int a = 2;
        }
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
}
