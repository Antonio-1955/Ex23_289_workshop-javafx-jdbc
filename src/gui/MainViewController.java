/*
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
    
    //Atributos dos controles de tela
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;
//==============================================================================    
    
    //Métodos para tratar os itens de menu
    @FXML
    public void onMenuItemSellerAction(){
        System.out.println("Clicou em 'onMenuItemSellerAction'");
    }
    @FXML
    public void onMenuItemDepartmentAction(){
        System.out.println("Clicou em 'onMenuItemDepartmentAction'");
    }
    @FXML
    public void onMenuItemAboutAction(){
        System.out.println("Clicou em 'onMenuItemAboutAction'");
    }
    
    
    
//==============================================================================
    
    //Método 'initialize()'
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
        // TODO
    }    
    
}
