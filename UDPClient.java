import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) {
        final String enderecoServidor = "localhost";
        final int portaServidor = 9876;
        Scanner input = new Scanner(System.in);

        try (DatagramSocket socketCliente = new DatagramSocket()) {
            InetAddress endereco = InetAddress.getByName(enderecoServidor);

            // Lê input do usuario
            System.out.println("Escreva o nome do arquivo que deseja recuperar do servidor");
            String nomeArquivo = input.nextLine();
            StringBuilder protocolo = new StringBuilder();
            protocolo.append("GET");
            protocolo.append(" /");
            protocolo.append(nomeArquivo);

            // System.out.println("Cabeçalho do protocolo: " + protocolo.toString());  
            String mensagem = protocolo.toString();
            byte[] dados = mensagem.getBytes();

            // Cria um pacote UDP com os dados a serem enviados, o endereço e a porta do servidor
            DatagramPacket pacoteParaServidor = new DatagramPacket(dados, dados.length, endereco, portaServidor);

            // Envia o pacote para o servidor
            socketCliente.send(pacoteParaServidor);

            System.out.println("Mensagem enviada para o servidor: " + mensagem);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
