package JFX;

import excel.Employee;
import excel.Excel;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hsmf.datatypes.PropertyValue;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Controller extends Excel implements Initializable {

    private String filePath;

    @FXML
    private BorderPane BorderPane;
    @FXML
    private TableView<Employee> employeesList;
    @FXML
    private TableColumn<Employee, Integer> employeeId;
    @FXML
    private TableColumn<Employee, SimpleStringProperty> employeeFirstName;
    @FXML
    private TableColumn<Employee, SimpleStringProperty> employeeLastName;
    @FXML
    private TableColumn<Employee, Integer> employeeBase;
    @FXML
    private TableColumn<Employee, Double> employeeCoefficient;
    @FXML
    private TableColumn<Employee, Double> employeeSalary;
    @FXML
    private TableColumn<Employee, CheckBox> employeeSelected;

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
            ObservableList<Employee> employeesListCollection = FXCollections.observableArrayList();
            employeesListCollection.addAll(employees);
            employeesList.setItems(employeesListCollection);
        }
    }

    @FXML
    public void generatePDF(javafx.scene.input.MouseEvent mouseEvent) {

        ObservableList<Employee> employeesSelectedCollection = FXCollections.observableArrayList();
        for(Employee employee : employees){
            if( employee.getSelected().isSelected() ){
                employeesSelectedCollection.add(employee);
            }
        }
        if( employeesSelectedCollection.isEmpty() ){
            // TODO - error to select employee
        } else {

            System.out.println(employeesSelectedCollection);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<Employee, SimpleStringProperty>("name"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<Employee, SimpleStringProperty>("surname"));
        employeeBase.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("base"));
        employeeCoefficient.setCellValueFactory(new PropertyValueFactory<Employee, Double>("coefficient"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        employeeSelected.setCellValueFactory(new PropertyValueFactory<Employee, CheckBox>("selected"));
    }
}