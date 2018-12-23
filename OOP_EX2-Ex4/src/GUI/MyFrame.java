//package GUI;
//
//import java.awt.BasicStroke;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.MenuItem;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.filechooser.FileSystemView;
//
//import Game.ShortestPathAlgo;
//import Game.Map;
//import Game.Fruit;
//import Game.Game;
//import Game.Packman;
//import Game.Path;
//import Geom.Point3D;
//
//
//public class MyFrame extends JFrame implements MouseListener {
//
//	
//	public BufferedImage image;
//	public BufferedImage backman_image;
//	public BufferedImage fruit_image;
//
//
//	double radius = 1;
//	int speed = 1;
//	MenuBar menu = new MenuBar();
//	public Map map = new Map();
//	public  ArrayList<Packman> packmen = new ArrayList<>();
//	public  ArrayList<Fruit> fruits = new ArrayList<>();
//	private Game Game=new Game(packmen, fruits);
//	private int isGamer=0;
//	public boolean Start_game=false;
//	public boolean draw_line = false;
//	public ArrayList<Packman> tmp_list=new ArrayList<>();
//	Path path;
//
//
//	public MyFrame() {
//		initGUI();		
//		this.addMouseListener(this); 
//	}
//
//	private void initGUI() {
//		try {
//			image = ImageIO.read(new File(map.getPic()));
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}	
//		try {
//			backman_image = ImageIO.read(new File("packman.png"));
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			fruit_image = ImageIO.read(new File("fruit2.png"));
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//
//
//		//menu for running and reseting the game, exite too
//		Menu OptionMenu = new Menu("File"); 
//		menu.add(OptionMenu);
//		MenuItem run = new MenuItem("Run");
//		OptionMenu.add(run);
//		MenuItem reset = new MenuItem("Resset");
//		OptionMenu.add(reset);
//
//		//adding opjects of the game
//		Menu add = new Menu("Game Opjects"); 
//		menu.add(add);
//		MenuItem packman = new MenuItem("Packman");
//		add.add(packman);
//		MenuItem fruit = new MenuItem("Fruit");		
//		add.add(fruit);
//
//		//adding file open of the game
//		Menu read=new Menu ("Open");
//		MenuItem csv_file = new MenuItem("Csv File");
//		read.add(csv_file);
//		menu.add(read);
//
//		//adding write csv/kml
//		Menu save_file=new Menu ("Save");
//		menu.add(save_file);
//		MenuItem Csv_write = new MenuItem(" Csv ");
//		save_file.add(Csv_write);
//		MenuItem kml_write = new MenuItem(" Kml ");
//		save_file.add(kml_write);
//		this.setMenuBar(menu);
//
//		Menu more = new Menu("more"); 
//		menu.add(more);
//		MenuItem exit = new MenuItem("Exit");
//		more.add(exit);
//
//
//		//making the add action
//		run.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(Game.packmans.size() >0 && Game.fruits.size() > 0 ) {
//					Start_game=true;
//					draw_line = true;
//					isGamer=2;
//					for (int i = 0; i < Game.packmans.size(); i++) {
//						tmp_list.add(new Packman(Game.packmans.get(i)));
//						tmp_list.get(i).getPath().setFruits(Game.packmans.get(i).getPath().getFruits());
//					}
//					packSmiulation();
//				}
//				Start_game = false;
//			}
//		});
//
//		//making the reset action
//		reset.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				radius = 1;
//				speed = 1;
//				new MenuBar();
//				new Map();
//				packmen = new ArrayList<>();
//				fruits = new ArrayList<>();
//				Game=new Game(packmen, fruits);
//				isGamer=0;
//				Start_game=false;
//				draw_line = false;
//				tmp_list=new ArrayList<>();
//				path=null;
//				repaint();
//			}
//		});
//
//		//making exite action
//		exit.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				dispose();
//			}
//		});
//
//		//making fruit action
//		fruit.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				isGamer = 1;
//			}
//		});
//
//		//making packman action
//		packman.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				isGamer = -1;
//			}
//		});
//
//		//making csv read action
//		csv_file.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser file_read = new JFileChooser();
//				file_read.setCurrentDirectory(new File(System.getProperty("user.home")));
//				file_read.setDialogTitle("Select an Csv File");
//				file_read.setAcceptAllFileFilterUsed(false);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
//				file_read.addChoosableFileFilter(filter);
//				int value = file_read.showOpenDialog(null);
//				if (value == JFileChooser.APPROVE_OPTION) {
//					System.out.println(file_read.getSelectedFile().getPath());
//					Game game = new Game(Game.packmans,Game.fruits);
//					game.setFile(file_read.getSelectedFile().getPath());
//					try {
//						game.CsvReader();
//						Game.packmans = game.packmans;
//						Game.fruits = game.fruits;
//						Game.file = game.file;
//						isGamer = 2;
//						repaint();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
//
//
//
//		//making csv write action
//		Csv_write.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//				jfc.setDialogTitle("Export to Csv File");
//				jfc.setAcceptAllFileFilterUsed(false);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
//				jfc.addChoosableFileFilter(filter);
//				int value = jfc.showSaveDialog(null);
//				if (value == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = jfc.getSelectedFile();
//					System.out.println(selectedFile.getAbsolutePath());
//					Game.setFile(selectedFile.getAbsolutePath());
//					try {
//						Game.CsvWriter();
//					} catch (FileNotFoundException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
//
//		//making kml write action
//		kml_write.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//				jfc.setDialogTitle("Export to KML File");
//				jfc.setAcceptAllFileFilterUsed(false);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("kml","KML");
//				jfc.addChoosableFileFilter(filter);
//
//				int value = jfc.showSaveDialog(null);
//				if (value == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = jfc.getSelectedFile();
//					System.out.println(selectedFile.getAbsolutePath());
//					Game.setFile(selectedFile.getAbsolutePath());
//					try {
//						Game.KmlWriter();
//					} catch (FileNotFoundException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
//	}
//	
//	private void  packSmiulation() {
//
//		ArrayList<Packman> Packmen = new ArrayList<>();
//		ShortestPathAlgo algo = new ShortestPathAlgo(Game);
//		if(Game.packmans.size() == 1) {
//			Packmen = Game.packmans;
//			Path p = algo.algoSinglePackman(Packmen.get(0));
//			fruits=p.getFruits();
//			Packmen.get(0).getPath().setFruits(p.getFruits());
//			Packmen.get(0).getPath().setTime(p.getTime());
//		}
//		else {
//			Packmen = algo.algoMultiPackmans();
//		}
//		for (Packman packman : Packmen){
//			Thread threads = new Thread() {
//				@Override
//				public void run() {
//
//					for (int i = 0; i < packman.getPath().getFruits().size(); i++) {
//						for (int j = 0; j < packman.getPath().getTime(); j++) {
//							if (i == packman.getPath().getFruits().size()) {
//								continue;
//							}
//							Point3D answer = packman.getPath().theNextPoint(packman,packman.getPath().getFruits().get(i) , j);
//							packman.setPackman(answer);
//							repaint();
//							if(packman.getPath().time_P_F(packman,packman.getPath().getFruits().get(i) ) <= 0) {
//								continue;
//							}
//							try {
//								sleep(15);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			};
//			threads.start();
//		}
//	}
//	
////	private void  packSmiulation() {
////		ArrayList<Packman> myPackmens = new ArrayList<>();
////		ArrayList<Fruit> tempfruit = new ArrayList<>();
////
////
////		ShortestPathAlgo algo = new ShortestPathAlgo(Game);
////		myPackmens = algo.algoMultiPackmans();
////
////		for (int i = 0; i < myPackmens.size(); i++) {
////			Path p = algo.algoSinglePackman(myPackmens.get(i));
////			myPackmens.get(i).getPath().setFruits(p.getFruits());
////			myPackmens.get(i).getPath().setTime(p.getTime());
////
////		}
////
////		for (Packman packman : myPackmens)	{
////
////			Thread thread = new Thread() 
////			{
////				@Override
////				public void run()
////				{
////
////					for (int i = 0; i < packman.getPath().getFruits().size(); i++) {
////
////
////						for (int j = 0; j < packman.getPath().getTime(); j++) {
////							if (i == packman.getPath().getFruits().size()) {
////
////								continue;
////							}
////							Point3D ans = packman.getPath().theNextPoint(packman,packman.getPath().getFruits().get(i) , j);
////							packman.setPackman(ans);
////
////							repaint();
////
////							if(packman.getPath().time_P_F(packman,packman.getPath().getFruits().get(i) ) <= 0) {
////
////								continue;
////
////							}
////							try {
////								sleep(15);
////							} catch (InterruptedException e) {
////								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
////						}
////
////					}
////				}
////			};
////			thread.start();
////
////
////		}
////	}
//
//
//	public void paint(Graphics g) {
//
//		Image Image = image.getScaledInstance(this.getWidth(),this.getHeight(),image.SCALE_SMOOTH);
//		g.drawImage(Image, 8,50, getWidth()-20, getHeight()-60,null);
//
//
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setStroke(new BasicStroke(3));
//		double x1 = 0;
//		double y1 = 0 ;
//		double x2 = 0;
//		double y2 = 0 ;
//
//
//		if (isGamer!=0) {
//			for (int i=0; i<Game.fruits.size(); i++) {
//				x1=(int)(Game.fruits.get(i).getGps_point().x()*getWidth());
//				y1=(int)(Game.fruits.get(i).getGps_point().y()*getHeight());	
//				g.drawImage(fruit_image, (int)x1-5, (int)y1-6,30, 30, null);
//			}
//			
//		}
//		for (int j=0; j<Game.packmans.size(); j++) {
//			x1=(Game.packmans.get(j).getPackman().x()*getWidth());
//			y1=(Game.packmans.get(j).getPackman().y()*getHeight());	
//			g.drawImage(backman_image, (int)x1-6,(int) y1-7,30, 30, null);
//		}
//		if(draw_line == true) {		
//			for(Packman pack : tmp_list) {		
//				if (tmp_list.size()==1) {
//					double x1_ =  pack.getPackman().x();
//					double y1_ =  pack.getPackman().y();
//					double x2_ =  fruits.get(0).getGps_point().x();
//					double y2_ =  fruits.get(0).getGps_point().y();
//					g.setColor(Color.blue);
//					g.drawLine((int)(x1_*getWidth()), (int)(y1_*getHeight()),
//							(int)(x2_*getWidth()), (int)(y2_*getHeight()));
//					for (int i = 1; i < fruits.size(); i++) {
//						x1 =  fruits.get(i).getGps_point().x();
//						y1 =  fruits.get(i).getGps_point().y();
//						x2 =  fruits.get(i-1).getGps_point().x();
//						y2 =  fruits.get(i-1).getGps_point().y();
//
//						g.setColor(Color.BLUE);
//						g.drawLine((int)(x1*getWidth()), 
//								(int)(y1*getHeight()),(int)(x2*getWidth()),
//								(int)(y2*getHeight()));	
//					}
//				}
//				if(pack.getPath().getFruits().size() != 0) {
//					double x1_ =  pack.getPackman().x();
//					double y1_ =  pack.getPackman().y();
//					double x2_ =  pack.getPath().getFruits().get(0).getGps_point().x();
//					double y2_ =  pack.getPath().getFruits().get(0).getGps_point().y();
//
//					g.setColor(Color.blue);
//					g.drawLine((int)(x1_*getWidth()), (int)(y1_*getHeight()),
//							(int)(x2_*getWidth()), (int)(y2_*getHeight()));	
//
//					for (int i = 1; i < pack.getPath().getFruits().size(); i++) {
//						x1 =  pack.getPath().getFruits().get(i).getGps_point().x();
//						y1 =  pack.getPath().getFruits().get(i).getGps_point().y();
//						x2 =  pack.getPath().getFruits().get(i-1).getGps_point().x();
//						y2 =  pack.getPath().getFruits().get(i-1).getGps_point().y();
//						g.setColor(Color.blue);
//						g.drawLine((int)(x1*getWidth()), (int)(y1*getHeight()),
//								(int)(x2*getWidth()), (int)(y2*getHeight()));	
//					}
//				}
//			}
//		}
//	}
//
//
//
//	@Override
//	public void mouseClicked(MouseEvent arg) {
//
//		double tmp_x=arg.getX();
//		double tmp_y=arg.getY();
//		tmp_x=tmp_x/getWidth();
//		tmp_y=tmp_y/getHeight();
//		Point3D point_return=new Point3D(tmp_x, tmp_y, 0);
//		Point3D covertedfromPixel = map.Pixel2Gps(tmp_x, tmp_y);
//
//		if (isGamer==(1)) {	
//			Game.fruits.add(new Fruit(point_return,1));
//			System.out.println("Fruit "+covertedfromPixel.toString());
//			repaint();
//		}
//		else if (isGamer==(-1)) {
//			Game.packmans.add(new Packman(point_return, radius, speed));
//			System.out.println("Packman "+covertedfromPixel.toString());
//			repaint();
//		}
//	}
//
//	@Override
//	public void mouseExited(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void mousePressed(MouseEvent arg0) {
//		// TODO Auto-generated method stub		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//	}
//
//}




