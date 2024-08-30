package mera.proj.tiff.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class View extends JFrame {

    private JPanel appPanel;

    private JLabel titleLabelOne;
    private JLabel titleLabelTwo;
    private JLabel productLabel;
    private JLabel sizeLabel;
    private JLabel quantityLabel;
    private JLabel typeLabel;

    private JComboBox<String> productComboBox;
    private JComboBox<String> sizeComboBox;
    private JSpinner quantitySpinner;
    private JComboBox<String> typeComboBox;

    private JButton okButton;

    Measure measure = new Measure();

    Controller controller = new Controller(this);

    public View(){
        setAppPanel();
    }

    public void setAppPanel() {
        this.setSize(850,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(100,80);
        SpringLayout springLayout = new SpringLayout();
        this.appPanel = new JPanel(springLayout);
        this.appPanel.setBackground(Color.WHITE);

        titleLabelOne = new JLabel("TIFF");
        titleLabelTwo = new JLabel("SHOP");

        titleLabelOne.setFont(new Font("Myfont",Font.BOLD,40));
        titleLabelTwo.setFont(new Font("Myfont",Font.BOLD,40));

        titleLabelOne.setForeground(Color.BLACK);
        titleLabelTwo.setForeground(Color.RED);

        springLayout.putConstraint(SpringLayout.NORTH,titleLabelOne,20,SpringLayout.NORTH,appPanel);
        springLayout.putConstraint(SpringLayout.NORTH,titleLabelTwo,20,SpringLayout.NORTH,appPanel);
        springLayout.putConstraint(SpringLayout.WEST,titleLabelOne,270,SpringLayout.WEST,appPanel);
        springLayout.putConstraint(SpringLayout.WEST,titleLabelTwo,10,SpringLayout.EAST,titleLabelOne);

        productLabel = new JLabel("Alege produs:");
        sizeLabel = new JLabel("Alege marime:");
        quantityLabel = new JLabel("Alege numar de bucati:");
        typeLabel = new JLabel("Alege tip:");

        productLabel.setFont(new Font("Myfont",Font.BOLD,30));
        sizeLabel.setFont(new Font("Myfont",Font.BOLD,30));
        quantityLabel.setFont(new Font("Myfont",Font.BOLD,30));
        typeLabel.setFont(new Font("Myfont",Font.BOLD,30));

        productLabel.setForeground(Color.BLACK);
        sizeLabel.setForeground(Color.RED);
        quantityLabel.setForeground(Color.BLACK);
        typeLabel.setForeground(Color.RED);

        springLayout.putConstraint(SpringLayout.NORTH,productLabel,30,SpringLayout.SOUTH,titleLabelOne);
        springLayout.putConstraint(SpringLayout.WEST,productLabel,20,SpringLayout.WEST,appPanel);
        springLayout.putConstraint(SpringLayout.NORTH,sizeLabel,30,SpringLayout.SOUTH,productLabel);
        springLayout.putConstraint(SpringLayout.WEST,sizeLabel,20,SpringLayout.WEST,appPanel);
        springLayout.putConstraint(SpringLayout.NORTH,quantityLabel,30,SpringLayout.SOUTH,sizeLabel);
        springLayout.putConstraint(SpringLayout.WEST,quantityLabel,20,SpringLayout.WEST,appPanel);
        springLayout.putConstraint(SpringLayout.NORTH,typeLabel,30,SpringLayout.SOUTH,quantityLabel);
        springLayout.putConstraint(SpringLayout.WEST,typeLabel,20,SpringLayout.WEST,appPanel);

        productComboBox = setProducts();
        sizeComboBox = setMeasures();
        typeComboBox = new JComboBox<>();
        typeComboBox.addItem("Nu are");
        typeComboBox.addItem("F");
        typeComboBox.addItem("M");
        typeComboBox.setFont(new Font("Times New Roman",Font.PLAIN,30));
        typeComboBox.setFocusable(false);

        springLayout.putConstraint(SpringLayout.NORTH,productComboBox,30,SpringLayout.SOUTH,titleLabelOne);
        springLayout.putConstraint(SpringLayout.WEST,productComboBox,20,SpringLayout.EAST,productLabel);
        springLayout.putConstraint(SpringLayout.NORTH,sizeComboBox,30,SpringLayout.SOUTH,productComboBox);
        springLayout.putConstraint(SpringLayout.WEST,sizeComboBox,20,SpringLayout.EAST,sizeLabel);

        quantitySpinner = new JSpinner();
        quantitySpinner.setFont(new Font("Times New Roman",Font.BOLD,20));
        SpinnerNumberModel snm = new SpinnerNumberModel(0,0,15,1);
        quantitySpinner.setModel(snm);
        quantitySpinner.setFocusable(false);

        springLayout.putConstraint(SpringLayout.NORTH,quantitySpinner,30,SpringLayout.SOUTH,sizeComboBox);
        springLayout.putConstraint(SpringLayout.WEST,quantitySpinner,20,SpringLayout.EAST,quantityLabel);

        springLayout.putConstraint(SpringLayout.NORTH,typeComboBox,30,SpringLayout.SOUTH,quantitySpinner);
        springLayout.putConstraint(SpringLayout.WEST,typeComboBox,20,SpringLayout.EAST,typeLabel);

        okButton = new JButton("OK");
        okButton.setFont(new Font("My font", Font.BOLD,30));
        okButton.setBackground(Color.BLACK);
        okButton.setForeground(Color.RED);
        okButton.setActionCommand("OK");
        okButton.addActionListener(this.controller);
        okButton.setPreferredSize(new Dimension(120,70));

        springLayout.putConstraint(SpringLayout.SOUTH,okButton,-20,SpringLayout.SOUTH,appPanel);
        springLayout.putConstraint(SpringLayout.EAST,okButton,-40,SpringLayout.EAST,appPanel);

        appPanel.add(titleLabelOne);
        appPanel.add(titleLabelTwo);
        appPanel.add(productLabel);
        appPanel.add(sizeLabel);
        appPanel.add(productComboBox);
        appPanel.add(sizeComboBox);
        appPanel.add(quantityLabel);
        appPanel.add(quantitySpinner);
        appPanel.add(okButton);
        appPanel.add(typeLabel);
        appPanel.add(typeComboBox);
        this.setContentPane(appPanel);
    }

    private JComboBox<String> setMeasures(){
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Times New Roman",Font.PLAIN,30));
        comboBox.setFocusable(false);
        for(String m : measure.measures)
            comboBox.addItem(m);

        return comboBox;
    }

    private JComboBox<String> setProducts(){
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Times New Roman",Font.PLAIN,30));
        comboBox.setFocusable(false);
        try{
            InputStream inputStream = View.class.getResourceAsStream("/product.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String product = reader.readLine();
            while(product != null){
                comboBox.addItem(product);
                product = reader.readLine();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comboBox;
    }

    public String getProduct(){
        return (String) productComboBox.getSelectedItem();
    }

    public String getMeasure(){
        return (String) sizeComboBox.getSelectedItem();
    }

    public int getQuantity(){
        return (int) quantitySpinner.getValue();
    }

    public String getType2(){
        return (String) typeComboBox.getSelectedItem();
    }

    public void reset(){
        productComboBox.setSelectedIndex(0);
        sizeComboBox.setSelectedIndex(0);
        quantitySpinner.setValue(0);
        typeComboBox.setSelectedIndex(0);
    }
}
