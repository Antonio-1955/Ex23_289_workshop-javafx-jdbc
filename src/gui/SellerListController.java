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
import java.util.Date;
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
import model.services.DepartmentService;
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
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Seller> obsList;
//==============================================================================  

    //M??todo para o evento do bot??o
    @FXML
    public void onBtNewAction(ActionEvent event) {

        Stage parentStage = Utils.currentStage(event);
        //Instancia um Seller vazio
        Seller obj = new Seller();
        createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
    }
//============================================================================== 

    //M??todo para inje????o de depend??ncia
    public void setSellerService(SellerService service) {

        this.service = service;
    }
//==============================================================================    

    //M??todo initialize()
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeNodes();
    }
//==============================================================================
    
    //M??todo initializeNode()
    private void initializeNodes() {

        //Este ?? um padr??o do JavaFX para ele iniciar o comportamento das colunas.
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
        //Faz com que a TableViewSeller fique do tamanho da janela principal
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }
//==============================================================================    

    //M??todo respons??vel por acessar o service, carregar os departamentos e inseri-los na 'ObservableList'
    public void updateTableView() {

        if (service == null) {
            throw new IllegalStateException("Servi??o estava nullo.");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }
//==============================================================================

    //M??todo recebe como par??metro uma refer??ncia para o Stage da janela que criou a janela de di??logo.
    private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {

        try {
            //Carregar a tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            SellerFormController controller = loader.getController();
            controller.setSeller(obj);
            controller.setServices(new SellerService(), new DepartmentService());
            controller.loadAssociatedObjects();
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            /*Quando vai se carregar uma janela modal na frente de uma 
             *janela existente ?? preciso instanciar um novo stage (palco), o que
             *resulta em um state ?? frente de outro*/
            Stage dialogStage = new Stage();

            //Configura o t??tulo da janela
            dialogStage.setTitle("Enter Seller data");
            //Configura uma nova cena para o novo stage
            dialogStage.setScene(new Scene(pane));
            //Configura para que a janela n??o possa ser redimensionada.
            dialogStage.setResizable(false);
            //Configura o stage pai da janela
            dialogStage.initOwner(parentStage);
            //Configura o comportamento da janela se vai ser do tipo modal ou n??o.
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //Configura a janela para exibir e esperar.
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlerts("IO Exception", "ERRO AO CARREGAR A TELA", e.getMessage(), AlertType.ERROR);

        }

    }
//==============================================================================    

    @Override
    public void onDataChanged() {

        updateTableView();
    }
//==============================================================================

    //M??todo p/ criar o bot??o de edi????o em cada linha da tabela
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

    //M??todo p/ criar o bot??o de REMOVE em cada linha da tabela
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

    //M??todo para remover um 'Seller'.
    private void removeEntity(Seller obj) {

        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Tem certeza que quer deletar?");

        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Servi??o estava vazio.");
            }

            try {
                service.remove(obj);
                updateTableView();

            } catch (DbIntegrityException e) {

                Alerts.showAlerts("Erro ao remover o Departamento", null, e.getMessage(), AlertType.ERROR);
            }

        }
    }
}
