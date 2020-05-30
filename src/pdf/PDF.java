package pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import excel.Employee;
import org.apache.fop.apps.*;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.itextpdf.layout.Document;

public class PDF {

    private final Employee employee;

    public PDF(Employee employee){
        this.employee = employee;
    }

    public static final String DEST = "./src/resources/hello.pdf";

    private String createFileName(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d.M.y. HH:mm");
        LocalDateTime now = LocalDateTime.now();

        return employee.getName() + " " + employee.getSurname() + " - " + dtf.format(now);

    }

    private String getPath(){
        return "./src/resources/" +  createFileName() + ".pdf";
    }

    public void convertToPDF() throws IOException, FOPException, TransformerException{

        PdfDocument pdf = new PdfDocument(new PdfWriter(getPath()));
        Document document = new Document(pdf);

        document.add(new Paragraph(employee.getId().toString()));
        document.add(new Paragraph(employee.getName()));
        document.add(new Paragraph(employee.getSurname()));
        document.add(new Paragraph(employee.getBase().toString()));
        document.add(new Paragraph(employee.getCoefficient().toString()));
        document.add(new Paragraph(employee.getSalary().toString()));
        document.close();

    }

}
