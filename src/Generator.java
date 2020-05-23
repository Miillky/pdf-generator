import excel.Excel;

import java.io.IOException;

public class Generator {

    public void run() throws IOException {

        Excel excel = new Excel("Place");
        //excel.create();
        excel.load();
        //excel.toXML();
    }

}
