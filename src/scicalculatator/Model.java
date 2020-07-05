/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scicalculatator;

import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author user
 */
public class Model {
    String[] operatorsArray={"+","-","x","247","^"};
    String[] matExprsOperatorsArray={"+","-","*","/","^"};
    static String mathematicalExpression="";
    
    static int numbDotPressed=0;
    
    static int nthRootIncomplete=0;
    
    static int numbParenthesisOpened=0;
    
    public static int isRadOrDegClicked=0; // 0 - Degree; 1 - Radian
    
    String[] matExpressionChars=
    {
        "/","*","S","C","T","s","c","t","@","|","[","P","#","$","?"
    };
    String[] screenContentChars=
    {
        "u00F7","x","sin","cos","tan","sin\u207B\u00B9","cos\u207B\u00B9","tan\u207B\u00B9"
            ,"\u221A","\u03C0","C","P","ln","log","\u2610"
    };
    
    String[] ScreenNumbersArray=
    {
        "0","1","2","3","4","5","6","7","8","9","."
    };
    String[] ScreenNumbersSuperscriptsArray=
    {
        "\u2070","\u00B9","\u00B2","\u00B3","\u2074","\u2075",
        "\u2076","\u2077","\u2078","\u2079","\u02D9"
    };
    
    String[] trigFunctionsArray={"sin","cos","tan","sin\u207B\u00B9","cos\u207B\u00B9","tan\u207B\u00B9","ln","log"};
    String[] matExprsTrigFunctionsArray={"S","C","T","s","c","t","#","$"};
    
    String[] selectionsArray={"x!","!","P","C"};
    String[] matExprsSelectionsArray={"!","!","P","["};
    
    // Operator Stack
    Stack<String> operatorStack=new Stack <>();
    // Operator Stack
    Stack<String> trigFunctionStack=new Stack <>();
    // Permution and Combination Stack
    Stack<String> selectionStack=new Stack <>();
    // Root Stack
    Stack<String> rootStack=new Stack <>();
    // Precedence Stack
    Stack<String> precedenceStack=new Stack <>();
    
    public boolean isOperator(char character){
        int characterUnicodeInt=(int)character;
        String characterUnicode=Integer.toString(characterUnicodeInt);
        String characterString=Character.toString(character);
        for(int i= 0;i<operatorsArray.length; i++){
            String operator=operatorsArray[i];
            if(characterString.equals(operator) || characterUnicode.equals(operator)){
                return true;
            }
        }
        return false;
    }
    
    public boolean isMatExprsOperator(char character){
        
        String characterString=Character.toString(character);
        for(int i= 0;i<matExprsOperatorsArray.length; i++){
            String operator=matExprsOperatorsArray[i];
            if(characterString.equals(operator)){
                return true;
            }
        }
        return false;
    }
    
    public String convertOperatorToMatExprsChar(char operator){
        String matExprsOpVal="";
        int operatorUnicodeInt=(int)operator;
        if(operator=='x'){
            matExprsOpVal="*";
        }else if(operatorUnicodeInt==247){
            matExprsOpVal="/";
        }else{
            matExprsOpVal=Character.toString(operator);
        }
        return matExprsOpVal;
    }
    
    public String convertTrigFunctionToMatExprsChar(String trigFunction){
        int indexOfTrigFunction=Arrays.asList(trigFunctionsArray).indexOf(trigFunction);
        return matExprsTrigFunctionsArray[indexOfTrigFunction];
    }
    
    public String convertNumberToSuperscript(String number){
        String superscript="";
        if(!number.isEmpty()){
            for(int i=0;i<number.length();i++){
                char symbol=number.charAt(i);
                int indexOfSymbol=Arrays.asList(ScreenNumbersArray).indexOf(Character.toString(symbol));
                System.out.println(indexOfSymbol);
                superscript+=ScreenNumbersSuperscriptsArray[indexOfSymbol];
                
            }
        }
        return superscript;
        
    }
    
    public String convertSelectionToMatExprsChar(String selection){
        int indexOfSelection=(Arrays.asList(selectionsArray)).indexOf(selection);
        return matExprsSelectionsArray[indexOfSelection];
    }
    
