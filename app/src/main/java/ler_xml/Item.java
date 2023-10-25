package ler_xml;

import java.math.BigDecimal;

public class Item {
    String cfop;
    String descProduto;
    BigDecimal quantidade;
    BigDecimal preco;
    BigDecimal subtotal;

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
        return "\n" + //
                "   Item [cfop=" + cfop + ", descProduto=" + descProduto + ", quantidade=" + quantidade + ", preco=" + preco
                + ", subtotal=" + subtotal + "]";
    }
}
