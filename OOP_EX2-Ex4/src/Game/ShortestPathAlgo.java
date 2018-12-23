//package Game;
//
//import java.util.ArrayList;
//
//public class ShortestPathAlgo {
//
//	ArrayList<Fruit> fruits = new ArrayList<>();
//	ArrayList<Packman> Packmans = new ArrayList<>();
//	Map map = new Map();
//	Compare comp = new Compare(); 
//
//
//	/**
//	 * Constructor
//	 * @param game
//	 */
//	public ShortestPathAlgo(Game game) {	
//		ArrayList<Fruit> fruits = new ArrayList<Fruit>(game.fruits.size());  
//		for (Fruit fruit : game.fruits) fruits.add(fruit);
//		this.fruits = fruits;
//		this.Packmans = game.packmans;
//	}
//
//	/**
//	 * algorithim for one Packman
//	 * @param p
//	 * @return
//	 */
//	public Path algoSinglePackman(Packman p){
//		ArrayList<Fruit> fruits = this.fruits;
//
//		Packman tempPackman = new Packman(p.getPackman(),p.getSpeed(),p.getRadius());
//		Path distance = disAlgo(tempPackman,fruits);
//		Path path = calFastDisOnePack(tempPackman, fruits);
//
//		p.getPath().setFruits(distance.getFruits());	
//		double time1 = distance.totalTime_Path(p);
//		p.getPath().setFruits(path.getFruits());
//		double time2 = path.totalTime_Path(p);
//		if(time1 <= time2) {
//			p.getPath().setFruits(distance.getFruits());	
//			p.getPath().setTime(time1);
//			System.out.println(p.getPath().getTime());
//			return p.getPath();
//		}
//		else {
//			p.getPath().setTime(time2);
//			System.out.println(p.getPath().getTime());
//			return p.getPath();
//		}
//	}
//
//
//	/**
//	 * function gives us the closest fruit to the packman
//	 * @param packman
//	 * @param fruits
//	 * @return
//	 */
//	public Path calFastDisOnePack (Packman packman, ArrayList<Fruit> fruits) {
//		if (fruits.isEmpty()) 
//			return packman.getPath();
//
//		Fruit closeFruit = TheCloserFurit(packman,fruits); // the closer furit to packman 
//		packman.getPath().getFruits().add(closeFruit);
//		packman.setPackman(closeFruit.getGps_point());
//		fruits.remove(getIndexFurit(closeFruit, fruits));
//		return calFastDisOnePack(packman,fruits);
//	}
//
//	/**
//	 * function gives us the distance between one packman and ea fruit
//	 * @param packman
//	 * @param fruits
//	 * @return
//	 */
//	public Path disAlgo(Packman packman, ArrayList<Fruit> fruits) {
//
//		ArrayList<Double> sorted_paths = new ArrayList<>();
//		Path path = new Path();
//		for (int i = 0; i < fruits.size(); i++) {
//			double tmp = map.distance3D(packman.getPackman(), 
//					fruits.get(i).getGps_point());
//			sorted_paths.add(tmp);
//		}
//		sorted_paths.sort(comp);;
//		double temp;
//		for (int i = 0; i < sorted_paths.size(); i++) {
//			for (int j = 0; j < fruits.size(); j++) {
//				temp = map.distance3D(packman.getPackman(),
//						fruits.get(j).getGps_point());
//				if(temp == sorted_paths.get(i)) {
//					path.getFruits().add(fruits.get(j));
//					break;
//				}
//			}
//		}
//		return path;
//	}
//
//	
///**
// * function to get every packman the best path for him
// * @return
// */
//	public ArrayList<Packman> algoMultiPackmans (){
//		Path path = new Path();
//		ArrayList<Fruit> fruits = this.fruits;
//		ArrayList<Packman> packmen = this.Packmans;
//		ArrayList<Packman> answer = new ArrayList<>();
//		ArrayList<Packman> temp_answer = new ArrayList<>();
//
//		for (int i = 0; i < packmen.size(); i++) {
//			Packman p = new Packman(packmen.get(i).getPackman(),packmen.get(i).getSpeed(), packmen.get(i).getRadius());
//			answer.add(p);
//		}
//		packmen = Algomulti(packmen,fruits);
//		for (int i = 0; i < packmen.size(); i++) {
//			packmen.get(i).setPackman(answer.get(i).getPackman());
//		}
//		Path temp_path = new Path();
//		Path path2 = new Path();
//		for (int i = 0; i < packmen.size(); i++) {
//			temp_answer.add(new Packman(packmen.get(i)));
//			temp_answer.get(i).getPath().setFruits(packmen.get(i).getPath().getFruits());	
//		}
//		for (int i = 0; i < temp_answer.size(); i++) {
//			temp_path = disAlgo(temp_answer.get(i),temp_answer.get(i).getPath().getFruits());
//		}
//
//		double time = path.totalTime_Path(packmen.get(0));
//		double temp = 0;
//
//		for (int i = 1; i < packmen.size(); i++) {
//			temp = path.totalTime_Path(packmen.get(i));
//			if (temp > time) time = temp;
//		}
//		System.out.println("finishing time: "+time);
//		return packmen;
//	}
//
//
//	private ArrayList<Packman> Algomulti (ArrayList<Packman> packmen , ArrayList<Fruit>fruits) {
//		Path path = new Path();
//		if(fruits.isEmpty()) return packmen;
//
//		int random = (int)(Math.random()*packmen.size());
//		Packman Packman = packmen.get(random);
//		Fruit near_fruit = TheCloserFurit(packmen.get(random),fruits);
//		double best_time = path.time_P_F(packmen.get(random),near_fruit);
//		Packman temp_Packman;
//		Fruit temp_fruit;
//		double temp_time = 0;
//		for (int i = 0; i < packmen.size(); i++) {
//			temp_Packman = packmen.get(i);
//			temp_fruit = TheCloserFurit(packmen.get(i),fruits);
//			temp_time = path.time_P_F(packmen.get(i),temp_fruit);
//
//			if (temp_time < best_time) {
//				Packman = temp_Packman;
//				best_time = temp_time;
//				near_fruit = temp_fruit;
//			}
//		}
//		Packman.getPath().getFruits().add(near_fruit);
//		Packman.setPackman(near_fruit.getGps_point());
//		fruits.remove(getIndexFurit(near_fruit, fruits));
//
//		return Algomulti(packmen ,fruits);
//
//	}
//
//
//	double FastSpeedToFriut(Packman packman ,ArrayList<Fruit> fruits ) {
//		Path path = new Path();
//		double best_time = path.time_P_F(packman, fruits.get(0));
//		double temp_time = 0;
//
//		for (int i = 1; i < fruits.size(); i++) {
//			temp_time = path.time_P_F(packman, fruits.get(i));
//			if(temp_time <best_time) {
//				best_time = temp_time;
//			}
//		}
//		return best_time;
//	}
//
//	/**
//	 * function to give the nearest fruit of a packman
//	 * @param packman
//	 * @param fruits
//	 * @return
//	 */
//	public Fruit TheCloserFurit(Packman packman,ArrayList<Fruit> fruits) {
//		Path path = new Path();
//		double best_time = path.time_P_F(packman,fruits.get(0));
//		Fruit nearest_fruit = fruits.get(0);
//		double temp_time = 0;
//
//		for (int i = 1; i < fruits.size(); i++) {
//			temp_time = path.time_P_F(packman, fruits.get(i));
//			if(temp_time < best_time)	{
//				best_time = temp_time;
//				nearest_fruit = fruits.get(i);
//			}	
//		}
//		return nearest_fruit;
//	}
//
//
//	
//	public int getIndexFurit(Fruit furit , ArrayList<Fruit> fruits) {
//		for (int i = 0; i < fruits.size(); i++) {
//			if(furit.equals(fruits.get(i))) {
//				return i;
//			}
//		}
//		return -1;
//	}
//
//}


