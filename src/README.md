### LookBook - Sistema di Gestione CLI

Un'applicazione Java command-line (CLI) per la gestione di un catalogo capi d'abbigliamento, utenti e storico delle transazioni di acquisto/prenotazione e restituzione.

## 🚀 Caratteristiche principali

Visualizzazione del catalogo completo: Stampa a schermo la lista aggiornata di tutti i capi con taglia, prezzo e stato di disponibilità.

Acquisto / Prenotazione dinamica:

Mostra solo i capi attualmente disponibili (Disponibile=SI).

Verifica la validità dell'ID capo inserito prima di procedere.

Mostra l'elenco degli utenti registrati per la selezione dell'acquirente.

Gestione del rettifica stato del capo in tempo reale (Disponibile=NO).

Possibilità di annullare l'operazione in qualsiasi momento digitando 0.

Restituzione capo avanzata:

Mostra esclusivamente i capi attualmente venduti/assegnati basandosi sulle transazioni attive.

Controlla la correttezza dell'ID inserito con possibilità di re-inserimento.

Ripristina la disponibilità del capo a SI e rimuove la transazione attiva.

Inserimento nuovo utente sicuro:

Generazione automatica dell'ID utente univoco e autoincrementale per evitare duplicati e conflitti di dati.

Esportazione report CSV:

Genera un file capi_disponibili.csv contenente unicamente i capi ancora acquistabili.

## 💻 Requisiti di sistema

Java JDK: 17 o superiore

Apache Maven: 3.6+

Sistema Operativo: Windows / macOS / Linux




## 📄 Formato dei File CSV

I file di configurazione iniziale vanno posizionati all'interno della cartella src/main/resources/:

utenti.csv: ID,Nome,Cognome,Email/Contatto

capi.csv: ID,Descrizione,Taglia,Prezzo,Disponibile

vendite.csv: ID_Vendita,ID_Capo,ID_Utente

## ⚙️ Installazione e Avvio

1. Clona o apri il progetto

Apri il terminale all'interno della directory principale del progetto.

2. Compilazione ed Esecuzione

Esegui il seguente comando Maven per pulire, compilare ed avviare l'applicazione CLI:

mvn clean compile exec:java "-Dexec.mainClass=com.lookbook.Main"



## 🛠️ Guida all'Uso del Menu CLI

All'avvio dell'applicazione comparirà il seguente menu interattivo:

========================================
   BENVENUTO NEL SISTEMA LOOKBOOK (CLI)  
========================================

--- MENU PRINCIPALE ---
1. Visualizza tutti i capi
2. Compra / Prenota un capo
3. Restituisci un capo
4. Aggiungi un nuovo utente
5. Esporta capi disponibili in CSV
0. Esci
Scegli un'opzione:



Comandi principali:

1: Mostra tutti i capi presenti nel database con prezzo in Euro e stato.

2: Guida passo-passo per l'acquisto (mostra capi disponibili -> seleziona ID capo -> mostra utenti -> seleziona ID utente).

3: Guida per la restituzione di un capo precedentemente venduto/assegnato.

4: Inserimento guidato di un nuovo utente (l'ID viene calcolato automaticamente).

5: Salva nella cartella di esecuzione un file capi_disponibili.csv.

0: Esce in sicurezza dall'applicazione.