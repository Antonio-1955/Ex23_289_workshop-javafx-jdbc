/* Projeto: Ex23_289_workshop-javafx-jdbc 
 * Esta classe controla os eventos do formulário Department*/
package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
//==============================================================================
    
    //Atributo para criar uma dependência com a classe Department do pacote model.entities.
    private Department entity;
    private DepartmentService service;
//============================================================================== 
    
    //Instacia uma lista que guarda os objetos interessados em receber o evento.
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
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
    
    //Método Set para que o controlador tenha uma instância do 'Department'.
    public void setDepartment(Department entity){
        this.entity = entity;
    }
//============================================================================== 
    
    //Método Set para...
    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }
//============================================================================== 
    
    //Método para inscrever (adicionar) um 'listener' na lista.
    public void subscribeDataChangeListener(DataChangeListener listener){
        
        dataChangeListeners.add(listener);
    }
//==============================================================================  
    
    //Métodos para tratar o evento do botão 'Save'.
    public void onBtSaveAction(ActionEvent event){
        
        if (entity == null){
            throw new IllegalStateException("Entidade estava nula.");
        }
        if (service == null){
            throw new IllegalStateException("Serviço estava nullo.");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            
            //Chama o método para notificar os 'listeners'.
            notifyDataChangeListeners();
            
            //Fechar a janela
            Utils.currentStage(event).close();
        } 
        catch (DbException e) {
            Alerts.showAlerts("Erro al salvar o objeto", null, e.getMessage(), AlertType.ERROR);
        }
        
    }
//============================================================================== 
    
    //Método para notificar os 'listeners'.
    private void notifyDataChangeListeners() {
        
        /* Para cada 'DataChangeListener' pertencente (:) à lista 
         * dataChangeListeners, chama o método 'onDataChanged' da 
         * interface 'DataChangeListener' */
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }
//==============================================================================    

    private Department getFormData() {
        
        Department obj = new Department();
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setName(txtName.getText());
        
        return obj;
    }
    
//==============================================================================    
    
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
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

    

    
}
