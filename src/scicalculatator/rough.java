///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package scicalculatator;
//
///**
// *
// * @author user
// */
//public class rough {
//    static Model model =new Model();
//    public static void main(String[] args) {
//        
//        boolean encounteredDot=false;
//        
//        int matExprsRootIndex=-1;
//        if(matExprsRootIndex-1>=0){
//            char matExprsSymbol2BeforeRoot=' ';
//            char matExprsSymbolBeforeRoot=Model.mathematicalExpression.charAt(matExprsRootIndex-1);
//            int matExprsSymbolBeforeRootIndex=matExprsRootIndex-1;
//            int matExprsSymbol2BeforeRootIndex=-1;
//            if(matExprsRootIndex-2>=0){
//                matExprsSymbol2BeforeRoot=Model.mathematicalExpression.charAt(matExprsRootIndex-2);
//                matExprsSymbol2BeforeRootIndex=matExprsRootIndex-2;
//            }
//            
//            int calculationScreenRootIndex=calculationScreenContent.lastIndexOf("u221A");
//            int calculationScreenBeforeRootIndex=calculationScreenRootIndex-1;
//            int calculationScreen2BeforeRootIndex=-1;
//            if(calculationScreenRootIndex-2>=0){
//                calculationScreen2BeforeRootIndex=calculationScreenRootIndex-2;
//            }
//            
//            if(model.isNumb(matExprsSymbolBeforeRoot)){
//                
//                // Conditions to replace with ballot box ; else remove
//                if(matExprsRootIndex-2>=0){
//                    if(matExprsSymbol2BeforeRoot=='('){
//                        calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
//                        Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, calculationScreenContent);
//                        Model.nthRootIncomplete=1;
//                    }else if(model.isOperator(calculationScreenContent.charAt(calculationScreen2BeforeRootIndex))){
//
//                        calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
//                        Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, calculationScreenContent);
//                        Model.nthRootIncomplete=1;
//                    }else{
//                        calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, "", calculationScreenContent);
//                        Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, "", Model.mathematicalExpression);
//                        Model.nthRootIncomplete=2;
//
//                        // To check Number of Dot Pressed
//                        for(int k=rootIndex-1;k>=0;k--){
//                            if(Model.mathematicalExpression.charAt(k)=='.'){
//                                encounteredDot=true;
//                                Model.numbDotPressed=1;
//                                break;
//                            }
//                            if(model.isMatExprsOperator(Model.mathematicalExpression.charAt(k)) || 
//                                Model.mathematicalExpression.charAt(k)=='^'  || Model.mathematicalExpression.charAt(k)=='('){
//
//                                if(encounteredDot) Model.numbDotPressed=1;
//                                else Model.numbDotPressed=0;
//                                break;
//                            }
//                        }
//                        Model.nthRootIncomplete=2;
//                    }
//                }else{
//                    calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
//                    Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, calculationScreenContent);
//                    Model.nthRootIncomplete=1;
//                }
//                
//                
//            }else if(matExprsSymbolBeforeRoot=='-'){
//                // If what comes 2nd to last of calc screen content is supMinus replace with emptyBox
//                //Model.nthRootIncomplete=1;
//                if(calculationScreenContent.charAt(calculationScreenBeforeRootIndex)=='\u207B'){
//                    calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, ballotBox, calculationScreenContent);
//                    Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, matExprsBallotBox, calculationScreenContent);
//                    Model.nthRootIncomplete=1;
//                }
//            }else if(matExprsSymbolBeforeRoot=='.'){
//                // Remove 2nd to last char Model.nthRootIncomplete=2;
//                // Model.numbDotPressed=0;
//                calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex, "", calculationScreenContent);
//                Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, "", Model.mathematicalExpression);
//
//                Model.nthRootIncomplete=2;
//                Model.numbDotPressed=0;
//
//            }else if(matExprsSymbolBeforeRoot=='?'){
//                // Remove 2nd to last char Model.nthRootIncomplete=2;
//                // Model.numbDotPressed=0;
//                calculationScreenContent=model.replaceChar(calculationScreenBeforeRootIndex, calculationScreenRootIndex-1, "", calculationScreenContent);
//                Model.mathematicalExpression=model.replaceChar(matExprsSymbolBeforeRootIndex, matExprsRootIndex, "", Model.mathematicalExpression);
//
//                Model.nthRootIncomplete=0;
//                Model.numbDotPressed=0;
//
//            }else{
//                // remove last char from calc screen and mathExprs Model.nthRootIncomplete=0;
//                calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
//                Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
//
//                Model.nthRootIncomplete=0;
//                Model.numbDotPressed=0;
//
//            }
//            
//        }
//        
//        
//        
//        
//        
//        
//        
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//if(model.isNumb(matExprs2ToLastChar)){
//                    // Remove number  Model.nthRootIncomplete=2;
//                    // scan backwars to you find an operator or ( StringEnd, if you encounter "." then numDotPressed=1
//                    
//                    // if index of number is 0 replace numb with emptyBox Model.nthRootIncomplete=1;
//                    // if what comes b4 numb is ( replace numb with emptyBox Model.nthRootIncomplete=1;
//                    // If what comes b4 numb in calc scrren is supMinus, replace
//                    // supMinus & Numb with Empty box. else if just operator replace numb only Model.nthRootIncomplete=1;
//                    if(matExprsLength==2){
//                        calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, ballotBox, calculationScreenContent);
//                        Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, matExprsBallotBox, calculationScreenContent);
//                        Model.nthRootIncomplete=1;
//                    }else if(matExprsLength>=3){
//                        if(matExprs3ToLastChar=='('){
//                            calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, ballotBox, calculationScreenContent);
//                            Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, matExprsBallotBox, calculationScreenContent);
//                            Model.nthRootIncomplete=1;
//                        }else if(model.isOperator(calculationScreenContent.charAt(calculationScreenContentLength-3))){
//                            
//                            calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, ballotBox, calculationScreenContent);
//                            Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, matExprsBallotBox, calculationScreenContent);
//                            Model.nthRootIncomplete=1;
//                        }else{
//                            calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, "", calculationScreenContent);
//                            Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, "", calculationScreenContent);
//                            Model.nthRootIncomplete=2;
//                        }
//                        
//                        
//                        
//                    }
//                    
//                }else if(matExprs2ToLastChar=='-'){
//                    // If what comes 2nd to last of calc screen content is supMinus replace with emptyBox
//                    //Model.nthRootIncomplete=1;
//                    if(calculationScreenContent.charAt(calculationScreenContentLength-2)=='\u207B'){
//                        calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, ballotBox, calculationScreenContent);
//                        Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, matExprsBallotBox, calculationScreenContent);
//                        Model.nthRootIncomplete=1;
//                    }
//                }else if(matExprs2ToLastChar=='.'){
//                    // Remove 2nd to last char Model.nthRootIncomplete=2;
//                    // Model.numbDotPressed=0;
//                    calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, "", calculationScreenContent);
//                    Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, "", Model.mathematicalExpression);
//
//                    Model.nthRootIncomplete=2;
//                    Model.numbDotPressed=0;
//                    
//                }else if(matExprs2ToLastChar=='?'){
//                    // Remove 2nd to last char Model.nthRootIncomplete=2;
//                    // Model.numbDotPressed=0;
//                    calculationScreenContent=model.replaceChar(calculationScreenContentLength-2, calculationScreenContentLength-1, "", calculationScreenContent);
//                    Model.mathematicalExpression=model.replaceChar(matExprsLength-2, matExprsLength-1, "", Model.mathematicalExpression);
//
//                    Model.nthRootIncomplete=0;
//                    Model.numbDotPressed=0;
//                    
//                }else{
//                    // remove last char from calc screen and mathExprs Model.nthRootIncomplete=0;
//                    calculationScreenContent=model.replaceLastChar("", calculationScreenContent);
//                    Model.mathematicalExpression=model.replaceLastChar("", Model.mathematicalExpression);
//                    
//                    Model.nthRootIncomplete=0;
//                    Model.numbDotPressed=0;
//
//                }