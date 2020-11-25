package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;


public class PixelArtMaker implements MouseListener, ActionListener {
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	ColorSelectionPanel csp;
	private JButton save;
	private JButton load;
	private String SaveFileLocation = "src/_02_Pixel_Art/saved.dat";
	
	public void start() {
		gip = new GridInputPanel(this);	
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);
		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		csp = new ColorSelectionPanel();	
		save = new JButton("Save");
		load = new JButton("Load");
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		window.add(save);
		window.add(load);
		gp.repaint();
		gp.addMouseListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		window.pack();
	}
	private void save(GridPanel gopr) {
		try (FileOutputStream fos = new FileOutputStream(new File(SaveFileLocation)); 
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(gopr);
				System.out.println(SaveFileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private GridPanel load() {
		GridPanel gopr = null;
		try (FileInputStream fis = new FileInputStream(new File(SaveFileLocation)); 
			ObjectInputStream ois = new ObjectInputStream(fis)) {
				gopr = (GridPanel) ois.readObject();
				System.out.println("loading file...");
				System.out.println(SaveFileLocation);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return (gopr);
	}
	private void setUpGridMethod () {
		csp = new ColorSelectionPanel();	
		save = new JButton("Save");
		load = new JButton("Load");
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		window.add(save);
		window.add(load);
		gp.repaint();
		gp.addMouseListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		window.pack();
	}
	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==save) {
			save(gp);
		}
		else if (e.getSource()== load) {
			window.getContentPane().removeAll();
			gp = load();
			setUpGridMethod();
		}
		
	}
}
