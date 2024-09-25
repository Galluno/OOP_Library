import lombok.Getter;

public class Utente {

    @Getter public String nome;
    @Getter public String CodFis;

    public Utente(String nome, String CodFis) {
        if(CodFis.isEmpty()) throw new IllegalArgumentException("Il codice fiscale non può essere vuoto"); else if(CodFis.length()<14) throw new IllegalArgumentException("Codice fiscale non valido");

        this.nome = nome;
        this.CodFis = CodFis;
    }



}
