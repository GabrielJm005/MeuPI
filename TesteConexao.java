package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Parâmetros de conexão
            String url = "jdbc:mysql://localhost:3306/teste";
            String usuario = "root";
            String senha = "@Joaopedro825";

            // Tenta conectar
            try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
                if (conexao != null) {
                    JOptionPane.showMessageDialog(null, 
                        "Conexão com o banco de dados estabelecida com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao conectar ao banco de dados: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 