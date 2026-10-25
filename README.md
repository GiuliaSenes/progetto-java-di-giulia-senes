# LookBook - Sistema di Gestione CLI

Un'applicazione Java command-line (CLI) per la gestione di un catalogo capi d'abbigliamento, utenti e storico delle transazioni di acquisto/prenotazione e restituzione.

### 🚀 Caratteristiche principali
Catalogo Capi: Visualizzazione completa dell'inventario con taglie, prezzi e stato di disponibilità.

Acquisto Guidato: Selezione dei capi disponibili, scelta di un utente registrato, aggiornamento in tempo reale dello stato a non disponibile e possibilità di annullare con 0.

Restituzione Avanzata: Elenco dei soli capi venduti/assegnati, con convalida dell'ID e ripristino automatico dello stato di disponibilità.

Gestione Utenti Sicura: Inserimento di nuovi utenti con generazione automatica di un ID progressivo e univoco per evitare duplicati.

Esportazione CSV: Generazione rapida di un report (capi_disponibili.csv) contenente solo i capi acquistabili.

### 💻 Requisiti di sistema

Java JDK: 17 o superiore

Apache Maven: 3.6+

Sistema Operativo: Windows / macOS / Linux


### 📄 Formato dei File CSV

I file di configurazione iniziale vanno posizionati all'interno della cartella src/main/resources/:

utenti.csv: ID,Nome,Cognome,Email/Contatto

capi.csv: ID,Descrizione,Taglia,Prezzo,Disponibile

vendite.csv: ID_Vendita,ID_Capo,ID_Utente

### ⚙️ Installazione e Avvio

1. Clona o apri il progetto

Apri il terminale all'interno della directory principale del progetto.

2. Compilazione ed Esecuzione

Esegui il seguente comando Maven per pulire, compilare ed avviare l'applicazione CLI:

mvn clean compile exec:java "-Dexec.mainClass=com.lookbook.Main"

