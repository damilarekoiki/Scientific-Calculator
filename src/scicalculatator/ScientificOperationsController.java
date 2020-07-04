/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scicalculatator;

import java.io.IOException;
import java.net.URL;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author user
 */
public class ScientificOperationsController implements Initializable {
    
    @FXML
    private VBox scientificOperations;
    
    double scrollDuration=400;
    Duration TransitionDuration = Duration.millis(scrollDuration);
    double navigationX=120;
    boolean standardOperationsLoaded =true;
    
    private MainController mainController;
    
    Model model =new Model();
    
    @FXML
    private Label label;
    @FXML
    private Button xSupY;
    
    @FXML
    private Button degree,radian;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    public VBox getMyRoot(){
        return scientificOperations;
    }
    
    public void init(MainController mainController) {
        
        this.mainController=mainController;
        xSupY.setText("x\u02b8");
        
    }
    
    public KeyValue scrollSceneToRightKv() throws IOException{
        Scene mainScene=this.mainController.getMainScene();
        double scrollFrom=0;
        double scrollTo=mainScene.getWidth();
        
        scientificOperations.translateXProperty().set(scrollFrom);
        
        KeyValue sceneTransitionKv = new KeyValue(scientificOperations.translateXProperty(),scrollTo,Interpolator.EASE_OUT);
        return sceneTransitionKv;
    }
    
    public KeyValue scrollSceneInKv() throws IOException{
        Scene mainScene=this.mainController.getMainScene();
        double scrollFrom=mainScene.getWidth();
        double scrollTo=0;
        
        scientificOperations.translateXProperty().set(scrollFrom);
        
        KeyValue sceneTransitionKv = new KeyValue(scientificOperations.translateXProperty(),scrollTo,Interpolator.EASE_OUT);
        return sceneTransitionKv;
    }
    
    public void showInverseOperationsScene() throws IOException{
        scientificOperations.setVisible(false); 
        this.mainController.getStandardOperationsRoot().setVisible(false);
        this.mainController.getInverseOperationsRoot().setVisible(true);
        // Set inverse operations back to zero in case it has scrolled away
        this.mainController.getInverseOperationsRoot().translateXProperty().set(0);
    }
    
    public Button getDegBtn(){
        return degree;
    }
    public Button getRadBtn(){
        return radian;
    }
    
    public void displayExponential(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String exponentialSymbol=((Button)event.getSource()).getText();
        String matExprsexponentialSymbol="2.71828182846";
        
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        if(model.isNumb(calculationScreenLastChar)){
            matExprsexponentialSymbol="*10^";
        }
        
        if(model.endsWithTrigFunction(Model.mathematicalExpression)) return;
        if(model.endsWithNumbExp(calculationScreenContent)) return;
        else{
            if(calculationScreenLastChar=='.'){
                return;
            }
            if(calculationScreenLastChar==')' || 
                    calculationScreenLastChar=='e' || 
                    calculationScreenLastChar=='%' || calculationScreenLastChar=='!' 
                    || model.isPi(calculationScreenLastChar))
            {
                calculationScreenContent=calculationScreenContent.concat("x"+exponentialSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+matExprsexponentialSymbol);
            }
            if(model.isOperator(calculationScreenLastChar) || calculationScreenContent.isEmpty()  
                    ||calculationScreenLastChar=='('){
                calculationScreenContent=calculationScreenContent.concat(exponentialSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsexponentialSymbol);
            }
            if(model.isNumb(calculationScreenLastChar)){
                calculationScreenContent=calculationScreenContent.concat(exponentialSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsexponentialSymbol);
                resultScreen.clear();
            }
        }
        
        calculationScreen.setText(calculationScreenContent);
        if(!model.endsWithNumbExp(calculationScreenContent)){
            // Result Screen
            String answer=model.compute(Model.mathematicalExpression);

            resultScreen.setText(answer);

        }
        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayTrigFunction(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        if(Model.matExprsEndsWithPermOrComb()) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String trigFunction=((Button)event.getSource()).getText();
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        String matExprsTrigFunction=model.convertTrigFunctionToMatExprsChar(trigFunction);
        
        if(calculationScreenLastChar=='.' || Model.getMatExprsLastChar()=='^'){
            return;
        }
        
        if(model.isOperator(calculationScreenLastChar) || calculationScreenContent.isEmpty() 
                || calculationScreenLastChar=='('){
            
            calculationScreenContent=calculationScreenContent.concat(trigFunction+"(");
            Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsTrigFunction+"(");
            Model.numbParenthesisOpened++;
        }else{
            if(model.endsWithTrigFunction(Model.mathematicalExpression)){
                calculationScreenContent=calculationScreenContent.concat("("+trigFunction+"(");
                Model.mathematicalExpression=Model.mathematicalExpression.concat("("+matExprsTrigFunction+"(");
                Model.numbParenthesisOpened++;Model.numbParenthesisOpened++;
            }else{
                calculationScreenContent=calculationScreenContent.concat("x"+trigFunction+"(");
                Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+matExprsTrigFunction+"(");
                Model.numbParenthesisOpened++;
            }
            
        }
        calculationScreen.setText(calculationScreenContent);
        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayFactorialAndSelections(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        if(Model.numbDotPressed>0) return;
        if(Model.matExprsEndsWithPermOrComb()) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String selection=((Button)event.getSource()).getText();
        if("x!".equals(selection)) selection="!";
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        String matExprsSelection=model.convertSelectionToMatExprsChar(selection);
        if(!model.isNumb(calculationScreenLastChar)) return;
        
        
        
        calculationScreenContent=calculationScreenContent.concat(selection);
        Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsSelection);
        
