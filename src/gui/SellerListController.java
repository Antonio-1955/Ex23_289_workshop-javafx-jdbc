/*
 */
package gui;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {
//==============================================================================  

    //Atributo
    private SellerService service;
//==============================================================================    

    //Atributos dos controles de TableView
    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Seller> obsList;
//==============================================================================  

    //Método para o evento do botão
    @FXML
    public void onBtNewAction(ActionEvent event) {

        Stage parentStage = Utils.currentStage(event);
        //Instancia um Seller vazio
        Seller obj = new Seller();
        createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
    }
//============================================================================== 

    //Método para injeção de dependência
    public void setSellerService(SellerService service) {

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

        //Faz com que a TableViewSeller fique do tamanho da janela principal
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }
//==============================================================================    

    //Método responsável por acessar o service, carregar os departamentos e inseri-los na 'ObservableList'
    public void updateTableView() {

        if (service == null) {
            throw new IllegalStateException("Serviço estava nullo.");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }
//==============================================================================

    //Método recebe como parâmetro uma referência para o Stage da janela que criou a janela de diálogo.
    private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
//
//        try {
//            //Carregar a tela
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = loader.load();
//
//            SellerFormController controller = loader.getController();
//            controller.setSeller(obj);
//            controller.setSellerService(new SellerService());
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();
//
//            /*Quando vai se carregar uma janela modal na frente de uma 
//             *janela existente é preciso instanciar um novo stage (palco), o que
//             *resulta em um state à frente de outro*/
//            Stage dialogStage = new Stage();
//
//            //Configura o título da janela
//            dialogStage.setTitle("Enter Seller data");
//            //Configura uma nova cena para o novo stage
//            dialogStage.setScene(new Scene(pane));
//            //Configura para que a janela não possa ser redimensionada.
//            dialogStage.setResizable(false);
//            //Configura o stage pai da janela
//            dialogStage.initOwner(parentStage);
//            //Configura o comportamento da janela se vai ser do tipo modal ou não.
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            //Configura a janela para exibir e esperar.
//            dialogStage.showAndWait();
//
//        } catch (IOException e) {
//            Alerts.showAlerts("IO Exception", "ERRO AO CARREGAR A TELA", e.getMessage(), AlertType.ERROR);
//
//        }

    }
//==============================================================================    

    @Override
    public void onDataChanged() {

        updateTableView();
    }
//==============================================================================

    //Método p/ criar o botão de edição em cada linha da tabela
    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {

            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
            }
        });
    }
//==============================================================================

    //Método p/ criar o botão de REMOVE em cada linha da tabela
    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }

        });
    }

    //Método para remover um 'Seller'.
    private void removeEntity(Seller obj) {

        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Tem certeza que quer deletar?");
        
        if (result.get() == ButtonType.OK){
            if (service == null){
                throw new IllegalStateException("Serviço estava vazio.");
            }
            
            try {
                service.remove(obj);
                updateTableView();
                
            } 
            catch (DbIntegrityException e) {
                
                Alerts.showAlerts("Erro ao remover o Departamento", null, e.getMessage(), AlertType.ERROR);
            }
            
        }
    }
}
