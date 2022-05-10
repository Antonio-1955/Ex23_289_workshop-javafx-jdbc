/*
 */
package gui;

import application.Main;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
//==============================================================================  

    private DepartmentService service;
//==============================================================================    

    //Atributos dos controles de TableView
    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;
//==============================================================================  

    //Método para o evento do botão
    @FXML
    public void onBtNewAction() {
        System.out.println("CLICOU NO BOTÃO!");
    }
//============================================================================== 

    //Método para injeção de dependência
    public void setDepartmentService(DepartmentService service) {

        this.service = service;
    }
//==============================================================================    

    //Método initialize()
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeNodes();
    }

    private void initializeNodes() {

        //Este é um padrão do JavaFX para ele iniciar o comportamento das colunas.
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Faz com que a TableViewDepartment fique do tamanho da janela principal
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }
//==============================================================================    

    //Método responsável por acessar o service, carregar os departamentos e inseri-los na 'ObservableList'
    public void updateTableView() {
        
        if (service == null) {
            throw new IllegalStateException("Serviço estava nullo.");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
    }

}
