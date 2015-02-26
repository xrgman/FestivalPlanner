import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -2095136277753179215L;

	
	public MenuBar(Window w)
	{
		super();
		
		/*
		 * FILE
		 */
		JMenu file = new JMenu("File");
		
		JMenuItem newEvent = new JMenuItem("New Event");
		newEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		JMenuItem newArtist = new JMenuItem("New Artist");
		newArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		JMenuItem newStage = new JMenuItem("New Stage");
		newStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		JMenuItem newAgenda = new JMenuItem("New Agenda");
		newAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agenda a = new Agenda();
				w.setAgenda(a);
				Window.updatePanel("table");
			}
		});
		
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				w.getAgenda().loadAgenda();
				Window.updatePanel("table");
			}
		});
		
		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				w.getAgenda().saveAgenda();
				Window.updatePanel("table");
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit"); 
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this program?", "Close Agenda", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					System.exit(0);
				}
			}
		});
		
		file.add(newEvent);
		file.add(newArtist);
		file.add(newStage);
		file.addSeparator();
		file.add(newAgenda);
		file.addSeparator();
		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		add(file);
		
		
		/*
		 * VIEW
		 */
		
		JMenu view = new JMenu("View");
		
		JMenuItem timeline = new JMenuItem("Timeline");
		timeline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.updatePanel("timeline");
			}
		});
		
		JMenuItem table = new JMenuItem("Table");
		table.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.updatePanel("table");
			}
		});
		
		JMenuItem art_sta = new JMenuItem("Artists and Stages");
		art_sta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.updatePanel("art_sta");
			}
		});
		
		view.add(timeline);
		view.add(table);
		view.add(art_sta);
		add(view);
	}
}
