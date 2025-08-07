public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema di Gestione Biblioteca - Test");
        
        // Test the basic functionality
        try {
            // Create a library factory
            LibroFactory factory = new LibroFactory();
            
            // Create a user
            Utente utente = factory.createUtente("Mario Rossi", "RSSMRA80A01H501X");
            System.out.println("Utente creato: " + utente.getNome() + " - CF: " + utente.getCodFis());
            
            // Create a book
            Libro libro = factory.createLibro("Il nome della rosa", "978-88-04-65432-1", "Umberto Eco", TipoLibro.Narrativo);
            libro.setDisponibilità(3);
            System.out.println("Libro creato: " + libro.getTitolo() + " di " + libro.getAutore());
            System.out.println("Disponibilità: " + libro.getDisponibilità());
            
            // Create a library
            Libreria libreria = factory.createLibreria();
            libreria.addLibro(libro);
            System.out.println("Libro aggiunto alla libreria");
            
            // Test finding a book
            Libro trovatoLibro = libreria.getLibro("978-88-04-65432-1");
            if (trovatoLibro != null) {
                System.out.println("Libro trovato: " + trovatoLibro.getTitolo());
            }
            
            // Create a librarian and test loan
            Bibliotecario bibliotecario = new Bibliotecario();
            
            if (bibliotecario.checkDisponibilità(libro)) {
                bibliotecario.registraPrestiti(utente, libro);
                System.out.println("Prestito registrato con successo");
                System.out.println("Nuova disponibilità: " + libro.getDisponibilità());
            }
            
            System.out.println("Test completato con successo!");
            
        } catch (Exception e) {
            System.out.println("Errore durante il test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}