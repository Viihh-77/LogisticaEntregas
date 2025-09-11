package org.example.model;

import java.time.LocalDate;


public class Pedido {

    private int id;
    private Cliente id_cliente;
    private LocalDate data_pedido;
    private double volume;
    private double peso;
    private Status status;

    public enum Status {
        Pendente,
        Entregue,
        Cancelado
    }

    public Pedido(int id, Cliente id_cliente, LocalDate data_pedido, double volume, double peso, Status status) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.data_pedido = data_pedido;
        this.volume = volume;
        this.peso = peso;
        this.status = status;
    }

    public Pedido(Cliente id_cliente, LocalDate data_pedido, double volume, double peso, Status status) {
        this.id_cliente = id_cliente;
        this.data_pedido = data_pedido;
        this.volume = volume;
        this.peso = peso;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public LocalDate getData_pedido() {
        return data_pedido;
    }

    public double getVolume() {
        return volume;
    }

    public double getPeso() {
        return peso;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setData_pedido(LocalDate data_pedido) {
        this.data_pedido = data_pedido;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
