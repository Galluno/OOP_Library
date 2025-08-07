import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

public class Prestito implements Observable {
    public LocalDate data;
    public long inizioPrestito;
    private long finePrestito;
    private Map<Libro, Utente> prestiti = new HashMap<>(); // Fixed: use HashMap implementation
        /*
        PRESTITO: rappresenta tutti i prestiti fatti da utenti della biblioteca.
        @param libro il libro in prestito
        @param utente l'utente che ha il prestito attivo
        @param data il giorno in cui è cominciato il prestito
         */

    /**
     * add an observer to the list of observers
     *
     * @param o osservatore
     */
    @Override
    public void addObserver(Observer o) {

    }

    /**
     * notify all observers
     */
    @Override
    public void notifyObservers() {

    }

    /**
     * Add a new prestito (loan) between a user and a book
     * @param utente the user taking the loan
     * @param libro the book being loaned
     */
    public void addPrestito(Utente utente, Libro libro) {
        prestiti.put(libro, utente);
        utente.getLibriInPrestito().add(this);
        this.data = LocalDate.now();
        this.inizioPrestito = LocalDate.now().toEpochDay();
        this.finePrestito = LocalDate.now().plusDays(30).toEpochDay();
    }

    /*
    con observer continua a controllare che il prestito sia ancora in tempo per essere restituito
    @param utente utente da verificare
     */
    public void controlloPrestito(Utente utente) {
        notifyObservers();
        if (LocalDate.now().toEpochDay() - finePrestito == 0) {
            utente.sospendiUtente();
        }
    }

    // Getters for accessing prestiti data
    public Map<Libro, Utente> getPrestiti() {
        return new HashMap<>(prestiti); // Return copy for encapsulation
    }

    public LocalDate getData() {
        return data;
    }

    public long getFinePrestito() {
        return finePrestito;
    }

    public boolean isScaduto() {
        return LocalDate.now().toEpochDay() > finePrestito;
    }
}
