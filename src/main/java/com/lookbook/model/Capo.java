package com.lookbook.model;

public class Capo {
    private String id;
    private String descrizione;
    private String taglia;
    private double prezzo;
    private String disponibile; // "SI" o "NO"

    // Costruttore
    public Capo(String id, String descrizione, String taglia, double prezzo, String disponibile) {
        this.id = id;
        this.descrizione = descrizione;
        this.taglia = taglia;
        this.prezzo = prezzo;
        this.disponibile = disponibile;
    }

    // Getter e Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getTaglia() { return taglia; }
    public void setTaglia(String taglia) { this.taglia = taglia; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    public String getDisponibile() { return disponibile; }
    public void setDisponibile(String disponibile) { this.disponibile = disponibile; }

    @Override
    public String toString() {
        return "Capo [ID=" + id + ", Descrizione=" + descrizione + ", Taglia=" + taglia + 
               ", Prezzo=€" + prezzo + ", Disponibile=" + disponibile + "]";
    }
}