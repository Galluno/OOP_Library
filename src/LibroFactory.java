import java.time.temporal.UnsupportedTemporalTypeException;

public class LibroFactory {

    public Libro createLibro(String titolo, String ISBN, String autore, TipoLibro tipo){
        return new Libro(titolo, autore, ISBN, tipo);
    }

    public Libreria createLibreria(){
        return new Libreria();
    }

    public Utente createUtente(String nome, String CodiceFiscale){
        return new Utente(nome, CodiceFiscale);
    }

}
