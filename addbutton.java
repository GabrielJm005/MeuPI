import java.awt.*;
import javax.swing.*;

public class addbutton extends JFrame {

    public addbutton() {
        setTitle("Menu Metrô");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        // Pegando tamanho da tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Painel em camadas
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, screenWidth, screenHeight);

        // Carregando e redimensionando imagem de fundo
        ImageIcon originalIcon = new ImageIcon("C:/Users/25.00961-4/OneDrive - Instituto Mauá de Tecnologia/ProjetoPi/Projeto-Integrador/imagens/imagenspi/Tela jes, jng, jso, jar.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon bgImage = new ImageIcon(scaledImage);
        JLabel background = new JLabel(bgImage);
        background.setBounds(0, 0, screenWidth, screenHeight);
        layeredPane.add(background, Integer.valueOf(0)); // camada de fundo

        // Escalas para ajuste dinâmico baseado na resolução
        double xRatio = screenWidth / 1365.0;
        double yRatio = screenHeight / 768.0;

        // Criação dos botões com posições baseadas na imagem original
        JButton btnJogadores = createTransparentButton(escalar(135, xRatio), escalar(370, yRatio), escalar(500, xRatio), escalar(100, yRatio), "Jogadores");
        JButton btnRanking   = createTransparentButton(escalar(740, xRatio), escalar(370, yRatio), escalar(500, xRatio), escalar(100, yRatio), "Ranking");
        JButton btnProgresso = createTransparentButton(escalar(135, xRatio), escalar(490, yRatio), escalar(500, xRatio), escalar(100, yRatio), "Progresso");
        JButton btnJogar     = createTransparentButton(escalar(740, xRatio), escalar(490, yRatio), escalar(500, xRatio), escalar(100, yRatio), "Jogar");

        // Ações dos botões
        btnJogadores.addActionListener(e -> JOptionPane.showMessageDialog(this, "Jogadores clicado!"));
        btnRanking.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ranking clicado!"));
        btnProgresso.addActionListener(e -> JOptionPane.showMessageDialog(this, "Progresso clicado!"));
        btnJogar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Jogar clicado!"));

        // Adicionando botões acima da imagem
        layeredPane.add(btnJogadores, Integer.valueOf(1));
        layeredPane.add(btnRanking, Integer.valueOf(1));
        layeredPane.add(btnProgresso, Integer.valueOf(1));
        layeredPane.add(btnJogar, Integer.valueOf(1));

        setContentPane(layeredPane); // define painel como conteúdo da janela
    }

    // Método para escalonar valores proporcionalmente à tela
    private int escalar(int valorOriginal, double proporcao) {
        return (int) (valorOriginal * proporcao);
    }

    // Botão invisível, sem cor, sem borda, totalmente funcional
    private JButton createTransparentButton(int x, int y, int width, int height, String tooltip) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setToolTipText(tooltip);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new addbutton().setVisible(true));
    }
}
