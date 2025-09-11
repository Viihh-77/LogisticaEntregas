package org.example.dao;

import org.example.model.Cliente;
import org.example.model.Entrega;
import org.example.model.Motorista;
import org.example.model.Pedido;
import org.example.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public void cadastrarEntrega(Entrega entrega) throws SQLException {
        String query = """
                INSERT INTO entrega
                (pedido_id,motorista_id,data_saida,data_entrada,status)
                VALUES (?,?,?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entrega.getPedido().getId());
            stmt.setInt(2, entrega.getMotorista().getId());
            stmt.setDate(3, Date.valueOf(entrega.getData_saida()));
            stmt.setDate(4, entrega.getData_entrega() != null ? Date.valueOf(entrega.getData_entrega()) : null);
            stmt.setString(5, entrega.getStatus().name());
            stmt.executeUpdate();
        }
    }

    public void registrarEntrega(int entregaId, boolean atrasada) throws SQLException {
        String query = """
                UPDATE entrega
                SET data_entrada = ?, status = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(2, atrasada ? "Atrasada" : "Entrega");
            stmt.setInt(3, entregaId);
            stmt.executeUpdate();
        }
    }

    public void atualizarStatus(int entregaId, String status) throws SQLException {
        String query = """
                UPDATE entrega
                SET status = ?
                WHERE id = ?;
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, entregaId);
            stmt.executeUpdate();
        }
    }

    public List<Entrega> listarEntregas() throws SQLException {
        String query = """
                SELECT e.id AS entrega_id, e.data_saida, e.data_entrada, e.status AS entrega_status,
                       p.id AS pedido_id, p.cliente_id, p.volume, p.peso, p.data_pedido, p.status AS pedido_status,
                       c.id AS cliente_id, c.nome AS cliente_nome, c.cpf, c.endereco, c.cidade, c.estado,
                       m.id AS motorista_id, m.nome AS motorista_nome, m.cnh, m.veiculo, m.cidade_base
                FROM entrega e
                JOIN pedido p ON e.pedido_id = p.id
                JOIN cliente c ON p.cliente_id = c.id
                JOIN motorista m ON e.motorista_id = m.id
                """;

        List<Entrega> entregas = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("cliente_nome"),
                        rs.getString("cpf"),
                        rs.getString("endereco"),
                        rs.getString("cidade"),
                        rs.getString("estado")
                );

                Pedido pedido = new Pedido(
                        rs.getInt("pedido_id"),
                        cliente,
                        rs.getDate("data_pedido").toLocalDate(),
                        rs.getDouble("volume"),
                        rs.getDouble("peso"),
                        Pedido.Status.valueOf(rs.getString("pedido_status"))
                );

                Motorista motorista = new Motorista(
                        rs.getInt("motorista_id"),
                        rs.getString("motorista_nome"),
                        rs.getString("cnh"),
                        rs.getString("veiculo"),
                        rs.getString("cidade_base")
                );

                Entrega entrega = new Entrega(
                        rs.getInt("entrega_id"),
                        pedido,
                        motorista,
                        rs.getDate("data_saida").toLocalDate(),
                        rs.getDate("data_entrada") != null ? rs.getDate("data_entrada").toLocalDate() : null,
                        Entrega.Status.valueOf(rs.getString("entrega_status").toUpperCase())
                );

                entregas.add(entrega);
            }
        }

        return entregas;

    }
}
