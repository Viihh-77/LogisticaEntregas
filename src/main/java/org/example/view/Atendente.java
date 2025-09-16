package org.example.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.example.dao.ClienteDAO;
import org.example.dao.EntregaDAO;
import org.example.dao.MotoristaDAO;
import org.example.dao.PedidoDAO;
import org.example.model.Cliente;
import org.example.model.Entrega;
import org.example.model.Motorista;
import org.example.model.Pedido;

public class Atendente {

    static Scanner input = new Scanner(System.in);

    // Cadastrar Cliente
    public static void cadastrarCliente() {

        try {
            var dao = new ClienteDAO();

            System.out.println("Nome: ");
            String nome = input.nextLine();

            System.out.println("CPF: ");
            String cpf = input.nextLine();

            System.out.println("Endereço: ");
            String endereco = input.nextLine();

            System.out.println("Cidade: ");
            String cidade = input.nextLine();

            System.out.println("Estado: ");
            String estado = input.nextLine();

            Cliente cliente = new Cliente(nome,cpf,endereco,cidade,estado);
            dao.cadastrarCliente(cliente);

            System.out.println("Sucesso: Cliente cadastrado!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Cliente não cadastrado!");
        }
    }

    // Cadastrar Motorista
    public static void cadastrarMotorista() {

        try {
            var dao = new MotoristaDAO();

            System.out.println("Nome: ");
            String nome = input.nextLine();

            System.out.println("CNH: ");
            String cnh = input.nextLine();

            System.out.println("Veiculo: ");
            String veiculo = input.nextLine();

            System.out.println("Cidade Base: ");
            String cidade_base = input.nextLine();

            Motorista motorista = new Motorista(nome,cnh,veiculo,cidade_base);
            dao.cadastrarMotorista(motorista);

            System.out.println("Sucesso: Motorista cadastrado!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Motorista não cadastrado!");
        }
    }

    // Criar Pedido
    public static void cadastrarPedido() {

        try {
            var pedidoDAO = new PedidoDAO();
            var clienteDAO = new ClienteDAO();

            System.out.println("ID Cliente: ");
            int clienteId = input.nextInt();
            input.nextLine();

            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            if(cliente == null) {
                System.out.println("Erro: Cliente não encontrado!");
                return;
            }

            System.out.println("Data pedido: ");
            String data = input.nextLine();
            LocalDate dataPedido = LocalDate.parse(data);

            System.out.println("Volume: ");
            double volume = input.nextDouble();

            System.out.println("Peso: ");
            double peso = input.nextDouble();
            input.nextLine();

            Pedido.Status status = Pedido.Status.Pendente;

            Pedido pedido = new Pedido(cliente,dataPedido,volume,peso,status);
            pedidoDAO.cadastrarPedido(pedido);

            System.out.println("Sucesso: Pedido cadastrado!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Pedido não cadastrado!");
        }
    }

    //Atribuir Pedido a Motorista
    public static void gerarEntrega () {
        try {
            var entregaDAO = new EntregaDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();
            MotoristaDAO motoristaDAO = new MotoristaDAO();

            System.out.println("ID Pedido: ");
            int pedido_id = input.nextInt();

            System.out.println("ID Motorista: ");
            int motorista_id = input.nextInt();
            input.nextLine();

            Pedido pedido = pedidoDAO.buscarPorId(pedido_id);
            Motorista motorista = motoristaDAO.buscarPorId(motorista_id);
            if(pedido == null || motorista == null){
                System.out.println("Erro: Pedido ou Motorista não encontrado!");
                return;
            }

            LocalDate dataSaida = LocalDate.now();
            Entrega.Status status = Entrega.Status.EM_ROTA;

            Entrega entrega = new Entrega(pedido,motorista,dataSaida,null,status);
            entregaDAO.cadastrarEntrega(entrega);

            System.out.println("Sucesso: Entrega cadastrada!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Entrega não cadastrada!");
        }
    }

    //Registrar evento de entrega
    public static void registrarEntrega() {
        try {
            var entregaDAO = new EntregaDAO();

            System.out.println("ID Entrega: ");
            int entregaId = input.nextInt();
            input.nextLine();

            boolean atrasada = false;

            entregaDAO.registrarEntrega(entregaId, atrasada);

            System.out.println("Sucesso: Entrega registrada!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Entrega não registrada!");
        }
    }

    // Atualizar status da entrega
    public static void atualizarStatusEntrega() {
        try {
            var entregaDAO = new EntregaDAO();

            System.out.println("ID Entrega: ");
            int entregaID = input.nextInt();
            input.nextLine();

            System.out.println("'Em_Rota' | 'Entrega' | 'Atrasada' - Novo Status: ");
            String status = input.nextLine();

            if (!status.equals("Em_Rota") && !status.equals("Entrega") && !status.equals("Atrasada")) {
                System.out.println("Erro: Status inválido!");
                return;
            }

            entregaDAO.atualizarStatus(entregaID,status);

            System.out.println("Sucesso: Status atualizado!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Status não atualizado!");
        }
    }

