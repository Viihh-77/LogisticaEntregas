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
                    break;
                }

                case 7 -> {

                    System.out.println(" ");
                    System.out.println("|            ESCOLHA RELATÓRIO            |");
                    System.out.println("|                                         |");
                    System.out.println("| 1 - Total de Entregas por Motorista     |");
                    System.out.println("| 2 - Clientes com Maior Volume Entregue  |");
                    System.out.println("| 3 - Pedidos Pendentes por Estado        |");
                    System.out.println("| 4 - Entregas Atrasadas por Cidade       |");
                    System.out.println(" ");
                    int escolha = input.nextInt();
                    input.nextLine();

                    switch (escolha) {

                        case 1 -> {
                            Atendente.entregasPorMotorista();
                            break;
                        }

                        case 2 -> {
                            Atendente.maiorVolumeEntregue();
                            break;
                        }

                        case 3 -> {
                            Atendente.pedidosPendentes();
                            break;
                        }

                        case 4 -> {
                            Atendente.entregasAtrasadas();
                            break;
                        }
                    }
                }

                case 8 -> {
                    Atendente.buscarPorCPF();
                    break;
                }

                case 9 -> {
                    Atendente.cancelarPedido();
                    break;
                }

                case 10 -> {

                    System.out.println(" ");
                    System.out.println("|     ESCOLHA EXCLUIR     |");
                    System.out.println("|                         |");
                    System.out.println("| 1 - Excluir Entrega     |");
                    System.out.println("| 2 - Excluir Cliente     |");
                    System.out.println("| 3 - Excluir Motorista   |");
                    System.out.println(" ");
                    int escolha = input.nextInt();
                    input.nextLine();

                    switch (escolha) {

                        case 1 -> {
                            Atendente.excluirEntrega();
                            break;
                        }

                        case 2 -> {
                            Atendente.excluirCliente();
                            break;
                        }

                        case 3 -> {
                            Atendente.excluirMotorista();
                            break;
                        }
                    }
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
