package com.decathlon.finance.taxreport.util;

public class Constants {

    public static final String REPORT_TYPE_SUBLEDGER = "subledger";

    public static final String REPORT_TYPE_VOUCHER = "voucher";

    public static final String COMPANY_TYPE_FISCAL = "fiscal";

    public static final String COMPANY_TYPE_STORE = "store";

    public static final String SRC_FOLDER_GL = System.getProperty("user.dir") + "\\PDFSourceFile\\GLFile\\";

    public static final String SRC_FOLDER_BL = System.getProperty("user.dir") + "\\PDFSourceFile\\BalanceFile\\";

    public static final String JRXML_SUBLEDGER = "/reports/subledger.jrxml";

    public static final String JRXML_VOUCHER = "/reports/voucher.jrxml";

    public static final String STATIC_TEXT_TOTAL_VOUCHER = "合计:";

    public static final String STATIC_TEXT_TOTAL_SUBLEDGER = "本期合计";

    public static final String STATIC_TEXT_INNITIAL_SUBLEDGER = "初期余额";

    public static final String STATIC_TEXT_DEBIT_SUBLEDGER = "借";

    public static final String STATIC_TEXT_CREDIT_SUBLEDGER = "贷";

    public static final String STATIC_TEXT_LEVEL_SUBLEDGER = "平";
}