    public String convertScreenContentToMatExprs(String stringContent){
        if(stringContent.isEmpty()){
            return stringContent;
        }
        int i=0;
        for(String symbol:screenContentChars){
            i++;
            stringContent=stringContent.replaceAll(symbol, matExpressionChars[i]);
        }
        return stringContent;
    }
    
    public String convertMatExprsToScreenContent(String stringContent){
        if(stringContent.isEmpty()){
            return stringContent;
        }
        int i=0;
        for(String symbol:matExpressionChars){
            i++;
            stringContent=stringContent.replaceAll(symbol, screenContentChars[i]);
        }
        return stringContent;
    }

    public String replaceLastChar(String newChar,String stringContent){
        stringContent=replaceChar(stringContent.length()-1, stringContent.length(), newChar,stringContent);
        return stringContent;
    }

    public String replaceChar(int startInclusive, int endExclusive, String newChar,String stringContent){
        StringBuilder str= new StringBuilder(stringContent);
        str=str.replace(startInclusive, endExclusive, newChar);
        return str.toString();
    }
    
    public String insertChar(int offset, String newChar,String stringContent){
        StringBuilder str= new StringBuilder(stringContent);
        str=str.insert(offset, newChar);
        return str.toString();
    }
    
    public String reverseString(String stringContent){
        StringBuilder str= new StringBuilder(stringContent);
        str=str.reverse();
        return str.toString();
    }

    public boolean endsWithNumbExp(String mathematicalExpression){
        if(mathematicalExpression.length()<2) return false;
        int mathematicalExpressionLength=mathematicalExpression.length();
        char mathematicalExpressionLastChar;
        char mathematicalExpressionSecondToLastChar;
        // If mathematicalExpression is empty, then last character is empty
        if(mathematicalExpressionLength==0){
            mathematicalExpressionLastChar=' ';
            mathematicalExpressionSecondToLastChar=' ';
        }else{ //else we get the last character
            mathematicalExpressionLastChar=mathematicalExpression.charAt(mathematicalExpressionLength-1);
            mathematicalExpressionSecondToLastChar=mathematicalExpression.charAt(mathematicalExpressionLength-2);
        }
        if(mathematicalExpressionLastChar=='e' && isNumb(mathematicalExpressionSecondToLastChar) ){
            return true;
        }

        return false;
    }

    public boolean isNumb(char character){
        return Character.isDigit(character);
    }
    
    public boolean isPi(char character){
        int characterUnicodeInt=(int)character;
        String characterUnicode=Integer.toString(characterUnicodeInt);
        if(characterUnicode.equals("960")){
            return true;
        }
        return false;

    }
    
    public boolean endsWithTrigFunction(String mathematicalExpression) {
        if(mathematicalExpression.isEmpty()) return false;
        char mathematicalExpressionLastChar=mathematicalExpression.charAt(mathematicalExpression.length()-1);
        
        return isTrigFunction(mathematicalExpressionLastChar);
        
    }
    
    public static boolean matExprsEndsWithRoot() {
        if(mathematicalExpression.isEmpty()) return false;
        char mathematicalExpressionLastChar=mathematicalExpression.charAt(mathematicalExpression.length()-1);
        
        return mathematicalExpressionLastChar=='@';
        
    }
    
    public static boolean matExprsEndsWithPermOrComb() {
        if(mathematicalExpression.isEmpty()) return false;
        char mathematicalExpressionLastChar=mathematicalExpression.charAt(mathematicalExpression.length()-1);
        
        return isPermOrComb(mathematicalExpressionLastChar);
        
    }
    
    public static boolean isPermOrComb(char symbol){
        return symbol=='P' || symbol=='[';
    }
    
    public boolean isTrigFunction(char symbol){
        for(int i=0;i<matExprsTrigFunctionsArray.length;i++){
            if(Character.toString(symbol).equals(matExprsTrigFunctionsArray[i])){
                return true;
            }
        }
        return false;
    }
    
