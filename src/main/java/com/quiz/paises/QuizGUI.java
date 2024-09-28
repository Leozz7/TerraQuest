package com.quiz.paises;

import com.quiz.paises.entity.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

class RoundedButton extends JButton {
    private final int arcWidth;
    private final int arcHeight;

    public RoundedButton(String text, int arcWidth, int arcHeight) {
        super(text);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.BLACK);
        setFont(new Font("Comic Sans MS", Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 100));
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
        g2.dispose();
    }
}

public class QuizGUI extends JFrame {
    private final String[] resps = {"A", "B", "C", "D"};
    private final Usuario u = new Usuario();
    private final UsuarioDAO dao = new UsuarioDAO();
    private float pont = 0;
    private int questaoAtual = 0;
    private boolean usouDica = false;
    private final Random random = new Random();
    private final ArrayList<Integer> usados = new ArrayList<>();
    private long tempoInicio;
    private int maxQuest = 10;

    private JLabel lblPergunta;
    private RoundedButton[] btnRespostas;
    private RoundedButton btnDica;
    private JLabel lblResultado;
    private RoundedButton btnProxima;
    private JLabel lblPontuacao;
    private RoundedButton btnJogarNovamente;
    private RoundedButton btnExibirRank;
    private RoundedButton btnIniciar;

