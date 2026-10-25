package com.lookbook.service;

import com.lookbook.model.Capo;
import com.lookbook.model.Utente;
import com.lookbook.model.Vendita;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LookBookManager {
    private List<Utente> utenti = new ArrayList<>();
    private List<Capo> capi = new ArrayList<>();
    private List<Vendita> vendite = new ArrayList<>();

    public LookBookManager() {
        caricaDati();
    }

    private void caricaDati() {
        caricaUtenti();
        caricaCapi();
        caricaVendite();
    }

    private void caricaUtenti() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("utenti.csv"), StandardCharsets.UTF_8))) {
            String linea;
            boolean primaRiga = true;
            while ((linea = br.readLine()) != null) {
                if (primaRiga) { primaRiga = false; continue; }
                String[] campi = linea.split("[,;]");
                if (campi.length >= 4) {
                    utenti.add(new Utente(campi[0].trim(), campi[1].trim(), campi[2].trim(), campi[3].trim()));
                }
            }
            System.out.println("Utenti caricati con successo: " + utenti.size());
        } catch (Exception e) {
            System.out.println("Errore durante il caricamento di utenti.csv: " + e.getMessage());
        }
    }

    private void caricaCapi() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("capi.csv"), StandardCharsets.UTF_8))) {
            String linea;
            boolean primaRiga = true;
            while ((linea = br.readLine()) != null) {
                if (primaRiga) { primaRiga = false; continue; }
                String[] campi = linea.split("[,;]");
                if (campi.length >= 6) {
                    try {
                        String prezzoPulito = campi[5].replaceAll("[^0-9,.]", "").replace(",", ".");
                        double prezzo = Double.parseDouble(prezzoPulito);
                        String disponibile = campi.length > 6 ? campi[6].trim() : "SI";
                        capi.add(new Capo(campi[0].trim(), campi[2].trim(), campi[4].trim(), prezzo, disponibile));
                    } catch (Exception ex) {
                        System.out.println("Impossibile parsare la riga del capo: " + linea);
                    }
                }
            }
            System.out.println("Capi caricati con successo: " + capi.size());
        } catch (Exception e) {
            System.out.println("Errore durante il caricamento di capi.csv: " + e.getMessage());
        }
    }

    private void caricaVendite() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("vendite.csv"), StandardCharsets.UTF_8))) {
            String linea;
            boolean primaRiga = true;
            while ((linea = br.readLine()) != null) {
                if (primaRiga) { primaRiga = false; continue; }
                String[] campi = linea.split("[,;]");
                if (campi.length >= 3) {
                    vendite.add(new Vendita(campi[0].trim(), campi[1].trim(), campi[2].trim()));
                }
            }
            System.out.println("Vendite caricate con successo: " + vendite.size());
        } catch (Exception e) {
            System.out.println("Errore durante il caricamento di vendite.csv: " + e.getMessage());
        }
    }

    private String estraiId(Object obj) {
        try {
            Method m = obj.getClass().getMethod("getId");
            Object res = m.invoke(obj);
            if (res != null) return res.toString().trim();
        } catch (Exception ignored) {}

        try {
            String s = obj.toString();
            int start = s.indexOf("ID=");
            if (start != -1) {
                int end = s.indexOf(",", start);
                if (end == -1) end = s.indexOf("]", start);
                if (end != -1) {
                    return s.substring(start + 3, end).trim();
                }
            }
        } catch (Exception ignored) {}
        return "";
    }

    private String estraiIdCapoDaVendita(Vendita v) {
        try {
            String s = v.toString();
            String[] parts = s.replace("Vendita [", "").replace("]", "").split(",");
            if (parts.length >= 2) {
                String part = parts[1].trim();
                int eq = part.indexOf("=");
                return (eq != -1) ? part.substring(eq + 1).trim() : part;
            }
        } catch (Exception ignored) {}
        return "";
    }

    private String estraiValoreCampo(String toString, String campo) {
        try {
            int start = toString.indexOf(campo + "=");
            if (start != -1) {
                start += campo.length() + 1;
                int end = toString.indexOf(",", start);
                if (end == -1) end = toString.indexOf("]", start);
                if (end != -1) return toString.substring(start, end).trim();
            }
        } catch (Exception ignored) {}
        return "";
    }

    private String formattaStampa(Object obj) {
        // Pulisce simboli corrotti e mostra EUR in modo pulito
        String s = obj.toString();
        return s.replace("?", "").replace("€", "").replace("Prezzo=", "Prezzo=EUR ");
    }

    // 1. Visualizza tutti i capi
    public void visualizzaCapi() {
        System.out.println("\n--- LISTA COMPLETA CAPI ---");
        if (capi.isEmpty()) {
            System.out.println("Nessun capo disponibile.");
            return;
        }
        for (Capo c : capi) {
            System.out.println(formattaStampa(c));
        }
    }

    // 2. Compra / Prenota un capo
    public void compraCapo(Scanner scanner) {
        System.out.println("\n--- COMPRA / PRENOTA CAPO ---");
        System.out.println("(Digita '0' in qualsiasi momento per tornare al menu principale)");

        System.out.println("\n--- CAPI DISPONIBILI ---");
        boolean ciSonoDisponibili = false;
        for (Capo c : capi) {
            if (!c.toString().contains("Disponibile=NO")) {
                System.out.println(formattaStampa(c));
                ciSonoDisponibili = true;
            }
        }
        if (!ciSonoDisponibili) {
            System.out.println("[X] Al momento non ci sono capi disponibili per l'acquisto.");
            return;
        }
        System.out.println("------------------------");

        Capo capoTrovato = null;
        int indiceCapo = -1;

        while (capoTrovato == null) {
            System.out.print("\nInserisci l'ID del capo da comprare/prenotare: ");
            String idCapo = scanner.nextLine().trim();

            if (idCapo.equals("0")) {
                System.out.println("[<-] Operazione annullata. Ritorno al menu principale.");
                return;
            }

            for (int i = 0; i < capi.size(); i++) {
                Capo c = capi.get(i);
                if (estraiId(c).equals(idCapo)) {
                    capoTrovato = c;
                    indiceCapo = i;
                    break;
                }
            }

            if (capoTrovato == null) {
                System.out.println("[X] Errore: Capo con ID '" + idCapo + "' non trovato. Riprova.");
            } else if (capoTrovato.toString().contains("Disponibile=NO")) {
                System.out.println("[X] Errore: Questo capo risulta gia venduto/non disponibile. Scegline un altro.");
                capoTrovato = null;
            }
        }

        System.out.println("\n--- ELENCO UTENTI REGISTRATI ---");
        if (utenti.isEmpty()) {
            System.out.println("Nessun utente registrato nel sistema.");
            return;
        }
        for (Utente u : utenti) {
            System.out.println(u);
        }
        System.out.println("---------------------------------");

        String idUtente = "";
        boolean utenteTrovato = false;
        Utente utenteObjTrovato = null;

        while (!utenteTrovato) {
            System.out.print("Inserisci l'ID dell'utente acquirente (o '0' per annullare): ");
            idUtente = scanner.nextLine().trim();

            if (idUtente.equals("0")) {
                System.out.println("[<-] Operazione annullata. Ritorno al menu principale.");
                return;
            }

            for (Utente u : utenti) {
                if (estraiId(u).equals(idUtente)) {
                    utenteTrovato = true;
                    utenteObjTrovato = u;
                    break;
                }
            }

            if (!utenteTrovato) {
                System.out.println("[X] Errore: Utente con ID '" + idUtente + "' inesistente. Riprova.");
            }
        }

        String idCapoScelto = estraiId(capoTrovato);
        String nomeCapo = estraiValoreCampo(capoTrovato.toString(), "Descrizione");
        String nomeUtente = estraiValoreCampo(utenteObjTrovato.toString(), "Nome");
        String cognomeUtente = estraiValoreCampo(utenteObjTrovato.toString(), "Cognome");

        vendite.add(new Vendita(String.valueOf(vendite.size() + 1), idCapoScelto, idUtente));

        String strCapoOriginale = capoTrovato.toString();
        try {
            boolean aggiornato = false;
            try {
                Method setDisp = capoTrovato.getClass().getMethod("setDisponibile", String.class);
                setDisp.invoke(capoTrovato, "NO");
                aggiornato = true;
            } catch (Exception ignored) {}

            if (!aggiornato) {
                capi.set(indiceCapo, new Capo(idCapoScelto, nomeCapo, estraiValoreCampo(strCapoOriginale, "Taglia"), Double.parseDouble(estraiValoreCampo(strCapoOriginale, "Prezzo").replaceAll("[^0-9.]", "")), "NO"));
            }
        } catch (Exception ignored) {}

        System.out.println("[OK] Successo! Il capo '" + nomeCapo + "' (ID: " + idCapoScelto + ") e stato acquistato correttamente da " + nomeUtente + " " + cognomeUtente + ".");
    }

    // 3. Restituisci un capo
    public void restituisciCapo(Scanner scanner) {
        System.out.println("\n--- RESTITUISCI CAPO ---");
        System.out.println("(Digita '0' in qualsiasi momento per tornare al menu principale)");

        List<String> idsCapiAssegnati = new ArrayList<>();
        for (Vendita v : vendite) {
            String idC = estraiIdCapoDaVendita(v);
            if (!idC.isEmpty() && !idsCapiAssegnati.contains(idC)) {
                idsCapiAssegnati.add(idC);
            }
        }

        System.out.println("\n--- CAPI ATTUALMENTE ASSEGNATI / VENDUTI ---");
        if (idsCapiAssegnati.isEmpty()) {
            System.out.println("[X] Non ci sono capi attualmente assegnati o venduti da restituire.");
            return;
        }

        for (Capo c : capi) {
            String idC = estraiId(c);
            if (idsCapiAssegnati.contains(idC)) {
                System.out.println(formattaStampa(c));
            }
        }
        System.out.println("-------------------------------------------");

        Vendita venditaDaRimuovere = null;
        String idCapo = "";

        while (venditaDaRimuovere == null) {
            System.out.print("\nInserisci l'ID del capo da restituire: ");
            idCapo = scanner.nextLine().trim();

            if (idCapo.equals("0")) {
                System.out.println("[<-] Operazione annullata. Ritorno al menu principale.");
                return;
            }

            if (!idsCapiAssegnati.contains(idCapo)) {
                System.out.println("[X] Errore: Il capo con ID '" + idCapo + "' non risulta tra quelli assegnati. Riprova.");
                continue;
            }

            for (Vendita v : vendite) {
                if (estraiIdCapoDaVendita(v).equals(idCapo)) {
                    venditaDaRimuovere = v;
                    break;
                }
            }

            if (venditaDaRimuovere == null) {
                System.out.println("[X] Errore: Nessuna transazione attiva trovata per il capo con ID '" + idCapo + "'. Riprova.");
            }
        }

        vendite.remove(venditaDaRimuovere);
        String nomeCapo = "";
        for (Capo c : capi) {
            if (estraiId(c).equals(idCapo)) {
                nomeCapo = estraiValoreCampo(c.toString(), "Descrizione");
                try {
                    Method setDisp = c.getClass().getMethod("setDisponibile", String.class);
                    setDisp.invoke(c, "SI");
                } catch (Exception ignored) {}
            }
        }
        System.out.println("[OK] Successo! Il capo '" + (nomeCapo.isEmpty() ? idCapo : nomeCapo) + "' (ID: " + idCapo + ") e stato restituito correttamente. Disponibilita ripristinata.");
    }

    // 4. Aggiungi un nuovo utente con ID autoincrementale univoco
    public void aggiungiUtente(Scanner scanner) {
        System.out.println("\n--- AGGIUNGI UN NUOVO UTENTE ---");
        System.out.println("(Digita '0' come Nome per annullare l'operazione)");

        System.out.print("Inserisci Nome: ");
        String nome = scanner.nextLine().trim();
        if (nome.equals("0")) {
            System.out.println("[<-] Operazione annullata. Ritorno al menu principale.");
            return;
        }

        System.out.print("Inserisci Cognome: ");
        String cognome = scanner.nextLine().trim();

        System.out.print("Inserisci Contatto (Email/Telefono): ");
        String contatto = scanner.nextLine().trim();

        int maxId = 0;
        for (Utente u : utenti) {
            try {
                int idVal = Integer.parseInt(estraiId(u));
                if (idVal > maxId) {
                    maxId = idVal;
                }
            } catch (Exception ignored) {}
        }
        String nuovoId = String.valueOf(maxId + 1);

        utenti.add(new Utente(nuovoId, nome, cognome, contatto));
        System.out.println("[OK] Utente aggiunto con successo! ID assegnato automaticamente: " + nuovoId + ". Totale utenti registrati: " + utenti.size());
    }

    // 5. Esporta capi disponibili in CSV
    public void esportaCapiDisponibili() {
        System.out.println("\n--- ESPORTAZIONE CAPI DISPONIBILI ---");
        String nomeFile = "capi_disponibili.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile, StandardCharsets.UTF_8))) {
            writer.write("ID;Tipologia;Taglia;Prezzo;Disponibile\n");
            int count = 0;
            for (Capo c : capi) {
                if (!c.toString().contains("Disponibile=NO")) {
                    writer.write(formattaStampa(c).replace("Capo [", "").replace("]", "").replace(", ", ";") + "\n");
                    count++;
                }
            }
            System.out.println("[OK] Esportazione completata con successo! Creato il file '" + nomeFile + "' con " + count + " capi disponibili.");
        } catch (Exception e) {
            System.out.println("[X] Errore durante l'esportazione in CSV: " + e.getMessage());
        }
    }
}