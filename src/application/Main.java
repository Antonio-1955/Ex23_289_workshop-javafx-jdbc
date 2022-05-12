/*Projeto: Ex23_289_workshop-javafx-jdbc 
 */
package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class Main extends Application {
    
    //Atributo para guardar a referência
    private static Scene mainScene;
//=============================================================================    

      /* Código gerado automaticamente na criação da classe e substituído pelo
       * correspondente abaixo
       */
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/gui/MainView.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();
//    }
//==============================================================================    
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            //Parent parent = loader.load();
            ScrollPane scrollPane = loader.load();
            
            //Configura o Menubar para encaixar na altura e largura da tela.
            scrollPane.setFitToHeight(true); //(?)Não vi diferença
            scrollPane.setFitToWidth(true);
            
            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Sample JavaFX application");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//==============================================================================

    //Método para pegar a referência
    public static Scene getMainScene() {
        return mainScene;
    }
//=============================================================================    

    public static void main(String[] args) {
        launch(args);
    }

}
