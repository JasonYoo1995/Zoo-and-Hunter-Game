package V;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import C.Controller;

public class EndingView extends JPanel{
	BasicFrame basicFrame;
	Controller c;
	JLabel endLabel;
	JButton endButton;
	
	public EndingView(Controller c){
		this.c = c;

		
		this.setVisible(true);
		this.setLayout(null);
		this.setSize(940,600);
		this.setBackground(new Color(83,193,75));
		this.requestFocus();
		

		
		endLabel = new JLabel("Ending....");
		endLabel.setBounds(270, 150, 400, 300);
		endLabel.setBackground(Color.CYAN);
		endLabel.setFont(new Font("", Font.CENTER_BASELINE, 80));
		
		endButton = new JButton("Exit");
		endButton.setBounds(850, 50, 70, 60);
		endButton.setBackground(Color.GRAY);
		endButton.setMargin(new Insets(0, 0, 0, 0));
		endButton.setFont(new Font("", Font.CENTER_BASELINE, 20));
		endButton.setVisible(true);
		endButton.repaint();
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		this.add(endLabel);
		this.add(endButton, 0);
	}
}
