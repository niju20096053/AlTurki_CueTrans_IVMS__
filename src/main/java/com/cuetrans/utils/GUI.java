package com.cuetrans.utils;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.cuetrans.utils.ExcelWriter;

public class GUI {
	public static boolean guiFlag ;
 	public static void main(String args[]) {
		openGUI();
	}

	public static boolean openGUI() {
		//Creating the Frame
		JFrame frame = new JFrame("Test Data Input");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

		//Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output
		panel.setLayout(new GridLayout(5, 0, 0, 50));
		
        String[] labels = {"ApplicationName", "BrowserName", "URL", "Automation Type", "Environment"};
        int numPairs = labels.length;

        Map<String,String> map = new HashMap<>();
        List<JTextField> list = new ArrayList<>();
        JLabel label = null;
        JTextField tf = null;
        
        for(int i=0;i<numPairs;i++) {
        	label = new JLabel(labels[i]);
        	tf = new JTextField(50);
        	panel.add(label);
    		panel.add(tf);
    		list.add(tf);
        }

		//Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		JButton generateButton = new JButton("Generate Excel");
		frame.getContentPane().add(BorderLayout.SOUTH, generateButton);
		frame.setVisible(true);

		generateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ExcelWriter writer = new ExcelWriter();
				try {
					for(int i=0;i<numPairs;i++) {
						map.put(labels[i], list.get(i).getText());
					}
					boolean status = writer.generateExcel(map);
					if(status) {
						JOptionPane.showMessageDialog(frame, "Excel Generated Successfully !!!");
						guiFlag = true;
						frame.dispose();
						
					} else {
						guiFlag = false;
						JOptionPane.showMessageDialog(frame, "Excel Generation Failed !!!  Please check if the data file is open..." );
					}
					JButton button = new JButton();
					button.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							System.out.println("Clicked button");
							
						}
					});
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		return guiFlag;
	}

}
