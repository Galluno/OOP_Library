import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bibliotecario {

    private String codBibliotecario;
    private final Prestito prestito;
    private final LibroFactory librofactory = new LibroFactory();

    public Bibliotecario()
    {
        this.prestito = new Prestito();
    }

    public Bibliotecario(String codBibliotecario) {
        this.codBibliotecario = codBibliotecario;
        this.prestito = new Prestito();
    }


    /**
     * Registra un prestito per l'utente specificato.
     * Lancia un'eccezione IllegalUtenteException se l'utente è sospeso.
     * Lancia un'eccezione IllegalLibroAmount se il libro non è disponibile.
     *
     * @param utente l'utente per il quale si vuole registrare il prestito
     * @param libro  il libro che si vuole prestare
     */
    public void registraPrestiti(Utente utente, Libro libro) {
        if (!utente.isSospeso()) {
            if (checkDisponibilità(libro)) {
                libro.uscitaLibro(libro);
                prestito.addPrestito(utente, libro);
            } else throw new IllegalLibroAmount("Libro non disponibile");
        } else throw new IllegalUtenteException("L'utente è sospeso");
    }

    /**
     * Genera un report sui libri attualmente in prestito
     * @return una mappa con i libri come chiave e l'utente che li ha in prestito come valore
     */
    public Map<Libro, Utente> generaReportLibriInPrestito() {
        return prestito.getPrestiti();
    }

    /**
     * Genera un report sugli utenti che hanno libri in prestito
     * @return una lista di utenti che hanno almeno un libro in prestito
     */
    public List<Utente> generaReportUtentiConPrestiti() {
        return prestito.getPrestiti().values().stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Verifica quali libri sono in ritardo di restituzione
     * @return una lista di libri in ritardo
     */
    public List<Libro> verificaLibriInRitardo() {
        return prestito.getPrestiti().entrySet().stream()
                .filter(entry -> {
                    // Check if this specific loan is overdue by checking all user's loans
                    Utente utente = entry.getValue();
                    return utente.getLibriInPrestito().stream()
                            .anyMatch(prestitoUtente -> prestitoUtente.isScaduto());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Verifica se un utente ha libri in ritardo
     * @param utente l'utente da verificare
     * @return true se l'utente ha almeno un libro in ritardo
     */
    public boolean utenteHaLibriInRitardo(Utente utente) {
        return utente.getLibriInPrestito().stream()
                .anyMatch(prestito -> prestito.isScaduto());
    }


    /**
     * Registra la restituzione di un libro
     *
     * @param libro il libro da restituire
     */
    public void registraRestituzione(Libro libro) {
        libro.addLibro(libro);
    }


    public boolean checkDisponibilità(Libro libro) {
        return libro.getDisponibilità() > 0;
    }

    // Getters
    public String getCodBibliotecario() {
        return codBibliotecario;
    }

    public LibroFactory getLibrofactory() {
        return librofactory;
    }

    /**
     * Builder pattern for Bibliotecario
     */
    public static class Builder {
        private String codBibliotecario;

        public Builder setCodBibliotecario(String codBibliotecario) {
            this.codBibliotecario = codBibliotecario;
            return this;
        }

        public Bibliotecario build() {
            if (codBibliotecario != null) {
                return new Bibliotecario(codBibliotecario);
            } else {
                return new Bibliotecario();
            }
        }
    }

    /**
     * Static method to create a new builder
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
