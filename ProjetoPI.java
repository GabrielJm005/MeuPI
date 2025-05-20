import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalTime; 
import javax.imageio.ImageIO;
import javax.swing.*; 

// Componentes básicos

// Painel de fundo
class BackgroundPanel extends JPanel {
    private final Image image;
    
    public BackgroundPanel(Image backgroundImage) {
        this.image = backgroundImage;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

// Classes dos componentes
class UIUtils {
    // Chama a imagem de fundo
    public static Image loadImage(String path) {
        File imageFile = new File(path);
        if (imageFile.exists()) {
            return new ImageIcon(path).getImage();
        }
        return null;
    }

    // Config pro btn invisível
    public static JButton createInvisibleButton(Rectangle bounds, ActionListener listener) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(bounds);
        if (listener != null) button.addActionListener(listener);
        return button;
    }

    // btn p opcões
    public static JButton createOptionButton(String text, Rectangle bounds, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(bounds);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBackground(new Color(0, 0, 0, 100));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        if (listener != null) button.addActionListener(listener);
        return button;
    }
}

// Telas do programa

// Tela inicial
public class ProjetoPI extends JFrame {
    // Variável do cursor diferente
    public static Cursor customCursor;

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Configuração do cursor
                String cursorPath = "imagens/imagenspi/icon_mão-removebg-preview.png";
                Image cursorImage = ImageIO.read(new File(cursorPath));
                customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImage, new Point(0, 0), "CursorPersonalizado");
            } catch (Exception e) {
                customCursor = Cursor.getDefaultCursor();
            }
            new ProjetoPI().setVisible(true);
        });
    }

    // Config da tela principal
    public ProjetoPI() {
        configurarJanela();
        initComponents();
    }

    // Config da janela
    private void configurarJanela() {
        setTitle("Bem-vindo!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1800, 735);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(customCursor);
    }

    // Inicializa os componentes da tela
    private void initComponents() {
        // Chama a imagem do fnd
        Image backgroundImage = UIUtils.loadImage("imagens/imagenspi/imagem de entrada.jpg");
        BackgroundPanel panel = new BackgroundPanel(backgroundImage);
        panel.setLayout(null);

        // Adiciona o botão invisível para iniciar
        JButton iniciarButton = UIUtils.createInvisibleButton(
            new Rectangle(400, 445, 570, 70),
            evt -> abrirTelaLogin()
        );
        panel.add(iniciarButton);
        add(panel);
    }

    // Abre a tela de login
    private void abrirTelaLogin() {
        dispose();
        new TelaLogin().setVisible(true);
    }
}

// Tela de login
class TelaLogin extends JFrame {
    // Campos de texto
    private JTextField nomeUsuarioField;
    private JTextField nomeAdministradorField;
    private JPasswordField passwordField;

    // Construtor
    public TelaLogin() {
        configurarJanela();
        initComponents();
    }

