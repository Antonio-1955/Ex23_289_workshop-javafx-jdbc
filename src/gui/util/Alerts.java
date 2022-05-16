/*Projeto: Ex23_289_workshop-javafx-jdbc
 * Gera um Alerta
 */
package gui.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

    public static void showAlerts(String title, String header, String content, AlertType type) {

        //Instancia o objeto 'alert' tipo classe Alert.
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
        //==============================================================================

        //
    public static Optional<ButtonType> showConfirmation(String title, String content) {

        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        return alert.showAndWait();
        }

    }


