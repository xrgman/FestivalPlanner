import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings({ "serial" })
public class Timeline extends JPanel
{
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	private JPanel frame;

//	Geef dit object je JFrame mee, en zet in je JFrame de volgende code:
//	this.setContentPane(timeline.getTimeline());
//	this.getRootPane().addComponentListener(new ComponentAdapter() 
//	{
//		public void componentResized(ComponentEvent e) {
//            timeline.refresh();
//        }
//	});
	
	
	public Timeline(JPanel frame) 
	{
		super();
		this.frame = frame;

		genStages();
		this.setSize(frame.getWidth(), frame.getHeight());
		refresh();
		
	}

	public void refresh()
	{
		this.removeAll();	
//		createWestPanel();
		createMainPanel();
		this.revalidate();
	}
	
	private void createMainPanel()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		Container topContainer = createTopPanel();
		Dimension dim = new Dimension(this.getWidth(), 60);
		topContainer.setMaximumSize(dim);
		mainPanel.add(topContainer);
		
		Dimension centerDim = new Dimension(this.getWidth(), frame.getHeight());
		
//		Container centerContainer = createCenterPanel();
		JPanel centerContainer = createCenterPanel();
		centerContainer.setPreferredSize(centerDim);

		centerContainer.setMaximumSize(centerDim);
		System.out.println(centerDim);
		mainPanel.add(centerContainer);
		this.add(mainPanel, BorderLayout.CENTER);
	}

	private JPanel createCenterPanel()
	{

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.lightGray);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		for(Stage stage : stages)
		{
			StagePanel stagepanel = new StagePanel(calcLengthOfStage(stage.getLength()), 30, calcPositionOfStage(stage.getStartTime()), stage);
			Container content = stagepanel;
			content.setMaximumSize(new Dimension(this.getWidth(),30));
			content.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					resizeTimeline(stagepanel);
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			content.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {

				}

				@Override
				public void mouseDragged(MouseEvent e) {
					stagepanel.update(e.getX() - stagepanel.getImageWidth() / 2);
				}
			});
			centerPanel.add(content);
			centerPanel.add(Box.createRigidArea(new Dimension(0,15)));
		}