    // Config da janela
    private void configurarJanela() {
        setTitle("Metrô day - Cadastro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1800, 730);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(ProjetoPI.customCursor);
    }

    // Inicia os botoes
    private void initComponents() {
        // Configura o painel principal
        Image backgroundImage = UIUtils.loadImage("imagens/imagenspi/imagem cadastro.jpg");
        BackgroundPanel mainPanel = new BackgroundPanel(backgroundImage);
        mainPanel.setLayout(null);

        // Add os campos p digitar
        adicionarCampoNomeUsuario(mainPanel);
        adicionarCampoEmailAdministrador(mainPanel);
        adicionarCampoSenha(mainPanel);

        // Add btn de entrar
        JButton entrarButton = UIUtils.createInvisibleButton(
            new Rectangle(480, 580, 400, 50),
            this::entrarAction
        );
        mainPanel.add(entrarButton);
        add(mainPanel);
    }

    // campo do nome
    private void adicionarCampoNomeUsuario(BackgroundPanel panel) {
        JLabel label = new JLabel("Nome do Usuário:");
        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        label.setBounds(400, 270, 200, 30);
        panel.add(label);

        nomeUsuarioField = new JTextField(20);
        nomeUsuarioField.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
        nomeUsuarioField.setBounds(400, 260, 590, 40);
        nomeUsuarioField.setOpaque(false);
        nomeUsuarioField.setBorder(BorderFactory.createEmptyBorder());
        nomeUsuarioField.setForeground(Color.BLACK);
        nomeUsuarioField.setCaretColor(Color.BLACK);
        panel.add(nomeUsuarioField);

        // config pra esconder a caixa
        nomeUsuarioField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            private void update() {
                label.setVisible(nomeUsuarioField.getText().isEmpty());
            }
        });
    }

    // Add o email
    private void adicionarCampoEmailAdministrador(BackgroundPanel panel) {
        JLabel label = new JLabel("Email de Administrador:");
        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        label.setBounds(400, 360, 220, 30);
        panel.add(label);

        nomeAdministradorField = new JTextField(20);
        nomeAdministradorField.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
        nomeAdministradorField.setBounds(400, 350, 590, 40);
        nomeAdministradorField.setOpaque(false);
        nomeAdministradorField.setBorder(BorderFactory.createEmptyBorder());
        nomeAdministradorField.setForeground(Color.BLACK);
        nomeAdministradorField.setCaretColor(Color.BLACK);
        panel.add(nomeAdministradorField);

        // esconde o campo
        nomeAdministradorField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            private void update() {
                label.setVisible(nomeAdministradorField.getText().isEmpty());
            }
        });
    }

    // Add senha
    private void adicionarCampoSenha(BackgroundPanel panel) {
        JLabel label = new JLabel("Senha:");
        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        label.setBounds(400, 445, 100, 30);
        panel.add(label);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        passwordField.setBounds(400, 440, 590, 40);
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        panel.add(passwordField);

        // esconde campo tbm
        passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent evt) { update(); }
            private void update() {
                label.setVisible(passwordField.getPassword().length == 0);
            }
        });
    }

    // config de onde o botão "entrar" leva
    private void entrarAction(ActionEvent evt) {
        String nomeUsuario = nomeUsuarioField.getText();
        String email = nomeAdministradorField.getText();
        String senha = new String(passwordField.getPassword());

        // Validações básicas
        if (nomeUsuario.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha todos os campos!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conecta ao banco de dados
            String url = "jdbc:mysql://localhost:3306/teste";
            String usuario = "root";
            String senhaDB = "@Joaopedro825";

            try (Connection conexao = DriverManager.getConnection(url, usuario, senhaDB)) {
                // Prepara o comando SQL
                String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setString(1, nomeUsuario);
                    ps.setString(2, email);
                    ps.setString(3, senha);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, 
                        "Cadastro realizado com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);

                    // Limpa os campos
                    nomeUsuarioField.setText("");
                    nomeAdministradorField.setText("");
                    passwordField.setText("");

                    // Abre a próxima tela
                    this.dispose();
                    new TelaDeOpcoes().setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Tela de opções 
class TelaDeOpcoes extends JFrame {
    // Construtor
    public TelaDeOpcoes() {
        configurarJanela();
        initComponents();
    }

    // Config da janela
    private void configurarJanela() {
        setTitle("Tela de opções");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1800, 730);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(ProjetoPI.customCursor);
    }

    // Inicia os componentes
    private void initComponents() {
        // Configura o painel principal
        Image backgroundImage = UIUtils.loadImage("imagens/imagenspi/tela de opções.jpg");
        BackgroundPanel panel = new BackgroundPanel(backgroundImage);
        panel.setLayout(null);

        // botoes de ranking e jogar
        JButton btnOpcao1 = UIUtils.createInvisibleButton(
            new Rectangle(390, 341, 595, 80),
            evt -> abrirTelaDeRegras()
        );
        panel.add(btnOpcao1);

        JButton btnOpcao2 = UIUtils.createInvisibleButton(
            new Rectangle(390, 442, 595, 80),
            evt -> abrirTelaDeRegras()
        );
        panel.add(btnOpcao2);

        add(panel);
    }

    // Método para abrir a tela de regras
    private void abrirTelaDeRegras() {
        dispose();
        new TelaDeRegras().setVisible(true);
    }

    // Abre o painel do metrô
    private void abrirPainelPrincipal() {
        dispose();
        new TelaPrincipal().setVisible(true);
    }
}

// Tela de regras
class TelaDeRegras extends JFrame {
    public TelaDeRegras() {
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("Tela de Regras");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1800, 730);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(ProjetoPI.customCursor);
    }

    private void initComponents() {
        Image backgroundImage = UIUtils.loadImage("imagens/imagenspi/regras.jpg");
        BackgroundPanel panel = new BackgroundPanel(backgroundImage);
        panel.setLayout(null);

        JButton btnIniciar = UIUtils.createInvisibleButton(
            new Rectangle(670, 550, 470, 62),
            evt -> abrirPainelPrincipal()
        );
        panel.add(btnIniciar);
        add(panel);
    }

    private void abrirPainelPrincipal() {
        dispose();
        new TelaPrincipal().setVisible(true);
    }
}

// Tela do painel
// Classe das areas clicaveis
class TelaPrincipal extends JFrame {
    private static class AreaConfig {
        Rectangle bounds;
        String imagePath;
        String title;
        
        AreaConfig(int x, int y, int width, int height, String imagePath, String title) {
            this.bounds = new Rectangle(x, y, width, height);
            this.imagePath = imagePath;
            this.title = title;
        }
    }

    // Configuração das áreas clicáveis
    private final AreaConfig[] areasConfig = {
        new AreaConfig(90, 300, 250, 150, "imagens/imagenspi/painel_zoom_1.jpg", "Painel - Área"),
        new AreaConfig(350, 250, 100, 100, "imagens/imagenspi/05 - Módulo de Comunicação - tela de início.jpg", "Painel- Área 2"),
        new AreaConfig(500, 250, 330, 150, "imagens/imagenspi/02 - ADU e sinaleiras.jpg", "Painel- Area 3"),
        new AreaConfig(950, 250, 370, 150, "imagens/imagenspi/04 - VDU.jpg", "Painel- Area 4")
    };

    // Construtor
    public TelaPrincipal() {
        configurarJanela();
        initComponents();
    }

    // Config da janela
    private void configurarJanela() {
        setTitle("Painel do Metrô");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1800, 730);
        setLocationRelativeTo(null);
        setResizable(false);
        setCursor(ProjetoPI.customCursor);
    }

    // Inicializa os componentes
    private void initComponents() {
        Image backgroundImage = UIUtils.loadImage("imagens/imagenspi/01 - Painel.jpg");
        BackgroundPanel mainPanel = new BackgroundPanel(backgroundImage);
        mainPanel.setLayout(null);

        // Add os listeners de mouse
        configurarListenersMouse(mainPanel);
        add(mainPanel);
    }

    // Configura os listeners de mouse
    private void configurarListenersMouse(BackgroundPanel mainPanel) {
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                Point pontoClique = evt.getPoint();
                for (AreaConfig area : areasConfig) {
                    if (area.bounds.contains(pontoClique)) {
                        System.out.println(LocalTime.now());
                        abrirZoom(area);
                        break;
                    }
                }
            }
        });
    }

    // janelas com zoom
    private void abrirZoom(AreaConfig area) {
        System.out.println(LocalTime.now());
        // Cria a janela de diálogo
        JDialog zoomDialog = new JDialog(this, area.title, true);
        zoomDialog.setCursor(ProjetoPI.customCursor);

            // Código existente para imagens
            System.out.println("1a" + LocalTime.now());
            ImageIcon imagemOriginal = loadCustomImage(area.imagePath, area.title);  
            System.out.println("1b" + LocalTime.now());
            ImageIcon imagemAjustada = ajustarImagemParaTela(imagemOriginal);  
            System.out.println("2" + LocalTime.now());
            JLabel labelImagem = new JLabel(imagemAjustada);
            System.out.println("3" + LocalTime.now());
            labelImagem.setPreferredSize(new Dimension(
                imagemAjustada.getIconWidth(),
                imagemAjustada.getIconHeight()
            ));
            System.out.println("4" + LocalTime.now());
            JButton botaoVoltar = new JButton("Voltar");
            botaoVoltar.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
            botaoVoltar.addActionListener(e -> zoomDialog.dispose());

            JPanel painelPrincipal = new JPanel(new BorderLayout());
            painelPrincipal.add(labelImagem, BorderLayout.CENTER);
            painelPrincipal.add(botaoVoltar, BorderLayout.SOUTH);

            zoomDialog.add(painelPrincipal);
            zoomDialog.pack();
            zoomDialog.setLocationRelativeTo(this);
            zoomDialog.setVisible(true);
            System.out.println(LocalTime.now());
       // }
    }

    // Ajusta o tamanho da imagem para caber na tela
    private ImageIcon ajustarImagemParaTela(ImageIcon imagem) {
        // Pega o tamanho da tela
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraMaxima = (int)(tamanhoTela.width * 0.95);
        int alturaMaxima = (int)(tamanhoTela.height * 0.85);

        // Se a imagem já é menor que o máximo, retorna ela mesma
        if (imagem.getIconWidth() <= larguraMaxima && 
            imagem.getIconHeight() <= alturaMaxima) {
            return imagem;
        }

        // Calcula o novo tamanho mantendo a proporção
        double proporcao = Math.min(
            (double)larguraMaxima / imagem.getIconWidth(),
            (double)alturaMaxima / imagem.getIconHeight()
        );

        int novaLargura = (int)(imagem.getIconWidth() * proporcao);
        int novaAltura = (int)(imagem.getIconHeight() * proporcao);

        // Cria a nova imagem redimensionada
        Image imagemRedimensionada = imagem.getImage().getScaledInstance(
            novaLargura, novaAltura, Image.SCALE_SMOOTH
        );

        return new ImageIcon(imagemRedimensionada);
    }

    // Carrega a imagem do arquivo
    private ImageIcon loadCustomImage(String caminhoImagem, String tituloArea) {
        try {
            // Tenta carregar a imagem do arquivo
            File arquivo = new File(caminhoImagem);
            if (arquivo.exists()) {
                BufferedImage imagem = ImageIO.read(arquivo);
                return new ImageIcon(imagem);
            }
        } catch (Exception e) {
            // Se der erro, retorna null
        }
        return null;
    }
}