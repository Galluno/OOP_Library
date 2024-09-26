import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class Libro {

    String Titolo;
    String ISBN;
    String Autore;
    TipoLibro tipoLibro;
    @Setter
    private int disponibilità;

    public Libro(@NonNull String Titolo, @NonNull String ISBN, @NonNull String Autore) {
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
}
