import lombok.Getter;
import lombok.NonNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Utente {

    @Getter public String nome;
    @Getter public String CodFis;
    private Date data;
    private boolean utenteSospeso;

    public Set<Prestito> libriInPrestito;

    public Utente(String nome, @NonNull String CodFis) {
        if(CodFis.isEmpty()) throw new IllegalArgumentException("Il codice fiscale non può essere vuoto"); else if(CodFis.length()<14) throw new IllegalArgumentException("Codice fiscale non valido");

        this.nome = nome;
        this.CodFis = CodFis;
        this.libriInPrestito = new HashSet<>();
        this.utenteSospeso = false;
    }

    public void sospendiUtente() {
        //TODO
    }


    public boolean isSospeso() {
        return utenteSospeso;
    }


}
