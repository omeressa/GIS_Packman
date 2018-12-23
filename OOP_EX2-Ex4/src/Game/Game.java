package Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import File_format.Csv2Kml;
import Geom.Point3D;

public class Game {

	public  ArrayList<Packman> packmans = new ArrayList<>();
	public  ArrayList<Fruit> fruits = new ArrayList<>();

	public Map map = new Map();
	public 	String file;


	public Game() {};

	/**
	 * Constructor
	 * @param packmans
	 * @param fruits
	 */
	public Game(ArrayList<Packman> packmans , ArrayList<Fruit> fruits) {
		this.packmans = packmans;
		this.fruits = fruits;
	}


	/**
	 * Csv file reader for Ex3
	 * @throws IOException
	 */
	public void CsvReader() throws IOException{	
		List<String[]> tempFile = new ArrayList<String[]>();
		FileReader fr = new FileReader(getFile());
		BufferedReader br = new BufferedReader(fr);
		for (String line = br.readLine(); line != null; line = br.readLine())
			tempFile.add(line.split(","));
		br.close();
		fr.close();
		//packman
		for (int i = 1; i < tempFile.size(); i++) {
			String[] row = tempFile.get(i);
			if(row[0].equals("P")) {
				Point3D p = new Point3D(row[2],row[3],row[4]);
				p = map.Gps2Pixel(p);
				double speed = Double.parseDouble(row[5]);
				double radius = Double.parseDouble(row[6]);
				packmans.add(new Packman(p, speed, radius));	
			}
			//fruit
			else if(row[0].equals("F")) {
				Point3D p = new Point3D(row[2],row[3],row[4]);
				p = map.Gps2Pixel(p);
				int Weight = Integer.parseInt(row[5]);
				fruits.add(new Fruit(p, Weight));
			}
		}
	}



	public  void CsvWriter() throws FileNotFoundException {
		PrintWriter printwriter = new PrintWriter(new File(getFile()+".csv"));
		StringBuilder stringbuilder = new StringBuilder();
		String[] requested_info = {"Type","id"	,"Lat"	,"Lon"	,"Alt"	,"Speed/Weight"	,"Radius"};
		for (int i = 0; i < requested_info.length; i++)
			stringbuilder.append(requested_info[i]+",");
		stringbuilder.append(this.packmans.size());
		stringbuilder.append(',');
		stringbuilder.append(this.fruits.size());
		stringbuilder.append('\n');
		for (int i = 0; i < packmans.size(); i++) {
			packmans.get(i).Packman = map.Pixel2Gps(packmans.get(i).getPackman().x(), packmans.get(i).getPackman().y());
		}
		for (int i = 0; i < fruits.size(); i++) {
			fruits.get(i).gps_point = map.Pixel2Gps(fruits.get(i).getGps_point().x(), fruits.get(i).getGps_point().y());
		}
		//writing PackMan info
		for (int i = 0; i < this.packmans.size(); i++)
			stringbuilder.append("P"+","+i+","+packmans.get(i).Packman.x()+","+packmans.get(i).Packman.y()+","+packmans.get(i).Packman.z()+","+this.packmans.get(i).getSpeed()+","+this.packmans.get(i).getRadius()+"\n");
		//writing Fruit info
		for (int i = 0; i < fruits.size(); i++)
			stringbuilder.append("F"+","+i+","+fruits.get(i).gps_point.x()+","+fruits.get(i).gps_point.y()+","+fruits.get(i).gps_point.z()+","+this.fruits.get(i).getWeight()+"\n");
		printwriter.write(stringbuilder.toString());
		printwriter.close();
	}

