/*
 */
package gui;

import application.Main;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {

    //Atributos dos controles de tela
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;
//==============================================================================    

    //Método para tratar os itens de onMenuItemSellerAction()
    @FXML
    public void onMenuItemSellerAction() {
        loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
            controller.setSellerService(new SellerService());
            controller.updateTableView();});
    }
//==============================================================================    

     //Método para tratar os itens de onMenuItemDepartmentAction()
    @FXML
    public void onMenuItemDepartmentAction() {
        
        loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();});
    }
//==============================================================================    

    //Método para tratar os itens de onMenuItemAboutAction()
    @FXML
    public void onMenuItemAboutAction() {
        //System.out.println("Clicou em 'onMenuItemAboutAction'");
        loadView("/gui/About.fxml", x -> {});
    }
//==============================================================================

    //Método 'initialize()'
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // TODO
    }
//==============================================================================

    /* A palavra 'synchronized' abaixo garante que todo o processamento não seja 
     * interrrompido visto que a aplicação gráfica é 'Multithreading' (várias
     * threads executando ao mesmo tempo)*/
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {

        try {
            //Carregar a tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            //Variável 'newVBox' do tipo classe Vbox
            VBox newVBox = loader.load();

            /* Mostrar essa view dentro da janela principal é preciso pegar uma 
             * referência da cena*/
            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o primeiro elemento da view

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());
            
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException e) {
            Alerts.showAlerts("IO Exception", "Error loading view-1", e.getMessage(), AlertType.ERROR);
        }
    }
//==============================================================================

//    /* A palavra 'synchronized' abaixo garante que todo o processamento não seja 
//     * interrrompido visto que a aplicação gráfica é 'Multithreading' (várias
//     * threads executando ao mesmo tempo)*/
//    private synchronized void loadView2(String absoluteName) {
//
//        try {
//            //Carregar a tela
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            //Variável 'newVBox' do tipo classe Vbox
//            VBox newVBox = loader.load();
//
//            /* Mostrar essa view dentro da janela principal é preciso pegar uma 
//         * referência da cena*/
//            Scene mainScene = Main.getMainScene();
//            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o primeiro elemento da view
//
//            Node mainMenu = mainVBox.getChildren().get(0);
//            mainVBox.getChildren().clear();
//            mainVBox.getChildren().add(mainMenu);
//            mainVBox.getChildren().addAll(newVBox.getChildren());
//            
//            DepartmentListController controller = loader.getController();
//            controller.setDepartmentService(new DepartmentService());
//            controller.updateTableView();
//
//        } catch (IOException e) {
//            Alerts.showAlerts("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//        }
//    }
}
