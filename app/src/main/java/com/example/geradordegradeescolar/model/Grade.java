package com.example.geradordegradeescolar.model;

import android.content.Context;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Grade {

    public List<Disciplina> geraGrade(List<Disciplina> disciplinas, Context context) {

        List<Disciplina> disciplinasDisponiveis = new ArrayList<>();
        List<Disciplina> disciplinasDiasIguais = new ArrayList<>();
        List<Disciplina> gradeDisciplinas = new ArrayList<>();

        int primeiroOndeRequisito, segundoOndeRequisito;

        Conexao conexao = new Conexao(context);
        DisciplinaDAO dao = new DisciplinaDAO(conexao.getConexao());

        //preenche lista com as disciplinas disponiveis
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getSituacao().equals("Disponível")) {
                disciplinasDisponiveis.add(disciplina);
            }
        }

        if (disciplinasDisponiveis.size() > 1) {

            //ordena lista pelos dias da semana
            disciplinasDisponiveis.sort(Comparator.comparing(Disciplina::diaDaSemanaParaInt));

            //verifica se existem disciplinas no mesmo dia
            for (int primeiraPosicao = 0; primeiraPosicao < disciplinasDisponiveis.size(); primeiraPosicao++) {
                disciplinasDiasIguais.clear();
                disciplinasDiasIguais.add(disciplinasDisponiveis.get(primeiraPosicao));

                for (int segundaPosicao = primeiraPosicao + 1; segundaPosicao < disciplinasDisponiveis.size(); segundaPosicao++) {

                    //se houver disciplinas no mesmo dia, coloca ela na lista disciplinasDiasIguais
                    if (disciplinasDisponiveis.get(primeiraPosicao).equals(disciplinasDisponiveis.get(segundaPosicao))) {
                        disciplinasDiasIguais.add(disciplinasDisponiveis.get(segundaPosicao));
                    }
                }

                //se a lista for maior que 1, logo temos disciplinas no mesmo dia
                if (disciplinasDiasIguais.size() > 1) {

                    //se a lista for maior que 2
                    if (disciplinasDiasIguais.size() > 2) {

                        boolean execucao = true;
                        int x = 0;
                        int y = 1;

                        //incia loop para escolher a disciplina\disciplinas do dia
                        while (execucao) {

                            //verifica se o horario das disciplinas são conflitantes
                            if (!comparaPeriodo(disciplinasDiasIguais.get(x), disciplinasDiasIguais.get(y))) {

                                //se for, busca qual disciplina e mais pré requisito em outras disciplinas
                                primeiroOndeRequisito = dao.buscaOndeERequisito(disciplinasDiasIguais.get(x).getId()).size();
                                segundoOndeRequisito = dao.buscaOndeERequisito(disciplinasDiasIguais.get(y).getId()).size();

                                //faz comparação para saber qual disciplina vai ser escolhida
                                if (primeiroOndeRequisito > segundoOndeRequisito) {
                                    disciplinasDiasIguais.remove(y);
                                    if (disciplinasDiasIguais.size() == 1) execucao = false;
                                    /*
                                }else if(primeiroOndeRequisito == segundoOndeRequisito){
                                    execucao = false;
                                     */
                                } else {
                                    disciplinasDiasIguais.remove(x);
                                    if (disciplinasDiasIguais.size() == 1) execucao = false;
                                }

                            /*se as disciplinas não tem horarios conflitantes, verifica se o
                            tamanho da lista é maior que dois*/
                            } else if (disciplinasDiasIguais.size() > 2) {

                                /*se a lista for menor que 2, verifica se y é menor que o tamanho da lista,
                                e aumenta o valor de y, para comparar com as outras disciplinas*/
                                if (y < disciplinasDiasIguais.size()) y++;
                                /*mas se o valor de y tem o mesmo valor da lista, quer dizer que a disciplina
                                no valor x não conflita com nenhum disciplina que esteve no valor y, logo
                                as disciplinas de y conflitam entre si*/
                                else {
                                    x++;
                                    y = 2;
                                }
                            /*se a lista não for maior que dois, adiciona as disciplinas na lista a grade
                            final*/
                            } else {
                                gradeDisciplinas.addAll(disciplinasDiasIguais);
                                execucao = false;
                            }

                        }

                        //se na lista tiver somente 2 numeros
                    } else {

                        //remove os dois pontos do horario da aula dos dois dias, para ver se as aulas acontem ao mesmo tempo
                        String primeiroPeriodo = disciplinasDiasIguais.get(0).getPeriodo();
                        String segundoPeriodo = disciplinasDiasIguais.get(1).getPeriodo();

                        /*se as disciplinas tiverem aula no mesmo horario, verifica qual disciplina é requisito de mais disciplinas e
                        adiciona ela na lista final*/
                        if (primeiroPeriodo.equals(segundoPeriodo) || primeiroPeriodo.equals("Todo Período") || segundoPeriodo.equals("Todo Periodo")){

                            primeiroOndeRequisito = dao.buscaOndeERequisito(disciplinasDiasIguais.get(0).getId()).size();
                            segundoOndeRequisito = dao.buscaOndeERequisito(disciplinasDiasIguais.get(1).getId()).size();

                            if (primeiroOndeRequisito > segundoOndeRequisito)
                                gradeDisciplinas.add(disciplinasDiasIguais.get(0));
                            else gradeDisciplinas.add(disciplinasDiasIguais.get(1));

                        /*se os horarios das aulas foram diferentes, adiciona as duas disciplinas
                        na lista final de disciplinas*/
                        } else gradeDisciplinas.addAll(disciplinasDiasIguais);
                    }

                } else gradeDisciplinas.addAll(disciplinasDiasIguais);

            }
        }
        //retorna a lista com as disciplinas da grade
        return gradeDisciplinas;

    }

    public boolean comparaPeriodo(Disciplina primeiraDisciplina, Disciplina segundaDisciplina) {

        String primeiroPeriodo = primeiraDisciplina.getPeriodo();
        String segundoPeriodo = segundaDisciplina.getPeriodo();

        /*se for true - horarios diferentes
        se for false - horarios iguais ou conflitantes*/
        return primeiroPeriodo.equals(segundoPeriodo) || primeiroPeriodo.equals("Todo Período")
                || segundoPeriodo.equals("Todo Periodo");

    }


}
