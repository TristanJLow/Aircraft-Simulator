/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = -7031008862559936404L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 540;
    
    private JTextField seedInput = new JTextField(Integer.toString(Constants.DEFAULT_SEED));
    private JTextField meanInput = new JTextField(Double.toString(Constants.DEFAULT_DAILY_BOOKING_MEAN));
    private JTextField queueInput = new JTextField(Integer.toString(Constants.DEFAULT_MAX_QUEUE_SIZE));
    private JTextField cancelInput = new JTextField(Double.toString(Constants.DEFAULT_CANCELLATION_PROB));
    private JTextField firstInput = new JTextField(Double.toString(Constants.DEFAULT_FIRST_PROB));
    private JTextField businessInput = new JTextField(Double.toString(Constants.DEFAULT_BUSINESS_PROB));
    private JTextField premiumInput = new JTextField(Double.toString(Constants.DEFAULT_PREMIUM_PROB));
    private JTextField economyInput = new JTextField(Double.toString(Constants.DEFAULT_ECONOMY_PROB));
    
    private JLabel seedLabel = new JLabel("RNG Seed:");
    private JLabel meanLabel = new JLabel("Daily Mean:");
    private JLabel queueLabel = new JLabel("Queue Size:");
    private JLabel cancelLabel = new JLabel("Cancellation Chance: ");
    private JLabel firstLabel = new JLabel("First Chance:");
    private JLabel businessLabel = new JLabel("Business Chance: ");
    private JLabel premiumLabel = new JLabel("Premium Chance: ");
    private JLabel economyLabel = new JLabel("Economy Chance: ");
    
    private JButton runBtn = new JButton("Run Simulation");
    private JButton chartBtn = new JButton("Show Chart");
    private JButton chart2Btn = new JButton("Show 2nd Chart");
    
    private JPanel mainPanel = new JPanel();
    private JPanel bottPanel = new JPanel();
    private JPanel btnPanel = new JPanel();
    private JPanel paramPanel = new JPanel();
    private JPanel classPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    
    private static JTextArea logArea = new JTextArea();
    JScrollPane logScroll = new JScrollPane(logArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    private static XYSeries firstData = new XYSeries("First");
    private static XYSeries businessData = new XYSeries("Business");
    private static XYSeries premiumData = new XYSeries("Premium");
    private static XYSeries economyData = new XYSeries("Economy");
    private static XYSeries totalData = new XYSeries("Total");
    private static XYSeries emptyData = new XYSeries("Empty");
    private static XYSeries queueData = new XYSeries("Queued");
    private static XYSeries refusedData = new XYSeries("Refused");
    
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		
		
	}
	
	private void createGUI() {
	    setSize(WIDTH, HEIGHT);
	    setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        btnPanel.setBackground(Color.white);
        paramPanel.setBackground(Color.white);
        classPanel.setBackground(Color.white);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        layoutButtonPanel();
        
        mainPanel.add(logArea, null);
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottPanel, BorderLayout.SOUTH);
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(rightPanel, BorderLayout.WEST);
        this.getContentPane().add(leftPanel, BorderLayout.EAST);
        repaint();
        
        chartBtn.setEnabled(false);
        chart2Btn.setEnabled(false);
        runBtn.addActionListener(this);
        chartBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(firstData);
                dataset.addSeries(businessData);
                dataset.addSeries(premiumData);
                dataset.addSeries(economyData);
                dataset.addSeries(totalData);
                dataset.addSeries(emptyData);
                JFreeChart chartyMcChartFace = createChart(dataset, "Simulation Results");
                ChartPanel chartPanel = new ChartPanel(chartyMcChartFace);
                chartPanel.setPreferredSize(new java.awt.Dimension(800, 540));
                XYPlot plot = chartyMcChartFace.getXYPlot();
                XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                plot.setRenderer(renderer);
                setContentPane(chartPanel);
            }
        });
        chart2Btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(queueData);
                dataset.addSeries(refusedData);
                JFreeChart chartyMcChartFace = createChart(dataset, "Simulation Results");
                ChartPanel chartPanel = new ChartPanel(chartyMcChartFace);
                chartPanel.setPreferredSize(new java.awt.Dimension(800, 540));
                XYPlot plot = chartyMcChartFace.getXYPlot();
                XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                plot.setRenderer(renderer);
                setContentPane(chartPanel);
            }
        });
        this.setVisible(true);
	}
	
	private JFreeChart createChart(XYDataset dataset, String chartTitle){
	    JFreeChart result = ChartFactory.createXYLineChart(chartTitle, "Days", "Passengers", dataset, PlotOrientation.VERTICAL, true, true, false);
	    return result;
	}
	
	public static void createFlightData(double time, double firstC, double businessC, double premiumC, double economyC
	        , double availableC,double totalC){
	        firstData.add(time, firstC);
	        businessData.add(time, businessC);
	        premiumData.add(time, premiumC);
	        economyData.add(time, economyC);
	        emptyData.add(time, availableC);
	        totalData.add(time, totalC);
	}
	
	public static void createQ_RData(double time, double queueC, double refusedC){
	    queueData.add(time, queueC);
        refusedData.add(time, refusedC);
	}
	
	private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        btnPanel.setLayout(layout);
        
        //add components to grid
        GridBagConstraints constraints = new GridBagConstraints(); 
        
        //Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;
        
        paramPanel.setLayout(new GridLayout(4,2,1,1));
        classPanel.setLayout(new GridLayout(4,2,1,1));
        btnPanel.setLayout(new GridLayout(3,1,1,1));
        
        addToPanel(bottPanel,paramPanel,constraints,1,1,2,1);
        addToPanel(bottPanel,classPanel,constraints,3,1,2,1);
        addToPanel(bottPanel,btnPanel,constraints,5,1,2,1);
        
        addToPanel(paramPanel, seedLabel,constraints,1,1,2,1);
        addToPanel(paramPanel, seedInput,constraints,2,1,2,1);
        addToPanel(paramPanel, meanLabel,constraints,1,2,2,1);
        addToPanel(paramPanel, meanInput,constraints,2,2,2,1);
        addToPanel(paramPanel, queueLabel,constraints,1,3,2,1);
        addToPanel(paramPanel, queueInput,constraints,2,3,2,1);
        addToPanel(paramPanel, cancelLabel,constraints,1,4,2,1);
        addToPanel(paramPanel, cancelInput,constraints,2,4,2,1);
        addToPanel(classPanel, firstLabel,constraints,1,1,2,1);
        addToPanel(classPanel, firstInput,constraints,2,1,2,1);
        addToPanel(classPanel, businessLabel,constraints,1,2,2,1);
        addToPanel(classPanel, businessInput,constraints,2,2,2,1);
        addToPanel(classPanel, premiumLabel,constraints,1,3,2,1);
        addToPanel(classPanel, premiumInput,constraints,2,3,2,1);
        addToPanel(classPanel, economyLabel,constraints,1,4,2,1);
        addToPanel(classPanel, economyInput,constraints,2,4,2,1);
        
        addToPanel(btnPanel, runBtn,constraints,1,1,2,1); 
        addToPanel(btnPanel, chartBtn,constraints,1,2,2,1);  
        addToPanel(btnPanel, chart2Btn,constraints,1,3,2,1);
    }
	
	private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
	      constraints.gridx = x;
	      constraints.gridy = y;
	      constraints.gridwidth = w;
	      constraints.gridheight = h;
	      jp.add(c, constraints);
	   }

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createGUI();

	}
	
	 public void actionPerformed(ActionEvent e) {
         //Get event source 
         Object src=e.getSource(); 
         src.toString();
         boolean error = false;
         try {
             Integer.parseInt(seedInput.getText());
             Double.parseDouble(meanInput.getText());
             Integer.parseInt(queueInput.getText());
             Double.parseDouble(cancelInput.getText());
             Double.parseDouble(firstInput.getText());
             Double.parseDouble(businessInput.getText());
             Double.parseDouble(premiumInput.getText());
             Double.parseDouble(economyInput.getText());
             } catch(NumberFormatException e1) {
                 error = true;
                 JOptionPane.showMessageDialog(this, "Numbers only ya big goose","Advertencia!",JOptionPane.WARNING_MESSAGE);
             }
         if(!error){
             chartBtn.setEnabled(true);
             chart2Btn.setEnabled(true);
             String[] args = new String[]{seedInput.getText(),queueInput.getText(),meanInput.getText(),Double.toString(Double.parseDouble(meanInput.getText())*0.33)
                     ,firstInput.getText(),businessInput.getText(),premiumInput.getText(),economyInput.getText(),cancelInput.getText()};
             SimulationRunner.main(args);
         }
         
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new GUISimulator("Zip zoom planes"));
	}

	public static void outputLog(String log){
	    //logArea.setText(log);
	    logArea.append(log);
	}
	
}
