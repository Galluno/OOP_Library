import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.awt.Desktop;
import java.net.URI;

/**
 * Modern web-based GUI for the Library Management System
 * Provides a beautiful and user-friendly interface without using Java's standard GUI components
 */
public class BibliotecaGUI {
    private HttpServer server;
    private final LibroFactory factory;
    private final Libreria libreria;
    private final Bibliotecario bibliotecario;
    
    public BibliotecaGUI() {
        this.factory = new LibroFactory();
        this.libreria = factory.createLibreria();
        this.bibliotecario = new Bibliotecario();
        initializeTestData();
    }
    
    private void initializeTestData() {
        try {
            // Add some sample books
            Libro libro1 = factory.createLibro("Il nome della rosa", "978-88-04-65432-1", "Umberto Eco", TipoLibro.Narrativo);
            libro1.setDisponibilità(3);
            libreria.addLibro(libro1);
            
            Libro libro2 = factory.createLibro("Il Signore degli Anelli", "978-88-04-12345-6", "J.R.R. Tolkien", TipoLibro.Fantascienza);
            libro2.setDisponibilità(2);
            libreria.addLibro(libro2);
            
            Libro libro3 = factory.createLibro("Sherlock Holmes", "978-88-04-98765-4", "Arthur Conan Doyle", TipoLibro.Giallo);
            libro3.setDisponibilità(1);
            libreria.addLibro(libro3);
            
        } catch (Exception e) {
            System.err.println("Errore nell'inizializzazione dei dati di test: " + e.getMessage());
        }
    }
    
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Serve static files (HTML, CSS, JS)
        server.createContext("/", new StaticFileHandler());
        
        // API endpoints
        server.createContext("/api/books", new BooksHandler());
        server.createContext("/api/users", new UsersHandler());
        server.createContext("/api/loans", new LoansHandler());
        
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        
        System.out.println("🚀 Biblioteca GUI avviata su http://localhost:8080");
        System.out.println("💻 Aprendo il browser automaticamente...");
        