package Game;

import java.util.ArrayList;


import Geom.Point3D;

/**
 * This class manages our algorithms that know how to find the best way for my Packman
 * There are several different methods depending on the quantity of packmans and the speed.
 * Example Algo Details: https://fr.wikipedia.org/wiki/Algorithme_glouton
 * @author Omer Paz and Shimon Mimoun
 */
public class ShortestPathAlgo {

	private ArrayList<Fruit> fruits = new ArrayList<>(); // Arraylist of fruit
	private ArrayList<Packman> Packmans = new ArrayList<>();//Arraylist of Packman 
	private Compare comp = new Compare();// Call to the Comparator 
	private Map map = new Map();// create a Map object


	/**
	 * Contractor of ShortestPathAlgo Who receives Game Object
	 * @param game Object Game receiv 
	 */
	public ShortestPathAlgo(Game game) {	


		ArrayList<Fruit> clone = new ArrayList<Fruit>(game.fruits.size());  for (Fruit item : game.fruits) clone.add(item);
		this.fruits = clone;	//Create a new fruit for not to overwrite Game data later
		this.Packmans = game.packmans;
	}

	/**
	 * algorithim for one Packman
	 * @param p
	 * @return
	 */
	public Path algoSinglePackman(Packman p){
		ArrayList<Fruit> fruits = this.fruits;

		Packman tempPackman = new Packman(p.getPackman(),p.getSpeed(),p.getRadius());
		Path distance = disAlgo(tempPackman,fruits);
		Path path = calFastDisOnePack(tempPackman, fruits);

		p.getPath().setFruits(distance.getFruits());	
		double time1 = distance.totalTime_Path(p);
		p.getPath().setFruits(path.getFruits());
		double time2 = path.totalTime_Path(p);
		if(time1 < time2) {
			p.getPath().setFruits(distance.getFruits());	
			p.getPath().setTime(time1);
			System.out.println(p.getPath().getTime());
			return p.getPath();
		}
		else {
			p.getPath().setTime(time2);
			System.out.println(p.getPath().getTime());
			return p.getPath();
		}
	}




