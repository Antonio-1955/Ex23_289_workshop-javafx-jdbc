/*
 */
package gui;

import application.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable{
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
//==============================================================================  
    
    //Método para o evento do botão
    @FXML
    public void onBtNewAction(){
        System.out.println("CLICOU NO BOTÃO!");
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
    
    
    
}
