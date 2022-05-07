/*Projeto: Ex23_289_workshop-javafx-jdbc
 * 
 */
package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
    
    //Método p/ verificar a digitação apenas de números inteiros
    public static void setTextFieldInteger (TextField txt){
        
        //Chama o método 'textProperty()' da classe TextField.
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                txt.setText(oldValue);
            }
        });
    }
//==============================================================================

     //Método p/ verificar a qtde máxima de números a digitar no campo
    public static void setTextFieldMaxLength (TextField txt, int max) {
        
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max) {
            txt.setText(oldValue);
        }
        });
    }
//==============================================================================

     //Método p/ verificar a digitação apenas de números doubles
    public static void setTextFieldDouble (TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")){
                txt.setText(oldValue);
            }
        });
    }
    
}
