package co.edu.unbosque.dto;

import com.opencsv.bean.CsvBindByName;

public class Colections {
    @CsvBindByName
    private String name;

    @CsvBindByName
    private String categoria;

   @CsvBindByName
    private String cantidad;

    public Colections(String name, String categoria, String cantidad) {
        this.name = name;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Colections{" +
                "name='" + name + '\'' +
                ", categoria='" + categoria + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
