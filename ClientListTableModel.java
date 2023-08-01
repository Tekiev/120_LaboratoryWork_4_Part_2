package DEV120_4_2_Tekiev;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ClientListTableModel  implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{
            "Type client",
            "Phone number",
            "Client name",
            "Client address",
            "Birthday person",
            "Director company",
            "Contact company",
            "Registration date"
    };
    private final Set<TableModelListener> modelListeners = new HashSet<>();
    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return String.class;
            case 1:
                return PhoneNumber.class;
            case 7:
                return LocalDate.class;
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }
    @Override
    public int getRowCount() {
        return ClientList.getInstance().getClientsCount();
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientInfo ci = ClientList.getInstance().getClientInfo(rowIndex);
        switch(columnIndex) {
            case 0: return ci.getType();
            case 1: return ci.getPhoneNumber();
            case 2: return ci.getName();
            case 3: return ci.getAddress();
            case 4: return ci.getBirthday();
            case 5: return ci.getDirector();
            case 6: return ci.getContact();
            case 7: return ci.getRegDate();
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    @Override
    public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
    }
    @Override
    public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
    }
    public ClientInfo getClient(int rowNdx) {
        return ClientList.getInstance().getClientInfo(rowNdx);
    }
    public void addClient(PhoneNumber number, String name, String address, String birthday) {
        ClientList.getInstance().addClient(number, name, address, birthday);
        int rowNdx = ClientList.getInstance().getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }
    public void addClient(PhoneNumber number, String name, String address, String director, String contact) {
        ClientList.getInstance().addClient(number, name, address, director, contact);
        int rowNdx = ClientList.getInstance().getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }
    public void clientChanged(int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }
    public void dropClient(int index) {
        ClientList.getInstance().remove(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }
    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }
}