	/**
	 * Help function that calculates the closest distance between a packman and fruits
	 * @param packman Receiv Packman Only
	 * @param myFurits Arraylist in Fruits 
	 * @return the Path the most suitable
	 */
	public Path calFastDisOnePack (Packman packman, ArrayList<Fruit> myFurits) {

		if (myFurits.isEmpty()) {
			return packman.getPath();
		}


		Fruit theCloserTemp = TheCloserFurit(packman,myFurits); // the closer furit to packman 
		packman.getPath().getFruits().add(theCloserTemp);
		packman.setPackman(theCloserTemp.getGps_point());
		myFurits.remove(getIndexFurit(theCloserTemp, myFurits));

		return calFastDisOnePack(packman,myFurits);


	}
	/**
	 * Method that calculates the distance between a pacman and each fruit
	 * @param packman a single Packman on which we will calculate the distance
	 * @param myFurits Fruit list
	 * @return Path with distance add
	 */
	public Path disAlgo(Packman packman, ArrayList<Fruit> myFurits) {
		ArrayList<Double> SortPathByDis = new ArrayList<>();

		Path ans = new Path();

		for (int i = 0; i < myFurits.size(); i++) {
			double temp = map.distance3D(packman.getPackman(), myFurits.get(i).getGps_point());
			SortPathByDis.add(temp);
		}
		SortPathByDis.sort(comp);
		double temp;

		for(double distance : SortPathByDis) {

			for (int j = 0; j < myFurits.size(); j++) {
				temp = map.distance3D(packman.getPackman(), myFurits.get(j).getGps_point());

				if(temp == distance) {
					ans.getFruits().add(myFurits.get(j));
					break;
				}


			}
		}

		return ans;
	}

	/**
	 * Algorithm that calculates the course of several pacman and who know how to return:
	 * what will be the course of each pacman to have the best time 
	 * Fonction Help
	 * 
	 * @return Pacman's ArrayList to which will be added to each Pacman a path of the course he will perform
	 * 
	 */

	public ArrayList<Packman> algoMultiPackmans (){
		Path myPath = new Path();
		ArrayList<Fruit> myFurits = this.fruits;
		ArrayList<Packman> ans = new ArrayList<>();
		ArrayList<Packman> myPackmens = this.Packmans;
		

		for (int i = 0; i < myPackmens.size(); i++) {
			Packman p = new Packman(myPackmens.get(i).getPackman(),myPackmens.get(i).getSpeed(), myPackmens.get(i).getRadius());
			ans.add(p);
		}
		myPackmens = Algomulti(myPackmens,myFurits);

		for (int i = 0; i < myPackmens.size(); i++) {
			myPackmens.get(i).setPackman(ans.get(i).getPackman());
		}

		
		
		for(Packman packman : myPackmens) {
			
			Path PackmanPath = new Path();
			Path p = new Path();
			PackmanPath = packman.getPath();


//			Packman pack = new Packman(packman);
//			packman.getPath().setFruits(algoSinglePackman(packman).getFruits());
//			packman.getPath().setTime(packman.getPath().totalTime_Path(packman));
//			
//			
//				if(PackmanPath.getTime() <= pack.getPath().getTime()) {
//				packman.getPath().setFruits(PackmanPath.getFruits());
//				PackmanPath.totalTime_Path(packman);
//			}
//			else {
//	
//				packman.getPath().setFruits(pack.getPath().getFruits());
//		
//			}

		}


		double longestTime = myPath.totalTime_Path(myPackmens.get(0));
		double temp = 0;

		for (int i = 1; i < myPackmens.size(); i++) {
			temp = myPath.totalTime_Path(myPackmens.get(i));
			if (temp > longestTime) {
				longestTime = temp;
			}
		}

		
		
		System.out.println("the Total time is: "+longestTime);
		return myPackmens;
	}

