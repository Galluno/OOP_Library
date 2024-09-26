import java.time.LocalDate;
import java.util.Map;
import java.util.Observer;

public class Prestito implements Observable {
    public LocalDate data;
    public long inizioPrestito;
    private long finePrestito;
    private Map<Libro, Utente> prestiti = new Map<Libro, Utente>(); //TODO: implementare la coppia Libro-Utente per il prestito
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
}
