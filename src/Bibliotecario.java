import lombok.Getter;

public class Bibliotecario {

    @Getter
    String codBibliotecario;
    private final Prestito prestito;
    private final @Getter LibroFactory librofactory = new LibroFactory();

    public Bibliotecario()
    {
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
            if (!checkDisponibilità(libro)) {
                libro.uscitaLibro(libro);
                new Prestito(libro, utente);
            } else throw new IllegalLibroAmount("Libro non disponibile");
        } else throw new IllegalUtenteException("L'utente è sospeso");
    }

    /*TODO: builder and all methods
            methods: registrazione prestiti
                     verifica disponibilità libro
                     registrare restituzione libro
                     generare report su libri in prestito
                     generare report su utenti che hanno libri in prestito
                     verificare quali libri sono in ritardo di restituzione
                 */


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



}
