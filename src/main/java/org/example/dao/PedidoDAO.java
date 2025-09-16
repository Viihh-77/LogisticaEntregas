package org.example.dao;

import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void cadastrarPedido(Pedido pedido) throws SQLException {
        String query = """
                INSERT INTO pedido
                (cliente_id,data_pedido,volume,peso,status)
                VALUES
                (?,?,?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, pedido.getId_cliente().getId());
            stmt.setDate(2, java.sql.Date.valueOf(pedido.getData_pedido()));
            stmt.setDouble(3, pedido.getVolume());
            stmt.setDouble(4, pedido.getPeso());
            stmt.setString(5, pedido.getStatus().name());
            stmt.executeUpdate();
        }
    }

    public Pedido buscarPorId(int id) throws SQLException {
        String query = """
                SELECT * FROM pedido WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1,id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.buscarPorId(rs.getInt("cliente_id"));

                LocalDate dataPedido = rs.getDate("data_pedido").toLocalDate();
                double volume = rs.getDouble("volume");
                double peso = rs.getDouble("peso");
                Pedido.Status status = Pedido.Status.valueOf(rs.getString("status").toUpperCase());

                return new Pedido(rs.getInt("id"), cliente, dataPedido, volume, peso, status);

            }
        }
        return null;
    }

    public void pedidosPendentes() throws SQLException {
        String query = """
                    SELECT c.estado, COUNT(p.id) AS total_pedidos
                    FROM pedido p
                    JOIN cliente c ON p.cliente_id = c.id
                    WHERE p.status = 'Pendente'
                    GROUP BY c.estado
                    ORDER BY total_pedidos DESC       
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String estado = rs.getString("estado");
                int total = rs.getInt("total_pedidos");

                System.out.println("Estado: " + estado + " | Pedidos pendentes: " + total);
            }
        }
    }

    public List<Pedido> buscarPorCPF(String cpf) throws SQLException {
        String query = """
                    SELECT p.id AS pedido_id, p.cliente_id, p.data_pedido, p.volume, p.peso, p.status,
                           c.nome, c.cpf, c.endereco, c.cidade, c.estado
                    FROM pedido p
                    JOIN cliente c ON p.cliente_id = c.id
                    WHERE c.cpf = ?
                """;

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("cliente_id"),
                        rs.getString("nome"),
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
                        Pedido.Status.valueOf(rs.getString("status"))
                );
                pedidos.add(pedido);
            }
        }

        return pedidos;

    }

    public void cancelarPedido(int id) throws SQLException {
        String query = """
                UPDATE pedido SET status = ? WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, Pedido.Status.Cancelado.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        }
    }
}
