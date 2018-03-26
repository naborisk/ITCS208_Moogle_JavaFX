package sample;// Name: Tussoun Jitpanyoyos
// Student ID: 6088030
// Section: 1

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleMovieSearchEngine implements BaseMovieSearchEngine {
	public Map<Integer, Movie> movies;
	
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename) {
		
		// YOUR CODE GOES HERE
		movies = new HashMap<>();

		try{
			BufferedReader br = new BufferedReader(new FileReader(movieFilename));
			String line = "";

			br.readLine(); //to clear the first line

			String pStr = "([0-9]+),\"?(.*) \\(([ 0-9]+)\\)\"?,(.*)"; //regex
			Pattern p = Pattern.compile(pStr);
			Matcher m;

			while((line = br.readLine()) != null){
				m = p.matcher(line);

				if(m.matches()){
					int mid = Integer.parseInt(m.group(1));
					String title = m.group(2);
					int year = Integer.parseInt(m.group(3));

					//System.out.println(mid + " " + title + " " + year + " " + m.group(4));

					movies.put(mid, new Movie(mid, title, year));

					String[] tags = m.group(4).split("\\|");
					for(String tag: tags){
						movies.get(mid).addTag(tag);
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		
		return movies;
	}

	@Override
	public void loadRating(String ratingFilename) {

		// YOUR CODE GOES HERE
		String line = "";

		try{
			BufferedReader br = new BufferedReader(new FileReader(ratingFilename));

			String pStr = "([0-9]+),([0-9]+),([0-9.]+),([0-9]+)"; //regex
			Pattern p = Pattern.compile(pStr);
			Matcher m;

			br.readLine();
			while((line = br.readLine()) != null) {

				m = p.matcher(line);

				if (m.matches()) {
					int uid = Integer.parseInt(m.group(1));
					int mid = Integer.parseInt(m.group(2));
					double rating = Double.parseDouble(m.group(3));
					long timestamp = Long.parseLong(m.group(4));


					//System.out.println(uid + " " + mid + " " + rating + " " + timestamp);

					if(movies.get(mid) != null) movies.get(mid).addRating(new User(uid), movies.get(mid), rating, timestamp);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		for (Integer key: movies.keySet()){
			movies.get(key).calMeanRating();
		}
	}

	@Override
	public void loadData(String movieFilename, String ratingFilename) {
	
		// YOUR CODE GOES HERE
		loadMovies(movieFilename);
		loadRating(ratingFilename);
	}

	@Override
	public Map<Integer, Movie> getAllMovies() {

		// YOUR CODE GOES HERE
		return movies;
	}

	@Override
	public List<Movie> searchByTitle(String title, boolean exactMatch) {

		// YOUR CODE GOES HERE
		List<Movie> movieList =  new LinkedList<>();

		if(title == null) return movieList;

		if(exactMatch){
			for(Integer key: movies.keySet()){
				if(movies.get(key).getTitle().toLowerCase().startsWith(title.toLowerCase())) movieList.add(movies.get(key));

			}
		}else{
			for(Integer key: movies.keySet()){
				if(movies.get(key).getTitle().toLowerCase().contains(title.toLowerCase())) movieList.add(movies.get(key));
			}
		}
		return movieList;
	}

	@Override
	public List<Movie> searchByTag(String tag) {

		// YOUR CODE GOES HERE
		List<Movie> movieList = new LinkedList<>();

		for(Integer key: movies.keySet()){
			if(movies.get(key).getTags().contains(tag)){
				movieList.add(movies.get(key));
			}
		}

		return movieList;
	}

	@Override
	public List<Movie>searchByYear(int year) {

		// YOUR CODE GOES HERE
		List<Movie> movieList = new LinkedList<>();

		for(Integer key: movies.keySet()){
			if(movies.get(key).getYear() == year){
				movieList.add(movies.get(key));
			}
		}
		return movieList;
	}

	@Override
	public List<Movie> advanceSearch(String title, String tag, int year) {

		// YOUR CODE GOES HERE

		HashSet<Movie> movieList = new HashSet<>(); //HashSet (or any set) don't allow repetitions
		LinkedList<Movie> titleMatch, tagMatch, yearMatch;

		titleMatch = (LinkedList<Movie>) searchByTitle(title, false);
		tagMatch = (LinkedList<Movie>) searchByTag(tag);
		yearMatch = (LinkedList<Movie>) searchByYear(year);

		movieList.addAll(titleMatch);
		movieList.addAll(tagMatch);
		movieList.addAll(yearMatch);

		if(title != null) movieList.retainAll(titleMatch);
		if(tag != null) movieList.retainAll(tagMatch);
		if(year != -1) movieList.retainAll(yearMatch);
		
		return sortByTitle(new LinkedList<>(movieList), false);
	}

	@Override
	public List<Movie> sortByTitle(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		LinkedList<Movie> movieList = (LinkedList<Movie>) unsortedMovies;

		//unsortedMovies.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
		movieList.sort(Comparator.comparing(Movie::getTitle));
		if(!asc) Collections.reverse(movieList);

		return movieList;
	}

	@Override
	public List<Movie> sortByRating(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		LinkedList<Movie> movieList = (LinkedList<Movie>) unsortedMovies;

		movieList.sort(Comparator.comparing(Movie::getMeanRating));
		if(!asc) Collections.reverse(movieList);

		return movieList;
	}
}
