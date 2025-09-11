package org.example.dao;

import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
}
