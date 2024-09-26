package com.quiz.paises.entity;

public class Usuario {

    private String nome;
    private float temp;
    private int id;
    private String dica;
    private String pergunta;
    private String alt_a;
    private String alt_b;
    private String alt_c;
    private String alt_d;
    private String alt_correta;
    private String nivel;
    private float pontos;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPontos() {
        return pontos;
    }

    public void setPontos(float pontos) {
        this.pontos = pontos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getAlt_a() {
        return alt_a;
    }

    public void setAlt_a(String alt_a) {
        this.alt_a = alt_a;
    }

    public String getAlt_b() {
        return alt_b;
    }

    public void setAlt_b(String alt_b) {
        this.alt_b = alt_b;
    }

    public String getAlt_c() {
        return alt_c;
    }

    public void setAlt_c(String alt_c) {
        this.alt_c = alt_c;
    }

    public String getAlt_d() {
        return alt_d;
    }

    public void setAlt_d(String alt_d) {
        this.alt_d = alt_d;
    }

    public String getAlt_correta() {
        return alt_correta;
    }

    public void setAlt_correta(String alt_correta) {
        this.alt_correta = alt_correta;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }
}