	/**
	 * Algorithm that calculates the course of several pacman and who know how to return:
	 * what will be the course of each pacman to have the best time 
	 * @param myPackmans Get a Pacmans ArrayList
	 * @param myFurits Get a Fruits ArrayList
	 * @return Pacman's ArrayList to which will be added to each Pacman a path of the course he will perform
	 */

	private ArrayList<Packman> Algomulti (ArrayList<Packman> myPackmans , ArrayList<Fruit>myFurits) {
		Path myPath = new Path();
		if(myFurits.isEmpty()) {
			return myPackmans;
		}


		Packman thePackman = myPackmans.get(0);
		Fruit theCloserFurit = TheCloserFurit(myPackmans.get(0),myFurits);
		double FastTime = myPath.time_P_F(myPackmans.get(0),theCloserFurit);

		Packman tempPack;
		Fruit tempFruit;
		double tempTime = 0;

		for (int i = 0; i < myPackmans.size(); i++) {

			tempPack = myPackmans.get(i);
			tempFruit = TheCloserFurit(myPackmans.get(i),myFurits);
			tempTime = myPath.time_P_F(myPackmans.get(i),tempFruit);

			if (tempTime < FastTime) {
				thePackman = tempPack;
				FastTime = tempTime;
				theCloserFurit = tempFruit;
			}

		}
		thePackman.getPath().getFruits().add(theCloserFurit);
		thePackman.setPackman(theCloserFurit.getGps_point());
		myFurits.remove(getIndexFurit(theCloserFurit, myFurits));

		return Algomulti(myPackmans ,myFurits);

	}

	/**
	 * method that calculates  the fastest point between a pac man and the path
	 * @param packman Receiv Pacman on which we will look for the fastest time
	 * @param myFurits ArrayList of Fruits on which we seek the fastest fruit of the Pac-Man
	 * @return The double time of the fastest route
	 */

	double FastSpeedToFriut(Packman packman ,ArrayList<Fruit> myFurits ) {
		Path p = new Path();
		double fastTime = p.time_P_F(packman, myFurits.get(0));
		double tempTime = 0;

		for (int i = 1; i < myFurits.size(); i++) {

			tempTime = p.time_P_F(packman, myFurits.get(i));

			if(tempTime <fastTime) {
				fastTime = tempTime;
			}

		}
		return fastTime;

	}
	/**
	 * Return the most closers furit to the packman
	 * @param packman Receiv Pacman on which we will look for the fastest Fruit
	 * @param myFurits ARraylist Of Fruit on wiche we seek the most closers PAcman
	 * @return a Fruit the most closers of PAcman
	 */
	public Fruit TheCloserFurit(Packman packman,ArrayList<Fruit> myFurits) {
		Path p = new Path();

		double FastTime = p.time_P_F(packman,myFurits.get(0));
		Fruit theMostCloser = myFurits.get(0);
		double tempTime = 0;

		for (int i = 1; i < myFurits.size(); i++) {
			tempTime = p.time_P_F(packman, myFurits.get(i));

			if(tempTime < FastTime)	{
				FastTime = tempTime;
				theMostCloser = myFurits.get(i);
			}	
		}

		return theMostCloser;
	}

	/**
	 * Calculate the index of the furit.
	 * @param fruit Receiv Fruit of ArrayList
	 * @param myFurits ArrayList contain The "fruit"
	 * @return Index of Fruit (if no found return -1)
	 */
	public int getIndexFurit(Fruit furit , ArrayList<Fruit> myFurits) {

		for (int i = 0; i < myFurits.size(); i++) {

			if(furit.equals(myFurits.get(i))) {
				return i;
			}

		}
		return -1;
	}



}
