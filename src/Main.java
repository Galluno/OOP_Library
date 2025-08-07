public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Avvio Sistema di Gestione Biblioteca con GUI Moderna");
        System.out.println("📚 Caricamento interfaccia grafica...");
        
        try {
            // Launch the modern web-based GUI
            BibliotecaGUI gui = new BibliotecaGUI();
            gui.start();
            
            // Keep the application running
            System.out.println("✅ Sistema avviato con successo!");
            System.out.println("🌐 Interfaccia disponibile su http://localhost:8080");
            System.out.println("🔄 Premi Ctrl+C per terminare l'applicazione");
            
            // Add shutdown hook for clean exit
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n🛑 Chiusura del sistema...");
                gui.stop();
                System.out.println("✅ Sistema chiuso correttamente. Arrivederci!");
            }));
            
            // Keep main thread alive
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("❌ Errore durante l'avvio del sistema: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}