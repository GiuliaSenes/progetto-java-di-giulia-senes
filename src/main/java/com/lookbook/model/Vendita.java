package com.lookbook.model;

public class Vendita {
    private String idVendita;
    private String idUtente;
    private String idCapo;

    // Costruttore
    public Vendita(String idVendita, String idUtente, String idCapo) {
        this.idVendita = idVendita;
        this.idUtente = idUtente;
        this.idCapo = idCapo;
    }

    // Getter e Setter
    public String getIdVendita() { return idVendita; }
    public void setIdVendita(String idVendita) { this.idVendita = idVendita; }

    public String getIdUtente() { return idUtente; }
    public void setIdUtente(String idUtente) { this.idUtente = idUtente; }

    public String getIdCapo() { return idCapo; }
    public void setIdCapo(String idCapo) { this.idCapo = idCapo; }

    @Override
    public String toString() {
        return "Vendita [ID Vendita=" + idVendita + ", ID Utente=" + idUtente + ", ID Capo=" + idCapo + "]";
    }
}