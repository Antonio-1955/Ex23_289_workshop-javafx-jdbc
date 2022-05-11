/*Projeto: Ex21_261_demo-dao-JDBC7
 *Esta é uma exceção personalizada de 'integridade referencial'.
 */
package db;

public class DbIntegrityException extends RuntimeException {
    
    //A declaração abaixo foi comentada porque não foi solicitada pelo RuntimeException
    private static final long serialVersionUID = 1L; 
    
    //Construtor
    public DbIntegrityException(String msg){
        super(msg);
    }
    
}