    // INCOMPLETE
    public boolean isPowerSymbol(char mathematicalExpression) {
        
        return false;
    }
    
    public static char getMatExprsLastChar(){
        if(mathematicalExpression.isEmpty()) return ' ';
        else return mathematicalExpression.charAt(mathematicalExpression.length()-1);
    }
    
    public String getLastExpression(String stringContent){
        String lastExpression="";
        int numbParenthesis=0;
        if(!stringContent.isEmpty()){
            for(int i=stringContent.length()-1;i>=0;i--){
                if(stringContent.charAt(i)=='('){
                    numbParenthesis++;
                }
                if(stringContent.charAt(i)=='^' || numbParenthesis>1){
                    break;
                }
                if(isOperator(stringContent.charAt(i))){
//                    if(i>0) break;
                    if(i>0){
                        if(stringContent.charAt(i)=='-'){
                            if(stringContent.charAt(i-1)!='(') break;
                        }else{
                            break;
                        }
                        if(stringContent.charAt(i-1)!='(' && stringContent.charAt(i)!='-') break;
                    }
                }
                lastExpression+=stringContent.charAt(i);
            }
        }
        lastExpression=reverseString(lastExpression);
        return lastExpression;
    }
    
    public int getLastExpressionIndex(String stringContent){
        int lastExpressionIndex=0;
        int numbParenthesis=0;
        if(!stringContent.isEmpty()){
            for(int i=stringContent.length()-1;i>=0;i--){
                if(stringContent.charAt(i)=='('){
                    numbParenthesis++;
                }
                if(stringContent.charAt(i)=='^' || numbParenthesis>1){
                    return i+1;
                }
                
                if(isOperator(stringContent.charAt(i))){
                    if(i>0){
                        if(stringContent.charAt(i)=='-'){
                            if(stringContent.charAt(i-1)!='(') return i+1;
                        }else{
                            return i+1;
                        }
                    }
                }
            }
        }
        return lastExpressionIndex;
    }
    
    public int getLastRootIndex(String stringContent){
        if(!stringContent.isEmpty()){
            for(int i=stringContent.length()-1;i>=0;i--){
                if(stringContent.charAt(i)=='\u221A' || stringContent.charAt(i)=='@'){
                    return i;
                }
            }
        }
        
        return 0;
    }
    
    
    
