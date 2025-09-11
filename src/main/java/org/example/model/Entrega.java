package org.example.model;

import java.time.LocalDate;

public class Entrega {

    private int id;
    private Pedido pedido;
    private Motorista motorista;
    private LocalDate data_saida;
    private LocalDate data_entrega;
    private Status status;

    public enum Status {
        EM_ROTA,
        ENTREGUE,
        ATRASADA
    }

    public Entrega(int id, Pedido pedido, Motorista motorista, LocalDate data_saida, LocalDate data_entrega, Status status) {
        this.id = id;
        this.pedido = pedido;
        this.motorista = motorista;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status = status;
    }

    public Entrega(Pedido pedido, Motorista motorista, LocalDate data_saida, LocalDate data_entrega, Status status) {
        this.pedido = pedido;
        this.motorista = motorista;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public LocalDate getData_saida() {
        return data_saida;
    }

    public LocalDate getData_entrega() {
        return data_entrega;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setData_entrega(LocalDate data_entrega) {
        this.data_entrega = data_entrega;
    }

    public void setData_saida(LocalDate data_saida) {
        this.data_saida = data_saida;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setId(int id) {
        this.id = id;
    }
}
