# Esame di Programmazione Orientata agli Oggetti - Parte 2

15 giugno 2024 - Sistema di Gestione Biblioteca

La biblioteca comunale vuole implementare un sistema software per gestire il prestito e la restituzione dei libri. Il
sistema deve consentire ai bibliotecari di gestire l'inventario dei libri, le prenotazioni degli utenti e il
monitoraggio dei prestiti.

Ogni libro ha un titolo, un autore, un codice ISBN, una categoria (narrativa, saggistica, fumetti, etc.) e può essere
disponibile in più copie. Gli utenti della biblioteca hanno un profilo con nome, cognome, numero di tessera e storico
dei prestiti. Un utente può prendere in prestito fino a 5 libri contemporaneamente per un massimo di 30 giorni ciascuno.

Il sistema deve permettere ai bibliotecari di:

- Aggiungere nuovi libri all'inventario o aumentare il numero di copie disponibili
- Registrare il prestito di un libro a un utente
- Registrare la restituzione di un libro
- Verificare la disponibilità di un libro
- Prenotare un libro attualmente non disponibile per un utente
- Generare report sui libri più richiesti, utenti più attivi, libri in ritardo nella restituzione

Inoltre, il sistema deve gestire le seguenti regole:

- Se un libro prenotato diventa disponibile, l'utente che lo ha prenotato ha 48 ore per ritirarlo prima che la
  prenotazione decada
- Gli utenti con libri in ritardo non possono effettuare nuovi prestiti finché non restituiscono tutti i libri scaduti
- Dopo 3 ritardi in un anno, un utente viene sospeso per 1 mese

Si richiede di progettare una o più gerarchie di tipi in Java per supportare le operazioni sopra indicate. Definire
classi e interfacce, le segnature dei metodi e gli stati astratti, specificando il ruolo e il protocollo delle
classi/interfacce e i contratti dei metodi più importanti. Implementare lo stato concreto e i metodi principali.

La valutazione dell'elaborato si baserà sulla qualità del progetto e della sua implementazione, considerando aspetti
quali:

- Corretta definizione delle responsabilità
- Uso appropriato di tipologie e interfacce
- Definizione chiara dei contratti
- Implementazione di Abstract Data Types (ADT)
- Corretta parametrizzazione
- Qualità del codice
- Utilizzo di design pattern
- Livello di astrazione
- Incapsulamento

Si prega di scrivere in modo chiaro e comprensibile. Non verranno considerate spiegazioni complesse, schemi o diagrammi.
Ciò che risulta incomprensibile sarà interpretato come errato.
