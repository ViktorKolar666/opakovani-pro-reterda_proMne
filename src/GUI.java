import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI extends JFrame{
    private JPanel panel;
    private JTextField TFznacka;
    private JTextField TFvykon;
    private JTextField TFvyroba;
    private JTextField TFcena;
    private JTable table;
    private JButton BTNprev;
    private JButton BTNnext;

    private ArrayList<Motorka> motorky = new ArrayList<>();
    private int index = 0;
    private File soubor;

    //konstruktor
    public GUI() {
        add(panel);
        setTitle("Motorky");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenu();

        //tlačítka pro listování motorkami
        BTNprev.addActionListener(e -> predchozí());
        BTNnext.addActionListener(e -> dalsi());
    }

    //vytvoření menu
    public void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem menuItem = new JMenuItem("Přidat motorku");
        JMenuItem menuItem2 = new JMenuItem("Vyber soubor");
        JMenuItem menuItem3 = new JMenuItem("Uložit soubor");
        JMenuItem menuItem4 = new JMenuItem("Generovat tabulku");

        setJMenuBar(menuBar);
        menuBar.add(menu);
        menuBar.add(menuItem4);

        menu.add(menuItem);
        menu.add(menuItem2);
        menu.add(menuItem3);

        menuItem.addActionListener(e -> pridejMotorku()); //tlačítko pro přidání motorky
        menuItem2.addActionListener(e -> vyberSoubor()); //tlačítko pro výběr souboru
        menuItem3.addActionListener(e -> ulozSoubor(soubor)); //tlačítko pro uložení souboru
        menuItem4.addActionListener(e -> generujTabulku()); //tlačítko pro generování tabulky
    }

    //načtení souboru
    public void nactiSoubor(File soubor){
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(soubor))))
        {
            while(sc.hasNextLine()){
                String radek = sc.nextLine();
                String[] pole = radek.split(";");
                String znacka = pole[0];
                int vykon = Integer.parseInt(pole[1]);
                LocalDate rokVyroby = LocalDate.parse(pole[2]);
                BigDecimal cena = new BigDecimal(pole[3]);
                motorky.add(new Motorka(znacka, vykon, rokVyroby, cena));
                zobrazMotorku(zjistiMotorku(index));
            }
        }catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(this, "Soubor nebyl nalezen! Chyba: "
                    +e.getLocalizedMessage());
        }
    }

    //výběr souboru
    public void vyberSoubor(){
        JFileChooser fc = new JFileChooser(".");
        int vysledek = fc.showOpenDialog(this);
        if(vysledek == JFileChooser.APPROVE_OPTION){
            soubor = fc.getSelectedFile();
            nactiSoubor(soubor);
        }
    }

    //přidání motorky
    public void pridejMotorku() {
        TFznacka.setText("");
        TFvykon.setText(String.valueOf(0));
        TFvyroba.setText(String.valueOf(LocalDate.now()));
        TFcena.setText(String.valueOf(0));

        String znacka = TFznacka.getText();
        int vykon = Integer.parseInt(TFvykon.getText());
        LocalDate rokVyroby = LocalDate.parse(TFvyroba.getText());
        BigDecimal cena = new BigDecimal(TFcena.getText());
        motorky.add(new Motorka(znacka, vykon, rokVyroby, cena));
    }

    //uložení souboru
    public void ulozSoubor(File soubor) {
        try(PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter(soubor))))
        {
            for(Motorka motorka : motorky)
            {
                wr.println(motorka.getZnacka()+";"+motorka.getVykon()+";"+motorka.getRokVyroby()+";"+motorka.getCena());
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, "Soubor se nepodařilo uložit! Chyba: "
                    +e.getLocalizedMessage());
        }
    }

    //generování tabulky
    public void generujTabulku() {
        table.setModel(new TableModel());
    }
    public class TableModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return motorky.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Motorka motorka = motorky.get(rowIndex);
            switch (columnIndex){
                case 0:
                    return motorka.getZnacka();
                case 1:
                    return motorka.getVykon();
                case 2:
                    return motorka.getRokVyroby();
                case 3:
                    return motorka.getCena();
            }
            return null;
        }
    }

    //zobrazení motorky
    public void zobrazMotorku(Motorka motorka) {
        TFznacka.setText(motorka.getZnacka());
        TFvykon.setText(String.valueOf(motorka.getVykon()));
        TFvyroba.setText(String.valueOf(motorka.getRokVyroby()));
        TFcena.setText(String.valueOf(motorka.getCena()));
    }
    public Motorka zjistiMotorku(int i) {
        return motorky.get(i);
    }

    //listování seznamem motorek
    public void predchozí() {
        if (index > 0)
        {
            index--;
            zobrazMotorku(zjistiMotorku(index));
        }
    }
    public void dalsi() {
        if (index < motorky.size() - 1)
        {
            index++;
            zobrazMotorku(zjistiMotorku(index));
        }
    }

    //spuštění aplikace
    public static void main(String[] args) {
        GUI g = new GUI();
        g.setVisible(true);
    }
}
