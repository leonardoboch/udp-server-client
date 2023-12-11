import java.net.InetAddress;

public class Client {
    private InetAddress endereco;
    private int port;
    public Client(InetAddress enderecoCliente, int port) {
        this.endereco = enderecoCliente;
        this.port = port;
    }
    public int getPort() {
        return this.port;
    }
    public InetAddress getEndereco() {
        return this.endereco;
    }

}