	public ArrayList<String> KmlWriter1() throws FileNotFoundException {
		//dataArray will contain all the info of the kml file in ArrayList
		//this code based on  someone elses code on github
		ArrayList<String> dataArray = new ArrayList<String>();
		ArrayList<Packman> Packmen = new ArrayList<>();
		ShortestPathAlgo shortest_path_algo = new ShortestPathAlgo(this);
		
		
		
		

		
		
		
		
		dataArray.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>\r\n<name> Points with TimeStamps</name>\r\n <Style id=\"red\">\r\n" + 
				"<IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle>\r\n" + 
				"</Style><Style id=\"Packman\"><IconStyle><Icon><href>http://www.iconhot.com/icon/png/quiet/256/pac-man.png</href></Icon></IconStyle>\r\n" + 
				"</Style><Style id=\"Fruit\"><IconStyle><Icon><href>http://www.stickpng.com/assets/images/580b57fcd9996e24bc43c316.png</href></Icon></IconStyle></Style>\r\n" + 
				"\r\n" + 
				"    <Style id=\"check-hide-children\">\r\n" + 
				"      <ListStyle>\r\n" + 
				"        <listItemType>checkHideChildren</listItemType>\r\n" + 
				"      </ListStyle>\r\n" + 
				"    </Style>\r\n" + 
				"    <styleUrl>#check-hide-children</styleUrl>"+
				"\r\n"+"<Folder><name>Packman Game</name>\n\n");
		String[] requestedInfo = {"Type","id","Lat","Lon","Speed/Weight"	,"Radius"};
		if(packmans.size() == 1) {
			Packmen =packmans;
			Path path = shortest_path_algo.algoSinglePackman(Packmen.get(0));
			Packmen.get(0).getPath().setFruits(path.getFruits());
			Packmen.get(0).getPath().setTime(path.getTime());
		}
		else 
			Packmen = shortest_path_algo.algoMultiPackmans();
		//loading packman gps points
		for (int i = 0; i < Packmen.size(); i++) {
			Packmen.get(i).Packman = map.Pixel2Gps(Packmen.get(i).getPackman().x(), Packmen.get(i).getPackman().y());
			//loading fruits gps points
			for (int j = 0; j < Packmen.get(i).getPath().getFruits().size(); j++) {
				Packmen.get(i).getPath().getFruits().get(j).gps_point =
						map.Pixel2Gps( Packmen.get(i).getPath().getFruits().get(j).getGps_point().y(),  Packmen.get(i).getPath().getFruits().get(j).getGps_point().x());
			}
		}
		Date date = new Date();//date gives us the current time
		int count=(-1);
		for (Packman packman_for : Packmen) {
			count++;
			dataArray.add("<Placemark>\n" +
					"<name><![CDATA[ PACKMAN START "+count+"]]></name>\n" +
					"<description>"+
					"<![CDATA["
					+requestedInfo[0]+": <b> PACKMAN  </b><br/>"
					+requestedInfo[1]+": <b> PACKMAN Start Number"+count+" </b><br/>"
					+requestedInfo[2]+": <b>"+packman_for.Packman.x()+" </b><br/>" 
					+requestedInfo[3]+": <b>"+packman_for.Packman.y()+" </b><br/>" 
					+requestedInfo[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
					+requestedInfo[5]+": <b>"+packman_for.getRadius()+" </b><br/>"
					+"]]></description>\n" +
					"<TimeStamp>\r\n" + 
					"<when>"+date.getDate()+" of the month"+","+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"</when>\r\n" + 
					"</TimeStamp>"+
					"<styleUrl>#Packman</styleUrl>"+
					"<Point>\n" +
					"<coordinates>"+packman_for.Packman.y()+","+packman_for.Packman.x()+"</coordinates>" +
					"</Point>\n" +
					"</Placemark>\n");


			if(packman_for.getPath().getFruits().size()==0) {
				dataArray.add("<Placemark>\n" +
						"<name><![CDATA[ PACKMAN Without PATH "+count +"]]></name>\n" +
						"<description>"+
						"<![CDATA["
						+requestedInfo[0]+": <b> PACKMAN  </b><br/>"
						+requestedInfo[1]+": <b> PACKMAN  Without PATH Number"+count+" </b><br/>"
						+requestedInfo[2]+": <b>"+packman_for.Packman.x()+" </b><br/>" 
						+requestedInfo[3]+": <b>"+packman_for.Packman.y()+" </b><br/>" 
						+requestedInfo[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
						+requestedInfo[5]+": <b>"+packmans.get(count).getRadius()+" </b><br/>" 
						+"]]></description>\n" +
						"<TimeStamp>\r\n" + 
						"<when>"+date.getDate()+" of the month"+","+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"</when>\r\n" + 
						"</TimeStamp>"+
						"<styleUrl>#Packman</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+(packman_for.Packman.y()/*+0.1218500323*/)+","+(packman_for.Packman.x()/*-0.3181922431*/)+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n");
				

			}
			int i=-1;
			for (Fruit f  : fruits) {
				i++;
				//			for (int i = 0; i < packman_for.getPath().getFruits().size(); i++) {
				//				now_start=now_start.plusMinutes(5);
				//				temp_start=now_start.plusMinutes(10);
				dataArray.add("<Placemark>\n" +
						"<name><![CDATA[ FRUIT "+(i)+",Pac:"+count+"]]></name>\n <description>"+
						"<![CDATA["
						+requestedInfo[0]+": <b> FRUIT  </b><br/>"
						+requestedInfo[1]+": <b> FRUIT Number :"+i+",Pac:"+count+" </b><br/>"
						//////////////////////
						+requestedInfo[2]+": <b>"+f.getGps_point().x()+" </b><br/>" 
						+requestedInfo[3]+": <b>"+f.getGps_point().y()+" </b><br/>" 
						+requestedInfo[4]+": <b>"+f.getWeight()+" </b><br/>" 



						//						+requestedInfo[2]+": <b>"+packman_for.getPath().getFruits().get(i).getGps_point().x()+" </b><br/>" 
						//						+requestedInfo[3]+": <b>"+packman_for.getPath().getFruits().get(i).getGps_point().y()+" </b><br/>" 
						//						+requestedInfo[4]+": <b>"+packman_for.getPath().getFruits().get(i).getWeight()+" </b><br/>" 
						+"]]></description>\n" +
						"<TimeStamp>\r\n" + 
						"<when>"+date.getDate()+" of the month"+","+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"</when>\r\n" + 
						"</TimeStamp>"+
						"<styleUrl>#Fruit</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+f.getGps_point().y()+","
						+f.getGps_point().x()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n");			
				dataArray.add("<Placemark>\n" +
						"<name><![CDATA[ PACKMAN Moving "+count+", "+i+"]]></name>\n" +
						"<description>"+
						"<![CDATA["
						+requestedInfo[0]+": <b> PACKMAN  </b><br/>"
						+requestedInfo[1]+": <b> PACKMAN Moving "+count+", "+i+"</b><br/>"
						+requestedInfo[2]+": <b>"+packman_for.Packman.x()+" </b><br/>" 
						+requestedInfo[3]+": <b>"+packman_for.Packman.y()+" </b><br/>" 
						+requestedInfo[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
						+requestedInfo[5]+": <b>"+packmans.get(count).getRadius()+" </b><br/>"
						+"]]></description>\n" +
						"<TimeStamp>\r\n" + 
						"<when>"+date.getDate()+" of the month"+","+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"</when>\r\n" + 
						"</TimeStamp>"+
						"<styleUrl>#Packman</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+f.gps_point.y()+","
						+f.getGps_point().x()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n");
			}
		}
		dataArray.add("</Folder>\n" + 
				"</Document>\n</kml>");
		return dataArray;
	} 
	
	
	
	
	
	
	
//	public ArrayList<String> KmlWriter1() throws FileNotFoundException {
//		
//
//
//
//		ArrayList<String> content = new ArrayList<String>();
//		String kmlstart = 
//				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
//						"<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>\r\n<name> Points with TimeStamps</name>\r\n <Style id=\"red\">\r\n" + 
//						"<IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle>\r\n" + 
//						"</Style><Style id=\"Packman\"><IconStyle><Icon><href>http://www.iconhot.com/icon/png/quiet/256/pac-man.png</href></Icon></IconStyle>\r\n" + 
//						"</Style><Style id=\"Fruit\"><IconStyle><Icon><href>https://www.clipartmax.com/png/full/10-109149_heart-outline-clipart.png</href></Icon></IconStyle></Style>\r\n" + 
//						"\r\n" + 
//						"    <Style id=\"check-hide-children\">\r\n" + 
//						"      <ListStyle>\r\n" + 
//						"        <listItemType>checkHideChildren</listItemType>\r\n" + 
//						"      </ListStyle>\r\n" + 
//						"    </Style>\r\n" + 
//						"    <styleUrl>#check-hide-children</styleUrl>"+
//						"\r\n"+"<Folder><name>GAME PACKMAN</name>\n\n";
//
//		content.add(kmlstart);
//		String[] nameData = {"Type","id","Lat","Lon","Speed/Weight"	,"Radius"};
//
//		String kmlend = "</Folder>\n" + 
//				"</Document>\n</kml>";
//
//		ArrayList<Packman> myPackmens = new ArrayList<>();
//		ShortestPathAlgo algo = new ShortestPathAlgo(this);
//
//		myPackmens = algo.algoMultiPackmans();
//
//		for (int i = 0; i < myPackmens.size(); i++) {
//			Path p = algo.algoSinglePackman(myPackmens.get(i));
//			myPackmens.get(i).getPath().setFruits(p.getFruits());
//			myPackmens.get(i).getPath().setTime(p.getTime());
//
//		}
//		
//
//
//		for (int i = 0; i < myPackmens.size(); i++) {
//			myPackmens.get(i).Packman = map.Pixel2Gps(myPackmens.get(i).getPackman().x(), myPackmens.get(i).getPackman().y());
//
//			for (int j = 0; j < myPackmens.get(i).getPath().getFruits().size(); j++) {
//				myPackmens.get(i).getPath().getFruits().get(j).gps_point =
//						map.Pixel2Gps( myPackmens.get(i).getPath().getFruits().get(j).getGps_point().x(),  myPackmens.get(i).getPath().getFruits().get(j).getGps_point().y());
//
//			}
//		}
//
//		LocalDateTime now_start=LocalDateTime.now();
//		LocalDateTime temp_start_official=now_start;
//		LocalDateTime now_start_end=temp_start_official.plusHours(6);
//		LocalDateTime temp_start=now_start;
//
//
//		int j=(-1);
//
//		for (Packman packman_for : myPackmens)
//		{
//			j++;
//
//			now_start=	temp_start_official;
//
//			String kmlelement ="<Placemark>\n" +
//					"<name><![CDATA[ PACKMAN START "+j+"]]></name>\n" +
//					"<description>"+
//					"<![CDATA["
//					+nameData[0]+": <b> PACKMAN  </b><br/>"
//					+nameData[1]+": <b> PACKMAN Start Number"+j+" </b><br/>"
//					+nameData[2]+": <b>"+packman_for.Packman.x()+" </b><br/>" 
//					+nameData[3]+": <b>"+packman_for.Packman.y()+" </b><br/>" 
//					+nameData[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
//					+nameData[5]+": <b>"+packman_for.getRadius()+" </b><br/>" // altito to meter
//
//
//					+"]]></description>\n" +
//					"<TimeStamp>\r\n" + 
//					"        <when>"+now_start+"</when>\r\n" + 
//					"      </TimeStamp>"+
//					"<styleUrl>#Packman</styleUrl>"+
//					"<Point>\n" +
//					"<coordinates>"+packman_for.Packman.y()+","+packman_for.Packman.x()+"</coordinates>" +
//					"</Point>\n" +
//					"</Placemark>\n";
//
//
//			content.add(kmlelement);
//
//
//			if(packman_for.getPath().getFruits().size()==0)
//			{
//
//				String kmlelement2 ="<Placemark>\n" +
//						"<name><![CDATA[ PACKMAN Without PATH "+j +"]]></name>\n" +
//						"<description>"+
//						"<![CDATA["
//						+nameData[0]+": <b> PACKMAN  </b><br/>"
//						+nameData[1]+": <b> PACKMAN  Without PATH Number"+j+" </b><br/>"
//						+nameData[2]+": <b>"+packman_for.Packman.x()+" </b><br/>" 
//						+nameData[3]+": <b>"+packman_for.Packman.y()+" </b><br/>" 
//						+nameData[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
//						+nameData[5]+": <b>"+packmans.get(j).getRadius()+" </b><br/>" // altito to meter
//
//
//
//						+"]]></description>\n" +
//						"<TimeStamp>\r\n" + 
//						"        <when>"+now_start_end+"</when>\r\n" + 
//						"      </TimeStamp>"+
//						"<styleUrl>#Packman</styleUrl>"+
//						"<Point>\n" +
//						"<coordinates>"+packman_for.Packman.y()+","+packman_for.Packman.x()+"</coordinates>" +
//						"</Point>\n" +
//						"</Placemark>\n";
//
//
//				content.add(kmlelement2);
//
//			}
//				
//				for (int i = 0; i < packman_for.getPath().getFruits().size(); i++) {
//
//					now_start=now_start.plusMinutes(5);
//					temp_start=now_start.plusMinutes(10);
//
//
//
//
//					kmlelement ="<Placemark>\n" +
//							"<name><![CDATA[ FRUIT "+(i)+",Pac:"+j+"]]></name>\n <description>"+
//							"<![CDATA["
//							+nameData[0]+": <b> FRUIT  </b><br/>"
//							+nameData[1]+": <b> FRUIT Number :"+i+",Pac:"+j+" </b><br/>"
//							+nameData[2]+": <b>"+packman_for.getPath().getFruits().get(i).getGps_point().x()+" </b><br/>" 
//							+nameData[3]+": <b>"+packman_for.getPath().getFruits().get(i).getGps_point().y()+" </b><br/>" 
//							+nameData[4]+": <b>"+packman_for.getPath().getFruits().get(i).getWeight()+" </b><br/>" 
//
//
//						+"]]></description>\n" +
//						"<TimeStamp>\r\n" + 
//						"        <when>"+now_start+"</when>\r\n" + 
//						"      </TimeStamp>"+
//						"<styleUrl>#Fruit</styleUrl>"+
//						"<Point>\n" +
//						"<coordinates>"+packman_for.getPath().getFruits().get(i).getGps_point().y()+","
//						+packman_for.getPath().getFruits().get(i).getGps_point().x()+"</coordinates>" +
//						"</Point>\n" +
//						"</Placemark>\n";
//
//
//
//					content.add(kmlelement);			
//					
//					
//					
//					
//					if(i+1==packman_for.getPath().getFruits().size()) temp_start=now_start_end;
//
//					String kmlelement2 ="<Placemark>\n" +
//							"<name><![CDATA[ PACKMAN Moving "+j+", "+i+"]]></name>\n" +
//							"<description>"+
//							"<![CDATA["
//							+nameData[0]+": <b> PACKMAN  </b><br/>"
//							+nameData[1]+": <b> PACKMAN Moving "+j+", "+i+"</b><br/>"
//							+nameData[2]+": <b>"+packman_for.Packman.x()+" </b><br/>" 
//							+nameData[3]+": <b>"+packman_for.Packman.y()+" </b><br/>" 
//							+nameData[4]+": <b>"+packman_for.getSpeed()+" </b><br/>" 
//							+nameData[5]+": <b>"+packmans.get(j).getRadius()+" </b><br/>" // altito to meter
//
//
//
//							+"]]></description>\n" +
//							"<TimeStamp>\r\n" + 
//							"        <when>"+temp_start+"</when>\r\n" + 
//							"      </TimeStamp>"+
//							"<styleUrl>#Packman</styleUrl>"+
//							"<Point>\n" +
//							"<coordinates>"+packman_for.getPath().getFruits().get(i).getGps_point().y()+","
//							+packman_for.getPath().getFruits().get(i).getGps_point().x()+"</coordinates>" +
//							"</Point>\n" +
//							"</Placemark>\n";
//
//
//					content.add(kmlelement2);
//
//				}
//
//
//		}
//
//		content.add(kmlend);
//
//		return content;
//	} 
	
	
	
	

	/**
	 * Kml writer 
	 * @throws FileNotFoundException
	 */
	public void KmlWriter() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(getFile()+".kml"));
		ArrayList<String> kml = new ArrayList<String>();
		kml=KmlWriter1();
		pw.write(String.join("\n", kml));
		pw.close();		
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
}
//
//
//
//
