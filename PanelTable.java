import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Constructs a JTable in a ScrolPane.
 * @author Wesley
 * @version 1.0
 */
public class PanelTable extends Panel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private Object[][] data;
	private String[] columnNames = {"Stage",
            "Event",
            "Artist",
            "Begin Time",
            "End Time"};
	private ArrayList<Event> events;
	private ArrayList<Event> fullEvents;
	private JTable table;
	private JTable selectedCell;
	
	/**
	 * Constructor makes the table and adds it to a scrollPane.
	 * @param events
	 */
	public PanelTable(ArrayList<Event> events, GregorianCalendar calendar) {
		super(calendar);
		this.events = events;
		fullEvents = new ArrayList<>();
		compileData(columnNames.length,events.size());
		table= new JTable();
		AbstractTableModel tableModel = new ContentTable(data,columnNames);
		table.setModel(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { if(e.getClickCount() > 1 ) cellClicked(e); else selectedCell = (JTable) e.getSource(); }
		});
		scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
		scrollPane.setBorder(null);
		add(scrollPane);		
	}
	
	/**
	 * Sets the size of the scrollPane, in which the table is placed to fill out its container panel (not sure if this is needed?)
	 */
	public void paintComponent(Graphics g) {
		scrollPane.setPreferredSize(new Dimension(getWidth(),getHeight()));
	}
	
	/**
	 * Gets the data from the events arrayList and makes a 2D-array of it.
	 * @param columnes
	 * @param rows
	 */
	public void compileData(int columnes, int rows) {
		data = new Object[rows][columnes];	
			for(int x = 0; x < rows; x++) {
				Object[] tempData = new Object[columnes];
				tempData[0] = "Stage 1";
				tempData[1] = events.get(x).getName();
				tempData[2] = events.get(x).getArtist().getName();
				tempData[3] = events.get(x).getStartDate();
				tempData[4] = events.get(x).getEndDate();
				data[x] = tempData;
			}		  
	}
	
	/**
	 * Refreshes the content on the table with a fresh ArrayList of events, useful when things are removed and added and should be called every time you update the list.
	 * @param events
	 */
	public void refreshContent(ArrayList<Event> events, boolean newList) {
		this.events = events;
		if(newList)
			this.fullEvents = events;
		compileData(columnNames.length,events.size());
		AbstractTableModel tableModel = new ContentTable(data,columnNames);
		table.setModel(tableModel);
	}
	
	/**
	 * Method for the action listener when a cell is clicked, opens dialog of the clicked cell.
	 * @param e
	 */
	private void cellClicked(MouseEvent e) {
		JTable target = (JTable) e.getSource();
		switch(target.getSelectedColumn()) {
			case 0:
				//A dialog for podia?
				break;
			case 1:
				openEventDialog(target.getSelectedRow());
				break;
			case 2:
				openArtistDialog(target.getSelectedRow());
				break;
		}
	}

	/**
	 * Opens a dialog with information of the selected event. 
	 * @param row
	 */
	private void openEventDialog(int row) {
		JFrame frame = new JFrame();
		Event event = events.get(row);
		//the dialog				
		frame.pack();
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(this);
		frame.setVisible(true);
	}
	
	/**
	 * Opens a dialog with information of the selected artist
	 * @param row
	 */
	private void openArtistDialog(int row) {
		JFrame frame = new JFrame();
		Artist artsit = events.get(row).getArtist();
		//the dialog				
		frame.pack();
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(this);
		frame.setVisible(true);
	}
	
	/**
	 * Filter method which filters on the currently selected cell.
	 * Needs to be under a button action, also a reset button is needed so list can be restored.
	 */
	public void filter() {
		if(selectedCell != null) {
			if(fullEvents.isEmpty()) {
				fullEvents = events;
			}
			ArrayList<Event> filteredList = new ArrayList<>();
			switch(selectedCell.getSelectedColumn()) {
			case 0:
				for(Event event : fullEvents) {
					/*
					if(event.getStage().equals(events.get(selectedCell.getSelectedRow()).getStage())) {
						filteredList.add(event);
					}
					*/
				}
				break;
			case 1: 
				for(Event event : fullEvents) {
					if(event.equals(events.get(selectedCell.getSelectedRow()))) {
						filteredList.add(event);
					}
				}
				break;
			case 2: 
				for(Event event : fullEvents) {
					if(event.getArtist().equals(events.get(selectedCell.getSelectedRow()).getArtist())) {
						filteredList.add(event);
					}
				}
				break;
				
			}
			refreshContent(filteredList,false);	
			selectedCell = null;
		}
		else {
			JOptionPane.showMessageDialog(this, "Select a cell with the value u want to filter on","No cell selected",JOptionPane.WARNING_MESSAGE);
		}
	}
}
