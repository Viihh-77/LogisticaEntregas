package org.example.dao;

import com.mysql.cj.jdbc.ConnectionWrapper;
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

    public void entregaPorMotorista() throws SQLException {
        String query = """
                SELECT m.id, m.nome, COUNT(e.id) AS total_entregas
                FROM motorista m
                LEFT JOIN entrega e ON e.motorista_id = m.id
                GROUP BY m.id, m.nome
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int total = rs.getInt("total_entregas");

                System.out.println("Motorista: " + nome + " | ID: " + id + " | Total Entregas: " + total);
            }
        }
    }

    public void maiorVolumeEntregue() throws SQLException {
        String query = """
                    SELECT c.id, c.nome, SUM(p.volume) AS total_volume
                    FROM cliente c
                    JOIN pedido p ON p.cliente_id = c.id
                    JOIN entrega e ON e.pedido_id = p.id
                    WHERE e.status = 'ENTREGA'
                    GROUP BY c.id, c.nome
                    ORDER BY total_volume DESC
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double volume = rs.getDouble("total_volume");

                System.out.println("Cliente: " + nome + " | ID: " + id + " | Volume: " + volume);
            }
        }
    }

    public void entregasAtrasadas() throws SQLException {
        String query = """
                    SELECT c.cidade, COUNT(e.id) AS total_atrasadas
                    FROM entrega e
                    JOIN pedido p ON e.pedido_id = p.id
                    JOIN cliente c ON p.cliente_id = c.id
                    WHERE e.status = 'ATRASADA'
                    GROUP BY c.cidade
                    ORDER BY total_atrasadas DESC
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String cidade = rs.getString("cidade");
                int total = rs.getInt("total_atrasadas");

                System.out.println("Cidade: " + cidade + " | Entregas atrasadas: " + total);
            }
        }
    }

    public void excluirEntrega(int id) throws SQLException {
        String validaQuery = """
                SELECT status FROM entrega WHERE id = ?
                """;
        String deletaQuery = """
                DELETE FROM entrega WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement validaStmt = conn.prepareStatement(validaQuery)) {

            validaStmt.setInt(1, id);
            ResultSet rs = validaStmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                if (!status.equalsIgnoreCase("EM_ROTA")) {
                    System.out.println("Erro: Não é possível excluir essa entrega!");
                    return;
                } else {
                    System.out.println("Erro: Entrega não encontrada!");
                    return;
                }
            }

            try (PreparedStatement deletaStmt = conn.prepareStatement(deletaQuery)) {
                deletaStmt.setInt(1, id);
                deletaStmt.executeUpdate();
                System.out.println("Sucesso: Entrega excluida!");
            }
        }
    }
}
