import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.io.IOException;

public class Presentation implements ActionListener {

	private JFrame Window;
	private JPanel Evac; 
	private static EvacAnimation ea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Presentation window = new Presentation();
					window.Window.setVisible(true);
					Timer timer = new Timer(10, window);
					timer.start();
					//window.ea.simulateAnimation();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	 
	public Presentation() throws IOException, InterruptedException {
		initialize();
		ea.setRandomImage();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		Window = new JFrame();
		Window.setTitle("COMP 4001 Project - Harth Majeed - 100 896 761 - Fall 2016 - Evangelos Kranakis");
		Window.getContentPane().setBackground(new Color(250,240,230));
		Window.setResizable(false);
		Window.setBounds(100, 100, 428, 387);
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Window.getContentPane().setLayout(null);
		
		Evac = new JPanel();
		Evac.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		Evac.setBounds(10, 11, 400, 335);
		Window.getContentPane().add(Evac);
		Evac.setLayout(null);
		
		ea = new EvacAnimation();
		ea.setBorder(new LineBorder(new Color(0,0,0)));
		ea.setBounds(10, 11, 379, 309);
		Evac.add(ea);
		ea.setLayout(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(ea.r1.isExitFound() == true)
			ea.r2.setExitFound(true, ea.r1.exitX, ea.r1.exitY);
		if(ea.r2.isExitFound() == true)
			ea.r1.setExitFound(true, ea.r2.exitX, ea.r2.exitY);
		ea.simulateAnimation();
		ea.repaint();
	}
}