    //Listar todas as entregas com Cliente e Motorista
    public static void listarEntregas() {
        try {
            var entregaDAO = new EntregaDAO();
            List<Entrega> entregas = entregaDAO.listarEntregas();

            for (Entrega entrega : entregas) {
                System.out.println(" ");
                System.out.println("| Entrega ID: " + entrega.getId());
                System.out.println("| Pedido ID: " + entrega.getPedido().getId());
                System.out.println("| Motorista: " + entrega.getMotorista().getNome());
                System.out.println("| Data Saida: " + entrega.getData_saida());
                System.out.println("| Data Entrada: " + entrega.getData_entrega());
                System.out.println("| Status: " + entrega.getStatus());
                System.out.println(" ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Entrega não pode ser listada!");
        }
    }

    // Relatorio: Total de entregas por Motorista
    public static void entregasPorMotorista() {
        var EntregaDAO = new EntregaDAO();

        try {
            EntregaDAO.entregaPorMotorista();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Relatório não pode ser gerado!");
        }
    }

    // Relatorio: Clientes com Maior Volume Entregue
    public static void maiorVolumeEntregue() {
        var EntregaDAO = new EntregaDAO();

        try {
            EntregaDAO.maiorVolumeEntregue();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Relatório não pode ser gerado!");
        }
    }

    // Relatorio: Pedidos Pendentes por Estado
    public static void pedidosPendentes() {
        var PedidoDAO = new PedidoDAO();

        try {
            PedidoDAO.pedidosPendentes();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Relatório não pode ser gerado!");
        }
    }

    // Relatorio: Entregas Atrasadas por Cidades
    public static void entregasAtrasadas() {
        var EntregaDAO = new EntregaDAO();

        try {
            EntregaDAO.entregasAtrasadas();
        } catch (SQLException e) {
            System.out.println("Erro: Ralatório não pode ser gerado!");
        }
    }

    // Buscar Pedido por CPF/CNPJ do Cliente
    public static void buscarPorCPF() {
        try {
            var PedidoDAO = new PedidoDAO();

            System.out.println("CPF: ");
            String cpf = input.nextLine();

            List<Pedido> pedidos = PedidoDAO.buscarPorCPF(cpf);

            for (Pedido pedido : pedidos) {
                System.out.println("ID: " + pedido.getId());
                System.out.println("Cliente: " + pedido.getId_cliente().getNome());
                System.out.println("Data: " + pedido.getData_pedido());
                System.out.println("Volume: " + pedido.getVolume());
                System.out.println("Peso: " + pedido.getPeso());
                System.out.println("Status: " + pedido.getStatus());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Não foi possivel fazer a busca do pedido!");
        }
    }

    // Cancelar Pedido
    public static void cancelarPedido() {
        try {
            var PedidoDAO = new PedidoDAO();

            System.out.println("ID: ");
            int id = input.nextInt();
            input.nextLine();

            PedidoDAO.cancelarPedido(id);

            System.out.println("Sucesso: Pedido cancelado!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Não foi possivel cancelar o pedido!");
        }
    }

    // Excluir Entrega
    public static void excluirEntrega() {
        try {
            var EntregaDAO = new EntregaDAO();

            System.out.println("ID: ");
            int id = input.nextInt();
            input.nextLine();

            EntregaDAO.excluirEntrega(id);

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Não foir possivel excluir a entrega!");
        }
    }

    // Excluir Cliente
    public static void excluirCliente() {
        try {
            var ClienteDAO = new ClienteDAO();

            System.out.println("ID: ");
            int id = input.nextInt();
            input.nextLine();

            boolean sucesso = ClienteDAO.excluirCliente(id);

            if (sucesso) {
                System.out.println("Sucesso: Cliente excluido!");
            } else {
                System.out.println("Erro: Não foi possivel excluir o cliente!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Não foi possivel excluir o cliente!");
        }
    }

    // Excluir Motorista
    public static void excluirMotorista() {
        try {
            var MotoristaDAO = new MotoristaDAO();

            System.out.println("ID: ");
            int id = input.nextInt();
            input.nextLine();

            boolean sucesso = MotoristaDAO.excluirMotorista(id);

            if (sucesso) {
                System.out.println("Sucesso: Motorista excluido!");
            } else {
                System.out.println("Erro: Não foi possivel excluir o motorista!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Não foi possivel excluir o motorista!");
        }
    }
}
