package org.ha1yu;

public class Utils {
    public static final String[] bugs_mainTab = new String[]{"CVE-2006-5750", "CVE-2007-1036", "CVE-2010-0738", "CVE-2010-1871", "CVE-2013-4810", "CVE-2015-7501", "CVE-2017-7504", "CVE-2017-12149"};
    public static final String splitLine = "=======================================================================================\n";
    public static final String jspShell = "<%out.println(\"hello world!!!\");%>";
    public static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";
    public static final String ABOUT_README = "\t\t\t\t\t\t\t\t\t\t\tJBoss漏洞验证工具\n\n\n\n1.支持单一URL漏洞验证和单一漏洞批量扫描。单一漏洞批量验证时，双击URL，URL填写到地址栏中，右击复制URL到剪贴板。\n\n2.支持CVE-2006-5750,CVE-2007-1036,CVE-2010-0738,CVE-2010-1871,CVE-2013-4810,CVE-2015-7501,CVE-2017-7504,CVE-2017-12149等漏洞的验证。\n\n3.支持CVE-2010-1871,CVE-2013-4810,CVE-2015-7501,CVE-2017-7504,CVE-2017-12149等漏洞的命令执行。\n\n";

    public Utils() {
    }
}
