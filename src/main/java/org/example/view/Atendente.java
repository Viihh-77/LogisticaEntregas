package org.example.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import org.example.dao.ClienteDAO;
import org.example.dao.MotoristaDAO;
import org.example.dao.PedidoDAO;
import org.example.model.Cliente;
import org.example.model.Motorista;
import org.example.model.Pedido;

public class Atendente {

    static Scanner input = new Scanner(System.in);

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

    public static void cadastrarPedido() {

        try {
            var pedidoDAO = new PedidoDAO();
            var clienteDAO = new ClienteDAO();

            System.out.println("ID Cliente: ");
            int clienteId = input.nextInt();

            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            if(cliente == null) {
                System.out.println("Erro: Cliente não encontrado!");
                return;
            }

            System.out.println("Data pedido: ");
            LocalDate dataPedido = LocalDate.parse(input.nextLine());

            System.out.println("Volume: ");
            double volume = input.nextDouble();

            System.out.println("Peso: ");
            double peso = input.nextDouble();
            input.nextLine();

            Pedido.Status status = Pedido.Status.PENDENTE;

            Pedido pedido = new Pedido(cliente,dataPedido,volume,peso,status);

            System.out.println("Sucesso: Pedido cadastrado!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Pedido não cadastrado!");
        }
    }
}
