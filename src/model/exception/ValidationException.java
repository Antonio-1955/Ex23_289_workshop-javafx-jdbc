/*Projeto: Ex23_289_workshop-javafx-jdbc 
 */
package model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
//==============================================================================    
    
    //Este número serial padrão não é exigido pelo NetBeans 12.5. 
    private static final long serialVersionUID = 1L;
//==============================================================================    
    
    /* Instancia uma variável 'errors' do tipo 'Map' (coleção chave, valor), para 
     * guardar cada possível erro dos campos do formulário*/
    private Map<String, String> errors = new HashMap<>();
//==============================================================================    
    
    //Método para forçar a instanciação da exceção com string.
    public ValidationException(String msg){
        super(msg);
    }
//============================================================================== 
    
    //Método 'getErrors()' para pegar os erros
    public Map<String, String> getErrors(){
        return errors;
    }
//==============================================================================

    //Método para permitir adicionar um elemento na coleção 'errors'.
    public void addError(String fieldName, String errorMessage){
        errors.put(fieldName, errorMessage);
    }
    
}
