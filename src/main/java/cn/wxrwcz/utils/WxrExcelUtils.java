package cn.wxrwcz.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

public class WxrExcelUtils {
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String[] columnNames, String path) {
        // 创建excel工作簿
        File excel = new File(path);
        FileInputStream is;
        try {
            is = new FileInputStream(excel);
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
            for (int i = 0; keys != null && i < keys.length; i++) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            }

            //设置每行每列的值
            for (short i = 1; list != null && i < list.size(); i++) {
                // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
                // 创建一行，在页sheet上
                Row row1 = sheet.createRow((short) i);
                // 在row行上创建一个方格
                for (short j = 0; keys != null && j < keys.length; j++) {
                    Cell cell = row1.createCell(j);
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
            }
            return wb;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeExcel(HttpServletResponse response, Workbook work, String fileName) throws IOException {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xlsx", "UTF-8"))));
            work.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static <T> XSSFWorkbook generateXlsxWorkbook(String title, LinkedHashMap<String, String> propertyHeaderMap, Collection<T> dataSet) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((int) 15);

        XSSFCellStyle headerStyle = getHeaderStyle(workbook);
        XSSFCellStyle contentStyle = getContentStyle(workbook);

        // 生成表格标题行
        XSSFRow row = sheet.createRow(0);
        int i = 0;
        for(String key : propertyHeaderMap.keySet()){
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            XSSFRichTextString text = new XSSFRichTextString(propertyHeaderMap.get(key));
            cell.setCellValue(text);
            i++;
        }

        //循环dataSet，每一条对应一行
        int index = 0;
        for(T data : dataSet){
            index ++;
            row = sheet.createRow(index);

            int j = 0;
            for(String property : propertyHeaderMap.keySet()){
                XSSFCell cell = row.createCell(j);
                cell.setCellStyle(contentStyle);

                //拼装getter方法名
                String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);

                try {
                    //利用反射机制获取dataSet中的属性值，填进cell中
                    Class<? extends Object> tCls = data.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(data, new Object[] {});

                    // 判断值的类型后进行类型转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = String.valueOf(value);
                    }


                    XSSFRichTextString richString = new XSSFRichTextString(textValue);
                    cell.setCellValue(richString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                j++;
            }
        }
        return workbook;
    }

    public static XSSFCellStyle getHeaderStyle(Workbook workbook){
        return getHeaderStyle(workbook, Color.WHITE, IndexedColors.BLACK.getIndex());
    }

    public static XSSFCellStyle getHeaderStyle(Workbook workbook, Color foregroundColor, short fontColor){

        // 生成一个样式（用于标题）
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(new XSSFColor(foregroundColor));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(THIN);
        style.setBorderLeft(THIN);
        style.setBorderRight(THIN);
        style.setBorderTop(THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(fontColor);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);

        return style;
    }

    public static XSSFCellStyle getContentStyle(Workbook workbook){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setBorderBottom(THIN);
        style.setBorderLeft(THIN);
        style.setBorderRight(THIN);
        style.setBorderTop(THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(false);
        style.setFont(font);
        return style;
    }

}