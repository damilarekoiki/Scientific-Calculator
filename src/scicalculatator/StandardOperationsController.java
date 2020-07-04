/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scicalculatator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author user
 */
public class StandardOperationsController implements Initializable {
    
    @FXML
    public VBox standardOperations;
    
    double scrollDuration=400;
    Duration TransitionDuration = Duration.millis(scrollDuration);
    double navigationX=120;
    boolean standardOperationsLoaded =true;
    
    private MainController mainController;
    
//    int Model.numbDotPressed=0;
    
    Model model= new Model();
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    public VBox getMyRoot(){
        return standardOperations;
    }
    
    public void init(MainController mainController) {
        this.mainController=mainController;
    }
    
    
    public KeyValue scrollSceneToLeftKv() throws IOException{
        Scene mainScene=this.mainController.getMainScene();
        double scrollFrom=0;
        double scrollTo=mainScene.getWidth()*-1;
        
        standardOperations.translateXProperty().set(scrollFrom);
        
        KeyValue sceneTransitionKv = new KeyValue(standardOperations.translateXProperty(),scrollTo,Interpolator.EASE_OUT);
        return sceneTransitionKv;
    }
    
    public KeyValue scrollSceneInKv() throws IOException{
        Scene mainScene=this.mainController.getMainScene();
        double scrollFrom=mainScene.getWidth()*-1;
        double scrollTo=0;
        
        standardOperations.translateXProperty().set(scrollFrom);
        
        KeyValue sceneTransitionKv = new KeyValue(standardOperations.translateXProperty(),scrollTo,Interpolator.EASE_OUT);
        return sceneTransitionKv;
    }
    
    // Not Through With The Method
    public void displayOperator(ActionEvent event){
        if(Model.matExprsEndsWithPermOrComb()) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String operatorValue=((Button)event.getSource()).getText();
        String matExprsOpVal=model.convertOperatorToMatExprsChar(operatorValue.charAt(0));
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        
        if(calculationScreenContent.contains("\u2610")){
            if("-".equals(operatorValue)){
                calculationScreenContent=calculationScreenContent.replaceAll("\u2610", "");
                Model.mathematicalExpression=Model.mathematicalExpression.replace("?", "");
            
                String calcScreenMinusSupUnicode="\u207B";
                int calcScreenLastRootIndex=model.getLastRootIndex(calculationScreenContent);

                int matExprsLastRootIndex=model.getLastRootIndex(Model.mathematicalExpression);

                calculationScreenContent=model.insertChar(calcScreenLastRootIndex,calcScreenMinusSupUnicode,calculationScreenContent);
                Model.mathematicalExpression=model.insertChar(matExprsLastRootIndex,operatorValue,Model.mathematicalExpression);
                Model.numbDotPressed=0;
            }else{
                return;
            }
            
        
        }else{
            if(model.isOperator(calculationScreenLastChar)){
                if(calculationScreenContentLength>0){
                    // Dont replace last char when calc screen content ends with "(-"
                    if(calculationScreenContentLength>=2){
                        if(calculationScreenContent.charAt(calculationScreenContentLength-2)=='(' && calculationScreenContent.charAt(calculationScreenContentLength-1)=='-'){
                            return ; // Return Void
                        }
                    }
                    if("-".equals(operatorValue)){
                        calculationScreenContent=calculationScreenContent.concat("("+operatorValue);
                        Model.mathematicalExpression=Model.mathematicalExpression.concat("("+matExprsOpVal);
                        Model.numbParenthesisOpened++;
                    }else{
                        calculationScreenContent = model.replaceLastChar(operatorValue, calculationScreenContent);
                        Model.mathematicalExpression=model.replaceLastChar(matExprsOpVal, Model.mathematicalExpression);
    //                    System.out.println(calculationScreenContent);
                    }
                }else{
                    if(!"-".equals(operatorValue)){
                        return;
                    }
                }
            }else{
                if(calculationScreenLastChar=='.'){
                    return;
                }
                if(calculationScreenLastChar=='('){
                    if(!"-".equals(operatorValue)){ // Only Minus can come after "("
                        return;
                    }
                }
                if(model.endsWithNumbExp(calculationScreenContent) || 
                        model.endsWithTrigFunction(Model.mathematicalExpression)){
                    return ;
                }
                if(model.isNumb(calculationScreenLastChar) || model.isPi(calculationScreenLastChar) 
                        || calculationScreenLastChar=='%'|| calculationScreenLastChar=='e'|| 
                        calculationScreenLastChar=='!'|| 
                        calculationScreenLastChar=='^' || calculationScreenLastChar==')' 
                        ||(calculationScreenLastChar=='(' && "-".equals(operatorValue)) ){
                    calculationScreenContent=calculationScreenContent.concat(operatorValue);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsOpVal);
                }
            }


    //        If operator is not "-"
            if(!"-".equals(operatorValue)){
    //            No operator can start an arithmetic expression except "-"
                if(calculationScreenContent.isEmpty()){
                    return;
                }

            }else if("-".equals(operatorValue)){
                if(calculationScreenContent.isEmpty()){
                    calculationScreenContent=calculationScreenContent.concat(operatorValue);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsOpVal);
                }
            }
            