        calculationScreen.setText(calculationScreenContent);
        
        if("!".equals(selection)){
            // Result Screen
            TextField resultScreen=this.mainController.getResultScreen();
            String answer=model.compute(Model.mathematicalExpression);

            resultScreen.setText(answer);

        }
        
        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayPower(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String powerSymbol="^";
        String buttonId=((Button)event.getSource()).getId();
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        if(model.isNumb(calculationScreenLastChar) || calculationScreenLastChar==')' 
                || calculationScreenLastChar=='e'
                || model.isPi(calculationScreenLastChar)){
            if("numbSquare".equals(buttonId)){
                calculationScreenContent=calculationScreenContent.concat(powerSymbol+"2");
                Model.mathematicalExpression+=powerSymbol+"2";

            }else{
                calculationScreenContent=calculationScreenContent.concat(powerSymbol);
                Model.mathematicalExpression+=powerSymbol;

            }
            System.out.println(Model.mathematicalExpression);
            calculationScreen.setText(calculationScreenContent);
        }
        
    }
    
    public void displayRoot(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String rootSymbol="\u221A";
        String ballotBox="\u2610";
        String matExprsRootSymbol="@";
        String matExprsBallotBox="?";
        String buttonId=((Button)event.getSource()).getId();
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        if(model.endsWithNumbExp(calculationScreenContent)) return;
        
        if(model.isNumb(calculationScreenLastChar) || calculationScreenLastChar==')' 
            || calculationScreenLastChar=='e' || calculationScreenLastChar=='!'
            || model.isPi(calculationScreenLastChar) ){
            calculationScreenContent+="x"+rootSymbol+"(";
            Model.mathematicalExpression+="*"+matExprsRootSymbol+"(";
            Model.numbParenthesisOpened++;
        }
        if(model.isOperator(calculationScreenLastChar) || calculationScreenContent.isEmpty() 
                || calculationScreenLastChar=='(' || calculationScreenLastChar=='^'){
            calculationScreenContent+=rootSymbol+"(";
            Model.mathematicalExpression+=matExprsRootSymbol+"(";
            Model.numbParenthesisOpened++;
        }
        
        System.out.println(Model.mathematicalExpression);
        calculationScreen.setText(calculationScreenContent);
    }
    
