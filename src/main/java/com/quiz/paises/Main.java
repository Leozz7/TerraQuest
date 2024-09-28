package com.quiz.paises;

import com.quiz.paises.entity.Usuario;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int start = 0;
        ArrayList<Integer> usados = new ArrayList<>();

        Usuario u = new Usuario();
        UsuarioDAO dao = new UsuarioDAO();

        while (start != 3) {
            System.out.println("[1] Iniciar Quiz \n[2] Exibir Rank \n[3] Sair");

            start = scanner.nextInt();
            scanner.nextLine();

            if (start == 1) {

                long tempoInicio = System.currentTimeMillis();

                System.out.println("NOME: ");
                u.setNome(scanner.nextLine());

                float pontuacaoTotal = 0;
                usados.clear();
                int facil = 0 , media = 0;

                for (int j = 0; j < 10; j++) {
                    int pais, perg;

                    if (facil < 4) {
                        perg = 1;
                    } else if (media < 4) {
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
                        pontuacaoTotal += fazerPergunta(scanner, u, 0.5f);
                        facil++;
                    } else if (perg == 2) {
                        dao.perguntaMedia(u);
                        pontuacaoTotal += fazerPergunta(scanner, u, 1f);
                        media++;
                    } else {
                        dao.perguntaDificil(u);
                        pontuacaoTotal += fazerPergunta(scanner, u, 2f);
                    }
                }

                long tempoFim = System.currentTimeMillis();

                long tempoTotal = tempoFim - tempoInicio;

                float tempoTotalSegundos = tempoTotal / 1000f;

                System.out.println("QUIZ FINALIZADO");
                System.out.println("SUA PONTUAÇÃO TOTAL: " + pontuacaoTotal);
                System.out.println("VOCÊ DEMOROU: " + tempoTotalSegundos + " segundos para fazer o quiz.");


                u.setPontos(pontuacaoTotal);
                u.setTemp(tempoTotalSegundos);

                dao.rank(u);
            }

            if (start == 2) {
                System.out.println(dao.exibirRank());
            }
        }
        scanner.close();
    }

    public static float fazerPergunta(Scanner scanner, Usuario u, float valorQst) {
        System.out.println("NÍVEL: " + u.getNivel());
        System.out.println("PERGUNTA: " + u.getPergunta());
        System.out.println("[A] " + u.getAlt_a());
        System.out.println("[B] " + u.getAlt_b());
        System.out.println("[C] " + u.getAlt_c());
        System.out.println("[D] " + u.getAlt_d());
        System.out.println("[F] DICA");

        String resp2 = scanner.nextLine().toLowerCase();

        if (resp2.equals("f")) {
            System.out.println("DICA: " + u.getDica());
            valorQst /= 2;
            resp2 = scanner.nextLine().toLowerCase();
        }

        if (resp2.equals(u.getAlt_correta().toLowerCase())) {
            System.out.println("Você acertou!");
            System.out.println("+" + valorQst + " pontos");
            return valorQst;
        } else {
            System.out.println("Você errou :( A resposta correta é: " + u.getAlt_correta());
            return 0;
        }
    }
}
