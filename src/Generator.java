import excel.Excel;

import java.io.IOException;

public class Generator {

    public void run() throws IOException {

        Excel excel = new Excel("my-excel");
        excel.create();
        excel.load();
        excel.toXML();

    }

}
