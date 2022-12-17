package bruteForceAlgo;

import java.util.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
//Jarrad Self
//Algorithm Analysis 
//Project 1
public class BruteForce{
	
	static ArrayList<Point> pointlist = new ArrayList<Point>(); //generating ArrayList for random point list, convexhull, and solutionlist to be printed to screen
	static ArrayList<Line> convexhull = new ArrayList<Line>(); 
	static Set<Point> solutionlist = new HashSet<Point>();
	
		public static void main(String[] args)	{
			
			
			Scanner scan = new Scanner(System.in);
			int randPoint; 
			
			System.out.print("How many points do you want the graph to have? ");
			randPoint = scan.nextInt();
			
			RandomNumGenerator(randPoint);
			
			PrintList(pointlist);
			
			System.out.println("Points of Convex Hull in counterclockwise order: ");
			BruteForceAlgo(pointlist, solutionlist);
			System.out.println(solutionlist);
			scan.close();
			
			Visualization();
			
		}
		
		public static void RandomNumGenerator(int randPoint)	{  //generating random numbers from -100 to 100
			for(int i = 1; i <= randPoint; i++)	{
				Random rand = new Random();
				int x = rand.nextInt(200) - 100;
				int y = rand.nextInt(200) - 100; 
				Point P = new Point(x,y);
				pointlist.add(P);
			}
		}
		
		public static void PrintList(ArrayList<Point> List)	{
			for(int i = List.size() - 1; i >= 0; i--) {	
				System.out.print(List.get(i).toString() + "\n");
			}
			
			System.out.println(" "); 
		}
		
		public static void BruteForceAlgo(ArrayList<Point> points, Set<Point> solutionlist2) { //Brute force algorithm for my convex hull
			for(int i = 0; i < points.size(); i++)	{
				for(int j = 0; j < points.size(); j++)	{
					if( i == j) {
						continue;
					}
					
					boolean Line = true; 
					Point point1 = points.get(i);
					Point point2 = points.get(j);
					int A = point2.GetY() - point1.GetY();
					int B = point1.GetX() - point2.GetX();
					int C = (point1.GetX() * point2.GetY()) - (point1.GetY() * point2.GetX()); 
					
					for(int k = 0; k < points.size(); k++)	{
						if(k == i || k == j)	{
							continue;
						}
						
						Point point3 = points.get(k);
						int Area = A * point3.GetX() + B * point3.GetY() - C;
						if(Area < 0)	{
							Line = false;
							break;
						}
					}
					
					if(Line) {
						solutionlist2.add(point1);		//points on right side of line means its added to the solution list
						solutionlist2.add(point2);
						
						convexhull.add(new Line(point1, point2));
					}
				}
			}
			
		}
		
		private static void Visualization()	{
			
			JFrame frame = new JFrame("Graph of Convex Hull using BruteForce Algorithm");
			frame.setSize(500,500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setResizable(false);
				frame.add(new JPanel(){
					@Override
						public void paintComponent(Graphics g)	{
						int x = 500;
						int y = 500;
						int center = 250;
						
						g.setColor(Color.BLACK);
						g.drawLine(0,  center, x, center);
						g.drawLine(center, 0, center, y);
						
						g.setColor(Color.BLACK);
						for(Point p : pointlist)	{
							g.fillOval(p.GetX() + center - 2, center - p.GetY() - 2, 4, 4);
						}
						
						for(int i = 0; i < convexhull.size(); i++)	{
							Point point1 = convexhull.get(i).getP1();
							Point point2 = convexhull.get(i).getP2();
							g.drawLine(point1.GetX() + center, center - point1.GetY(), point2.GetX() + center, center - point2.GetY());
							
						}
						
						int n = pointlist.size();
			                g.drawString("Convex Hull with " + n + " points", 160, 25);
					}
					
				});
		}
		
}