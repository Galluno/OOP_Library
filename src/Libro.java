public class Libro {

    private String Titolo;
    private String ISBN;
    private String Autore;
    private TipoLibro tipoLibro;
    private int disponibilità;

    public Libro(String Titolo, String ISBN, String Autore) {
        if(Titolo.isEmpty() || ISBN.isEmpty() || Autore.isEmpty()) {
            throw new IllegalArgumentException("Il campo titolo non valido");
        }
        this.Titolo = Titolo;
        this.ISBN = ISBN;
        this.Autore = Autore;
    }

    public Libro(String Titolo, String ISBN, String Autore, TipoLibro tipoLibro) {
        if(checkTipo(tipoLibro)) {
            this.Titolo = Titolo;
            this.ISBN = ISBN;
            this.Autore = Autore;
            this.tipoLibro = tipoLibro;
        }
        else{
            throw new IllegalArgumentException("Il campo titolo non valido");
        }
    }


    /*
    Mission: increase the Libro libro object by one
     */
    public void addLibro(Libro libro) {
        libro.disponibilità++;
    }

    public void uscitaLibro(Libro libro) {
        libro.disponibilità--;
    }


    private boolean checkTipo(TipoLibro tipo) {
        for( TipoLibro t : TipoLibro.values()) {
            if (t.equals(tipo)) {
                return true;
            }
        }
        return false;
    }

    // Getters
    public String getTitolo() {
        return Titolo;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAutore() {
        return Autore;
    }

    public TipoLibro getTipoLibro() {
        return tipoLibro;
    }

    public int getDisponibilità() {
        return disponibilità;
    }

    // Setters
    public void setDisponibilità(int disponibilità) {
        this.disponibilità = disponibilità;
    }

    public void setTipoLibro(TipoLibro tipoLibro) {
        this.tipoLibro = tipoLibro;
    }
}