//		this.add(centerPanel, BorderLayout.CENTER);
		return centerPanel;
	}

	public void resizeTimeline(StagePanel stagepanel)
	{
		int min = findMinMax(1);
		int max = findMinMax(2);
//		System.out.println(stagepanel.getStageStartTime());
//		System.out.println(calcStartTimeOfStagePanel(stagepanel.getPosX()));
		stagepanel.setStageStartTime(calcStartTimeOfStagePanel(stagepanel.getPosX()));
		stagepanel.setStageEndTime(calcEndTimeOfStagePanel(stagepanel.getPosX(), stagepanel.getImageWidth()));
//		stagepanel.setLength(calcLengthOfStagePanel(stagepanel.getImageWidth()));
//		System.out.println(calcLengthOfStagePanel(stagepanel.getImageWidth()));
		refresh();
		
		
	}
	
	public int calcLengthOfStage(int length)
	{
		double lengthOfStage = 0;
		int min = findMinMax(1);
		int max = findMinMax(2);
		
		double frameWidth = this.getWidth();
		double pixelsPerLength = frameWidth / (max - min);
		lengthOfStage =  pixelsPerLength * (double)length;
//		System.out.println((int)lengthOfStage);
		return (int)lengthOfStage;
	}
	
	public int calcLengthOfStagePanel(int length)
	{
		double lengthOfStagePanel = 0;
		int min = findMinMax(1);
		int max = findMinMax(2);
//		System.out.println(length);
		double frameWidth = this.getWidth();
		double pixelsPerLength = frameWidth / (max - min);
//		System.out.println(pixelsPerLength);
		lengthOfStagePanel =  (double)length / pixelsPerLength;
//		System.out.println(lengthOfStagePanel);
		
		return (int)lengthOfStagePanel;
	}
	
	public int calcPositionOfStage(int startTime)
	{
		int posx = 0;
		int min = findMinMax(1);
		int max = findMinMax(2);
		posx = startTime - min;
		posx = posx * this.getWidth() / (max-min);
//		System.out.println("Frame: " + this.getWidth());
//		System.out.println("startTime: " + startTime);
//		System.out.println("Max: " + max + " min: " + min);
//		System.out.println("Posx: " + posx);
		return posx;
	}
	
	public int calcStartTimeOfStagePanel(int posx)
	{
		int min = findMinMax(1);
		int max = findMinMax(2);
//		double startTime = (posx / (this.getWidth() / (max - min )) ) + min;
		//Omdat het anders onnodig afgerond wordt, alles apart in doubles.
		double minmax = max-min;
		double bottom = this.getWidth() / minmax;
		double all = posx / bottom;
		double startTime = all + min;
		
		
//		System.out.println("starttime----------------");
//		System.out.println("Frame: " + this.getWidth());
//		System.out.println("startTime: " + startTime);
//		System.out.println("Max: " + max + " min: " + min);
//		System.out.println("Posx: " + posx);
		return (int)startTime;
	}

	public int calcEndTimeOfStagePanel(int posx, int imageWidth)
	{
		int min = findMinMax(1);
		int max = findMinMax(2);
		//Omdat het anders onnodig afgerond wordt, alles apart in doubles.
		double minmax = max-min;
		double bottom = this.getWidth() / minmax;
		double all = posx / bottom;
		double endTime = all + min + calcLengthOfStagePanel(imageWidth);
//		System.out.println(calcLengthOfStagePanel(imageWidth));
//		System.out.println(endTime);
		return (int)endTime;
	}
	
	public int findMinMax(int i)
	{
		//		Event oldevent = new Event(0, 0, null, "Base", 0);
		//		ArrayList<Event> oldevents = new ArrayList<Event>();
		//		oldevents.add(oldevent);
		//		Stage oldstage = new Stage(oldevents,0,null, 0, 0);
		ArrayList<Stage> stagescopy = (ArrayList<Stage>) stages.clone();

		Integer earliestStartTime = null;
		Integer lastEndTime = null;

		for(Stage stage : stagescopy)
		{
			if(i == 1)
			{
				if(earliestStartTime == null)
				{

					earliestStartTime = stage.getStartTime();
				}

				else
				{
					if(stage.getStartTime() < earliestStartTime)
					{
						earliestStartTime = stage.getStartTime();
					}
				}
			}
			else if( i == 2)
			{
				if(lastEndTime == null)
				{
					lastEndTime = stage.getEndTime();
				}
				else
				{
					if(stage.getEndTime() > lastEndTime)
					{
						lastEndTime = stage.getEndTime();
					}
				}
			}
		}
		if(i == 1)
		{
			return earliestStartTime;
		}
		else if(i == 2)
		{
			return lastEndTime;
		}
		else
		{
			System.out.println("Return -1");
			return -1;
		}
	}

	private JPanel createTopPanel()
	{
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.Y_AXIS));
		JPanel topPanel = new JPanel(new FlowLayout());
		for(int i = 0; i <= this.getWidth() / 10; i++)
		{
			ImageIcon topImageIcon = new ImageIcon("src/test2.png");
			JLabel topImage = new JLabel(topImageIcon);
			topPanel.add(topImage);
		}
		topPane.add(Box.createRigidArea(new Dimension(0,20)));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(Box.createRigidArea(new Dimension(50, 10)));
		panel.add(new JLabel(Integer.toString(findMinMax(1))));
		panel.add(Box.createHorizontalGlue());
		panel.add(new JLabel(Integer.toString(findMinMax(2))));
		panel.add(Box.createRigidArea(new Dimension(50, 10)));
		topPane.add(panel);
		topPane.add(topPanel);
//		this.add(topPane, BorderLayout.NORTH);
		return topPane;
	}

	public JPanel createWestPanel()
	{
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(Box.createRigidArea(new Dimension(0, 100)));
		for(int i = 0; i < stages.size(); i++)
		{
			JLabel stageText = new JLabel("Stage: " + (i+1) + "    ");
			westPanel.add(stageText);
			westPanel.add(Box.createRigidArea(new Dimension(0, 30 )));
		}
//		this.add(westPanel, BorderLayout.WEST);
		return westPanel;
	}

	private void genStages()
	{
		ImageIcon image = new ImageIcon();
		Artist artist2 = new Artist("Henk", "paul2", "kaas", image, 2);
		Artist artist1 = new Artist("Kaas", "Paul", "Lala", image, 1);
		Event event2 = new Event(1900, 2000, artist2, "hi2", 2);
		Event event1 = new Event(600, 1800, artist1, "hi", 1);
		ArrayList<Event> performances = new ArrayList<Event>();
		performances.add(event1);
		performances.add(event2);
		Stage stage1 = new Stage(performances, 100, null, 1800, 2100);
		stages.add(stage1);
		Stage stage2 = new Stage(performances, 100, null, 1700, 1900);
		Stage stage3 = new Stage(performances, 100, null, 1600, 1800);
		Stage stage4 = new Stage(performances, 100, null, 1600, 2100);
		stages.add(stage3);
		stages.add(stage2);
//		stages.add(stage4);

	}

	public JPanel getTimeline()
	{
		return this;
	}

}