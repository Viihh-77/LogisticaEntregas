package org.example.dao;

import org.example.model.Pedido;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
