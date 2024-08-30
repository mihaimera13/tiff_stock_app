package mera.proj.tiff.gui;

import mera.proj.tiff.model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private View view;
    BufferedWriter bufferedWriter;

    public Controller(View view){
        this.view = view;
    }

    private int getLastFormatLine(File file){
        int lineNo = 0, lineNo2 = 0;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int lin = 1;
            while(line != null){
                if(line.charAt(0)=='=') {
                    lineNo2 = lineNo;
                    lineNo = lin;
                }
                line = bufferedReader.readLine();
                lin++;
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineNo2;
    }

    private String createFileName(){
        String filename = "TIFF_";
        LocalDate today = LocalDate.now();
        filename+=today+"_";
        LocalTime now = LocalTime.now();
        if(now.getHour()>=16)
            filename+="2";
        else filename += "1";
        filename+=".txt";

        return filename;
    }

    private ArrayList<Product> createProductSet(File file){
        ArrayList<Product> products = new ArrayList<>();

        try {
            if(!file.createNewFile()){
                int number = getLastFormatLine(file);

                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;

                for(int i=0;i<number;i++)
                    bufferedReader.readLine();

                line = bufferedReader.readLine();
                while(line!=null){
                    String[] splitter = line.split(" ~ ");
                    if(splitter.length>2){
                        String name = "";
                        String measure = "";
                        int quantity = 0;
                        String type = "";

                        if(!splitter[0].isEmpty())
                            name = splitter[0];
                        if(!splitter[1].isEmpty())
                            measure = splitter[1];
                        if(!splitter[2].isEmpty())
                            type = splitter[2];
                        if(!splitter[3].isEmpty())
                            quantity = Integer.parseInt(splitter[3]);

                        Product product = new Product(name,measure,quantity,type);
                        products.add(product);
                    }
                    line = bufferedReader.readLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return products;
    }

    public void writeToFile(File file,ArrayList<Product> products){

        try{
            bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            for (Product product:products){

                    bufferedWriter.append(product.getName()+" ~ "
                                        +product.getMeasure()+" ~ "
                                        +product.getType()+" ~ "
                                        +product.getQuantity());

                bufferedWriter.append('\n');
            }
            for(int i=0;i<30;i++)
                bufferedWriter.append('=');
            bufferedWriter.append('\n');
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals("OK")){
            String product = view.getProduct();
            String size = view.getMeasure();
            int quantity = view.getQuantity();
            String type = view.getType2();
            if(quantity != 0 && !product.equals("-")){
                String filename = createFileName();
                File file = new File(filename);
                boolean ok = true;
                ArrayList<Product> products = createProductSet(file);
                for(Product p : products){
                    if(p.getName().equals(product) && p.getMeasure().equals(size) && p.getType().equals(type)){
                        p.setQuantity(p.getQuantity()+quantity);
                        ok = false;
                    }
                }
                if(ok)
                    products.add(new Product(product,size,quantity,type));
                writeToFile(file,products);
                view.reset();
            }
            else {
                JOptionPane.showMessageDialog(view,"Invalid!","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