    public String compute(String expression){
        System.out.println("Expression: "+expression);
        if(expression.isEmpty()) return "";
        if(!isNumb(expression.charAt(expression.length()-1)) && expression.charAt(expression.length()-1)!=')'
                && expression.charAt(expression.length()-1)!='!' ){
            return "";
        }
        if(expression.contains("?")) return "";
        if(expression.length()>=2){
            for(int i=0;i<expression.length();i++){
                if(expression.charAt(i)=='.' && expression.charAt(i+1)=='@') return "";
                if(isOperator(expression.charAt(i)) && expression.charAt(i+1)=='-' && expression.charAt(i+1)=='@') return "";
            }
        }
        
        Stack<String> numberStack=new Stack <>();
        String operator="";
        String number="";
        char[] matOperatorsArray={'+','*','-','/'};
        
        // DO NOT USE ELSE-IF IN THE LOOP BELOW, IT WILL FAIL YOU
        for(int i=0;i<expression.length();i++){
            char token = expression.charAt(i);
            // Concatenate Sequence of numbers together
            if(Character.isDigit(token) || token=='.'){
                
                number+=Character.toString(token);
                
            }if( (!isNumb(token) && token!='.') || (isNumb(token) && i==expression.length()-1) ){
                
                if(!number.isEmpty()){
                    if(token=='@'){ // To know if number is nth of root
                        
                        operatorStack.push(number+","+token); // Push nth root
                        System.out.println("op pek: "+operatorStack.peek());
                        
                    }else{
                        System.out.println("Hey: "+token);
                        if(!operatorStack.empty()){
                            if(isTrigFunction(operatorStack.peek().charAt(0))){
                                numberStack.push(applyTrig(operatorStack.pop(), number));
                                if(!operatorStack.empty()){
                                    if(operatorStack.peek().contains("@")){
                                        String[] root=operatorStack.pop().split(",");
                                        String nthRoot=root[0];
                                        numberStack.push(applyRoot(nthRoot, number));
                                    }
                                }

                            }else if(operatorStack.peek().contains("@")){
                                String[] root=operatorStack.pop().split(",");
                                String nthRoot=root[0];
                                numberStack.push(applyRoot(nthRoot, number));
                                
                                if(!operatorStack.empty()){
                                    if(isTrigFunction(operatorStack.peek().charAt(0))){
                                        numberStack.push(applyTrig(operatorStack.pop(), number));
                                    }
                                }
                                
                            }else{
                                numberStack.push(number);
                            }
                        }else{
                            numberStack.push(number);
                        }
                        
                    }
                    
                    number="";
                    
                    if(!selectionStack.empty()){
                        if(!numberStack.empty()) numberStack.push(applySelection(selectionStack.pop(),numberStack.pop(),numberStack.pop()));
                    }
                    if(isNumb(token) && i==expression.length()-1){
                        
                        if(!operatorStack.empty() && numberStack.size()>=2){
                            while(!"(".equals(operatorStack.peek())){
                                
                                numberStack.push(applyOperation(operatorStack.pop(),numberStack.pop(),numberStack.pop()));
                                if(numberStack.size()<=1) break;
                                if(operatorStack.empty()) break;
                            }
                        }
                        
                        
                        // If operator stack contains '(' as top element remove it
                        if(!operatorStack.empty()){
                            if("(".equals(operatorStack.peek()) && i==expression.length()-1){
                                operatorStack.pop(); // Remove open bracket)
                            }
                        }
                        
                        // Check if operator stack peek is trig or root,
                        // then replace number stack peek with trig or root of number
                        if(!operatorStack.empty()){
                            
                            if(isTrigFunction(operatorStack.peek().charAt(0))){
                                if(!numberStack.empty()) numberStack.push(applyTrig(operatorStack.pop(), numberStack.pop()));
                                if(!operatorStack.empty()){
                                    if(operatorStack.peek().contains("@")){
                                        String[] root=operatorStack.pop().split(",");
                                        String nthRoot=root[0];
                                        if(!numberStack.empty()) numberStack.push(applyRoot(nthRoot, numberStack.pop()));
                                    }
                                }

                            }else if(operatorStack.peek().contains("@")){
                                System.out.println("Number top: "+numberStack.peek());
                                String[] root=operatorStack.pop().split(",");
                                String nthRoot=root[0];
                                if(!numberStack.empty()) numberStack.push(applyRoot(nthRoot, numberStack.pop()));
                                
                                if(!operatorStack.empty()){
                                    if(isTrigFunction(operatorStack.peek().charAt(0))){
                                        if(!numberStack.empty()) numberStack.push(applyTrig(operatorStack.pop(), numberStack.pop()));
                                    }
                                }
                                
                            }
                        }
                        
                    }
                    
                }
                
            }if(token=='('){ // If token is '(' push to operator stack
                
                operator=Character.toString(token);
                operatorStack.push(operator);
                
            }if(token==')'){ // If token is ')' solve entire expression inside bracket
                System.out.println("Operator stack object"+operatorStack);
                if(!operatorStack.empty() && numberStack.size()>=2 ){
                    while(!"(".equals(operatorStack.peek())){
                        
                        numberStack.push(applyOperation(operatorStack.pop(),numberStack.pop(),numberStack.pop()));
                        if(operatorStack.empty()) break;
                        if(numberStack.size()<=1) break;
                        
                    }
                }
                
                operatorStack.pop(); // Remove open bracket
                
                // Check if operator stack peek is trig or root,
                // then replace number stack peek with trig or root of number
                if(!operatorStack.empty()){
                    if(isTrigFunction(operatorStack.peek().charAt(0))){
                        if(!numberStack.empty()) numberStack.push(applyTrig(operatorStack.pop(), numberStack.pop()));
                        if(!operatorStack.empty()){
                            if(operatorStack.peek().contains("@")){
                                String[] root=operatorStack.pop().split(",");
                                String nthRoot=root[0];
                                if(!numberStack.empty()) numberStack.push(applyRoot(nthRoot, numberStack.pop()));
                            }
                        }

                    }else if(operatorStack.peek().contains("@")){
                        String[] root=operatorStack.pop().split(",");
                        String nthRoot=root[0];
                        if(!numberStack.empty()) numberStack.push(applyRoot(nthRoot, numberStack.pop()));

                        if(!operatorStack.empty()){
                            if(isTrigFunction(operatorStack.peek().charAt(0))){
                                if(!numberStack.empty()) numberStack.push(applyTrig(operatorStack.pop(), numberStack.pop()));
                            }
                        }

                    }
                }
                
            }if(isMatExprsOperator(token) || token=='^'){ // If token is an operator
                
                boolean pushOperatorIntoNumberStack=false;
                if(token=='-'){
                    if(expression.length()>1){
                        if(i==0 || expression.charAt(i-1)=='(' || isMatExprsOperator(expression.charAt(i-1))){
                            pushOperatorIntoNumberStack=true;
                        }
                    }
                    
                }
                if(pushOperatorIntoNumberStack){
                    number+=token;
                }else{
                    
                    if(!rootStack.empty()){
                        if(rootStack.size()==2){
                            System.out.println("first element: "+rootStack.firstElement());
                            numberStack.push(applyOperation(rootStack.pop(),rootStack.pop(),numberStack.pop()));
                        }else if(rootStack.size()==1){
                            numberStack.push(applyOperation(rootStack.pop(),"2",numberStack.pop()));
                        }
                    }
                    // If operator at top of stack has precedence >= current token, apply operator to
                    // top 2 elements in number stack
                    if(numberStack.size()>=2){
                        while(!operatorStack.empty() && hasPrecedence(Character.toString(token),operatorStack.peek())){
                            
                            numberStack.push(applyOperation(operatorStack.pop(), numberStack.pop(), numberStack.pop()));
                            if(numberStack.size()<=1) break;
                            if(!operatorStack.empty()) break;
                        }
                    }
                    operatorStack.push(Character.toString(token)); // Push current token to operator stack
                }
                
                
            }if(token=='!'){
                
                if(!numberStack.empty()) numberStack.push(applyFactorial(numberStack.pop()));
                
            }if(isTrigFunction(token)){
                
                operatorStack.push(Character.toString(token));
                
            }if(isPermOrComb(token)){
                
                selectionStack.push(Character.toString(token));
                
            }if(token=='@'){
                if(i-1>=0){
                    if( isOperator(expression.charAt(i-1)) || expression.charAt(i-1)=='^' ){
                        operatorStack.push("2,"+Character.toString(token));
                    }
                }
                if(i==0){
                    operatorStack.push("2,"+Character.toString(token));
                }
            }
            
            
            
        }
        
        // At this point the entire expression has been resolved
        // Apply remaining operators to remaining numbers
        if(!operatorStack.empty() && numberStack.size()>=2 ){
            while(!operatorStack.empty()){

                numberStack.push(applyOperation(operatorStack.pop(), numberStack.pop(), numberStack.pop()));
                if(operatorStack.empty()) break;
                if(numberStack.size()<=1) break;

            }
        }
        
        return numberStack.pop();
    }
    
