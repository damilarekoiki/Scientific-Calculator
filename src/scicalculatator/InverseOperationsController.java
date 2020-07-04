/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scicalculatator;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
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
public class InverseOperationsController implements Initializable {
    
    @FXML
    private VBox inverseOperations;
    
    @FXML
    private Button arcSine, arcCos, arcTan;
    @FXML
    private Button expSupNumb, tenSupNumb, numbSquare, nthRoot;
    
    double scrollDuration=400;
    Duration TransitionDuration = Duration.millis(scrollDuration);
    double navigationX=120;
    boolean standardOperationsLoaded =true;
    
    Model model=new Model();
    
    private MainController mainController;
    
    @FXML
    private Label label;
    
    @FXML
    private Button degree,radian;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    public VBox getMyRoot(){
        return inverseOperations;
    }
    
    public void init(MainController mainController) {
        this.mainController=mainController;
        arcSine.setText("sin\u207B\u00B9");
        arcCos.setText("cos\u207B\u00B9");
        arcTan.setText("tan\u207B\u00B9");
        expSupNumb.setText("e\u02e3");
        tenSupNumb.setText("10\u02e3");
        numbSquare.setText("x\u00B2");
        nthRoot.setText("\u02b8\u221Ax");
    }
    
    public KeyValue scrollSceneToRightKv() throws IOException{
        Scene mainScene=this.mainController.getMainScene();
        double scrollFrom=0;
        double scrollTo=mainScene.getWidth();
        
        inverseOperations.translateXProperty().set(scrollFrom);
        
        KeyValue sceneTransitionKv = new KeyValue(inverseOperations.translateXProperty(),scrollTo,Interpolator.EASE_OUT);
        return sceneTransitionKv;
    }
    
    public void showScientificOperationsScene() throws IOException{
        inverseOperations.setVisible(false);
        this.mainController.getStandardOperationsRoot().setVisible(false);
        this.mainController.getScientificOperationsRoot().setVisible(true);
        System.out.println("Inv cont");
    }
    
