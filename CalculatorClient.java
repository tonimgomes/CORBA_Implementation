import Calculator.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            // Cria e inicializa o ORB
            ORB orb = ORB.init(args, null);

            // Obtém uma referência para o serviço de nomes
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Obtém uma referência para a calculadora
            NameComponent path[] = ncRef.to_name("Calculator");
            CalculatorInterface calculator = CalculatorInterfaceHelper.narrow(ncRef.resolve(path));

            // Cria um scanner para ler a entrada do usuário
            Scanner scanner = new Scanner(System.in);

            // Menu de opções
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Soma");
            System.out.println("2 - Subtracaoo");
            System.out.println("3 - Multiplicacao");
            System.out.println("4 - Divisao");
            System.out.print("Opcao: ");

            // Lê a opção escolhida pelo usuário
            int opcao = scanner.nextInt();

            // Lê os números da operação
            System.out.print("Digite o primeiro numero: ");
            float numero1 = scanner.nextFloat();
            System.out.print("Digite o segundo numero: ");
            float numero2 = scanner.nextFloat();

            // Realiza a operação escolhida pelo usuário
            float resultado;
            switch (opcao) {
                case 1:
                    resultado = calculator.add(numero1, numero2);
                    System.out.println(numero1 + " + " + numero2 + " = " + resultado);
                    break;
                case 2:
                    resultado = calculator.subtract(numero1, numero2);
                    System.out.println(numero1 + " - " + numero2 + " = " + resultado);
                    break;
                case 3:
                    resultado = calculator.multiply(numero1, numero2);
                    System.out.println(numero1 + " * " + numero2 + " = " + resultado);
                    break;
                case 4:
                    resultado = calculator.divide(numero1, numero2);
                    System.out.println(numero1 + " / " + numero2 + " = " + resultado);
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e);
            e.printStackTrace(System.err);
        }
    }
}