import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {
    private final static int BUFFER_SIZE = 9876;

    public static void main(String[] args) {
        final int portaServidor = 9876;
        DatagramSocket serverSocket = null;
        FileInputStream fsInput = null;
        Client client = null;
        try {
            serverSocket = new DatagramSocket(portaServidor);
            byte[] dadosRecebidos = new byte[BUFFER_SIZE];
            DatagramPacket pacoteRecebido = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
            serverSocket.receive(pacoteRecebido);
            String recebidoCliente = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
            System.out.println(recebidoCliente);

            //CRIACAO DO CLIENTE
            client = new Client(pacoteRecebido.getAddress(), pacoteRecebido.getPort());

            if(recebidoCliente.contains("GET")) {
                File file = findFile(recebidoCliente.substring(5));
                fsInput = new FileInputStream(file);
                int bytesLidos = 0;
                while((bytesLidos = fsInput.read(dadosRecebidos)) != -1) {
                    System.out.println(bytesLidos);
                    DatagramPacket pacoteEnviar = new DatagramPacket(dadosRecebidos, bytesLidos, client.getEndereco(), client.getPort());
                    serverSocket.send(pacoteEnviar);
                }
                System.out.println("Arquivo enviado com sucesso");

            }


        }catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static File findFile(String path) {
        String currentPath = System.getProperty("user.dir");
        System.out.println("Diretorio atual do processo:: " + currentPath);
        String [] fileParams = path.split("\\.");
        System.out.println(fileParams.toString());
        if(fileParams.length <= 1) {
            System.out.println("Formato da string do arquivo errado");
        }
        File dir = new File(currentPath);
        FileFinder ff = new FileFinder(fileParams[0],fileParams[1]);
        String [] arr = dir.list(ff);

        if(arr == null || arr.length == 0) {
            System.out.println("Arquivo não encontrado no diretorio " + currentPath);
            return null;
        }
        else {
            for (String s : arr) {
                System.out.println(s + " encontrado.");
            }
            return new File(currentPath + "/" + path);

        }
    }
}
