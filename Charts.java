//	A01324276	Rafael Antonio Comonfort Viveros
//	A01328937	Luis Francisco Flores Romero
//	Created		Aug 23, 2018

//	Charts
//	This class draws a pie chart and a bars chart for input data read from a text file

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.Random;

public class Charts extends JPanel {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 500;
	public static int n;
	public static int total;
	public static int[] values;
	public static String[] labels;
	public static Color[] colors;
	
	/**
		Paint component
		This method overrides paintComponent method, it calls other methods to draw the pie
		char, the bars chart and the labels.
		@param	g	Grpahics object to draw components.
		@return		Nothing.
	*/
	@Override
	protected void paintComponent(Graphics g) {
		drawPieChart(g);
		drawLabels(g);
		drawBarChart(500, 50, g);
	}
	
	/**
		Generate colors
		This method generates a random set of n colors to be used in charts and labels.
		@return		Array of colors.
	*/
	public static Color[] generateColors() {
		Random ran = new Random();
		Color[] res = new Color[n];
		for(int i = 0; i < n; i++) {
			res[i] = new Color(ran.nextInt(200), ran.nextInt(200), ran.nextInt(200));
		}
		
		return res;
	}
	
	/**
		Draw pie chart
		This method receives a Graphics object and uses it to draw the pie chart for the data read.
		@param	g	Grpahics object.
		@return		Nothing.
	*/
	public void drawPieChart(Graphics g) {
		double top = 0;
		for(int i = 0; i < n; i++) {
			g.setColor(colors[i]);
			g.fillArc(50, 50, 400, 400, (int)Math.round(top), angle(values[i]));
			top += angle(values[i]);
		}
	}
	
	/**
		Draw bar chart
		This method uses received Graphics object to draw the bars chart for the data read.
		@param	xOffset	Starting point in x axis.
		@param	yOffset	Starting point in y axis.
		@param	g		Graphics object.
		@return			Nothing.
	*/
	private static void drawBarChart(int xOffset, int yOffset, Graphics g) {

		final int barWidth = 500 / 11;
		final int barChartHeight = 400;

		//Find max value to use as tallest bar and reference to the others'
		int maxValue = Integer.MIN_VALUE;
		for (int i = 0; i < values.length; i++) {
			if (values [i] > maxValue) {
				maxValue = values[i];
			}
		}

		//Print left side number scale composed of 5 labels
		for (int i = 0; i < 5; i++) {
			g.setColor(Color.BLACK);

			String printValue = i == 4 ? "0" : "" + (maxValue / (1 + i));

			g.drawString(printValue , xOffset, yOffset + barChartHeight/4 * i);
		}

		//Parameters to drive individual bars drawing, will be modified
		int nextBarXPos = xOffset;
		int nextBarYPos = yOffset;
		int nextBarHeight = 0;

		//Draw actual bars
		for (int i = 0; i < values.length; i++) {
			//Calculate the current bar's parameters
			nextBarXPos += barWidth;
			nextBarHeight = (values[i] * barChartHeight) / maxValue;
			nextBarYPos = yOffset + (barChartHeight - nextBarHeight);
			
			g.setColor(colors[i]);
			g.fillRect(nextBarXPos, nextBarYPos, barWidth, nextBarHeight);	
		}
	}
	
	/**
		Draw labels
		This method draws the data labels below the charts. It uses data global labels and colors arrays.
		@param	g	Graphics object.
		@return		Nothing.
	*/
	public void drawLabels(Graphics g) {
		int labelsTop = 460;
		int labelsLeft = 20;
		int topX = labelsLeft;
		int length;
		
		for(int i = 0; i < n; i++) {
			g.setColor(colors[i]);
			g.fillRect(topX, 460, 10, 10);
			g.drawString(labels[i], topX + 15, 470);
			length = labels[i].length() * 9;
			topX += 20 + length;
		}
	}
	
	/**
		Compute total
		This method sums data categories frequencies.
		@return		The total sum of frequencies.
	*/
	public static int computeTotal() {
		int res = 0;
		for(int i = 0; i < n; i++) {
			res += values[i];
		}
		return res;
	}
	
	/**
		Angle
		This method computes the angle for a given frequency in the pie chart.
		@param	frequency	The absolute frequency of the category.
		@return				The angle for the given frequency.
	*/
	public int angle(int frequency) {
		double res = 0;
		
		res = 360.0 * frequency / total;
		
		return (int)Math.round(res);
	}
	
	/**
		Main
		This is the main method for the Grpahs classs, which reads data from text file and
		calls other methods to generate colors, compute total and draw charts.
	*/
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		values = new int[n];
		labels = new String[n];
		
		colors = generateColors();
		
		for(int i = 0; i < n; i++) {
			values[i] = in.nextInt();
		}
		in.nextLine();
		total = computeTotal();
		for(int i = 0; i < n; i++) {
			labels[i] = in.nextLine();
		}
		
		for(int i = 0; i < n; i++) {
			System.out.println(labels[i] + ": " + values[i]);
		}
		
		Charts panel = new Charts();
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		window.setSize(Charts.WIDTH, Charts.HEIGHT);
		window.setVisible(true);
	}
}