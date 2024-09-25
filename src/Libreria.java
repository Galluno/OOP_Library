import javax.swing.*;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Libreria implements Observable {

    private Set<Libro> Libri = new HashSet<>();
    public Libreria() {
        this.Libri = new HashSet<>();

    }


    public Libreria (Set<Libro> Libri, TipoLibro tipoLibro) {
        if(Libri.isEmpty()) {
            throw new IllegalArgumentException("La libreria non può essere vuota");
        }
        this.Libri = Libri;
    }

    public void addLibro(Libro libro) {
        this.Libri.add(libro);
    }


    public Libro getLibro(String ISBNtoSearch){
        return this.Libri.stream()
                .filter(libro -> libro.getISBN().equals(ISBNtoSearch))
                .toList()
                .getFirst();
    }

    @Override
    public void addObserver(Observer o) {
        o = new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        };
    }

    @Override
    public void notifyObservers() {

    }

    /*TODO: verificare che quando un libro viene aggiunto alla libreria sia del tipo giusto
        also: controllare come funziona observer
     */

}