            Model.numbDotPressed=0;
            Model.nthRootIncomplete=0;
        }
        
        
        

        calculationScreen.setText(calculationScreenContent);
        System.out.println(Model.mathematicalExpression);
        
        

    }
    
    public void displayNumber(ActionEvent event){
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        char mathematicalExpressionLastChar;
        // Value On The Pressed Operator Button
        String number=((Button)event.getSource()).getText();
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
            
            String calcScreenNumbSupUnicode=model.convertNumberToSuperscript(number);
            int calcScreenLastRootIndex=model.getLastRootIndex(calculationScreenContent);
            
            int matExprsLastRootIndex=model.getLastRootIndex(Model.mathematicalExpression);
            
            calculationScreenContent=model.insertChar(calcScreenLastRootIndex,calcScreenNumbSupUnicode,calculationScreenContent);
            Model.mathematicalExpression=model.insertChar(matExprsLastRootIndex,number,Model.mathematicalExpression);
        
        }else{
            if(calculationScreenContent.isEmpty() || calculationScreenLastChar=='.' 
                || model.isNumb(calculationScreenLastChar) 
                || model.isOperator(calculationScreenLastChar) || mathematicalExpressionLastChar=='^'
                || Model.matExprsEndsWithRoot()|| Model.matExprsEndsWithPermOrComb()){
            
            calculationScreenContent=calculationScreenContent.concat(number);
            Model.mathematicalExpression=Model.mathematicalExpression.concat(number);
            
            }else{
                if(model.endsWithNumbExp(calculationScreenContent) || calculationScreenLastChar=='('){
                    calculationScreenContent=calculationScreenContent.concat(number);
                    String matExprsNumb=number;
                    if(model.endsWithNumbExp(calculationScreenContent)){
                        matExprsNumb="*"+number;
                    }
                    Model.mathematicalExpression=Model.mathematicalExpression.concat(matExprsNumb);

                    
                }else if(calculationScreenLastChar==')' || 
                    calculationScreenLastChar=='e' || 
                    calculationScreenLastChar=='!' || 
                    model.isPi(calculationScreenLastChar)|| calculationScreenLastChar=='%'){

                    calculationScreenContent=calculationScreenContent.concat("x"+number);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+number);

                }
                else if(model.endsWithTrigFunction(Model.mathematicalExpression)){
                    calculationScreenContent=calculationScreenContent.concat("("+number);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat("("+number);
                    Model.numbParenthesisOpened++;
                }
            }
        }
        
        
        
        calculationScreen.setText(calculationScreenContent);
        
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        String answer=model.compute(Model.mathematicalExpression);
        
        resultScreen.setText(answer);
        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayPercent(ActionEvent event){
        if(Model.nthRootIncomplete==1) return;
        if(Model.mathematicalExpression.length()>=2){
            if(Model.isPermOrComb(Model.mathematicalExpression.charAt(Model.mathematicalExpression.length()-2))){
                return;
            }
        }
        if(Model.matExprsEndsWithPermOrComb()) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String percentSymbol=((Button)event.getSource()).getText();
        String matPercentSymbol="/100";
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        if(model.endsWithTrigFunction(Model.mathematicalExpression)) return;
        
        if(calculationScreenContent.isEmpty()) return;
        else{
            if(model.isOperator(calculationScreenLastChar)){
                calculationScreenContent=model.replaceLastChar(percentSymbol, calculationScreenContent);
                Model.mathematicalExpression=model.replaceLastChar(matPercentSymbol, Model.mathematicalExpression);
            }else{
                if(!model.isNumb(calculationScreenLastChar)) return;
                calculationScreenContent=calculationScreenContent.concat(percentSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(matPercentSymbol);
            }
        }
        calculationScreen.setText(calculationScreenContent);
        
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        String answer=model.compute(Model.mathematicalExpression);
        
        resultScreen.setText(answer);
        
        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayDot(ActionEvent event){
        if(Model.mathematicalExpression.length()>=2){
            if(Model.isPermOrComb(Model.mathematicalExpression.charAt(Model.mathematicalExpression.length()-2))){
                return;
            }
        }
        if(Model.matExprsEndsWithPermOrComb()) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String dotSymbol=((Button)event.getSource()).getText();
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        if(Model.numbDotPressed>0) return;
        if(model.endsWithTrigFunction(Model.mathematicalExpression)) return;
        
        if(Model.nthRootIncomplete==1){
            int lastSupMinusIndex=calculationScreenContent.lastIndexOf("\u207B");
            int lastRootIndex=calculationScreenContent.lastIndexOf("\u221A");
            
            if(calculationScreenContent.contains("\u2610") || lastRootIndex==lastSupMinusIndex+1){
                dotSymbol="0"+dotSymbol;
            }
            
            calculationScreenContent=calculationScreenContent.replaceAll("\u2610", "");
            Model.mathematicalExpression=Model.mathematicalExpression.replace("?", "");
            
            String calcScreenNumbSupUnicode=model.convertNumberToSuperscript(dotSymbol);
            int calcScreenLastRootIndex=model.getLastRootIndex(calculationScreenContent);
            
            int matExprsLastRootIndex=model.getLastRootIndex(Model.mathematicalExpression);
            
            calculationScreenContent=model.insertChar(calcScreenLastRootIndex,calcScreenNumbSupUnicode,calculationScreenContent);
            Model.mathematicalExpression=model.insertChar(matExprsLastRootIndex,dotSymbol,Model.mathematicalExpression);

        }else{
            if(calculationScreenContent.isEmpty()){
                calculationScreenContent=calculationScreenContent.concat("0"+dotSymbol);
                Model.mathematicalExpression=Model.mathematicalExpression.concat("0"+dotSymbol);
            }else{
                if(model.endsWithNumbExp(calculationScreenContent) || 
                        model.isOperator(calculationScreenLastChar) || 
                        model.isPowerSymbol(calculationScreenLastChar) ||
                        calculationScreenLastChar=='(')
                {
                    calculationScreenContent=calculationScreenContent.concat("0"+dotSymbol);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat("0"+dotSymbol);
                }else{
                    if(!model.isNumb(calculationScreenLastChar)){
                        return;
                    }else{
                        calculationScreenContent=calculationScreenContent.concat(dotSymbol);
                        Model.mathematicalExpression=Model.mathematicalExpression.concat(dotSymbol);
                    }
                }

            }
        }
        
        Model.numbDotPressed++;
        calculationScreen.setText(calculationScreenContent);
        System.out.println(Model.mathematicalExpression);
    }
    
    public void displayParenthesis(ActionEvent event){
//        if(Model.nthRootIncomplete==1) return;
        if(Model.matExprsEndsWithPermOrComb()) return;
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String parenthesis=((Button)event.getSource()).getText();
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        if(calculationScreenLastChar=='.') return;
        
        switch(parenthesis){
            case "(":
                if(calculationScreenLastChar=='('){
                    return;
                }
                if(calculationScreenContent.isEmpty()){
                    calculationScreenContent=calculationScreenContent.concat(parenthesis);
                    Model.mathematicalExpression=Model.mathematicalExpression.concat(parenthesis);
                    Model.numbParenthesisOpened++;
                }else{
                    if(model.isPi(calculationScreenLastChar)||model.isNumb(calculationScreenLastChar) 
                        || calculationScreenLastChar=='!' || calculationScreenLastChar=='e' 
                        || calculationScreenLastChar=='%' || calculationScreenLastChar==')'){
                        System.out.println(parenthesis);
                        calculationScreenContent=calculationScreenContent.concat("x"+parenthesis);
                        Model.mathematicalExpression=Model.mathematicalExpression.concat("*"+parenthesis);
                        Model.numbParenthesisOpened++;
                        
                    }else if(model.endsWithTrigFunction(Model.mathematicalExpression) 
                            || calculationScreenLastChar=='^' ||model.isOperator(calculationScreenLastChar) 
                            || (Model.matExprsEndsWithRoot()&& Model.numbParenthesisOpened==0) ){
                        
                        calculationScreenContent=calculationScreenContent.concat(parenthesis);
                        Model.mathematicalExpression=Model.mathematicalExpression.concat(parenthesis);
                        Model.numbParenthesisOpened++;
                    }
                }
                break;
            case ")":
                if(!calculationScreenContent.contains("(") || Model.numbParenthesisOpened==0){
                    return;
                }
                if(calculationScreenLastChar=='('){
                    return;
                }
                if(model.endsWithTrigFunction(Model.mathematicalExpression) 
                        || model.isOperator(calculationScreenLastChar) 
                        ||  calculationScreenLastChar=='.'){
                    return;
                }
                
                if(calculationScreenLastChar=='^' || model.matExprsEndsWithRoot() || model.getMatExprsLastChar()=='@'){
                    return;
                }
                
                calculationScreenContent=calculationScreenContent.concat(parenthesis);
                Model.mathematicalExpression=Model.mathematicalExpression.concat(parenthesis);
                Model.numbParenthesisOpened--;
                System.out.println("Parenth: "+parenthesis);
                System.out.println("Mat exprs: "+Model.mathematicalExpression);
                break;
            default:
                break;
        }
        calculationScreen.setText(calculationScreenContent);
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
    
    public void handleDel(ActionEvent event){
        // Calculation Screen
        TextField calculationScreen=this.mainController.getCalculationScreen();
        // Content Of Calculation Screen
        String calculationScreenContent=calculationScreen.getText();
        int calculationScreenContentLength=calculationScreenContent.length();
        char calculationScreenLastChar;
        // Value On The Pressed Operator Button
        String parenthesis=((Button)event.getSource()).getText();
        
        String ballotBox="\u2610";
        String matExprsBallotBox="?";
        
        int matExprsLength=Model.mathematicalExpression.length();
        char matExprsLastChar=' ';
        char matExprs2ToLastChar=' ';
        char matExprs3ToLastChar=' ';
        if(matExprsLength>=1){
            matExprsLastChar=Model.mathematicalExpression.charAt( matExprsLength-1);
        }
        if(matExprsLength>=2){
            matExprs2ToLastChar=Model.mathematicalExpression.charAt( matExprsLength-2);
        }
        if(matExprsLength>=3){
            matExprs3ToLastChar=Model.mathematicalExpression.charAt( matExprsLength-3);
        }
        
        
//        If calculation screen is empty, then last character is empty
        if(calculationScreenContentLength==0){
            calculationScreenLastChar=' ';
        }else{ //else we get the last character
            calculationScreenLastChar=calculationScreenContent.charAt(calculationScreenContentLength-1);
        }
        
        // Result Screen
        TextField resultScreen=this.mainController.getResultScreen();
        String resultScreenContent=resultScreen.getText();
        
        if(calculationScreenContent.isEmpty()) return;
        
        if(calculationScreenContentLength==1){
            calculationScreenContent="";
            Model.mathematicalExpression="";
            resultScreenContent="";
        }else{
            if(calculationScreenLastChar=='('){
                if(calculationScreenContentLength>=2){
                    if(model.isTrigFunction(matExprs2ToLastChar) ){
                        // From calc screen keep removing until you get to t, c, s or l inclusive
                        // And remove ( and matExprs2ToLastChar from matExprs
                        for(int i=calculationScreenContentLength-1;i>=0;i--){
                           
                           char cSLC=calculationScreenContent.charAt(calculationScreenContent.length()-1);
                           calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                           if(cSLC=='s'||cSLC=='c'||cSLC=='t'||cSLC=='l') break;
                        }
                       
                        Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength, "", Model.mathematicalExpression);

                       
                    }else{
                        // Remove last char from calc screen and matExprs
                        calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                        Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
                        
                    }
                }else{
                    // Remove last char from calc screen and matExprs
                    calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                    Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
                    
                }
            }else if(matExprsLastChar=='@'){
                calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
                Model.nthRootIncomplete=0;

            }else if(matExprsLastChar=='.'){
                // Model.numbDotPressed=0;
                // remove last char from calc screen and mathExprs
                calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);

                Model.numbDotPressed=0;
                
            }else if(model.isNumb(matExprsLastChar) || matExprsLastChar==')'){
                System.out.println("Got here: "+matExprsLastChar);
                // remove last char from calc screen and mathExprs
                // scan backwars to you find an operator or ( StringEnd, if you encounter "." then numDotPressed=1
                
                boolean encounteredDot=false;
                boolean encounteredOp=false;
                boolean removeNumb=false;
                int opIndex=0;
                int matExprsRootIndex=Model.mathematicalExpression.lastIndexOf('@');

                
                for(int i=Model.mathematicalExpression.length()-1;i>=0;i--){
                    if(model.isOperator(Model.mathematicalExpression.charAt(i)) || 
                        Model.mathematicalExpression.charAt(i)=='^' ){
                        
                        opIndex=i;
                        
                        if(Model.mathematicalExpression.charAt(i)=='-'){
                            if(i-1>=0) {
                                if(Model.mathematicalExpression.charAt(i-1)=='('){
                                    encounteredOp=false;
                                    break;
                                }else{
                                    encounteredOp=true;
                                    break;
                                }
                            } else{
                                encounteredOp=false;
                                break;
                            }
                        }else{
                            encounteredOp=true;
                            break;
                        }
                        
                        
                    }
                }

                if(!Model.mathematicalExpression.contains("@")){
                    removeNumb=true;
                }else{
                    for(int i=Model.mathematicalExpression.length()-1;i>=0;i--){
                        if(Model.mathematicalExpression.charAt(i)=='@'){
                            if(i-1>0){ // If Root is not the first symbol
                                if(Model.mathematicalExpression.charAt(i-1)=='.' || 
                                        Model.mathematicalExpression.charAt(i-1)=='?' || 
                                    model.isNumb(Model.mathematicalExpression.charAt(i-1))){

                                    removeNumb=false;
                                    break;
                                }else if(Model.mathematicalExpression.charAt(i-1)=='-'){
                                    int calculationScreenRootIndex=calculationScreenContent.lastIndexOf("\u221A");
                                    int calculationScreenBeforeRootIndex=calculationScreenRootIndex-1;

                                    char calculationScreenBeforeRoot=calculationScreenContent.charAt(calculationScreenBeforeRootIndex);

                                    if(calculationScreenBeforeRoot=='\u207B'){
                                        removeNumb=false;
                                        break;
                                    }else{
                                        removeNumb=true;
                                        break;
                                    }
                                }else{
                                    removeNumb=true;
                                    break;
                                }
                            }else{ // If root is the first symbol
                                removeNumb=true;
                                break;
                            }
                        }
                    }
                }
                
                
                
                if(removeNumb){
                    
                    if(calculationScreenLastChar=='%'){
                        // Remove last char from calc screeen and remove Last /100 from matExprs
                        calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                        Model.mathematicalExpression=model.replaceChar(matExprsLength-4, matExprsLength, "", Model.mathematicalExpression);

                    }else if(model.isPi(calculationScreenLastChar)){
                        // Remove last char from calc screeen and remove Last 3.1415926536 from matExprs
                        calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                        Model.mathematicalExpression=model.replaceChar(matExprsLength-12, matExprsLength, "", Model.mathematicalExpression);
                        System.out.println("After PI deleted: "+Model.mathematicalExpression);
                    }else if(calculationScreenLastChar=='e'){

                        if(model.endsWithNumbExp(calculationScreenContent)){
                            // Remove last char from calc screeen and remove Last *10^ from matExprs
                            calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                            Model.mathematicalExpression=model.replaceChar(matExprsLength-4, matExprsLength, "", Model.mathematicalExpression);


                        }else{
                            // remove last char from calc screen and remove last 2.71828182846
                            calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                            Model.mathematicalExpression=model.replaceChar(matExprsLength-13, matExprsLength, "", Model.mathematicalExpression);


                        }

                    }else{
                        calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                        Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
                    }
                    
                    for(int j=Model.mathematicalExpression.length()-1;j>=0;j--){
                        if(Model.mathematicalExpression.charAt(j)=='.'){
                            encounteredDot=true;
                            Model.numbDotPressed=1;
                            break;
                        }
                        if(model.isMatExprsOperator(Model.mathematicalExpression.charAt(j)) || 
                            Model.mathematicalExpression.charAt(j)=='^'  || Model.mathematicalExpression.charAt(j)=='('){

                            if(encounteredDot) Model.numbDotPressed=1;
                            else Model.numbDotPressed=0;
                            break;
                        }
                    }
                }else{

                    if(matExprsRootIndex-1>=0){
                        char matExprsSymbol2BeforeRoot=' ';
                        char matExprsSymbolBeforeRoot=Model.mathematicalExpression.charAt(matExprsRootIndex-1);
                        int matExprsSymbolBeforeRootIndex=matExprsRootIndex-1;
                        int matExprsSymbol2BeforeRootIndex=-1;
                        if(matExprsRootIndex-2>=0){
                            matExprsSymbol2BeforeRoot=Model.mathematicalExpression.charAt(matExprsRootIndex-2);
                            matExprsSymbol2BeforeRootIndex=matExprsRootIndex-2;
                        }

                        int calculationScreenRootIndex=calculationScreenContent.lastIndexOf("\u221A");
                        int calculationScreenBeforeRootIndex=calculationScreenRootIndex-1;
                        int calculationScreen2BeforeRootIndex=-1;
                        if(calculationScreenRootIndex-2>=0){
                            calculationScreen2BeforeRootIndex=calculationScreenRootIndex-2;
                        }

                        if(model.isNumb(matExprsSymbolBeforeRoot)){

                            // Conditions to replace with ballot box ; else remove
                            if(matExprsRootIndex-2>=0){
                                if(matExprsSymbol2BeforeRoot=='('){
                                    calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
                                    Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, Model.mathematicalExpression);
                                    Model.nthRootIncomplete=1;
                                }else if(model.isOperator(calculationScreenContent.charAt(calculationScreen2BeforeRootIndex))){

                                    calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
                                    Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, Model.mathematicalExpression);
                                    Model.nthRootIncomplete=1;
                                    
                                }else{
                                    calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, "", calculationScreenContent);
                                    Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, "", Model.mathematicalExpression);
                                    Model.nthRootIncomplete=2;

                                    // To check Number of Dot Pressed
                                    for(int k=matExprsRootIndex-1;k>=0;k--){
                                        if(Model.mathematicalExpression.charAt(k)=='.'){
                                            encounteredDot=true;
                                            Model.numbDotPressed=1;
                                            break;
                                        }
                                        if(model.isMatExprsOperator(Model.mathematicalExpression.charAt(k)) || 
                                            Model.mathematicalExpression.charAt(k)=='^'  || Model.mathematicalExpression.charAt(k)=='('){

                                            if(encounteredDot) Model.numbDotPressed=1;
                                            else Model.numbDotPressed=0;
                                            break;
                                        }
                                    }
                                    Model.nthRootIncomplete=2;
                                }
                            }else{
                                calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
                                Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, Model.mathematicalExpression);
                                Model.nthRootIncomplete=1;
                            }


                        }else if(matExprsSymbolBeforeRoot=='-'){
                            // If what comes 2nd to last of calc screen content is supMinus replace with emptyBox
                            //Model.nthRootIncomplete=1;
                            if(calculationScreenContent.charAt(calculationScreenBeforeRootIndex)=='\u207B'){
                                calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
                                Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, Model.mathematicalExpression);
                                Model.nthRootIncomplete=1;
                            }
                        }else if(matExprsSymbolBeforeRoot=='.'){
                            // Remove 2nd to last char Model.nthRootIncomplete=2;
                            // Model.numbDotPressed=0;
                            calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, "", calculationScreenContent);
                            Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, "", Model.mathematicalExpression);

                            Model.nthRootIncomplete=2;
                            Model.numbDotPressed=0;

                        }else if(matExprsSymbolBeforeRoot=='?'){
                            // Remove 2nd to last char Model.nthRootIncomplete=2;
                            // Model.numbDotPressed=0;
                            calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, "", calculationScreenContent);
                            Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, "", Model.mathematicalExpression);

                            Model.nthRootIncomplete=0;
                            Model.numbDotPressed=0;

                        }else{
                            // remove last char from calc screen and mathExprs Model.nthRootIncomplete=0;
                            calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                            Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);

                            Model.nthRootIncomplete=0;
                            Model.numbDotPressed=0;

                        }

                    }
                    
                }

                if(matExprsLastChar==')'){
                    Model.numbParenthesisOpened++;
                }
            
            }else{
                // remove last char from both calc screen and matExprs
                calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
                Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
            }
        }
        
        String answer=model.compute(Model.mathematicalExpression);
        if(calculationScreenContent.contains("\u2610") || calculationScreenContent.contains("\u207B\u221A")){
            answer="";
        }
        calculationScreen.setText(calculationScreenContent);
        resultScreen.setText(answer);
        
    }
    
    public void clearScreen(ActionEvent event){
        
        TextField calculationScreen=this.mainController.getCalculationScreen();
        TextField resultScreen=this.mainController.getResultScreen();

        Model.mathematicalExpression="";
        calculationScreen.setText("");
        resultScreen.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
