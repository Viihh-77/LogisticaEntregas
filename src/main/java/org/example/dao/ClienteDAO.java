package org.example.dao;

import org.example.model.Cliente;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public void cadastrarCliente(Cliente cliente) throws SQLException {
        String query = """
                INSERT INTO cliente
                (nome,cpf,endereco,cidade,estado)
                VALUES
                (?,?,?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEstado());
            stmt.executeUpdate();
        }
    }

    public Cliente buscarPorId(int id) throws SQLException {
        String query = """
                SELECT * FROM cliente WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("endereco"),
                        rs.getString("cidade"),
                        rs.getString("estado")
                );
            }
        }
        return null;
    }

    public boolean excluirCliente(int id) throws SQLException {
        String verificaQuery = """
                SELECT COUNT(*) FROM pedido WHERE cliente_id = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(verificaQuery)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
        }
    }

        String deletaQuery = """
                DELETE FROM cliente WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(deletaQuery)){

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }
    }
}
