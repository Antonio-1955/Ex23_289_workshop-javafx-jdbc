/*  Projeto: Ex23_289_workshop-javafx-jdbc 
 * Esta interface define uma operação que dispara um evento para atualizar a 
 * tabela qdo os dados mudarem, ou seja, qdo for adicionado, modificado ou 
 * deletado */
package gui.listeners;

public interface DataChangeListener {
    
    //Método
    void onDataChanged();
}
