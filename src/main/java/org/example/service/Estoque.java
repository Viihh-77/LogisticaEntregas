package org.example.service;

import org.example.view.Atendente;

import java.util.Scanner;

public class Estoque {

    static Scanner input = new Scanner(System.in);

    public static void inicio() {
        boolean sair = false;

        while (!sair) {

            System.out.println(" ");
            System.out.println("|          MENU DE FUNCIONALIDADES          |");
            System.out.println("|                                           |");
            System.out.println("| 1 - Cadastro                              |");
            System.out.println("| 2 - Criar Pedido                          |");
            System.out.println("| 3 - Atribuir Pedido ao Motorista          |");
            System.out.println("| 4 - Registrar Evento de Entrega           |");
            System.out.println("| 5 - Atualizar Status da Entrega           |");
            System.out.println("| 6 - Listar Entregas                       |");
            System.out.println("| 7 - Relatório                             |");
            System.out.println("| 8 - Buscar Pedido                         |");
            System.out.println("| 9 - Cancelar Pedido                       |");
            System.out.println("| 10 - Excluir                              |");
            System.out.println("|                                           |");
            System.out.println("| 0 - Sair                                  |");
            int opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {

                case 1 -> {

                    System.out.println(" ");
                    System.out.println("|   ESCOLHA CADASTRO   |");
                    System.out.println("|                      |");
                    System.out.println("| 1 - Cliente          |");
                    System.out.println("| 2 - Motorista        |");
                    System.out.println(" ");
                    int escolha = input.nextInt();
                    input.nextLine();

                    switch (escolha) {

                        case 1 -> {
                            Atendente.cadastrarCliente();
                            break;
                        }

                        case 2 -> {
                            Atendente.cadastrarMotorista();
                            break;
                        }

                        default -> System.out.println("Erro: Digite um número válido");
                    }
                }

                case 2 -> {
                    Atendente.cadastrarPedido();
                    break;
                }

                case 3 -> {
                    Atendente.gerarEntrega();
                    break;
                }

                case 4 -> {
                    Atendente.registrarEntrega();
                    break;
                }

                case 5 -> {
                    Atendente.atualizarStatusEntrega();
                    break;
                }

                case 6 -> {
                    Atendente.listarEntregas();
                }

                case 7 -> {

                }

                case 8 -> {

                }

                case 9 -> {

                }

                case 10 -> {

                }

                case 0 -> {
                    System.out.println("Encerrando Sistema!");
                    sair = true;
                }

                default -> System.out.println("Erro: Digite um número válido!");
            }
        }
    }
}
