/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scicalculatator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author user
 */
public class MainController{
    
    @FXML
    private StandardOperationsController standardOperationsController;
    @FXML
    private ScientificOperationsController scientificOperationsController;
    @FXML
    private InverseOperationsController inverseOperationsController;
    
    
    @FXML
    private Rectangle navigationLine;
    
    @FXML
    private Label standardOperationsNav, scientificOperationsNav;
    
    @FXML
    private StackPane operationsContainer;
    
    @FXML
    private TextField calculationScreen;
    
    @FXML
    private TextField resultScreen;
    
    double scrollDuration=700;
    Duration TransitionDuration = Duration.millis(scrollDuration);
    double navigationX=120;
    boolean standardOperationsLoaded =true;
    
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @FXML
    private void loadStandardOperations() throws IOException {
        // If the operations currently loaded are standard, stop execution
        if(standardOperationsLoaded){
            return;
        }
        standardOperationsLoaded =true;
        
        Timeline navigationTimeline = new Timeline();
        KeyValue standardOperationsColorKv = new KeyValue(standardOperationsNav.textFillProperty(),
                Color.web("#000"),Interpolator.EASE_IN);
        
        KeyValue scientificOperationsColorKv = new KeyValue(scientificOperationsNav.textFillProperty(),
                Color.web("#666666"),Interpolator.EASE_IN);
        
        KeyValue navigationLineTransitionKv = new KeyValue(navigationLine.translateXProperty(),
                0,Interpolator.EASE_IN);
        
        KeyValue standardOperationsScrollInKv=standardOperationsController.scrollSceneInKv();
        
        KeyValue nonStandardOperationsScrollAwayKv = null;
        if(getScientificOperationsRoot().isVisible()){
            nonStandardOperationsScrollAwayKv=scientificOperationsController.scrollSceneToRightKv();
        }else if(getInverseOperationsRoot().isVisible()){
            nonStandardOperationsScrollAwayKv=inverseOperationsController.scrollSceneToRightKv();
        }
        

        KeyFrame kf = new KeyFrame(TransitionDuration, standardOperationsColorKv,
                scientificOperationsColorKv, navigationLineTransitionKv,
                nonStandardOperationsScrollAwayKv,standardOperationsScrollInKv);
        
        navigationTimeline.getKeyFrames().add(kf);
        getStandardOperationsRoot().setVisible(true);
        navigationTimeline.play();
        
        navigationTimeline.setOnFinished(event->{
            getScientificOperationsRoot().setVisible(false);
            getInverseOperationsRoot().setVisible(false);
        });
        
    }
    
    @FXML
    private void loadScientificOperations() throws IOException {
        // If the operations currently loaded are Scientific, stop execution
        if(!standardOperationsLoaded){
            return;
        }
        standardOperationsLoaded =false;
        
        // First of all hide Inverse operations scene
        getInverseOperationsRoot().setVisible(false);
        
        Timeline navigationTimeline = new Timeline();
        
        KeyValue scientificOperationsColorKv = new KeyValue(scientificOperationsNav.textFillProperty(),
                Color.web("#000"),Interpolator.EASE_IN);
        
        KeyValue standardOperationsColorKv = new KeyValue(standardOperationsNav.textFillProperty(),
                Color.web("#666666"),Interpolator.EASE_IN);
        
        KeyValue navigationLineTransitionKv = new KeyValue(navigationLine.translateXProperty(),
                navigationX,Interpolator.EASE_IN);
        
        KeyValue standardOperationsScrollAwayKv=standardOperationsController.scrollSceneToLeftKv();
        KeyValue scientificOperationsScrollInKv=scientificOperationsController.scrollSceneInKv();

        KeyFrame kf = new KeyFrame(TransitionDuration, standardOperationsColorKv,
                scientificOperationsColorKv, navigationLineTransitionKv,
                standardOperationsScrollAwayKv,scientificOperationsScrollInKv);
        
        navigationTimeline.getKeyFrames().add(kf);
        getScientificOperationsRoot().setVisible(true);
        navigationTimeline.play();
        navigationTimeline.setOnFinished(event->{
            getStandardOperationsRoot().setVisible(false);
        });
    }
    
    public void loadStandardOperationsOnStageLoad() throws IOException {
        Parent standardRoot = FXMLLoader.load(getClass().getResource("StandardOperations.fxml"));
        operationsContainer.getChildren().add(standardRoot);
    }
    
    public VBox getStandardOperationsRoot(){
        return standardOperationsController.getMyRoot();
    }
    
    public VBox getScientificOperationsRoot(){
        return scientificOperationsController.getMyRoot();
    }
    
    public VBox getInverseOperationsRoot(){
        return inverseOperationsController.getMyRoot();
    }
    
    public TextField getCalculationScreen(){
        return calculationScreen;
    }
    
    public TextField getResultScreen(){
        return resultScreen;
    }
    
    public Button getSciDegBtn(){
        return scientificOperationsController.getDegBtn();
    }
    public Button getSciRadBtn(){
        return scientificOperationsController.getRadBtn();
    }
    
    public Button getInvDegBtn(){
        return inverseOperationsController.getDegBtn();
    }
    public Button getInvRadBtn(){
        return inverseOperationsController.getRadBtn();
    }
    
    public Scene getMainScene(){
        Scene scene = navigationLine.getScene();
        return  scene;
    }
    
    
    
    @FXML
    private void initialize() {
        // TODO
        standardOperationsController.init(this);
        scientificOperationsController.init(this);
        inverseOperationsController.init(this);
        getScientificOperationsRoot().setVisible(false);
        getInverseOperationsRoot().setVisible(false);
    }
    
    
    
}