     // Returns true if 'op2' has higher or same precedence as 'op1', 

    // otherwise returns false. 

    public static boolean hasPrecedence(String op1, String op2){ 

        boolean result=false;
        
        if ("(".equals(op2) || ")".equals(op2)){ 
            result= false; 
        }
        
        if("^".equals(op1) && ( "*".equals(op2) || "/".equals(op2) || "+".equals(op2) || "-".equals(op2) ) ){
            result=false;
        }else{
            result=true;
            
            if (("*".equals(op1) || "/".equals(op1)) && ("+".equals(op2) || "-".equals(op2))){ 
                result= false; 
            }else{
                result= true; 
            }
        }

        return result;
    }

    // A utility method to apply an operator 'op' on operands 'a'  

    // and 'b'. Return the result. 

    public static String applyOperation(String op, String b, String a){ 

        double leftNumber=Double.parseDouble(a);
        double rightNumber=Double.parseDouble(b);
        
        System.out.println("Left : "+leftNumber);
        System.out.println("right: "+rightNumber);
        System.out.println("operator: "+op);
        
        double ans=0.0;
        switch (op){ 

            case "+": 
                ans= leftNumber + rightNumber; 
                break;

            case "-": 
                ans=leftNumber - rightNumber; 
                break;

            case "*": 
                ans= leftNumber * rightNumber;
                break;
                
            case "^": 
                ans= Math.pow(leftNumber, (rightNumber));
                break;

            case "/": 

                if (rightNumber == 0){
                    return "\u221E";
                }else{
                    ans= leftNumber / rightNumber; 
                }
                break;

        } 

        return Double.toString(ans); 

    }
    
