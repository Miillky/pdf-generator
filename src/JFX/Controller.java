package JFX;

import excel.Employee;
import excel.Excel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.fop.apps.FOPException;
import org.apache.poi.poifs.filesystem.FileMagic;
import pdf.PDF;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Excel implements Initializable {

    private String filePath;

    @FXML
    private Button generatePDFBtn;
    @FXML
    private Button generateEmployeesBtn;
    @FXML
    private CheckBox selectAllEmployess;
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
        fileChooser.setTitle(Constants.CHOSE_FILE_WINDOW_TITLE);
        Stage stage = (Stage)BorderPane.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if( FileMagic.valueOf(file).equals(FileMagic.OOXML) ){
            filePath = file.getPath();
            generateEmployeesBtn.setDisable(false);
            selectAllEmployess.setDisable(true);
            generatePDFBtn.setDisable(true);
        } else {

            if( employeesList.getItems().size() > 0 ){
                employeesList.getItems().clear();
            }

            AlertBox alertBox = new AlertBox();
            alertBox.display(Constants.EXCEL_FILE_ERROR_TITLE, Constants.EXCEL_FILE_ERROR_MESSAGE);
            generateEmployeesBtn.setDisable(true);
            selectAllEmployess.setDisable(true);
            generatePDFBtn.setDisable(true);
        }

    }

    @FXML
    public void generateEmployeesList(MouseEvent mouseEvent) throws IOException {
        if(filePath != null){
            load(filePath);
            ObservableList<Employee> employeesListCollection = FXCollections.observableArrayList();
            employeesListCollection.addAll(employees);
            employeesList.setItems(employeesListCollection);
            generateEmployeesBtn.setDisable(true);
            selectAllEmployess.setDisable(false);
            generatePDFBtn.setDisable(false);
        }
    }

    public void selectAllEmployees(MouseEvent mouseEvent) {
        for(Employee employee : employeesList.getItems() ){
            employee.getSelected().setSelected(selectAllEmployess.isSelected());
        }
    }

    @FXML
    public void generatePDF(javafx.scene.input.MouseEvent mouseEvent) throws TransformerException, IOException, FOPException {

        ObservableList<Employee> employeesSelectedCollection = FXCollections.observableArrayList();
        for(Employee employee : employees){
            if( employee.getSelected().isSelected() ){
                employeesSelectedCollection.add(employee);
            }
        }
        if( employeesSelectedCollection.isEmpty() ){
            AlertBox alertBox = new AlertBox();
            alertBox.display(Constants.EMPLOYEE_ERROR_TITLE, Constants.EMPLOYEE_ERROR_MESSAGE);
        } else {

            for(Employee employee : employeesSelectedCollection) {
                PDF employeePDF = new PDF(employee);
                employeePDF.convertToPDF();
            }

            AlertBox alertBox = new AlertBox();
            if( employeesSelectedCollection.size() == 1 ) {
                alertBox.display(Constants.EMPLOYEE_SUCCESS_TITLE, Constants.EMPLOYEE_SUCCESS_MESSAGE);
            } else {
                alertBox.display(Constants.EMPLOYEES_SUCCESS_TITLE, Constants.EMPLOYEES_SUCCESS_MESSAGE);
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateEmployeesBtn.setDisable(true);
        generatePDFBtn.setDisable(true);
        selectAllEmployess.setDisable(true);

        employeesList.setPlaceholder(new Label(Constants.TABLE_CONTENT_TEXT));

        employeeId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<Employee, SimpleStringProperty>("name"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<Employee, SimpleStringProperty>("surname"));
        employeeBase.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("base"));
        employeeCoefficient.setCellValueFactory(new PropertyValueFactory<Employee, Double>("coefficient"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        employeeSelected.setCellValueFactory(new PropertyValueFactory<Employee, CheckBox>("selected"));
    }
}