    public Button getDegBtn(){
        return degree;
    }
    public Button getRadBtn(){
        return radian;
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
            ||calculationScreenLastChar=='e' || model.isPi(calculationScreenLastChar) ){
            if("numbSquare".equals(buttonId)){
                calculationScreenContent=calculationScreenContent.concat(powerSymbol+"2");

            }else{
                calculationScreenContent=calculationScreenContent.concat(powerSymbol);

            }
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
        if(calculationScreenLastChar=='(') return;
        
        if(!model.isNumb(calculationScreenLastChar) && calculationScreenLastChar!=')' 
                && calculationScreenLastChar!='e'&& calculationScreenLastChar!='!'
                && !model.isPi(calculationScreenLastChar)){
            return;
        }
        
        String lastExpression=model.getLastExpression(calculationScreenContent);
        String matExprsLastExpression=model.getLastExpression(Model.mathematicalExpression);
        int lastExpressionIndex=model.getLastExpressionIndex(calculationScreenContent);
        int matExprsLastExpressionIndex=model.getLastExpressionIndex(Model.mathematicalExpression);

        calculationScreenContent=model.replaceChar(lastExpressionIndex, calculationScreenContentLength, 
                ballotBox+rootSymbol+lastExpression, calculationScreenContent);
        
        Model.mathematicalExpression=model.replaceChar(matExprsLastExpressionIndex, 
                Model.mathematicalExpression.length(), matExprsBallotBox+matExprsRootSymbol+matExprsLastExpression, 
                Model.mathematicalExpression);
        
        
        Model.nthRootIncomplete++;
        System.out.println("When Root Pressed: "+Model.mathematicalExpression);
        calculationScreen.setText(calculationScreenContent);
    }
    
    
    public void displayExponentialSup(ActionEvent event){
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
        String exponentialSup="e^";
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
            
            calculationScreenContent=calculationScreenContent.concat(exponentialSup);
            Model.mathematicalExpression=Model.mathematicalExpression.concat(exponentialSup);
            
        }else{
            if(calculationScreenLastChar=='('){

                calculationScreenContent=calculationScreenContent.concat(exponentialSup);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(exponentialSup);

            }else if(calculationScreenLastChar==')' || 
                calculationScreenLastChar=='e' || calculationScreenLastChar=='!' 
                || model.isPi(calculationScreenLastChar) || model.isNumb(calculationScreenLastChar)
                || calculationScreenLastChar=='%'){

                calculationScreenContent=calculationScreenContent.concat("x"+exponentialSup);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+exponentialSup);

            }
            else if(model.endsWithTrigFunction(Model.mathematicalExpression)){
                calculationScreenContent=calculationScreenContent.concat("("+exponentialSup);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("("+exponentialSup);
                Model.numbParenthesisOpened++;
            }
        }
        
        calculationScreen.setText(calculationScreenContent);
        
    }
    
    public void displayTenSup(ActionEvent event){
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
        String tenSup="10^";
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
            
            calculationScreenContent=calculationScreenContent.concat(tenSup);
            Model.mathematicalExpression=Model.mathematicalExpression.concat(tenSup);
            
        }else{
            if(calculationScreenLastChar=='('){

                calculationScreenContent=calculationScreenContent.concat(tenSup);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(tenSup);

            }else if(calculationScreenLastChar==')' || 
                calculationScreenLastChar=='e' || calculationScreenLastChar=='!' 
                || model.isPi(calculationScreenLastChar) || model.isNumb(calculationScreenLastChar)
                || calculationScreenLastChar=='%'){

                calculationScreenContent=calculationScreenContent.concat("x"+tenSup);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+tenSup);

            }
            else if(model.endsWithTrigFunction(Model.mathematicalExpression)){
                calculationScreenContent=calculationScreenContent.concat("("+tenSup);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("("+tenSup);
                Model.numbParenthesisOpened++;
            }
        }
        
        calculationScreen.setText(calculationScreenContent);
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

            System.out.println(Model.mathematicalExpression);
        }
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
    
    public void displayRandomNumber(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        // Calculation Screen
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        char mathematicalExpressionLastChar;
        
        Random rand =new Random();
        String randomNumber=Integer.toString(rand.nextInt(10));
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
        
        if(Model.nthRootIncomplete==1){
            calculationScreenContent=calculationScreenContent.replaceAll("\u2610", "");
            Model.mathematicalExpression=Model.mathematicalExpression.replace("?", "");
            
            String calcScreenNumbSupUnicode=model.convertNumberToSuperscript(randomNumber);
            int calcScreenLastRootIndex=model.getLastRootIndex(calculationScreenContent);
            
            int matExprsLastRootIndex=model.getLastRootIndex(Model.mathematicalExpression);
            
            calculationScreenContent=model.insertChar(calcScreenLastRootIndex,calcScreenNumbSupUnicode,calculationScreenContent);
            Model.mathematicalExpression=model.insertChar(matExprsLastRootIndex,randomNumber,Model.mathematicalExpression);

        }else{
            if(calculationScreenContent.isEmpty() || calculationScreenLastChar=='.' 
                || model.isNumb(calculationScreenLastChar) 
                || model.isOperator(calculationScreenLastChar) || mathematicalExpressionLastChar=='^'
                || Model.matExprsEndsWithRoot()|| Model.matExprsEndsWithPermOrComb()){
            
            calculationScreenContent=calculationScreenContent.concat(randomNumber);
            Model.mathematicalExpression=Model.mathematicalExpression.concat(randomNumber);
            
            }else{
                if(model.endsWithNumbExp(calculationScreenContent) || calculationScreenLastChar=='('){
                    calculationScreenContent=calculationScreenContent.concat(randomNumber);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat(randomNumber);
                }else if(calculationScreenLastChar==')' || 
                    calculationScreenLastChar=='e' || 
                    calculationScreenLastChar=='!' || 
                    model.isPi(calculationScreenLastChar)|| calculationScreenLastChar=='%'){

                    calculationScreenContent=calculationScreenContent.concat("x"+randomNumber);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+randomNumber);

                }
                else if(model.endsWithTrigFunction(Model.mathematicalExpression)){
                    calculationScreenContent=calculationScreenContent.concat("("+randomNumber);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat("("+randomNumber);
                    Model.numbParenthesisOpened++;
                }
            }
        }
        
        calculationScreen.setText(calculationScreenContent);
        
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        String answer=model.compute(Model.mathematicalExpression);
        
        resultScreen.setText(answer);
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
                this.mainController.getSciRadBtn().getStyleClass().add("rad-deg-buttons");
                
                Model.isRadOrDegClicked=1;
                break;
            case "degree":
                
                this.mainController.getSciRadBtn().getStyleClass().add("operation-buttons");
                this.mainController.getInvRadBtn().getStyleClass().add("operation-buttons");
                
                button.getStyleClass().clear();
                button.getStyleClass().add("rad-deg-buttons"); // Grey out clicked button
                this.mainController.getSciDegBtn().getStyleClass().add("rad-deg-buttons");
                
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
