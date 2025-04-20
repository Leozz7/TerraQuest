# TerraQuest - Quiz de Países

## 📝 Descrição
TerraQuest é um jogo de quiz sobre países que testa seus conhecimentos geográficos. O jogo possui três níveis de dificuldade (Fácil, Médio e Difícil) com um total de 10 perguntas por partida. Os jogadores podem competir por pontuações mais altas e aparecer no ranking de melhores jogadores.

## ✨ Funcionalidades

- **3 níveis de dificuldade**:
  - Fácil (0.5 pontos por acerto)
  - Médio (1.0 ponto por acerto)
  - Difícil (2.0 pontos por acerto)
  
- **Sistema de dicas** que reduz pela metade o valor da questão
  
- **Ranking** dos melhores jogadores ordenado por pontuação e tempo
  
- **Interface gráfica amigável** com botões arredondados e feedback visual
  
- **Sons de feedback** para acertos e erros
  
- **Cronômetro** para registrar o tempo de resposta

## 🛠️ Tecnologias Utilizadas

- **Java** (linguagem principal)
- **Swing** (para a interface gráfica)
- **MySQL** (banco de dados para armazenar perguntas e ranking)
- **JDBC** (conexão com o banco de dados)

## 🗃️ Estrutura do Banco de Dados

O projeto utiliza duas tabelas:

1. **PERGUNTAS** - Armazena todas as perguntas do quiz
   - Campos: ID, pergunta, a, b, c, d, correta, nivel, dica

2. **RANKING** - Armazena os recordes dos jogadores
   - Campos: NOME, PONTOS, TEMPO

## ▶️ Como Executar

1. Certifique-se de ter o MySQL instalado e configurado
2. Crie um banco de dados chamado `paises_info`
3. Importe a estrutura das tabelas (arquivo SQL fornecido)
4. Execute a classe `QuizGUI` (interface gráfica) ou `Main` (versão console)

## 🎮 Como Jogar

1. Insira seu nome
2. Responda as perguntas selecionando uma das alternativas
3. Use a dica se necessário (reduz o valor da questão pela metade)
4. Ao final de 10 perguntas, veja sua pontuação e tempo
5. Consulte o ranking para ver suas colocações

## 📊 Sistema de Pontuação

- Fácil: 0.5 pontos
- Médio: 1.0 ponto
- Difícil: 2.0 pontos
- Uso de dica: divide o valor da questão por 2
- Pontuação máxima possível: 10.0 pontos

## 📂 Estrutura do Projeto

```
src/
├── com/
│   └── quiz/
│       └── paises/
│           ├── Conexao.java         # Gerencia conexão com o BD
│           ├── Main.java            # Versão console do jogo
│           ├── QuizGUI.java         # Versão gráfica do jogo
│           ├── UsuarioDAO.java      # Operações de banco de dados
│           └── entity/
│               └── Usuario.java     # Entidade do jogador
```

## 🎨 Interface Gráfica

A interface gráfica inclui:
- Botões arredondados e coloridos
- Feedback visual para acertos/erros
- Exibição do cronômetro
- Painel de ranking formatado
- Efeitos sonoros

## 📌 Observações

- O projeto requer o driver JDBC do MySQL
- Os arquivos de áudio devem estar no classpath
- Configure as credenciais do banco de dados na classe `Conexao`

Divirta-se testando seus conhecimentos sobre países do mundo! 🌎
