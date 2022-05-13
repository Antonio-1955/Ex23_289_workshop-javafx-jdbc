/* Projeto: Ex23_289_workshop-javafx-jdbc 
 * Esta classe controla os eventos do formulário Department*/
package gui;

import gui.util.Constraints;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {
//==============================================================================
    
    //Atributo para criar uma dependência com a classe Department do pacote model.entities.
    private Department entity;
//============================================================================== 
    
    //Atributos dos controles
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private Label labelErrorName;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
//==============================================================================  
    
    //Método Set para que o controlador tenha uma instância do 'Departmen'.
    public void setDepartment(Department entity){
        this.entity = entity;
    }
//==============================================================================  
    
    //Métodos para tratar os eventos dos botões.
    public void onBtSaveAction(){
        System.out.println("Você clicou no botão 'SAVE'.");
    }
    
    public void onBtCancelAction(){
        System.out.println("Você clicou no botão 'CANCEL'.");
    }
//==============================================================================    

    //Método para inicializar as constraints dos campos de texto.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }
//============================================================================== 
    
    //Método para impor limitações aos campos de texto (constraints).
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }
//============================================================================== 
    
    //Método responsável por pegar os dados do 'Department' e popular as caixas de texto do formulário.
    public void updateFormData(){
        
        //Programação defensiva p/ testar se o 'entity' é nullo.
        if (entity == null) {
            throw new IllegalStateException("Entity estava nulo.");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
//==============================================================================    
    
}
