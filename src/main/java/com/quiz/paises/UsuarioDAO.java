package com.quiz.paises;

import com.quiz.paises.entity.Usuario;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public void perguntaFacil(Usuario u) {
        executar(u, "Fácil");
    }

    public void perguntaMedia(Usuario u) {
        executar(u, "Médio");
    }

    public void perguntaDificil(Usuario u) {
        executar(u, "Difícil");
    }

    private void executar(@NotNull Usuario u, String nivel) {
        String sql = "SELECT * FROM PERGUNTAS WHERE ID = ? AND NIVEL = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, u.getId());
            ps.setString(2, nivel);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u.setPergunta(rs.getString("pergunta"));
                    u.setAlt_a(rs.getString("a"));
                    u.setAlt_b(rs.getString("b"));
                    u.setAlt_c(rs.getString("c"));
                    u.setAlt_d(rs.getString("d"));
                    u.setAlt_correta(rs.getString("correta"));
                    u.setNivel(rs.getString("nivel"));
                    u.setDica(rs.getString("dica"));
                } else {
                    System.err.println("Nenhuma pergunta encontrada para o ID: " + u.getId() + " e nível: " + nivel);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar consulta: " + e.getMessage());
        }
    }

    public void rank(@NotNull Usuario u) {
        String sql = "INSERT INTO RANKING (NOME, PONTOS, TEMPO) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNome());
            ps.setFloat(2, u.getPontos());
            ps.setFloat(3, u.getTemp());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar jogador: " + e.getMessage());
        }
    }

    public String exibirRank() {
        StringBuilder ranke = new StringBuilder();
        String sql = "SELECT * FROM RANKING ORDER BY PONTOS DESC, TEMPO ASC LIMIT 3";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ranke.append(String.format("%-7s | %-15s | %-6s | %-7s%n", "Posição", "Nome", "Pontos", "Tempo"));
            ranke.append("------------------------------------------------").append(System.lineSeparator());

            int i = 1;
            while (rs.next()) {
                String nome = rs.getString("nome");
                float pontos = rs.getFloat("pontos");
                float tempo = rs.getFloat("tempo");

                ranke.append(String.format("%-7d | %-15s | %-6.1f | %-7.1fs%n", i, nome, pontos, tempo));
                i++;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao exibir rank: " + e.getMessage());
        }

        return ranke.toString();
    }
}