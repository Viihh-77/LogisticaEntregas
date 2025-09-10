package org.example.model;

import java.time.LocalDate;

public class Entrega {

    private int id;
    private Pedido pedido_id;
    private Motorista motorista_id;
    private LocalDate data_saida;
    private LocalDate data_entrega;
    private Status status;

    public enum Status {
        EM_ROTA,
        ENTREGUE,
        ATRASADA
    }

    public Entrega(int id, Pedido pedido_id, Motorista motorista_id, LocalDate data_saida, LocalDate data_entrega, Status status) {
        this.id = id;
        this.pedido_id = pedido_id;
        this.motorista_id = motorista_id;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status = status;
    }

    public Entrega(Pedido pedido_id, Motorista motorista_id, LocalDate data_saida, LocalDate data_entrega, Status status) {
        this.pedido_id = pedido_id;
        this.motorista_id = motorista_id;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido_id() {
        return pedido_id;
    }

    public Motorista getMotorista_id() {
        return motorista_id;
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

    public void setMotorista_id(Motorista motorista_id) {
        this.motorista_id = motorista_id;
    }

    public void setPedido_id(Pedido pedido_id) {
        this.pedido_id = pedido_id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
