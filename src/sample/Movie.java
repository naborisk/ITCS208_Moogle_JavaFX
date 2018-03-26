package sample;// Name: Tussoun Jitpanyoyos
// Student ID: 6088030
// Section: 1

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Movie {
	private int mid;
	private String title;
	private int year;
	private Set<String> tags;
	private Map<Integer, Rating> ratings;	//mapping userID -> rating
	private Double avgRating;
	//additional
	
	public Movie(int _mid, String _title, int _year){
		// YOUR CODE GOES HERE

		mid = _mid;
		title = _title;
		year = _year;
		tags = new HashSet<>();
		ratings = new HashMap<>();
		avgRating = 0.0;
	}
	
	public int getID() {
		
		// YOUR CODE GOES HERE
		return mid;
	}
	public String getTitle(){
		
		// YOUR CODE GOES HERE
		return title;
	}
	
	public Set<String> getTags() {
		
		// YOUR CODE GOES HERE
		return tags;
	}
	
	public void addTag(String tag){
		
		// YOUR CODE GOES HERE
		tags.add(tag);
	}
	
	public int getYear(){
		
		// YOUR CODE GOES HERE
		return year;
	}
	
	public String toString()
	{
		return "[mid: "+mid+":"+title+" ("+year+") "+tags+"] -> avg rating: " + avgRating;
	}
	
	
	public double calMeanRating(){
		
		// YOUR CODE GOES HERE
		double total = 0.0;

		for(Integer key: ratings.keySet()){
			total += ratings.get(key).rating;

		}
		avgRating = total/ratings.size();

		return avgRating;
	}
	
	public Double getMeanRating(){
		
		// YOUR CODE GOES HERE
		return avgRating;
	}
	
	public void addRating(User user, Movie movie, double rating, long timestamp) {
		// YOUR CODE GOES HERE

//		if(ratings.get(user.getID()).u == null){
//			r = new Rating(user, movie, rating, timestamp);
//		}else{
//			r = ratings.get(user.getID());
//		}
		ratings.put(user.uid, new Rating(user, movie, rating, timestamp));
	}
	
	public Map<Integer, Rating> getRating(){
		
		// YOUR CODE GOES HERE
		return ratings;
	}
	
}
