package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Excel {

    protected static final List<Employee> employees =  new ArrayList<>();

    public void load(String filePath) throws IOException {

        File myFile = new File(filePath);
        FileInputStream fis = new FileInputStream(myFile);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Radnici");

        int rowStart = 1;
        int rowEnd = sheet.getLastRowNum();

        for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {

            List<String> employeData =  new ArrayList<>();
            Row row = sheet.getRow(rowNum);

            if (row == null) {
                // TODO - Row empty error
                continue;
            }

            Sheet sheetCoef = workbook.getSheet("Koeficijenti");

            int cellEnd = row.getLastCellNum() - 1;
            for (int cellNum = 0; cellNum <= cellEnd; cellNum++) {
                Cell cell = row.getCell(cellNum);

                if (cell == null) {

                    // TODO - Cell empty error
                    continue;

                } else {

                    if( cellNum == cellEnd ){

                        Row rowCoef = sheetCoef.getRow(rowNum);
                        Cell cellCoef = rowCoef.getCell(0);
                        employeData.add(formatCell(cell));
                        employeData.add(cellCoef.toString());

                    } else {

                        employeData.add(formatCell(cell));

                    }
                }
            }

            Employee employee = new Employee(Integer.parseInt(employeData.get(0)),
                    employeData.get(1),
                    employeData.get(2),
                    Integer.parseInt(employeData.get(3)),
                    Double.parseDouble(employeData.get(4)));

            employees.add(employee);

        }

    }

    public void toXML(String filePath){

        try {

            InputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("Radnici");

            FileWriter fostream = new FileWriter(filePath);
            PrintWriter out = new PrintWriter(new BufferedWriter(fostream));

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<Radnici>");

            boolean firstRow = true;
            for(Row row: sheet){
                if(firstRow){
                    firstRow = false;
                }

                out.println(formatElement("\t\t", "ID", formatCell(row.getCell(0))));
                out.println(formatElement("\t\t", "Name", formatCell(row.getCell(1))));
                out.println(formatElement("\t\t", "Designation", formatCell(row.getCell(2))));

            }

            out.println("</Radnici>");
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