package org.example.model;

public class Motorista {

    private int id;
    private String nome;
    private String cnh;
    private String veiculo;
    private String cidade_base;

    public Motorista(int id, String nome, String cnh, String veiculo, String cidade_base) {
        this.id = id;
        this.nome = nome;
        this.cnh = cnh;
        this.veiculo = veiculo;
        this.cidade_base = cidade_base;
    }

    public Motorista(String nome, String cnh, String veiculo, String cidade_base) {
        this.nome = nome;
        this.cnh = cnh;
        this.veiculo = veiculo;
        this.cidade_base = cidade_base;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnh() {
        return cnh;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public String getCidade_base() {
        return cidade_base;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public void setCidade_base(String cidade_base) {
        this.cidade_base = cidade_base;
    }
}