    public void displayPi(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        // Calculation Screen
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        char mathematicalExpressionLastChar;
        // Value On The Pressed Operator Button
        String piSymbol=((Button)event.getSource()).getText();
        String matExprspiSymbol="3.1415926536";
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        if(Model.mathematicalExpression.length()==0){
            mathematicalExpressionLastChar=' ';
        }else{ //else we get the last character
            mathematicalExpressionLastChar=Model.mathematicalExpression.charAt(Model.mathematicalExpression.length()-1);
        }
        
        if(calculationScreenLastChar=='.' || Model.matExprsEndsWithPermOrComb()
                || model.endsWithNumbExp(calculationScreenContent)){
            return;
        }
        
        if(calculationScreenContent.isEmpty() || model.isOperator(calculationScreenLastChar) || mathematicalExpressionLastChar=='^'
                || Model.matExprsEndsWithRoot()){
            
            calculationScreenContent=calculationScreenContent.concat(piSymbol);
            Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprspiSymbol);
            
        }else{
            if(calculationScreenLastChar=='('){

                calculationScreenContent=calculationScreenContent.concat(piSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprspiSymbol);

            }else if(calculationScreenLastChar==')' || 
                calculationScreenLastChar=='e' || calculationScreenLastChar=='!' 
                || model.isPi(calculationScreenLastChar) || model.isNumb(calculationScreenLastChar)
                || calculationScreenLastChar=='%'){

                calculationScreenContent=calculationScreenContent.concat("x"+piSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+matExprspiSymbol);

            }
            else if(model.endsWithTrigFunction(Model.mathematicalExpression)){
                calculationScreenContent=calculationScreenContent.concat("("+piSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("("+matExprspiSymbol);
                Model.numbParenthesisOpened++;
            }
        }
        
        calculationScreen.setText(calculationScreenContent);
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        String answer=model.compute(Model.mathematicalExpression);

        resultScreen.setText(answer);

        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayAnswer(ActionEvent event){
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        
        String calculationScreenContent=calculationScreen.getText();
        
        String answer=model.compute(Model.mathematicalExpression);
        
        if(calculationScreenContent.contains("\u2610") || calculationScreenContent.contains("\u207B\u221A")){
            answer="";
        }
        
        resultScreen.setText(answer);
    }
    
    public void replaceCalculationScreenWithResult(ActionEvent event){
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        TextField calculationScreen=this.mainController.getCalculationScreen();
        String result=resultScreen.getText();
        if(result.isEmpty()) return;
        
        calculationScreen.setText(result); // replace calc screen content with result
        Model.mathematicalExpression=result; // replace mathematical expression with result
        
        resultScreen.clear(); // Clear result screen;
    }
    
    public void handleDegRadClick(ActionEvent event){
        
        Button button = ((Button)event.getSource());
        String buttonId = button.getId();
        
        this.mainController.getInvDegBtn().getStyleClass().clear();
        this.mainController.getInvRadBtn().getStyleClass().clear();
        
        this.mainController.getSciDegBtn().getStyleClass().clear();
        this.mainController.getSciRadBtn().getStyleClass().clear();


        switch(buttonId){
            case "radian":
                
                this.mainController.getSciDegBtn().getStyleClass().add("operation-buttons");
                this.mainController.getInvDegBtn().getStyleClass().add("operation-buttons");
                
                button.getStyleClass().clear();
                button.getStyleClass().add("rad-deg-buttons"); // Grey out clicked button
                this.mainController.getInvRadBtn().getStyleClass().add("rad-deg-buttons");
                
                Model.isRadOrDegClicked=1;
                break;
            case "degree":
                
                this.mainController.getSciRadBtn().getStyleClass().add("operation-buttons");
                this.mainController.getInvRadBtn().getStyleClass().add("operation-buttons");
                
                button.getStyleClass().clear();
                button.getStyleClass().add("rad-deg-buttons"); // Grey out clicked button
                this.mainController.getInvDegBtn().getStyleClass().add("rad-deg-buttons");
                
                Model.isRadOrDegClicked=0;
                break;
        }
        this.mainController.getSciRadBtn().getStyleClass().add("button");
        this.mainController.getSciDegBtn().getStyleClass().add("button");
        this.mainController.getInvRadBtn().getStyleClass().add("button");
        this.mainController.getInvDegBtn().getStyleClass().add("button");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
