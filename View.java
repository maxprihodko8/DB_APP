import org.omg.PortableInterceptor.INACTIVE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class View extends JFrame implements Runnable {
    JPanel buttonsPanel;
    JPanel showPanel;
    JPanel tablePanel;
    JPanel addInfoToTablePanel;
    JTable jTable;
    DefaultTableModel model;
    JTextArea lableTextTables;
    JScrollPane scroll;
    Controller c;
    String [] names = {"id", "name", "surname", "adress", "credit_card_number", "bank_card_number"};
    String [][] a;
    boolean tableLoaded;
    boolean query2RangeClicked=false;

    public View(){
        super("Tables!");
        run();
    }

    public void run(){

        //Подготавливаем компоненты объекта
        JButton addColumnButton = new JButton("Add col");
        JButton removeColumnButton = new JButton("Remove col");
        JButton queryExec1 = new JButton("Query1");
        JButton queryExec2 = new JButton("Query2");
        JButton showTablesButton = new JButton("Load tables!");
        showTablesButton.setForeground(Color.BLUE);

        JPanel p1 = new JPanel();
        JLabel j1 =  new JLabel("range1:"), j2 = new JLabel("range2:");;
        JTextField f1 = new JTextField("",5),f2 = new JTextField("",5);

        //Подготавливаем временные компоненты
        buttonsPanel = new JPanel(new FlowLayout());
        tablePanel = new JPanel(new FlowLayout());
        showPanel = new JPanel(new FlowLayout());

        //Расставляем компоненты по местам
        showPanel.add(showTablesButton);
        buttonsPanel.add(addColumnButton);
        buttonsPanel.add(removeColumnButton);
        buttonsPanel.add(queryExec1);
        buttonsPanel.add(queryExec2);


        add(tablePanel, BorderLayout.NORTH);
        add(showPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        showTablesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showTable(a = c.showTables());
            }
        });

        addColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addInfoToTablePanel = new JPanel(new FlowLayout());
                JTextField id = new JTextField("", 8);
                JTextField firstname = new JTextField("", 8);
                JTextField lastname = new JTextField("", 8);
                JTextField adress = new JTextField("", 8);
                JTextField credit_card_number = new JTextField("",8);
                JTextField bank_card_number = new JTextField("",8);
                addFocus(id);
                addFocus(firstname);
                addFocus(lastname);
                addFocus(adress);
                addFocus(credit_card_number);
                addFocus(bank_card_number);
                JLabel myId = new JLabel("id");
                JLabel myFirstname = new JLabel("name");
                JLabel myLastname = new JLabel("surname");
                JLabel myAdress = new JLabel("adress");
                JLabel myCreditCardNumber = new JLabel("credit_card");
                JLabel myBankCardNumber = new JLabel("bank_card");
                JButton insertMe = new JButton("Insert");
                addInfoToTablePanel.add(myId);
                addInfoToTablePanel.add(id);
                addInfoToTablePanel.add(myFirstname);
                addInfoToTablePanel.add(firstname);
                addInfoToTablePanel.add(myLastname);
                addInfoToTablePanel.add(lastname);
                addInfoToTablePanel.add(myAdress);
                addInfoToTablePanel.add(adress);
                addInfoToTablePanel.add(myCreditCardNumber);
                addInfoToTablePanel.add(credit_card_number);
                addInfoToTablePanel.add(myBankCardNumber);
                addInfoToTablePanel.add(bank_card_number);
                addInfoToTablePanel.add(insertMe);
                add(addInfoToTablePanel, BorderLayout.LINE_END);
                setCenter();

                insertMe.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        remove(addInfoToTablePanel);
                        setCenter();
                        String myid = id.getText();
                        String myname = firstname.getText();
                        String mysurname = lastname.getText();
                        String myadress = adress.getText();
                        String mycredit = credit_card_number.getText();
                        String mybank = bank_card_number.getText();
                        if( checkInsertData(myid, myname, mysurname, myadress, mycredit, mybank) ) {
                            Object[] o = new Object[6];
                            o[0] = myid; o[1] = myname; o[2] = mysurname; o[3] = myadress; o[4] = mycredit; o[5] = mybank;
                            model.addRow(o);
                            setCenter();
                            c.modelExecuteStatement("INSERT INTO Buyers VALUES (" + myid + ", '" + myname + "', '" + mysurname + "', '" + myadress + "', '" +
                                    mycredit + "', '" + mybank + "');");
                        }
                        else
                            setCenter();
                    }
                });
            }
        });

        removeColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(tableLoaded) {
                    int i = jTable.getSelectedRow();
                    if (i >= 0) {
                        Object idRemove = model.getValueAt(i, 0);
                        String idRemoveMe = idRemove.toString();
                        model.removeRow(i);
                        c.modelExecuteStatement("DELETE FROM Buyers WHERE id=" + idRemoveMe + ";");
                        showTable(a = c.showTables());
                    }
                }
                }
        });

        queryExec1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showTable(a=c.showQuery1());
            }
        });

        queryExec2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                if(!query2RangeClicked) {

                    p1.add(j1); p1.add(f1); p1.add(j2); p1.add(f2);
                    add(p1,BorderLayout.EAST);
                    setCenter();
                    query2RangeClicked=true;
                }
                else{
                    String s1 = f1.getText();
                    String s2 = f2.getText();
                    if(!s1.equals("") && !s2.equals("")){
                        try{
                            int i1 = Integer.parseInt(s1);
                            int i2 = Integer.parseInt(s2);
                            c.setRanges(i1,i2);
                            showTable(a=c.showQuery2());
                        } catch (NumberFormatException e){
                            c.setRanges(2000,4000);
                            showTable(a=c.showQuery2());
                        }
                    }
                }
            }
        });
    }

    public void setController(Controller c){
        this.c = c;
    }

    public void showTable(String [][] a){
        if(tableLoaded)
            tablePanel.remove(jTable);
        model = new DefaultTableModel(a, names);
        jTable = new JTable(model);
        //jTable = new JTable(a, names);
        scroll = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        View v = c.getView();
        tablePanel.add(jTable);
        tablePanel.revalidate();
        tablePanel.repaint();
        setCenter();
        tableLoaded = true;
    }

    public void setCenter(){
        c.app.setVisible(false);
        c.app.pack();
        c.app.setLocationRelativeTo(null);
        c.app.setVisible(true);
    }

    public boolean checkInsertData(String id, String name, String lastname, String adress, String credit, String bank){
        int id1;
        if(id!="")
            try {
                id1 = Integer.parseInt(id);
            } catch (NumberFormatException e){return false;}
        else
            return false;
        if(!tableLoaded)
            return false;
        for(int i=0; i<model.getRowCount(); i++){
            Object idme = model.getValueAt(i,0);
            String idstr = idme.toString();
            int checkId = Integer.parseInt(idstr);
            if(id1 ==checkId){
                return false;
            }
        }
        if(id1>0)
            if(name!=null && lastname!=null && adress!=null && credit!=null && bank!=null)
                return true;
            else
                return false;
        return false;
    }

    public void addFocus(JTextField j){
        j.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                j.setForeground(Color.RED);
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                j.setForeground(Color.BLACK);
            }
        });
    }
}