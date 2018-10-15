package gui;

import javax.swing.JFrame;
import javax.swing.JPanel; 
public class DemoFrame extends JFrame{		
	public DemoFrame(DemoPanel panel)		{			
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		this.setSize(300, 200);			
		this.setTitle("P_To_P Message");			
		this.add(panel);			
		this.setResizable(false);			
		this.setVisible(true);		
		}				
	public static void main(String[] args)		{			
		DemoPanel panel = new DemoPanel();			
		DemoFrame frame = new DemoFrame(panel);		
		}
	}

