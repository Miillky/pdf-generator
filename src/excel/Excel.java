package excel;

import JFX.AlertBox;
import JFX.Constants;
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
        Sheet sheet = workbook.getSheet(Constants.MAIN_EXCEL_SHEET_NAME);

        int rowStart = 1;
        int rowEnd = sheet.getLastRowNum();
        boolean emptyRow = false;
        boolean emptyCell = false;

        for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {

            List<String> employeeData =  new ArrayList<>();
            Row row = sheet.getRow(rowNum);

            if (row == null) {

                if(!emptyRow){
                    emptyRow = true;
                }

                continue;
            }

            Sheet sheetCoefficient = workbook.getSheet(Constants.SECONDARY_EXCEL_SHEET_NAME);

            int cellEnd = row.getLastCellNum() - 1;
            for (int cellNum = 0; cellNum <= cellEnd; cellNum++) {
                Cell cell = row.getCell(cellNum);

                if (cell == null) {

                    if(!emptyCell) {
                        emptyCell = true;
                    }

                } else {

                    if( cellNum == cellEnd ){

                        Row rowCoefficient = sheetCoefficient.getRow(rowNum);
                        Cell cellCoefficient = rowCoefficient.getCell(0);
                        employeeData.add(formatCell(cell));
                        employeeData.add(cellCoefficient.toString());

                    } else {

                        employeeData.add(formatCell(cell));

                    }
                }
            }

            if( emptyRow ){
                AlertBox alertBox = new AlertBox();
                alertBox.display(Constants.EXCEL_ROW_ERROR_TITLE, Constants.EXCEL_ROW_ERROR_MESSAGE);
            }

            if( emptyCell ){
                AlertBox alertBox = new AlertBox();
                alertBox.display(Constants.EXCEL_COLUMN_ERROR_TITLE, Constants.EXCEL_COLUMN_ERROR_MESSAGE);
            }

            Employee employee = new Employee(Integer.parseInt(employeeData.get(0)),
                    employeeData.get(1),
                    employeeData.get(2),
                    Integer.parseInt(employeeData.get(3)),
                    Double.parseDouble(employeeData.get(4)));

            employees.add(employee);

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

}