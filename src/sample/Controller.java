package sample;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Map;


public class Controller {

    @FXML
    private TextField textField;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btDefaultValue;
    @FXML
    private Button btCustomValue;
    @FXML
    private ImageView ivLogo;
    @FXML
    private ListView<Movie> lvResult;
    @FXML
    private Button btBack;

    private boolean isSearching;
    private Map<Integer, Movie> movies;
    private ObservableList<Movie> olMovies;

    SimpleMovieSearchEngine s;

    public Controller(){
        s = new SimpleMovieSearchEngine();
        isSearching = false;
    }

    @FXML
    private void initialize(){
        textField.setVisible(false);
        progressBar.setVisible(false);
        lvResult.setVisible(false);
        btBack.setVisible(false);
    }

    @FXML
    private void onDefaultValueClick(){
        //textField.setVisible(!textField.isVisible());
        //progressBar.setVisible(true);
        s.loadData("src/data/movies.csv", "src/data/ratings.csv");
        movies = s.getAllMovies();
        olMovies = FXCollections.observableList(s.searchByTitle("", false));
        lvResult.setItems(FXCollections.observableList(s.searchByTitle("", false)));
        btDefaultValue.setVisible(false);
        btCustomValue.setVisible(false);
        textField.setVisible(true);
    }

    @FXML
    private void startSearching(){

        if(!isSearching){
            TranslateTransition ttTextField = new TranslateTransition(Duration.seconds(0.25), textField);
            ttTextField.setByY(-250);
            ttTextField.play();

            TranslateTransition ttImageView = new TranslateTransition(Duration.seconds(0.25), ivLogo);
            ttImageView.setByY(-500);
            ttImageView.play();

            FadeTransition ftListView = new FadeTransition(Duration.seconds(0.25), lvResult);
            lvResult.setVisible(true);
            ftListView.setFromValue(0.0);
            ftListView.setToValue(1.0);
            ftListView.play();

            btBack.setVisible(true);

            isSearching = true;
        }

        //ObservableList<Movie> observableList = FXCollections.observableList(s.searchByTitle(textField.getText(), false));
        olMovies = FXCollections.observableList(s.searchByTitle(textField.getText(), false));
        lvResult.setItems(olMovies);

    }

    @FXML
    private void stopSearching(){
        if(isSearching){
            TranslateTransition ttTextField = new TranslateTransition(Duration.seconds(0.25), textField);
            ttTextField.setByY(250);
            ttTextField.play();

            TranslateTransition ttImageView = new TranslateTransition(Duration.seconds(0.25), ivLogo);
            ttImageView.setByY(500);
            ttImageView.play();

            FadeTransition ftListView = new FadeTransition(Duration.seconds(0.25), lvResult);
            lvResult.setVisible(true);
            ftListView.setFromValue(1.0);
            ftListView.setToValue(0.0);
            ftListView.play();

            btBack.setVisible(false);

            isSearching = false;
        }
    }

    @FXML
    private void deselectTextField(){
    }
}
