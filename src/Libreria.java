import javax.swing.*;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Libreria implements Observable {

    private Set<Libro> Libri = new HashSet<>();
    private TipoLibro tipoLibreriaConsentito;
    private List<Observer> observers = new ArrayList<>();
    public Libreria() {
        this.Libri = new HashSet<>();
        this.observers = new ArrayList<>();
        this.tipoLibreriaConsentito = null; // Accept all types by default
    }

    public Libreria(TipoLibro tipoConsentito) {
        this.Libri = new HashSet<>();
        this.observers = new ArrayList<>();
        this.tipoLibreriaConsentito = tipoConsentito;
    }


    public Libreria (Set<Libro> Libri, TipoLibro tipoLibro) {
        if(Libri.isEmpty()) {
            throw new IllegalArgumentException("La libreria non può essere vuota");
        }
        this.Libri = Libri;
        this.tipoLibreriaConsentito = tipoLibro;
        this.observers = new ArrayList<>();
        
        // Verify all books are of the correct type
        for (Libro libro : Libri) {
            if (!verificaTipoLibro(libro)) {
                throw new IllegalArgumentException("Libro '" + libro.getTitolo() + "' non è del tipo consentito: " + tipoLibro);
            }
        }
    }

    public void addLibro(Libro libro) {
        if (verificaTipoLibro(libro)) {
            this.Libri.add(libro);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Il libro '" + libro.getTitolo() + "' non è del tipo consentito per questa libreria: " + tipoLibreriaConsentito);
        }
    }

    private boolean verificaTipoLibro(Libro libro) {
        // If no specific type is set, accept all books
        if (tipoLibreriaConsentito == null) {
            return true;
        }
        // If book has no type set, reject it
        if (libro.getTipoLibro() == null) {
            return false;
        }
        // Check if book type matches library type
        return libro.getTipoLibro().equals(tipoLibreriaConsentito);
    }


    public Libro getLibro(String ISBNtoSearch){
        return this.Libri.stream()
                .filter(libro -> libro.getISBN().equals(ISBNtoSearch))
                .toList()
                .get(0); // Fixed: use get(0) instead of getFirst() for compatibility
    }

    @Override
    public void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(null, this);
        }
    }

    // Getters
    public Set<Libro> getLibri() {
        return new HashSet<>(Libri); // Return copy for encapsulation
    }

    public TipoLibro getTipoLibreriaConsentito() {
        return tipoLibreriaConsentito;
    }

}
