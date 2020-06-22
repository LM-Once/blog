package com.it.utils.pdfUtil;

import com.google.common.collect.Lists;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/***********************************************************
 ** introduction (C), 2019-09-17, OPPO Mobile Comm Corp., Ltd.
 ** File: - PDFReport.java
 ** Description: Log解析PDF测试报告类.
 ** Version: 1.0
 ** Date : 2019-09-17 11:45
 ** Author: 18576756475
 **************************************************************/
public class PDFReport extends Constant {
    /**
     * 设置主标题字体样式
     */
    private static Font headfont;

    /**
     * 设置一级标题样式
     */
    private static Font firstTitleFont;

    /**
     * 设置二级标题样式
     */
    private static Font secondTitleFont;

    /**
     * 设置关键字字体样式
     */
    private static Font keyfont;

    /**
     * 设置文本字体样式
     */
    private static Font textfont;

    /**
     * 文档流
     */
    private static Document document;

    /**
     * 实例
     */
    private static PDFReport pdfReport;

    static {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            headfont = new Font(bfChinese, 18, Font.BOLD);
            firstTitleFont = new Font(bfChinese, 15, Font.BOLD);
            secondTitleFont = new Font(bfChinese, 13, Font.BOLD);
            keyfont = new Font(bfChinese, 8, Font.BOLD);
            textfont = new Font(bfChinese, 8, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static PDFReport getInstance(String url){
        if (pdfReport == null ){
            pdfReport = new PDFReport();
        }
        if (document ==null){
            document = new Document(PageSize.A4, 100, 100, 90, 90);
        }
        try {
            PdfWriter.getInstance(document, new FileOutputStream(url));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        return pdfReport;
    }

    public void writePdf(List<String> reportTimeList ,List<String> contentList) throws Exception {

        titleCommon(TITLE,headfont,Element.ALIGN_CENTER,document);
        titleCommon(null,null,2,document);
        titleCommon(null,null,1,document);
        titleCommon(null,null,1,document);

        titleCommon(reportTime,firstTitleFont,Element.ALIGN_LEFT,document);
        content(reportTimeList,document);
        titleCommon(null,null,1,document);

        titleCommon(FIRST_CONTENT_TITLE,firstTitleFont,Element.ALIGN_LEFT,document);
        titleCommon(null,null,1,document);

        titleCommon(SECOND_CONTENT_TITLE,secondTitleFont,Element.ALIGN_LEFT,document);

        content(contentList,document);
        titleCommon(null,null,1,document);
        // 测试结果
        titleCommon(FIRST_RESULT_TITLE,firstTitleFont,Element.ALIGN_LEFT,document);
        titleCommon(null,null,1,document);

        titleCommon(SECOND_RESULT_DATA_TITLE,secondTitleFont,Element.ALIGN_LEFT,document);

    }

    /**
     * bugId相关数据写入pdf
     * @param bugData
     */
    public void writeBugIdData(BugData bugData){
        try {
            generatePDF(document,bugData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入标题
     * @param titleContent 内容
     * @param font 字体样式
     * @param alignment 位置
     * @param document 文档流
     * @throws Exception
     */
    public void titleCommon(String titleContent,Font font,int alignment, Document document) throws Exception{
        if (StringUtils.isBlank(titleContent)){
            for(int i=0;i<alignment;i++){
                document.add(new Paragraph("\n"));
            }
            return;
        }
        //设置字体样式
        Paragraph paragraph = new Paragraph(titleContent, font);
        //设置文字居中 0靠左   1，居中  2，靠右
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }

    /**
     * 写入文本内容
     * @param contentList 内容列表
     * @param document 文档流
     */
    public void content(final List<String> contentList, Document document) {

        contentList.stream().forEach(content -> {
            Paragraph paragraph = new Paragraph(25, content, textfont);
            paragraph.setIndentationLeft(30);
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
    }

    int maxWidth = 400;
    public PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);
        try{
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell();
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }

    public PdfPCell createCell(String value, Font font){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    public PdfPCell createCell(String value, Font font, int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }

    public PdfPCell createCell(String value,Font font,int align,
                               int colspan,boolean boderFlag){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value,font));
        cell.setPadding(3.0f);
        if(!boderFlag){
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }
    public void generatePDF(Document document,BugData bugData) throws Exception{
        titleCommon(null,null,1,document);
        titleCommon(BUG_ID +"-----> "+bugData.getBugId(),secondTitleFont,Element.ALIGN_LEFT,document);
        content(bugData.getBaseDataList(),document);
        float[] width = {50,50,50,50,200};
        PdfPTable table = createTable(5);
        table.setTotalWidth(width);

        table.addCell(createCell("解析结果数据分析:", keyfont,Element.ALIGN_LEFT,5,false));
        table.addCell(createCell("图文件", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("log文件", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("有效性", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("原因", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("说明", keyfont, Element.ALIGN_CENTER));

        bugData.getResultTable().stream().forEach(logTableData->{
            table.addCell(createCell(logTableData.getTuFileName(), textfont));
            table.addCell(createCell(logTableData.getLogFileName(), textfont));
            table.addCell(createCell(logTableData.getIsEffective(), textfont));
            table.addCell(createCell(logTableData.getReason(), textfont));
            table.addCell(createCell(logTableData.getDescription(), textfont));
        });
        document.add(table);
    }

    public void closeDocument(Document document){
        document.close();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("begin");

        List<String> reportTimeList= Lists.newArrayList();
        reportTimeList.add("测试时间：2019-09-17");

        List<String> contentList = Lists.newArrayList();
        contentList.add("图文件的创建，log文件解析与log文件模型匹配，解析结果的宏观展示等");
        contentList.add("测试模块：BT模块");
        contentList.add("测试项目：AD027_AD030_AD031_AD061_AD065");
        contentList.add( "测试场景：ONOFF");

        String bugId = "12306";

        List<String> basicDataList = Lists.newArrayList();
        basicDataList.add("图文件：[test.tu,test1.tu]");
        basicDataList.add( "LogTime:  from 2019-09-16 00:00:00 to 2019-09-17 00:00:00");
        basicDataList.add( "过滤后的log文件：[android1.txt,android2.txt]");

        List<LogTableData> resultTable = Lists.newArrayList();
        LogTableData logTableData = LogTableData.build().setTuFileName("test.tu").
                setLogFileName("android-test-test-test.txt").
                setIsEffective("无效").
                setReason("time error").
                setDescription("does not match any timestamp in log file, maybe you should input date of format year-month-day hour:minute:second");
        resultTable.add(logTableData);
        LogTableData logTableData1 = LogTableData.build().setTuFileName("test.tu").
                setLogFileName("android-test-test-test.txt").
                setIsEffective("无效").
                setReason("time error").
                setDescription("does not match any timestamp in log file, maybe you should input date of format year-month-day hour:minute:second");
        resultTable.add(logTableData);
        LogTableData logTableData2 = LogTableData.build().setTuFileName("test.tu").
                setLogFileName("android-test-test-test.txt").
                setIsEffective("无效").
                setReason("time error").
                setDescription("does not match any timestamp in log file, maybe you should input date of format year-month-day hour:minute:second");
        resultTable.add(logTableData);
        resultTable.add(logTableData1);
        resultTable.add(logTableData2);
        BugData bugData = BugData.build().setBugId(bugId).setBaseDataList(basicDataList).setResultTable(resultTable);
        PDFReport instance = PDFReport.getInstance("F:\\code\\ITextTest.pdf");
        instance.writePdf(reportTimeList, contentList);
        instance.writeBugIdData(bugData);
        instance.writeBugIdData(bugData);
        instance.closeDocument(document);
        System.out.println("end");
    }
}
