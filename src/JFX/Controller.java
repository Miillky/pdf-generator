package JFX;

import excel.Employee;
import excel.Excel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Controller extends Excel {

    private String filePath;

    @FXML
    private ListView<String> employeesList;


    @FXML
    private BorderPane BorderPane;

    @FXML
    public void choseFile(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Odaberite datoteku");
        Stage stage = (Stage)BorderPane.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if( FileMagic.valueOf(file).equals(FileMagic.OOXML) ){
            filePath = file.getPath();
        } else {
            // TODO - popup not excel file error
        }


    }

    @FXML
    public void generateEmployeesList(MouseEvent mouseEvent) throws IOException {
        if(filePath != null){
            load(filePath);

            Iterator<Employee> employeeIterator = employees.iterator();
            while (employeeIterator.hasNext()) {
                Employee employee = employeeIterator.next();
                employeesList.getItems().add(employee.toString());
            }


            employeesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            employeesList.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{

                System.out.println( employeesList.getSelectionModel().getSelectedItems() );
                // TODO - fill selected array to generate PDF file
            });

        }
    }

    @FXML
    public void generatePDF(javafx.scene.input.MouseEvent mouseEvent) {

        // TODO - Generate PDF file from selected list

    }
}