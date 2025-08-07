import java.util.List;
import java.util.Map;

public class TestTodoFunctionality {
    public static void main(String[] args) {
        System.out.println("=== Testing TODO Functionality Implementation ===");
        
        try {
            // Test 1: User suspension functionality
            System.out.println("\n1. Testing User Suspension:");
            LibroFactory factory = new LibroFactory();
            Utente utente = factory.createUtente("Mario Rossi", "RSSMRA80A01H501X");
            System.out.println("User suspended status before: " + utente.isSospeso());
            
            utente.sospendiUtente();
            System.out.println("User suspended status after suspension: " + utente.isSospeso());
            System.out.println("Suspension date: " + utente.getDataSospensione());
            
            // Test 2: Library book type verification
            System.out.println("\n2. Testing Library Book Type Verification:");
            Libreria libreriaGenerale = new Libreria();
            Libreria libreriaNarrativa = new Libreria(TipoLibro.Narrativo);
            
            Libro libroNarrativo = factory.createLibro("1984", "978-88-04-12345-6", "George Orwell", TipoLibro.Narrativo);
            Libro libroThriller = factory.createLibro("Il Silenzio degli Innocenti", "978-88-04-54321-9", "Thomas Harris", TipoLibro.Thriller);
            
            // Should work - general library accepts all
            libreriaGenerale.addLibro(libroNarrativo);
            libreriaGenerale.addLibro(libroThriller);
            System.out.println("✓ General library accepted both books");
            
            // Should work - narrative library accepts narrative book
            libreriaNarrativa.addLibro(libroNarrativo);
            System.out.println("✓ Narrative library accepted narrative book");
            
            // Should fail - narrative library rejects thriller book
            try {
                libreriaNarrativa.addLibro(libroThriller);
                System.out.println("✗ This should not have worked!");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Narrative library correctly rejected thriller book: " + e.getMessage());
            }
            
            // Test 3: Bibliotecario builder pattern
            System.out.println("\n3. Testing Bibliotecario Builder Pattern:");
            Bibliotecario bibliotecario1 = new Bibliotecario();
            Bibliotecario bibliotecario2 = Bibliotecario.builder()
                    .setCodBibliotecario("BIB001")
                    .build();
            
            System.out.println("✓ Created bibliotecario without code: " + (bibliotecario1.getCodBibliotecario() == null ? "null" : bibliotecario1.getCodBibliotecario()));
            System.out.println("✓ Created bibliotecario with code: " + bibliotecario2.getCodBibliotecario());
            
            // Test 4: Report generation functionality
            System.out.println("\n4. Testing Report Generation:");
            
            // Create fresh users for testing (previous user was suspended)
            Utente utenteTest1 = factory.createUtente("Paolo Verdi", "VRDPLA75M10H501A");
            Utente utenteTest2 = factory.createUtente("Anna Neri", "NRANNA80D15H501B");
            
            // Set up some loans for testing
            libroNarrativo.setDisponibilità(2);
            libroThriller.setDisponibilità(1);
            
            bibliotecario2.registraPrestiti(utenteTest1, libroNarrativo);
            bibliotecario2.registraPrestiti(utenteTest2, libroThriller);
            
            Map<Libro, Utente> reportLibri = bibliotecario2.generaReportLibriInPrestito();
            List<Utente> reportUtenti = bibliotecario2.generaReportUtentiConPrestiti();
            List<Libro> libriInRitardo = bibliotecario2.verificaLibriInRitardo();
            
            System.out.println("Books on loan: " + reportLibri.size());
            System.out.println("Users with loans: " + reportUtenti.size());
            System.out.println("Overdue books: " + libriInRitardo.size());
            
            for (Map.Entry<Libro, Utente> entry : reportLibri.entrySet()) {
                System.out.println("  - " + entry.getKey().getTitolo() + " loaned to " + entry.getValue().getNome());
            }
            
            System.out.println("\n=== All TODO functionality tests completed successfully! ===");
            
        } catch (Exception e) {
            System.out.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}