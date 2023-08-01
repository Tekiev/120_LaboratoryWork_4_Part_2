package DEV120_4_2_Tekiev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
public class MainFrame extends JFrame {
    private final ClientListTableModel clientsTableModel = new ClientListTableModel();
    private final JTable clientsTable = new JTable();
    private final PersonDialog clientPersonDialog = new PersonDialog(this);
    private final CompanyDialog clientCompanyDialog = new CompanyDialog(this);
    ClientList clientListOut = new ClientList();
    ClientList clientListIn = new ClientList();
    public MainFrame() {
        super("AvalonTelecom Ltd. clients list");
        super.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Да", "Нет"};
                int n = JOptionPane
                        .showOptionDialog(e.getWindow(),"Вы уверены что хотите закрыть приложение?",
                                "Подтверждение закрытия", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    saveFile();
                    e.getWindow().setVisible(false);
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
                else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        initMenu();
        initLayout();
        setBounds(300, 200, 1200, 400);
        openFile();
    }
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);
        addMenuItemTo(operations, "Add", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> toClickAddButton());
        addMenuItemTo(operations, "Change", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changeClient());
        addMenuItemTo(operations, "Delete", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delClient());
        setJMenuBar(menuBar);
    }
    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
                               KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }
    private void initLayout() {
        clientsTable.setModel(clientsTableModel);
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(clientsTable.getTableHeader(), BorderLayout.NORTH);
        add(new JScrollPane(clientsTable), BorderLayout.CENTER);
    }
    private void addClient() {
        clientPersonDialog.prepareForAdd();
        while (clientPersonDialog.showModal()) {
            try {
                PhoneNumber pn = new PhoneNumber(clientPersonDialog.getAreaCode(), clientPersonDialog.getPhoneNum());
                clientsTableModel.addClient(pn, clientPersonDialog.getClientName(), clientPersonDialog.getClientAddr(), clientPersonDialog.getClientBirthday());
                clientListOut.addClient(pn, clientPersonDialog.getClientName(), clientPersonDialog.getClientAddr(), clientPersonDialog.getClientBirthday());
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Client registration error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addClientCompany() {
        clientCompanyDialog.prepareForAdd();
        while (clientCompanyDialog.showModal()) {
            try {
                PhoneNumber pn = new PhoneNumber(clientCompanyDialog.getAreaCode(), clientCompanyDialog.getPhoneNum());
                clientsTableModel.addClient(pn, clientCompanyDialog.getClientName(), clientCompanyDialog.getClientAddr(), clientCompanyDialog.getClientDirector(), clientCompanyDialog.getClientContact());
                clientListOut.addClient(pn, clientCompanyDialog.getClientName(), clientCompanyDialog.getClientAddr(), clientCompanyDialog.getClientDirector(), clientCompanyDialog.getClientContact());
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Client registration error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeClient() {
        int seldRow = clientsTable.getSelectedRow();
        if (seldRow == -1)
            return;
        ClientInfo ci = clientsTableModel.getClient(seldRow);
        clientPersonDialog.prepareForChange(ci);
        ClientInfo tmp = clientListOut.getClientInfo(seldRow);
        if (ci.getType().equals("Person")) {
            if (clientPersonDialog.showModal()) {
                ci.setName(clientPersonDialog.getClientName());
                tmp.setName(clientPersonDialog.getClientName());
                ci.setAddress(clientPersonDialog.getClientAddr());
                tmp.setAddress(clientPersonDialog.getClientAddr());
                ci.setBirthday(clientPersonDialog.getClientBirthday());
                tmp.setBirthday(clientPersonDialog.getClientBirthday());
                clientsTableModel.clientChanged(seldRow);
            }
        } else {
            clientCompanyDialog.prepareForChange(ci);
            if (clientCompanyDialog.showModal()) {
                ci.setName(clientCompanyDialog.getClientName());
                tmp.setName(clientCompanyDialog.getClientName());
                ci.setAddress(clientCompanyDialog.getClientAddr());
                tmp.setAddress(clientCompanyDialog.getClientAddr());
                ci.setDirector(clientCompanyDialog.getClientDirector());
                tmp.setDirector(clientCompanyDialog.getClientDirector());
                ci.setContact(clientCompanyDialog.getClientContact());
                tmp.setContact(clientCompanyDialog.getClientContact());
                clientsTableModel.clientChanged(seldRow);
            }
        }
    }
    private void delClient() {
        int seldRow = clientsTable.getSelectedRow();
        if (seldRow == -1)
            return;
        ClientInfo ci = clientsTableModel.getClient(seldRow);
        if (JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete client\n"
                        + "with phone number " + ci.getPhoneNumber() + "?",
                "Delete confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            clientsTableModel.dropClient(seldRow);
            clientListOut.remove(seldRow);
        }
    }
    private void toClickAddButton() {
        Object[] typeOfClient = {"Person", "Company"};
        if (JOptionPane.showOptionDialog(null, "Would you like to add a new person or a new company?", "Choose type of client ", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, typeOfClient, typeOfClient[0]) == 0) {
            addClient();
        } else {
            addClientCompany();
        }
    }
    private void saveFile()  {
        try {
            System.out.println("запись");
            FileOutputStream outputStream = new FileOutputStream("user.dir");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(clientListOut);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    private void openFile()  {
        try {
            System.out.println("чтение");
            FileInputStream fileInputStream = new FileInputStream("user.dir");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            clientListIn = (ClientList) objectInputStream.readObject();
            for (ClientInfo client: clientListIn.getClients()){
                System.out.println(client.toString());
                if (client.getType().equals("Person")){
                    clientsTableModel.addClient(new PhoneNumber(client.getPhoneNumber().getAreaCode(), client.getPhoneNumber().getLocalNum()), client.getName(), client.getAddress(), client.getBirthday());
                    clientListOut.addClient(new PhoneNumber(client.getPhoneNumber().getAreaCode(), client.getPhoneNumber().getLocalNum()),client.getName(), client.getAddress(), client.getBirthday());
                }else {
                    clientsTableModel.addClient(new PhoneNumber(client.getPhoneNumber().getAreaCode(), client.getPhoneNumber().getLocalNum()), client.getName(), client.getAddress(), client.getDirector(), client.getContact());
                    clientListOut.addClient(new PhoneNumber(client.getPhoneNumber().getAreaCode(), client.getPhoneNumber().getLocalNum()), client.getName(), client.getAddress(), client.getDirector(), client.getContact());
                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