    public String applyTrig(String trigFunction, String value){
        
        double ans=0.0;
        double number=Double.parseDouble(value);
        String[] sineCosTan={"S","C","T","s","c","t"};
        
        if(isRadOrDegClicked==1){
            if(Arrays.asList(sineCosTan).contains(trigFunction)){
                number=Math.toRadians(number);
            }
        }
        
        switch(trigFunction){
            case "S":
                ans=Math.sin(number);
                break;
            case "C":
                ans=Math.cos(number);
                break;
            case "T":
                ans=Math.tan(number);
                break;
            case "s":
                ans=Math.asin(number);
                break;
            case "c":
                ans=Math.acos(number);
                break;
            case "t":
                ans=Math.atan(number);
                break;
            case "#":
                ans=Math.log(number);
                break;
            case "$":
                ans=Math.log10(number);
                break;
        }
        
        return Double.toString(ans);
            
    }

    public String applyFactorial(String value){
        int factorial=1;
        String minus="";
        if(value.contains("-")){ // case it's a negative number
            minus="-";
            value=value.replace("-", "");
        }
        
        int numb=Integer.parseInt(value);
        
        if(numb==0) return "1";
        
        for(int i=1;i<=numb;i++){
            factorial=factorial*i;
        }
        
        String ans=minus+Integer.toString(factorial); // factorial of negative numbers = -
        return ans;
    }
    
    public String applySelection(String selection, String b, String a){
        int ans=1;
        int left=Integer.parseInt(a);
        int right=Integer.parseInt(b);
        int numerator=1;
        int denominator=1;
        int leftMinusRight=left-right;
        switch(selection){
            case "P":
                
                numerator=Integer.parseInt(applyFactorial(Integer.toString(left)));
                denominator=Integer.parseInt(applyFactorial(Integer.toString(leftMinusRight)));
                break;
                
            case "[":
                
                String rFactorialString=applyFactorial(Integer.toString(right));
                int rFactorialInt=Integer.parseInt(rFactorialString);
                
                String leftMinusRightFactorialString=applyFactorial(Integer.toString(leftMinusRight));
                int leftMinusRightFactorialInt=Integer.parseInt(leftMinusRightFactorialString);
                
                numerator=Integer.parseInt(applyFactorial(Integer.toString(left)));
                denominator=rFactorialInt*leftMinusRightFactorialInt;
                break;
        }
        ans=numerator/denominator;
        return Integer.toString(ans);
    }
    
    public String applyRoot(String nthRoot, String number){
        // This method uses Newton's approach to solving roots of numbers
        double n= new Double(nthRoot);
        double x= new Double(number);
        double p=0.0001;
        if(x<0 && n%2.0==0.0){
            return "-1"; // Return error
        }
        if(x==0) return "0";
        double x1=x;
        double x2=x/n;
        while(Math.abs(x1-x2)>p){
            x1=x2;
            x2=( (n-1.0) * x2 + x/Math.pow(x2, n-1.0) )/n;
        }
        return Double.toString(Math.round(x2*1000.0)/1000.0);
    }
        
}
