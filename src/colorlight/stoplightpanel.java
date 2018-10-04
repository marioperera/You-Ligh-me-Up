package colorlight;

import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author wolfpack
 */
public class stoplightpanel extends javax.swing.JPanel {
    
    JButton startbtn;
    JButton stopbtn ;
    JLabel red_label;
    JLabel yellow_label;
    JLabel green_label;
    JTextField red_text;
    JTextField yellow_text ;
    JTextField green_text;
    public boolean carry =true;
    
    
    stopLightDrawing light =new stopLightDrawing();
    public int times[] ={1,1,1};
    
    
    
    
    public stoplightpanel(){
        startbtn =new JButton("Start");
        stopbtn =new JButton("Stop");
        red_label =new JLabel("RED");
        yellow_label =new JLabel("YELLOW");
        green_label =new JLabel("GREEN");
        red_text =new JTextField();
        yellow_text =new JTextField();
        green_text =new JTextField();
        red_text.setText(String.valueOf(1));
        yellow_text.setText(String.valueOf(1));
        green_text.setText(String.valueOf(1));
       
        red_text.setPreferredSize(new Dimension(55,30));
        yellow_text.setPreferredSize(new Dimension(55,30));
        green_text.setPreferredSize(new Dimension(55,30)); 
        //Action listner for change color
        buttonListner l =new buttonListner();
        startbtn.addActionListener(l);
        buttonendlistner e =new buttonendlistner();
        stopbtn.addActionListener(e);
        
       
        
        
        
        light.setPreferredSize(new Dimension(550, 170));
        light.setAlignmentX(RIGHT_ALIGNMENT);
        add(light);
        add(startbtn);
        add(stopbtn);
        add(red_label);
        add(red_text);
        add(yellow_label);
        add(yellow_text);
        add(green_label);
        add(green_text);
        
    }
    
    
    class buttonListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int times[] ={1,1,1};
            carry =true;
            
            times[0] =Integer.parseInt(red_text.getText().trim());
            times[1] =Integer.parseInt(yellow_text.getText().trim());
            times[2] =Integer.parseInt(green_text.getText().trim());
            
            if(red_text.getText()==null || yellow_text.getText() ==null){
                 times =new int[] {1,1,1};
            } 
            
            System.out.println("times = "+times[0]);
            
            light.changeColor();
            mainthread m =new mainthread();
            Thread main =new Thread(m);
            main.start();
        }
    }
    class changecolor{
        public void change(){
            light.changeColor();
            //System.out.println("changing color from class");
        }
    }    
    class buttonendlistner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            carry =false;
            
            
        }
    }
    
    class mainthread implements Runnable{
        
        public void run(){
            
            //int times[] =arr;
            System.out.println("val "+times[0]);
            while(carry){
                for (int i = 0; i < 3; i++) {
                    
                    synchronized(this) {
                        long k =times[i];
                        printhread p =new printhread((times[i]));
                        changecolor chngclr =new changecolor();
                        chngclr.change();
                        
                        try{
                            k*=1000;
                            Thread.sleep(k);
                            p.start();
                          
                        }
                        catch(InterruptedException inp){
                            inp.printStackTrace();
                        }
                        
                      
                        
                        
                        
                    } 
                    
                    System.out.println("change light color");
                }
                
            }
        }
    }
    
    class printhread extends Thread{
        int timess;
        public printhread(int t){
            
            this.timess =t;
            //light.changeColor();
            
        }
        @Override
        public void run(){
           
            //light.changeColor();
            for (int j = 0; j <=timess; j++) {
                System.out.println("now light:"+timess+" after "+(timess-j)+" seconds will change color");
            }
            synchronized(this){
                
                this.notifyAll();
            }
            //this.destroy();
           
        }
    }
}
    
    
    