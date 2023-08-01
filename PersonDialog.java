package DEV120_4_2_Tekiev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PersonDialog extends JDialog {
    private final JTextField areaCodeField;
    private final JTextField phoneNumField;
    private final JTextField clientNameField;
    private final JTextField clientAddrField;
    private final JTextField birthdayField;
    private boolean okPressed;
    public PersonDialog(JFrame owner) {
        super(owner, true);
        areaCodeField = new JTextField(3);
        phoneNumField = new JTextField(5);
        clientNameField = new JTextField(30);
        clientAddrField = new JTextField(30);
        birthdayField = new JTextField(30);
        initLayout();
        setResizable(false);
    }
    private void initLayout() {
        initControls();
        initOkCancelButtons();
    }
    private void initControls() {
        JPanel controlsPane = new JPanel(null);
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel("Phone number (");
        lbl.setLabelFor(areaCodeField);
        p.add(lbl);
        p.add(areaCodeField);
        lbl = new JLabel(")");
        lbl.setLabelFor(phoneNumField);
        p.add(lbl);
        p.add(phoneNumField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Person name");
        lbl.setLabelFor(clientNameField);
        p.add(lbl);
        p.add(clientNameField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Person address");
        lbl.setLabelFor(clientAddrField);
        p.add(lbl);
        p.add(clientAddrField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Birthday");
        lbl.setLabelFor(birthdayField);
        p.add(lbl);
        p.add(birthdayField);
        controlsPane.add(p);
        add(controlsPane, BorderLayout.CENTER);
    }

    private void initOkCancelButtons() {
        JPanel btnsPane = new JPanel();

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        okBtn.setDefaultCapable(true);
        btnsPane.add(okBtn);
        Action cancelDialogAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        JButton cancelBtn = new JButton(cancelDialogAction);
        btnsPane.add(cancelBtn);
        add(btnsPane, BorderLayout.SOUTH);
        final String esc = "escape";
        getRootPane()
                .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
        getRootPane()
                .getActionMap()
                .put(esc, cancelDialogAction);
    }
    public boolean showModal() {
        pack();
        setLocationRelativeTo(getOwner());
        if(areaCodeField.isEnabled())
            areaCodeField.requestFocusInWindow();
        else
            clientNameField.requestFocusInWindow();
        okPressed = false;
        setVisible(true);
        return okPressed;
    }
    public void prepareForAdd() {
        setTitle("New person registration");
        areaCodeField.setText("");
        phoneNumField.setText("");
        clientNameField.setText("");
        clientAddrField.setText("");
        birthdayField.setText("");
        areaCodeField.setEnabled(true);
        phoneNumField.setEnabled(true);
    }
    public void prepareForChange(ClientInfo ci) {
        setTitle("Client properties change");
        areaCodeField.setText(ci.getPhoneNumber().getAreaCode());
        phoneNumField.setText(ci.getPhoneNumber().getLocalNum());
        clientNameField.setText(ci.getName());
        clientAddrField.setText(ci.getAddress());
        birthdayField.setText(ci.getBirthday());
        areaCodeField.setEnabled(false);
        phoneNumField.setEnabled(false);
    }
    public String getAreaCode() {
        return areaCodeField.getText();
    }
    public String getPhoneNum() {
        return phoneNumField.getText();
    }
    public String getClientName() {
        return clientNameField.getText();
    }
    public String getClientAddr() {
        return clientAddrField.getText();
    }
    public String getClientBirthday() {
        return birthdayField.getText();
    }

}
