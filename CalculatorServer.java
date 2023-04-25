import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import Calculator.*;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Cria e inicializa o ORB
            ORB orb = ORB.init(args, null);

            // Cria a instância da calculadora
            CalculatorImpl calculatorImpl = new CalculatorImpl();

            // Obtém uma referência para o RootPOA e ativa o POAManager
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Cria o objeto CORBA e registra-o no serviço de nomes
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(calculatorImpl);
            CalculatorInterface href = CalculatorInterfaceHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent path[] = ncRef.to_name("Calculator");
            ncRef.rebind(path, href);

            System.out.println("Calculadora CORBA pronta e aguardando por invocacoes de metodos ...");

            // Aguarda por invocações de métodos
            orb.run();
        } catch (Exception e) {
            System.err.println("Erro: " + e);
            e.printStackTrace(System.err);
        }
    }
}