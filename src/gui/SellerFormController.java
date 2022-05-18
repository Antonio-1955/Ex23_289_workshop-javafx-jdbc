/* Projeto: Ex23_289_workshop-javafx-jdbc 
 * Esta classe controla os eventos do formulário Seller*/
package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {
//==============================================================================
    
    //Atributo para criar uma dependência com a classe Seller do pacote model.entities.
    private Seller entity;
    private SellerService service;
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
    
    //Método Set para que o controlador tenha uma instância do 'Seller'.
    public void setSeller(Seller entity){
        this.entity = entity;
    }
//============================================================================== 
    
    //Método Set para...
    public void setSellerService(SellerService service){
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
        catch (ValidationException e){
            setErrorMessages(e.getErrors());
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

    private Seller getFormData() {
        
        Seller obj = new Seller();
        
        ValidationException exception = new ValidationException("Erro de validação!");
        
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        
        if (txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "O campo não pode ser vazio!");
        }
        
        obj.setName(txtName.getText());
        
        if (exception.getErrors().size() > 0){
            throw exception;
        }
        
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
    
    //Método responsável por pegar os dados do 'Seller' e popular as caixas de texto do formulário.
    public void updateFormData(){
        
        //Programação defensiva p/ testar se o 'entity' é nullo.
        if (entity == null) {
            throw new IllegalStateException("Entity estava nulo.");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
//==============================================================================
    
    private void setErrorMessages(Map <String, String> errors){
        
        Set <String> fields = errors.keySet();
        
        if (fields.contains("name")){
            labelErrorName.setText(errors.get("name"));
        }
    }
    
}
