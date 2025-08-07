import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

public class Utente {

    private String nome;
    private String CodFis;
    private Date data;
    private boolean utenteSospeso;
    private LocalDate dataSospensione;

    public Set<Prestito> libriInPrestito;

    public Utente(String nome, String CodFis) {
        if(CodFis.isEmpty()) throw new IllegalArgumentException("Il codice fiscale non può essere vuoto"); else if(CodFis.length()<14) throw new IllegalArgumentException("Codice fiscale non valido");

        this.nome = nome;
        this.CodFis = CodFis;
        this.libriInPrestito = new HashSet<>();
        this.utenteSospeso = false;
    }

    public void sospendiUtente() {
        this.utenteSospeso = true;
        this.dataSospensione = LocalDate.now().plusMonths(1);
    }


    public boolean isSospeso() {
        // Check if suspension period has expired
        if (utenteSospeso && dataSospensione != null && LocalDate.now().isAfter(dataSospensione)) {
            utenteSospeso = false;
            dataSospensione = null;
        }
        return utenteSospeso;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCodFis() {
        return CodFis;
    }

    public Date getData() {
        return data;
    }

    public Set<Prestito> getLibriInPrestito() {
        return libriInPrestito;
    }

    public LocalDate getDataSospensione() {
        return dataSospensione;
    }
}