        // Try to open the browser automatically
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            }
        } catch (Exception e) {
            System.out.println("⚠️  Non è possibile aprire automaticamente il browser. Visita http://localhost:8080");
        }
    }
    
    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("📴 Server fermato");
        }
    }
    
    private class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            String content;
            String contentType;
            
            switch (path) {
                case "/index.html":
                    content = getIndexHtml();
                    contentType = "text/html";
                    break;
                case "/styles.css":
                    content = getStylesCss();
                    contentType = "text/css";
                    break;
                case "/app.js":
                    content = getAppJs();
                    contentType = "application/javascript";
                    break;
                default:
                    content = "404 - Pagina non trovata";
                    contentType = "text/html";
                    exchange.sendResponseHeaders(404, content.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(content.getBytes());
                    os.close();
                    return;
            }
            
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, content.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(content.getBytes());
            os.close();
        }
    }
    
    private class BooksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response;
            
            if ("GET".equals(method)) {
                // Return all books
                StringBuilder json = new StringBuilder();
                json.append("{\"books\":[");
                boolean first = true;
                for (Libro libro : libreria.getLibri()) {
                    if (!first) json.append(",");
                    json.append("{");
                    json.append("\"isbn\":\"").append(libro.getISBN()).append("\",");
                    json.append("\"titolo\":\"").append(libro.getTitolo()).append("\",");
                    json.append("\"autore\":\"").append(libro.getAutore()).append("\",");
                    json.append("\"tipo\":\"").append(libro.getTipoLibro()).append("\",");
                    json.append("\"disponibilita\":").append(libro.getDisponibilità());
                    json.append("}");
                    first = false;
                }
                json.append("]}");
                response = json.toString();
            } else {
                response = "{\"error\":\"Method not supported\"}";
            }
            
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    private class UsersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{\"users\":[]}"; // Placeholder
            
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    private class LoansHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{\"loans\":[]}"; // Placeholder
            
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    private String getIndexHtml() {
        return """
        <!DOCTYPE html>
        <html lang="it">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>📚 Sistema di Gestione Biblioteca</title>
            <link rel="stylesheet" href="/styles.css">
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        </head>
        <body>
            <div class="container">
                <header class="header">
                    <h1><i class="fas fa-book"></i> Sistema di Gestione Biblioteca</h1>
                    <p class="subtitle">Gestione moderna e intuitiva della tua biblioteca</p>
                </header>

                <nav class="nav-tabs">
                    <button class="tab-btn active" data-tab="dashboard">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </button>
                    <button class="tab-btn" data-tab="books">
                        <i class="fas fa-books"></i> Libri
                    </button>
                    <button class="tab-btn" data-tab="users">
                        <i class="fas fa-users"></i> Utenti
                    </button>
                    <button class="tab-btn" data-tab="loans">
                        <i class="fas fa-handshake"></i> Prestiti
                    </button>
                    <button class="tab-btn" data-tab="reports">
                        <i class="fas fa-chart-bar"></i> Report
                    </button>
                </nav>

                <main class="main-content">
                    <!-- Dashboard Tab -->
                    <div id="dashboard" class="tab-content active">
                        <div class="dashboard-grid">
                            <div class="stat-card">
                                <div class="stat-icon">
                                    <i class="fas fa-book"></i>
                                </div>
                                <div class="stat-info">
                                    <h3 id="total-books">-</h3>
                                    <p>Libri Totali</p>
                                </div>
                            </div>
                            <div class="stat-card">
                                <div class="stat-icon">
                                    <i class="fas fa-users"></i>
                                </div>
                                <div class="stat-info">
                                    <h3 id="total-users">0</h3>
                                    <p>Utenti Registrati</p>
                                </div>
                            </div>
                            <div class="stat-card">
                                <div class="stat-icon">
                                    <i class="fas fa-handshake"></i>
                                </div>
                                <div class="stat-info">
                                    <h3 id="active-loans">0</h3>
                                    <p>Prestiti Attivi</p>
                                </div>
                            </div>
                            <div class="stat-card">
                                <div class="stat-icon">
                                    <i class="fas fa-check-circle"></i>
                                </div>
                                <div class="stat-info">
                                    <h3 id="available-books">-</h3>
                                    <p>Libri Disponibili</p>
                                </div>
                            </div>
                        </div>
                        
                        <div class="welcome-section">
                            <h2>🎉 Benvenuto nel Sistema di Gestione Biblioteca</h2>
                            <p>Un'interfaccia moderna e intuitiva per gestire la tua biblioteca. Utilizza le schede qui sopra per navigare tra le diverse funzionalità.</p>
                            
                            <div class="quick-actions">
                                <h3>🚀 Azioni Rapide</h3>
                                <div class="action-buttons">
                                    <button class="action-btn" onclick="switchTab('books')">
                                        <i class="fas fa-plus"></i> Aggiungi Libro
                                    </button>
                                    <button class="action-btn" onclick="switchTab('users')">
                                        <i class="fas fa-user-plus"></i> Registra Utente
                                    </button>
                                    <button class="action-btn" onclick="switchTab('loans')">
                                        <i class="fas fa-handshake"></i> Nuovo Prestito
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Books Tab -->
                    <div id="books" class="tab-content">
                        <div class="section-header">
                            <h2><i class="fas fa-books"></i> Gestione Libri</h2>
                            <button class="btn primary" onclick="showAddBookForm()">
                                <i class="fas fa-plus"></i> Aggiungi Libro
                            </button>
                        </div>
                        
                        <div class="search-bar">
                            <input type="text" id="book-search" placeholder="🔍 Cerca libri per titolo, autore o ISBN...">
                        </div>
                        
                        <div id="books-grid" class="books-grid">
                            <!-- Libri verranno caricati dinamicamente -->
                        </div>
                    </div>

                    <!-- Users Tab -->
                    <div id="users" class="tab-content">
                        <div class="section-header">
                            <h2><i class="fas fa-users"></i> Gestione Utenti</h2>
                            <button class="btn primary">
                                <i class="fas fa-user-plus"></i> Registra Utente
                            </button>
                        </div>
                        <p class="coming-soon">🚧 Sezione in sviluppo - Gestione utenti</p>
                    </div>

                    <!-- Loans Tab -->
                    <div id="loans" class="tab-content">
                        <div class="section-header">
                            <h2><i class="fas fa-handshake"></i> Gestione Prestiti</h2>
                            <button class="btn primary">
                                <i class="fas fa-plus"></i> Nuovo Prestito
                            </button>
                        </div>
                        <p class="coming-soon">🚧 Sezione in sviluppo - Gestione prestiti</p>
                    </div>

                    <!-- Reports Tab -->
                    <div id="reports" class="tab-content">
                        <div class="section-header">
                            <h2><i class="fas fa-chart-bar"></i> Report e Statistiche</h2>
                        </div>
                        <p class="coming-soon">🚧 Sezione in sviluppo - Report avanzati</p>
                    </div>
                </main>
            </div>

            <!-- Modals -->
            <div id="add-book-modal" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3><i class="fas fa-book-plus"></i> Aggiungi Nuovo Libro</h3>
                        <button class="close-btn" onclick="closeModal('add-book-modal')">&times;</button>
                    </div>
                    <form id="add-book-form">
                        <div class="form-group">
                            <label>Titolo</label>
                            <input type="text" id="book-title" required>
                        </div>
                        <div class="form-group">
                            <label>Autore</label>
                            <input type="text" id="book-author" required>
                        </div>
                        <div class="form-group">
                            <label>ISBN</label>
                            <input type="text" id="book-isbn" required>
                        </div>
                        <div class="form-group">
                            <label>Tipo</label>
                            <select id="book-type" required>
                                <option value="">Seleziona tipo...</option>
                                <option value="Narrativo">Narrativo</option>
                                <option value="Fantascienza">Fantascienza</option>
                                <option value="Giallo">Giallo</option>
                                <option value="Thriller">Thriller</option>
                                <option value="Saggio">Saggio</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Disponibilità</label>
                            <input type="number" id="book-availability" min="1" value="1" required>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn secondary" onclick="closeModal('add-book-modal')">
                                Annulla
                            </button>
                            <button type="submit" class="btn primary">
                                <i class="fas fa-save"></i> Salva Libro
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <script src="/app.js"></script>
        </body>
        </html>
        """;
    }
    
    private String getStylesCss() {
        return """
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
            --accent-color: #e74c3c;
            --success-color: #27ae60;
            --warning-color: #f39c12;
            --bg-color: #ecf0f1;
            --card-bg: #ffffff;
            --text-color: #2c3e50;
            --text-muted: #7f8c8d;
            --border-color: #bdc3c7;
            --shadow: 0 2px 10px rgba(0,0,0,0.1);
            --shadow-hover: 0 4px 20px rgba(0,0,0,0.15);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: var(--text-color);
        }

        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            background: var(--card-bg);
            padding: 2rem;
            border-radius: 15px;
            box-shadow: var(--shadow);
            text-align: center;
            margin-bottom: 2rem;
        }

        .header h1 {
            color: var(--primary-color);
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }

        .header i {
            color: var(--secondary-color);
        }

        .subtitle {
            color: var(--text-muted);
            font-size: 1.1rem;
        }

        .nav-tabs {
            display: flex;
            background: var(--card-bg);
            border-radius: 15px;
            padding: 0.5rem;
            margin-bottom: 2rem;
            box-shadow: var(--shadow);
            overflow-x: auto;
        }

        .tab-btn {
            flex: 1;
            background: transparent;
            border: none;
            padding: 1rem 1.5rem;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
            color: var(--text-muted);
            font-weight: 500;
            white-space: nowrap;
        }

        .tab-btn:hover {
            background: rgba(52, 152, 219, 0.1);
            color: var(--secondary-color);
        }

        .tab-btn.active {
            background: var(--secondary-color);
            color: white;
            box-shadow: 0 2px 8px rgba(52, 152, 219, 0.3);
        }

        .tab-btn i {
            margin-right: 0.5rem;
        }

        .main-content {
            background: var(--card-bg);
            border-radius: 15px;
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .tab-content {
            display: none;
            padding: 2rem;
            animation: fadeIn 0.3s ease;
        }

        .tab-content.active {
            display: block;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 1.5rem;
            border-radius: 15px;
            display: flex;
            align-items: center;
            box-shadow: var(--shadow);
            transition: transform 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-hover);
        }

        .stat-icon {
            font-size: 2.5rem;
            margin-right: 1rem;
            opacity: 0.8;
        }

        .stat-info h3 {
            font-size: 2rem;
            margin-bottom: 0.25rem;
        }

        .stat-info p {
            opacity: 0.9;
            font-size: 0.9rem;
        }

        .welcome-section {
            text-align: center;
            padding: 2rem;
            background: linear-gradient(135deg, rgba(52, 152, 219, 0.1), rgba(155, 89, 182, 0.1));
            border-radius: 15px;
        }

        .welcome-section h2 {
            color: var(--primary-color);
            margin-bottom: 1rem;
        }

        .quick-actions {
            margin-top: 2rem;
        }

        .quick-actions h3 {
            color: var(--primary-color);
            margin-bottom: 1rem;
        }

        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .action-btn {
            background: var(--secondary-color);
            color: white;
            border: none;
            padding: 0.75rem 1.5rem;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .action-btn:hover {
            background: #2980b9;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(52, 152, 219, 0.3);
        }

        .section-header {
            display: flex;
            justify-content: between;
            align-items: center;
            margin-bottom: 2rem;
            flex-wrap: wrap;
            gap: 1rem;
        }

        .section-header h2 {
            color: var(--primary-color);
            flex: 1;
        }

        .btn {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .btn.primary {
            background: var(--secondary-color);
            color: white;
        }

        .btn.primary:hover {
            background: #2980b9;
            transform: translateY(-2px);
        }

        .btn.secondary {
            background: var(--text-muted);
            color: white;
        }

        .btn.secondary:hover {
            background: #6c7b7d;
        }

        .search-bar {
            margin-bottom: 2rem;
        }

        .search-bar input {
            width: 100%;
            padding: 1rem;
            border: 2px solid var(--border-color);
            border-radius: 10px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }

        .search-bar input:focus {
            outline: none;
            border-color: var(--secondary-color);
        }

        .books-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 1.5rem;
        }

        .book-card {
            background: var(--card-bg);
            border: 1px solid var(--border-color);
            border-radius: 15px;
            padding: 1.5rem;
            box-shadow: var(--shadow);
            transition: all 0.3s ease;
        }

        .book-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-hover);
        }

        .book-card h3 {
            color: var(--primary-color);
            margin-bottom: 0.5rem;
            font-size: 1.2rem;
        }

        .book-card .author {
            color: var(--text-muted);
            margin-bottom: 0.5rem;
        }

        .book-card .isbn {
            font-size: 0.9rem;
            color: var(--text-muted);
            margin-bottom: 1rem;
        }

        .book-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .book-type {
            background: var(--secondary-color);
            color: white;
            padding: 0.25rem 0.75rem;
            border-radius: 20px;
            font-size: 0.8rem;
        }

        .availability {
            font-weight: bold;
            color: var(--success-color);
        }

        .availability.low {
            color: var(--warning-color);
        }

        .availability.none {
            color: var(--accent-color);
        }

        .coming-soon {
            text-align: center;
            color: var(--text-muted);
            font-size: 1.1rem;
            padding: 3rem;
            background: rgba(52, 152, 219, 0.1);
            border-radius: 15px;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background: var(--card-bg);
            margin: 5% auto;
            border-radius: 15px;
            max-width: 500px;
            box-shadow: var(--shadow-hover);
            animation: modalSlideIn 0.3s ease;
        }

        @keyframes modalSlideIn {
            from { transform: translateY(-50px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }

        .modal-header {
            padding: 1.5rem;
            border-bottom: 1px solid var(--border-color);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-header h3 {
            color: var(--primary-color);
        }

        .close-btn {
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            color: var(--text-muted);
        }

        .close-btn:hover {
            color: var(--accent-color);
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: var(--primary-color);
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid var(--border-color);
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: var(--secondary-color);
        }

        .form-actions {
            display: flex;
            gap: 1rem;
            justify-content: flex-end;
            padding: 1.5rem;
            border-top: 1px solid var(--border-color);
        }

        form {
            padding: 0 1.5rem;
        }

        @media (max-width: 768px) {
            .container {
                padding: 10px;
            }
            
            .header h1 {
                font-size: 2rem;
            }
            
            .dashboard-grid {
                grid-template-columns: 1fr;
            }
            
            .nav-tabs {
                padding: 0.25rem;
            }
            
            .tab-btn {
                padding: 0.75rem;
                font-size: 0.9rem;
            }
            
            .section-header {
                flex-direction: column;
                align-items: stretch;
            }
            
            .action-buttons {
                flex-direction: column;
            }
            
            .books-grid {
                grid-template-columns: 1fr;
            }
        }
        """;
    }
    
    private String getAppJs() {
        return """
        // Global variables
        let books = [];
        let users = [];
        let loans = [];

        // Initialize the application
        document.addEventListener('DOMContentLoaded', function() {
            initializeTabs();
            loadData();
            setupEventListeners();
        });

        // Tab functionality
        function initializeTabs() {
            const tabButtons = document.querySelectorAll('.tab-btn');
            const tabContents = document.querySelectorAll('.tab-content');
            
            tabButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const targetTab = button.getAttribute('data-tab');
                    switchTab(targetTab);
                });
            });
        }

        function switchTab(tabName) {
            // Remove active class from all tabs and contents
            document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
            document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
            
            // Add active class to selected tab and content
            document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
            document.getElementById(tabName).classList.add('active');
            
            // Load specific data if needed
            if (tabName === 'books') {
                loadBooks();
            }
        }

        // Load data from API
        async function loadData() {
            await loadBooks();
            updateDashboard();
        }

        async function loadBooks() {
            try {
                const response = await fetch('/api/books');
                const data = await response.json();
                books = data.books || [];
                renderBooks();
                updateDashboard();
            } catch (error) {
                console.error('Errore nel caricamento dei libri:', error);
            }
        }

        // Render books in the grid
        function renderBooks() {
            const booksGrid = document.getElementById('books-grid');
            
            if (books.length === 0) {
                booksGrid.innerHTML = `
                    <div class="empty-state" style="grid-column: 1 / -1; text-align: center; padding: 3rem; color: var(--text-muted);">
                        <i class="fas fa-book" style="font-size: 3rem; margin-bottom: 1rem; opacity: 0.5;"></i>
                        <h3>Nessun libro trovato</h3>
                        <p>Aggiungi il primo libro alla tua biblioteca!</p>
                    </div>
                `;
                return;
            }
            
            booksGrid.innerHTML = books.map(book => `
                <div class="book-card" data-isbn="${book.isbn}">
                    <h3>${book.titolo}</h3>
                    <div class="author">di ${book.autore}</div>
                    <div class="isbn">ISBN: ${book.isbn}</div>
                    <div class="book-meta">
                        <span class="book-type">${book.tipo}</span>
                        <span class="availability ${getAvailabilityClass(book.disponibilita)}">
                            ${book.disponibilita} disponibili
                        </span>
                    </div>
                </div>
            `).join('');
        }

        function getAvailabilityClass(availability) {
            if (availability === 0) return 'none';
            if (availability <= 2) return 'low';
            return '';
        }

        // Update dashboard statistics
        function updateDashboard() {
            const totalBooks = books.length;
            const availableBooks = books.reduce((sum, book) => sum + book.disponibilita, 0);
            
            document.getElementById('total-books').textContent = totalBooks;
            document.getElementById('available-books').textContent = availableBooks;
            document.getElementById('total-users').textContent = users.length;
            document.getElementById('active-loans').textContent = loans.length;
        }

        // Modal functionality
        function showAddBookForm() {
            document.getElementById('add-book-modal').style.display = 'block';
        }

        function closeModal(modalId) {
            document.getElementById(modalId).style.display = 'none';
        }

        // Setup event listeners
        function setupEventListeners() {
            // Book search
            const bookSearch = document.getElementById('book-search');
            if (bookSearch) {
                bookSearch.addEventListener('input', filterBooks);
            }
            
            // Add book form
            const addBookForm = document.getElementById('add-book-form');
            if (addBookForm) {
                addBookForm.addEventListener('submit', handleAddBook);
            }
            
            // Modal close on outside click
            window.addEventListener('click', function(event) {
                if (event.target.classList.contains('modal')) {
                    event.target.style.display = 'none';
                }
            });
        }

        // Filter books based on search
        function filterBooks() {
            const searchTerm = document.getElementById('book-search').value.toLowerCase();
            const bookCards = document.querySelectorAll('.book-card');
            
            bookCards.forEach(card => {
                const title = card.querySelector('h3').textContent.toLowerCase();
                const author = card.querySelector('.author').textContent.toLowerCase();
                const isbn = card.querySelector('.isbn').textContent.toLowerCase();
                
                if (title.includes(searchTerm) || author.includes(searchTerm) || isbn.includes(searchTerm)) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        }

        // Handle add book form submission
        async function handleAddBook(event) {
            event.preventDefault();
            
            const formData = {
                titolo: document.getElementById('book-title').value,
                autore: document.getElementById('book-author').value,
                isbn: document.getElementById('book-isbn').value,
                tipo: document.getElementById('book-type').value,
                disponibilita: parseInt(document.getElementById('book-availability').value)
            };
            
            try {
                // For now, just add to local array (in a real app, this would POST to the server)
                books.push(formData);
                renderBooks();
                updateDashboard();
                closeModal('add-book-modal');
                
                // Reset form
                document.getElementById('add-book-form').reset();
                
                // Show success message
                showNotification('Libro aggiunto con successo!', 'success');
                
            } catch (error) {
                console.error('Errore nell\\'aggiunta del libro:', error);
                showNotification('Errore nell\\'aggiunta del libro', 'error');
            }
        }

        // Show notification
        function showNotification(message, type = 'info') {
            // Create notification element
            const notification = document.createElement('div');
            notification.className = `notification ${type}`;
            notification.style.cssText = `
                position: fixed;
                top: 20px;
                right: 20px;
                background: ${type === 'success' ? 'var(--success-color)' : 'var(--accent-color)'};
                color: white;
                padding: 1rem 1.5rem;
                border-radius: 10px;
                box-shadow: var(--shadow);
                z-index: 1001;
                animation: slideInRight 0.3s ease;
            `;
            notification.textContent = message;
            
            document.body.appendChild(notification);
            
            // Remove after 3 seconds
            setTimeout(() => {
                notification.style.animation = 'slideOutRight 0.3s ease';
                setTimeout(() => {
                    document.body.removeChild(notification);
                }, 300);
            }, 3000);
        }

        // Add CSS for notification animations
        const style = document.createElement('style');
        style.textContent = `
            @keyframes slideInRight {
                from { transform: translateX(100%); opacity: 0; }
                to { transform: translateX(0); opacity: 1; }
            }
            @keyframes slideOutRight {
                from { transform: translateX(0); opacity: 1; }
                to { transform: translateX(100%); opacity: 0; }
            }
        `;
        document.head.appendChild(style);

        // Utility functions
        function formatDate(date) {
            return new Date(date).toLocaleDateString('it-IT');
        }

        function truncateText(text, maxLength) {
            return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
        }

        // Export functions for global access
        window.switchTab = switchTab;
        window.showAddBookForm = showAddBookForm;
        window.closeModal = closeModal;
        """;
    }
}