package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


import Game.ShortestPathAlgo;
import Game.Map;
import Game.Fruit;
import Game.Game;
import Game.Packman;
import Game.Path;
import Geom.Point3D;

/**
 * This Class manages the graphical representation of the entire program.
 * the class is an implements of MouseListener is an extents of JFrame.
 * More: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
 * @author Mimoun Shimon and Omer Paz
 *
 */
public class MyFrame extends JFrame implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public BufferedImage image;
	public BufferedImage packimage;
	public BufferedImage Fruitimage;
	public BufferedImage DEED_Fruit;
	public BufferedImage DEED_Pack;

	double radius = 1;
	int speed = 1;
	MenuBar menuBarOption = new MenuBar();
	public Map theMap = new Map();
	public  ArrayList<Packman> Packman_arr = new ArrayList<>();
	public  ArrayList<Fruit> Fruits_arr = new ArrayList<>();
	private Game myGame=new Game(Packman_arr, Fruits_arr);
	private int isGamer=0;// if is Gamer==1 --> Fruit :::: if is Gamer == -1 --> Packman 
	public boolean Start_game=false;
	public boolean drwaline = false;
	public ArrayList<Packman> ArrayTemp=new ArrayList<>();
	Path TheCloserPackman;


	public MyFrame() 
	{
		initGUI();		
		this.addMouseListener(this); 

	}

	private void initGUI() {


		try {	image = ImageIO.read(new File(theMap.getPic())); } catch (IOException e) { e.printStackTrace();	}	
		try {	packimage = ImageIO.read(new File("packman.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	Fruitimage = ImageIO.read(new File("fruit.png")); } catch (IOException e) { e.printStackTrace();	}




		Menu OptionMenu = new Menu("File"); 
		menuBarOption.add(OptionMenu);
		MenuItem runItem = new MenuItem("Run");
		MenuItem reload_item = new MenuItem("Reload");
		OptionMenu.add(runItem);
		OptionMenu.add(reload_item);
		MenuItem exit = new MenuItem("Exit");
		OptionMenu.add(exit);




		Menu AddMenu = new Menu("Add"); 
		menuBarOption.add(AddMenu);

		MenuItem Packman_Item = new MenuItem("Packman");
		MenuItem Fruit_item = new MenuItem("Fruit");		
		AddMenu.add(Packman_Item);
		AddMenu.add(Fruit_item);

		Menu SetMenu = new Menu("Set"); 

		MenuItem setraduisAll = new MenuItem("Radius All");
		MenuItem setradius2Pack = new MenuItem("Radius To Packman");
		MenuItem setSpeedAll = new MenuItem("Speed All");
		MenuItem setSpeed2Pack = new MenuItem("Speed To Packman");
		MenuItem setWeightAll= new MenuItem("Weight All");
		MenuItem setWeight2Friut= new MenuItem("Weight to Friut");


		SetMenu.add(setraduisAll);
		SetMenu.add(setradius2Pack);
		SetMenu.add(setSpeedAll);
		SetMenu.add(setSpeed2Pack);
		SetMenu.add(setWeightAll);
		SetMenu.add(setWeight2Friut);


		menuBarOption.add(SetMenu);


		Menu Add_import=new Menu ("Import");
		MenuItem Csv_read = new MenuItem("Csv");
		Add_import.add(Csv_read);


		menuBarOption.add(Add_import);
		Menu Add_export=new Menu ("Export");
		menuBarOption.add(Add_export);
		MenuItem Csv_writing_csv = new MenuItem(" Csv ");
		MenuItem Csv_writing_kml = new MenuItem(" Kml ");
		Add_export.add(Csv_writing_csv);
		Add_export.add(Csv_writing_kml);

		this.setMenuBar(menuBarOption);


		//Turn on the buttons

		runItem.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				if(myGame.packmans.size() >0 && myGame.fruits.size() > 0 ) {
					Start_game=true;
					drwaline = true;
					isGamer=2;

					for (int i = 0; i < myGame.packmans.size(); i++) {
						ArrayTemp.add(new Packman(myGame.packmans.get(i)));
						ArrayTemp.get(i).getPath().setFruits(myGame.packmans.get(i).getPath().getFruits());
					}

					packSmiulation();


				}
				Start_game = false;

			}
		});

		reload_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				radius = 1;
				speed = 1;
				new MenuBar();
				new Map();
				Packman_arr = new ArrayList<>();
				Fruits_arr = new ArrayList<>();
				myGame=new Game(Packman_arr, Fruits_arr);
				isGamer=0;
				Start_game=false;
				drwaline = false;
				ArrayTemp=new ArrayList<>();
				TheCloserPackman=null;
				repaint();
			}
		});




		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		Fruit_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = 1;
			}
		});

		Packman_Item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isGamer = -1;

			}
		});

		setraduisAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGame.packmans.size() > 0) {
					String radius= JOptionPane.showInputDialog("Please input the Radius for all the Packmans: ");
					double input_radius = Double.parseDouble(radius);
					for (int i = 0; i < myGame.packmans.size(); i++) {
						myGame.packmans.get(i).setRadius(input_radius);
					}
				} else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}

			}
		});
		setradius2Pack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myGame.packmans.size() > 0) {
					String i = JOptionPane.showInputDialog("Please input which packman you want to change (starting from 0)");
					int numberPack = Integer.parseInt(i);
					if(numberPack > myGame.packmans.size()) {
						JOptionPane.showMessageDialog(null,"EROR: Cant find this Packman");

					}else {
						String radius= JOptionPane.showInputDialog("Please input the Radius for this Packman: ");
						double input_radius = Double.parseDouble(radius);
						myGame.packmans.get(numberPack).setRadius(input_radius);	
					}
				}else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}
			}
		});


		setSpeed2Pack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myGame.packmans.size() > 0) {
					String i = JOptionPane.showInputDialog("Please input which packman you want to change (starting from 0)");
					int numberPack = Integer.parseInt(i);
					if(numberPack > myGame.packmans.size()) {
						JOptionPane.showMessageDialog(null,"EROR: Cant find this Packman");

					}else {
						String Speed= JOptionPane.showInputDialog("Please input the Speed for this Packman: ");
						double input_Speed = Double.parseDouble(Speed);
						myGame.packmans.get(numberPack).setSpeed(input_Speed);	
					}
				}else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}
			}
		});
		setSpeedAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGame.packmans.size() > 0) {
					String speed= JOptionPane.showInputDialog("Please input the Speed for all the Packmans: ");
					double input_speed = Double.parseDouble(speed);
					for (int i = 0; i < myGame.packmans.size(); i++) {
						myGame.packmans.get(i).setSpeed(input_speed);
					}
				} else {
					JOptionPane.showMessageDialog(null,"EROR: There is no Packmans in the Game");

				}

			}
		});



		Csv_read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select an Csv File");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getSelectedFile().getPath());
					Game theGame = new Game(myGame.packmans,myGame.fruits);
					theGame.setFile(fileChooser.getSelectedFile().getPath());
					try {
						theGame.CsvReader();
						myGame.packmans = theGame.packmans;
						myGame.fruits = theGame.fruits;
						myGame.file = theGame.file;
						isGamer = 2;

						repaint();

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});





		Csv_writing_csv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Export to Csv File");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());


					myGame.setFile(selectedFile.getAbsolutePath());
					try {
						myGame.CsvWriter();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}


			}

		});


		Csv_writing_kml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Export to KML File");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("kml","KML");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());


					myGame.setFile(selectedFile.getAbsolutePath());




					try {
						myGame.KmlWriter();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}


				}


			}

		});

	}
	private void  packSmiulation() {
		ArrayList<Packman> myPackmens = new ArrayList<>();
		ArrayList<Fruit> tempfruit = new ArrayList<>();


		ShortestPathAlgo algo = new ShortestPathAlgo(myGame);
		myPackmens = algo.algoMultiPackmans();

		for (int i = 0; i < myPackmens.size(); i++) {
			Path p = algo.algoSinglePackman(myPackmens.get(i));
			myPackmens.get(i).getPath().setFruits(p.getFruits());
			myPackmens.get(i).getPath().setTime(p.getTime());

		}

		for (Packman packman : myPackmens)	{

			Thread thread = new Thread() 
			{
				@Override
				public void run()
				{

					for (int i = 0; i < packman.getPath().getFruits().size(); i++) {


						for (int j = 0; j < packman.getPath().getTime(); j++) {
							if (i == packman.getPath().getFruits().size()) {

								continue;
							}
							Point3D ans = packman.getPath().theNextPoint(packman,packman.getPath().getFruits().get(i) , j);
							packman.setPackman(ans);

							repaint();

							if(packman.getPath().time_P_F(packman,packman.getPath().getFruits().get(i) ) <= 0) {

								continue;

							}
							try {
								sleep(15);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}
			};
			thread.start();


		}
	}





	public void paint(Graphics g) {

		Image scaledImage = image.getScaledInstance(this.getWidth(),this.getHeight(),image.SCALE_SMOOTH);
		g.drawImage(scaledImage, 8,50, getWidth()-17, getHeight()-60,null);


		Graphics2D g2 = (Graphics2D)g;

		g2.setStroke(new BasicStroke(3));

		double x1 = 0;
		double y1 = 0 ;
		double x2 = 0;
		double y2 = 0 ;


		if (isGamer!=0) {
			for (int i=0; i<myGame.fruits.size(); i++) 
			{
				x1=(int)(myGame.fruits.get(i).getGps_point().x()*getWidth());
				y1=(int)(myGame.fruits.get(i).getGps_point().y()*getHeight());	

				g.drawImage(Fruitimage, (int)x1-5, (int)y1-6,30, 30, null);

			}
		}

		for (int j=0; j<myGame.packmans.size(); j++) {

			x1=(myGame.packmans.get(j).getPackman().x()*getWidth());
			y1=(myGame.packmans.get(j).getPackman().y()*getHeight());	

			g.drawImage(packimage, (int)x1-6,(int) y1-7,30, 30, null);

		}

		if(drwaline == true) {

			int j=0;
			for(Packman pack : myGame.packmans) {

				if(pack.getPath().getFruits().size() != 0) {
					double x1_ =  ArrayTemp.get(j).getPackman().x();
					double y1_ =  ArrayTemp.get(j).getPackman().y();
					double x2_ =  pack.getPath().getFruits().get(0).getGps_point().x();
					double y2_ = pack.getPath().getFruits().get(0).getGps_point().y();

					g.setColor(Color.orange);
					g.drawLine((int)(x1_*getWidth()), (int)(y1_*getHeight()),(int)(x2_*getWidth()), (int)(y2_*getHeight()));
					j++;

					for (int i = 1; i < pack.getPath().getFruits().size(); i++) {

						x1 =  pack.getPath().getFruits().get(i).getGps_point().x();
						y1 =  pack.getPath().getFruits().get(i).getGps_point().y();
						x2 =  pack.getPath().getFruits().get(i-1).getGps_point().x();
						y2 =  pack.getPath().getFruits().get(i-1).getGps_point().y();


						g.setColor(Color.orange);
						g.drawLine((int)(x1*getWidth()), (int)(y1*getHeight()),(int)(x2*getWidth()), (int)(y2*getHeight()));	

					}
				}

			}
		}


	}





	@Override
	public void mouseClicked(MouseEvent arg) {

		double x_temp=arg.getX();
		x_temp=x_temp/getWidth();


		double y_temp=arg.getY();
		y_temp=y_temp/getHeight();
		Point3D point_return=new Point3D(x_temp, y_temp, 0);
		Point3D covertedfromPixel = theMap.Pixel2Gps(x_temp, y_temp);


		if (isGamer==(1))
		{	
			myGame.fruits.add(new Fruit(point_return,1));

			System.out.println("Fruit "+covertedfromPixel.toString());

			repaint();

		}else if (isGamer==(-1))
		{
			myGame.packmans.add(new Packman(point_return, radius, speed));
			System.out.println("Packman "+covertedfromPixel.toString());

			repaint();
		}

	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub		

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


}
