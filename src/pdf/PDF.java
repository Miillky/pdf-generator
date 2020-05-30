package pdf;

import JFX.AlertBox;
import JFX.Constants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.property.TextAlignment;
import excel.Employee;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;

import org.apache.fop.apps.*;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.layout.Document;

public class PDF {

    private final Employee employee;

    public PDF(Employee employee){
        this.employee = employee;
    }

    private String createFileName(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.PDF_FILENAME_DATE_FORMAT);
        LocalDateTime now = LocalDateTime.now();

        StringBuilder sb = new StringBuilder().append(employee.getName())
                .append(" ")
                .append(employee.getSurname())
                .append(" - ")
                .append(dtf.format(now));

        return sb.toString();

    }

    private String getPath(){
        return Constants.PDF_GENERATED_PATH +  createFileName() + ".pdf";
    }

    private String getFont(String fontName) { return Constants.PDF_FONT_PATH + fontName; }

    public void convertToPDF() throws IOException, FOPException, TransformerException{

        try {

            PdfDocument pdf = new PdfDocument(new PdfWriter(getPath()));
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(
                    getFont(Constants.PDF_BUILDER_FONT_NAME),
                    PdfEncodings.IDENTITY_H, 
                    true);

            Paragraph title = new Paragraph(Constants.PDF_BUILDER_TITLE +
                        employee.getName() + " " +
                        employee.getSurname())
                    .setFontSize(30)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(font);

            Paragraph id = new Paragraph(Constants.PDF_BUILDER_ID + " "  + employee.getId().toString())
                    .setFontSize(14)
                    .setFont(font);

            Paragraph name = new Paragraph(Constants.PDF_BUILDER_NAME + " " + employee.getName())
                    .setFontSize(14)
                    .setFont(font);

            Paragraph surname = new Paragraph(Constants.PDF_BUILDER_SURNAME + " " + employee.getSurname())
                    .setFontSize(14)
                    .setFont(font);

            Paragraph base = new Paragraph(Constants.PDF_BUILDER_BASE + " " +
                        employee.getBase().toString() +
                        " " + Constants.PDF_BUILDER_CURRENCY)
                    .setFontSize(14)
                    .setFont(font);

            Paragraph coefficient = new Paragraph(Constants.PDF_BUILDER_COEFFICIENT + " " +
                        employee.getCoefficient().toString())
                    .setFontSize(14)
                    .setFont(font);

            Paragraph salary = new Paragraph(Constants.PDF_BUILDER_SALARY + " " +
                        employee.getSalary().toString() + " " +
                        Constants.PDF_BUILDER_CURRENCY)
                    .setFontSize(14)
                    .setFont(font);

            document.add(title);
            document.add(id);
            document.add(name);
            document.add(surname);
            document.add(base);
            document.add(coefficient);
            document.add(salary);
            document.close();

        } catch (IOException exception){
            new AlertBox().display(Constants.PDF_BUILDER_ERROR_TITLE, exception.toString());
        }


    }

}
