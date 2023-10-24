package ler_xml;

import java.math.BigDecimal;

public class NotaFiscal {
    Integer numero;
    String cfop;
    String descProduto;
    BigDecimal quantidade;
    BigDecimal preco;
    BigDecimal subtotal;

    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public String getCfop() {
        return cfop;
    }
    public void setCfop(String cfop) {
        this.cfop = cfop;
    }
    public String getDescProduto() {
        return descProduto;
    }
    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }
    public BigDecimal getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    @Override
    public String toString() {
        return 
        "Numero: " + numero + 
        "\nCFOP: " + cfop + 
        "\nProduto: " + descProduto + 
        "\nQuantidade: " + quantidade + 
        "\nPreco: " + preco + 
        "\nSubtotal: " + subtotal;
    }
}