    public QuizGUI() {
        setTitle("TerraQuest - Quiz de Países");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        lblPergunta = new JLabel("<html>Clique em 'Próxima' para começar <br> Pontuação Máxima: 10.0 <br> Fácil: 0.5 | Média: 1.0 | Difícil: 2.0</html>", SwingConstants.CENTER);
        lblPergunta.setFont(new Font("Verdana", Font.BOLD, 26));
        lblPergunta.setForeground(Color.BLACK);
        lblPontuacao = new JLabel("Pontuação: 0.0", SwingConstants.CENTER);
        lblPontuacao.setFont(new Font("Verdana", Font.BOLD, 30));
        lblPontuacao.setForeground(new Color(255, 140, 0));
        lblResultado = new JLabel("", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Verdana", Font.PLAIN, 18));
        lblResultado.setForeground(Color.BLACK);

        btnProxima = new RoundedButton("Próxima Questão", 20, 20);
        btnProxima.setBackground(new Color(52, 152, 219));

        btnIniciar = new RoundedButton("Iniciar", 20, 20);
        btnIniciar.setBackground(new Color(52, 152, 219));
        iniciar();
        btnDica = new RoundedButton("Dica", 20, 20);
        btnDica.setBackground(new Color(241, 196, 15));

        btnRespostas = new RoundedButton[4];
        Color respostaColor = new Color(52, 152, 219);

        for (int i = 0; i < btnRespostas.length; i++) {
            btnRespostas[i] = new RoundedButton(resps[i], 20, 20);
            btnRespostas[i].setEnabled(false);
            btnRespostas[i].setBackground(respostaColor);
        }

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(lblPergunta, gbc);

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;

        for (int i = 0; i < btnRespostas.length; i++) {
            gbc.gridy++;
            panelCentral.add(btnRespostas[i], gbc);
        }

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInferior.setOpaque(false);
        panelInferior.add(btnDica);
        panelInferior.add(btnProxima);
        panelInferior.add(btnIniciar);

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.setOpaque(false);
        panelSuperior.add(lblPontuacao);
        panelSuperior.add(lblResultado);

        btnJogarNovamente = new RoundedButton("Jogar Novamente", 20, 20);
        btnJogarNovamente.setBackground(new Color(39, 174, 96));
        btnJogarNovamente.setVisible(false);
        panelInferior.add(btnJogarNovamente);

        btnExibirRank = new RoundedButton("Exibir Rank", 20, 20);
        btnExibirRank.setBackground(new Color(255,105,97));
        btnExibirRank.setVisible(false);
        panelInferior.add(btnExibirRank);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        btnProxima.addActionListener(e -> {
            if (questaoAtual < maxQuest) {
                novaQuestao();
            } else {
                fimDoQuiz();
            }
        });

        for (int i = 0; i < btnRespostas.length; i++) {
            final int idx = i;
            btnRespostas[i].addActionListener(e -> verificarResposta(idx));
        }

        btnDica.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "DICA: " + u.getDica());
            usouDica = true;
            habilitarBotoesRespostas(true);
            btnDica.setEnabled(false);
        });

        btnIniciar.addActionListener(e -> {
            pedirNome();
            btnIniciar.setVisible(false);
            btnProxima.setVisible(true);
            tempoInicio = System.currentTimeMillis();
        });

        btnExibirRank.addActionListener(e -> {
            habilitarBotoesRespostas(false);
            sumirBotoesResposta(false);

            lblPergunta.setText("<html><pre>" + dao.exibirRank() + "</pre></html>");
        });

        btnJogarNovamente.addActionListener(e -> {
            pedirNome();
            tempoInicio = System.currentTimeMillis();
        });

        btnProxima.setEnabled(true);
    }

    private void pedirNome() {
        String nomeJogador = JOptionPane.showInputDialog("Digite seu nome:");
        if (nomeJogador != null && !nomeJogador.trim().isEmpty()) {
            u.setNome(nomeJogador);
            reiniciarQuiz();
        } else {
            JOptionPane.showMessageDialog(null, "Nome não pode ser vazio.");
        }
    }

    private void novaQuestao() {
        perguntaAlternativa();
    }

    private void perguntaAlternativa() {
        int pais, perg;

        if (questaoAtual < 4) {
            perg = 1;
        } else if (questaoAtual < 8) {
            perg = 2;
        } else {
            perg = 3;
        }

        do {
            if (perg == 1) {
                pais = random.nextInt(50 - 1 + 1) + 1;
            } else if (perg == 2) {
                pais = random.nextInt(100 - 51 + 1) + 51;
            } else {
                pais = random.nextInt(150 - 101 + 1) + 101;
            }
        } while (usados.contains(pais));
        usados.add(pais);

        u.setId(pais);

        if (perg == 1) {
            dao.perguntaFacil(u);
        } else if (perg == 2) {
            dao.perguntaMedia(u);
        } else {
            dao.perguntaDificil(u);
        }

        lblPergunta.setText("<html>" + "Nível: " + u.getNivel() + " <br> " + u.getPergunta() + "</html>");
        lblResultado.setText("");

        btnRespostas[0].setText("A) " + u.getAlt_a());
        btnRespostas[1].setText("B) " + u.getAlt_b());
        btnRespostas[2].setText("C) " + u.getAlt_c());
        btnRespostas[3].setText("D) " + u.getAlt_d());

        habilitarBotoesRespostas(true);
        btnDica.setEnabled(true);
    }

    private void sumirBotoesResposta(boolean b) {
        for (RoundedButton btn : btnRespostas) {
            btn.setVisible(b);
        }
    }

    private void iniciar() {
        btnIniciar.setVisible(true);
        btnProxima.setVisible(false);
    }

    private void habilitarBotoesRespostas(boolean b) {
        for (RoundedButton btn : btnRespostas) {
            btn.setEnabled(b);
        }
    }

    private void verificarResposta(int idx) {
        float valorQst;

        if (u.getNivel().equals("Fácil")) {
            valorQst = 0.5f;
        } else if (u.getNivel().equals("Médio")) {
            valorQst = 1f;
        } else {
            valorQst = 2f;
        }

        if (usouDica) {
            valorQst /= 2;
        }

        if (resps[idx].equals(u.getAlt_correta())) {
            lblResultado.setText("Você acertou!");
            lblResultado.setForeground(new Color(46, 204, 113));
            questaoAtual++;

            pont += valorQst;
        } else {
            lblResultado.setText("Você errou. A resposta correta é: " + u.getAlt_correta());
            lblResultado.setForeground(new Color(231, 76, 60));
            questaoAtual++;
        }
        lblPontuacao.setText("Pontuação: " + pont);

        usouDica = false;

        desabilitarBotoes();
    }

    private void desabilitarBotoes() {
        habilitarBotoesRespostas(false);
        btnDica.setEnabled(false);
    }

    private void fimDoQuiz() {
        long tempoFim = System.currentTimeMillis();
        long tempoTotal = tempoFim - tempoInicio;
        float tempoTotalSegundos = tempoTotal / 1000f;

        lblPergunta.setText("Quiz Finalizado!");
        lblResultado.setText(String.format("Pontuação Total: %.1f/10.0 Tempo: %.2f segundos", pont, tempoTotalSegundos));
        btnProxima.setEnabled(false);
        btnJogarNovamente.setVisible(true);
        btnExibirRank.setVisible(true);

        u.setPontos(pont);
        u.setTemp(tempoTotalSegundos);

        dao.rank(u);
    }

    private void reiniciarQuiz() {
        pont = 0;
        questaoAtual = 0;
        usados.clear();
        lblPontuacao.setText("Pontuação: 0.0");
        btnProxima.setEnabled(true);
        btnJogarNovamente.setVisible(false);
        btnExibirRank.setVisible(false);
        habilitarBotoesRespostas(true);
        sumirBotoesResposta(true);
        novaQuestao();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGUI().setVisible(true));
    }
}