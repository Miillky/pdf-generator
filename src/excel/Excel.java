package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Excel {

    private final String fileName;
    private final String sheetName;
    private static final String[] columns = { "Id", "Name", "Designation" };
    private static final List<Employee> employees =  new ArrayList<>();

    static {
        employees.add(new Employee(1, "Vedran Milković", "Executive" ));
        employees.add(new Employee(2,"Milković Vedran", "Manager"));
    }

    public Excel(String fileName){
        this.fileName = fileName;
        this.sheetName = "Employees";
    }

    public Excel(String fileName, String sheetName){
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    public void create() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet(sheetName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < columns.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("d.m.Y"));

        int rowNum = 1;
        for(Employee employee: employees){

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getDesignation());

        }

        for(int i = 0; i < columns.length; i++){
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream("files/" + fileName + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

    }

    public void load() throws IOException {

        File myFile = new File("files/" + fileName + ".xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        for(Row row: sheet){

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()){

                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {

                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;

                }

            }

            System.out.println();

        }

    }

    public void toXML(){

        try {

            InputStream inputStream = new FileInputStream(new File("files/" + fileName + ".xlsx"));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            FileWriter fostream = new FileWriter("files/" + fileName + ".xml");
            PrintWriter out = new PrintWriter(new BufferedWriter(fostream));

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<" + sheetName + ">");

            boolean firstRow = true;
            for(Row row: sheet){
                if(firstRow){
                    firstRow = false;
                }

                out.println(formatElement("\t\t", "ID", formatCell(row.getCell(0))));
                out.println(formatElement("\t\t", "Name", formatCell(row.getCell(1))));
                out.println(formatElement("\t\t", "Designation", formatCell(row.getCell(2))));

            }

            out.println("</" + sheetName + ">");
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String formatCell(Cell cell){

        if(cell == null){
            return "";
        }

        switch(cell.getCellType()){
            case BLANK:
                return "";
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:
                return new DecimalFormat("#####0").format(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case ERROR:
                return "*error*";
            default:
                return "<unknown value>";

        }

    }

    private String formatElement(String prefix, String tag, String value){

        StringBuilder sb = new StringBuilder(prefix);

        sb.append("<");
        sb.append(tag);

        if(value != null && value.length() > 0){
            sb.append(">");
            sb.append(value);
            sb.append("</");
            sb.append(tag);
            sb.append(">");
        } else {
            sb.append("/>");
        }

        return sb.toString();
    }

}