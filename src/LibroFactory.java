public class LibroFactory {


    /*
     * Mission: Crea un nuovo libro
     * @param titolo il titolo del libro
     * @param ISBN   il codice ISBN del libro
     * @param autore il nome dell'autore del libro
     * @param tipo   il tipo di libro
     */
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
