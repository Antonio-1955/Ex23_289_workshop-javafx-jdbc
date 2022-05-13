/*Projeto: Ex23_289_workshop-javafx-jdbc
 * Retorna o stage atual (palco atual)
 */
package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
//==============================================================================    
    
    /* Método currentStage() que retorna um Stage e recebe como argumento 
     * um objeto do tipo ActionEvent que é o evento do botão.
     * Serve para acessar o Stage onde está o controle (botão) que recebeu o evento */
    //public static Stage currentStage(ActionEvent event){
    public static Stage currentStage(ActionEvent event){
        
        return (Stage)((Node) event.getSource()).getScene().getWindow();
    }
//==============================================================================

    //Método p/ converter string p/ inteiro.
    public static Integer tryParseToInt(String str){
        
        try {
            return Integer.parseInt(str);
        } 
        catch (NumberFormatException e) {
            return null;
        }
        
    }    
    
}
