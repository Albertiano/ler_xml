package ler_xml;

import java.util.ArrayList;
import java.util.List;

public class NotaFiscal {
    private Integer numero;
    private String date;
    private List<Item> items;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NotaFiscal(){
        items = new ArrayList<Item>();
    }
    
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "NotaFiscal [Emissao=" + date + ", numero=" + numero + ", items=" + items + "\n]";
    }
}