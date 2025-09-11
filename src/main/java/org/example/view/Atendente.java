package org.example.view;

import java.sql.SQLException;
import java.time.LocalDate;
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
}
