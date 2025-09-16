package org.example.dao;

import org.example.model.Motorista;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotoristaDAO {

    public void cadastrarMotorista(Motorista motorista) throws SQLException {
        String query = """
                INSERT INTO motorista
                (nome,cnh,veiculo,cidade_base)
                VALUES
                (?,?,?,?)
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCnh());
            stmt.setString(3, motorista.getVeiculo());
            stmt.setString(4, motorista.getCidade_base());
            stmt.executeUpdate();
        }
    }

    public Motorista buscarPorId (int id) throws SQLException {
        String query = """
                SELECT * FROM motorista WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                return new Motorista(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnh"),
                        rs.getString("veiculo"),
                        rs.getString("cidade_base")
                );
            }
        }
        return null;
    }

    public boolean excluirMotorista(int id) throws SQLException {
        String verificaQuery = """
                SELECT COUNT(*) FROM entrega WHERE motorista_id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(verificaQuery)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }
        }

        String deletaQuery = """
                DELETE FROM motorista WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(deletaQuery)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }
    }
}
