/* Projeto: Ex23_289_workshop-javafx-jdbc 
 * Esta classe controla os eventos do formulário Seller*/
//Parei vídeo na posição 7:57 - setErrorMessages() 
package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.services.SellerService;

import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.Department;
import model.services.DepartmentService;

public class SellerFormController implements Initializable {
//==============================================================================
    
    //Atributos para criar uma dependência com a classe Seller do pacote model.entities.
    private Seller entity;
    private SellerService service;
//==============================================================================

    //Atributo para criar uma dependência com a classe Department do pacote model.entities.
    private DepartmentService departmentService;
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
    private TextField txtEmail;
    
    @FXML
    private DatePicker dpBirthDate;
    
    @FXML
    private TextField txtBaseSalary;
    
    @FXML
    //Atributo 'ComboBox' cujos objetos serão do tipo <Department>.
    private ComboBox<Department> comboBoxDepartment;
    
    @FXML
    private Label labelErrorName;
    
    @FXML
    private Label labelErrorEmail;
    
    @FXML
    private Label labelErrorBirthDate;
    
    @FXML
    private Label labelErrorBaseSalary;
    
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    
    //@FXML
    private ObservableList<Department> obsList;
//==============================================================================  
    
    //Método Set para que o controlador tenha uma instância do 'Seller'.
    public void setSeller(Seller entity){
        this.entity = entity;
    }
//============================================================================== 
    
    //Método Set para injetar o SellerService.
    public void setServices(SellerService service, DepartmentService departmentService){
        this.service = service;
        this.departmentService = departmentService;
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
        
        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
            exception.addError("email", "O campo não pode ser vazio!");
        }
        obj.setEmail(txtEmail.getText());
        
        if (dpBirthDate.getValue() == null){
            exception.addError("birthDate", "O campo não pode ser vazio!");
        }
        else {
        Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
        obj.setBirthDate(Date.from(instant));
        }
        
        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")){
             exception.addError("baseSalary", "O campo não pode ser vazio!");
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
        
        obj.setDepartent(comboBoxDepartment.getValue());
        
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
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
        
        initializeComboBoxDepartment();
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
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        //dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        
        if (entity.getBirthDate() != null){
            dpBirthDate.setValue(LocalDateTime.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        }
        if (entity.getDepartent() == null){
           comboBoxDepartment.getSelectionModel().selectFirst();
        }
        else {
        comboBoxDepartment.setValue(entity.getDepartent());
        }
    }
//==============================================================================
    
    //Método
    public void loadAssociatedObjects(){
        if (departmentService == null){
            throw new IllegalStateException("DepartmentService estava null.");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }
//==============================================================================
    
    private void setErrorMessages(Map <String, String> errors){
        Set <String> fields = errors.keySet();
        
        labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
        labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
        labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));
        labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));
    }
//==============================================================================

    private void initializeComboBoxDepartment(){
        
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>(){
            @Override
            protected void updateItem(Department item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
