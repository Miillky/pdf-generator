package pdf;

import org.apache.fop.apps.*;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class PDF {

    private final String file;

    public PDF(String file){
        this.file = file;
    }

    public void convertToPDF() throws IOException, FOPException, TransformerException{

        File xsltFile = new File("files/" + file + ".xsl");
        StreamSource xmlSource = new StreamSource("files/" + file + ".xml");
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        try( OutputStream out = new java.io.FileOutputStream("files/" + file + ".pdf") ) {

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);

        }

    }

}
