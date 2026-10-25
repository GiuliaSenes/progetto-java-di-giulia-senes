package com.lookbook;

import com.lookbook.service.LookBookManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LookBookManager manager = new LookBookManager();
        Scanner scanner = new Scanner(System.in);
        int scelta = -1;

        System.out.println("========================================");
        System.out.println("  BENVENUTO NEL SISTEMA LOOKBOOK (CLI)  ");
        System.out.println("========================================");

        while (scelta != 0) {
            System.out.println("\n--- MENU PRINCIPALE ---");
            System.out.println("1. Visualizza tutti i capi");
            System.out.println("2. Compra / Prenota un capo");
            System.out.println("3. Restituisci un capo");
            System.out.println("4. Aggiungi un nuovo utente");
            System.out.println("5. Esporta capi disponibili in CSV");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");

            try {
                scelta = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserisci un numero valido.");
                continue;
            }

            switch (scelta) {
                case 1:
                    manager.visualizzaCapi();
                    break;
                case 2:
                    manager.compraCapo(scanner);
                    break;
                case 3:
                    manager.restituisciCapo(scanner);
                    break;
                case 4:
                    manager.aggiungiUtente(scanner);
                    break;
                case 5:
                    manager.esportaCapiDisponibili();
                    break;
                case 0:
                    System.out.println("Chiusura dell'applicazione LookBook. Arrivederci!");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
        scanner.close();
    }
}