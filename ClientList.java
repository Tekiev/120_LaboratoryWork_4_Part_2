package DEV120_4_2_Tekiev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientList implements Serializable {
    private static final ClientList instance = new ClientList();
    private List<ClientInfo> clients = new ArrayList<>();
    private Set<PhoneNumber> numbers = new HashSet<>();
    protected ClientList() {
    }
    public void addClient(PhoneNumber number, String name, String address, String birthday) {
        if(numbers.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        clients.add(new ClientInfo(number, name, address, birthday));
        numbers.add(number);
    }
    public void addClient(PhoneNumber number, String name, String address, String director, String contact) {
        if(numbers.contains(number))
            throw new IllegalArgumentException("Such a number has already been registered earlier.");
        clients.add(new ClientInfo(number, name, address, director, contact));
        numbers.add(number);
    }
    public void remove(int index) {
        ClientInfo clientInfo = clients.get(index);
        numbers.remove(clientInfo.getPhoneNumber());
        clients.remove(index);
    }
    public int getClientsCount() {
        return clients.size();
    }
    public ClientInfo getClientInfo(int index) {
        return clients.get(index);
    }
    public List<ClientInfo> getClients() {
        return clients;
    }
    public static ClientList getInstance() {
        return instance;
    }
    @Override
    public String toString() {
        return "ClientList{" +
                "clients=" + clients +
                ", numbers=" + numbers +
                '}';
    }
}
