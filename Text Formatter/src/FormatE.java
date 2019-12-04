import java.util.*;
/**
 *
 * @author Kyle Benda
 */

//package CSE360TFS;

public class FormatE {
    final private int maxLineLen = 120;
    private int lineLen;
    private int justif = 0;         //poor man's enum:
                            //justification 0 left, 1 center, 2 right
    private boolean equal = false;  //equally space across line or not;
    private boolean wrapping = false;   //wrapping?
    private boolean doubleSpace = false;    //double space or not
    private boolean qTitle = false;         //check immediately - done
    //paragraph - happens once or multiple times?
    private int parSpace = 0;               //number of paragraph spaces
    private int blanks = 0;                 //check immediately - done
    private boolean twoCol = false; //two columns or not. - halfdone
    private ArrayList<String> output = new ArrayList<String>();
    private ArrayList<String> errors = new ArrayList<String>();
    private String wrapBuf = "";    //wrapping buffer - not needed?
    
    public String getOut(){
        String retval = "";
        for (int i = 0; i < output.size(); i++){
            retval = retval+output.get(i);
        }
        return retval;
    }
    
    public String getErr(){
        String retval = "";
        for (int i = 0; i < errors.size(); i++){
            retval = retval+errors.get(i);
        }
        return retval;
    }
    
    //modifies control for all commands but paragraph
    private void proCom(String comm, int inNum){
        String temp = comm.substring(1);
        int ret = 0;
        boolean error = false;
        switch(temp.charAt(0)){
            case 'n':
                temp = temp.substring(1);
                ret = 0;
                //int old = lineLen;
                error = false;
                try{
                    ret = Integer.parseInt(temp);
                } catch (Exception e){
                    error = true;
                    //put error on error arraylist
                    errors.add("Invalid line length input: Line " + inNum);
                }
                if (!error){
                    lineLen = ret;
                }
                break;
            case 'r':
                //right just
                if (temp.equals("r")){
                    justif = 2;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 'l':
                //left just
                if (temp.equals("l")){
                    justif = 0;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 'c':
                //center just
                if (temp.equals("c")){
                    justif = 1;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line" + inNum);
                }
                break;
            case 'e':
                //equal spacing
                if (temp.equals("e")){
                    equal = true;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 'w':
                //wrapping or not
                temp = temp.substring(1);
                if (temp.equals("+")){
                    wrapping = true;
                } else if (temp.equals("-")){
                    wrapping = false;
                } else {
                    //error
                    errors.add("Invalid wrapping option: Line " + inNum);
                }
                break;
            case 's':
                //single space
                if (temp.equals("s")){
                    doubleSpace = false;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 'd':
                //double space
                if (temp.equals("d")){
                    doubleSpace = true;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 't':
                //title
                if (temp.equals("t")){
                    qTitle = true;
                } else {
                    //print error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 'p':
                //paragraph spacing
                //handle it
                //blah
                break;
            case 'b':
                //number of blank lines
                temp = temp.substring(1);
                ret = 0;
                error = false;
                try{
                    ret = Integer.parseInt(temp);
                } catch (Exception e){
                    error = true;
                    // write error
                    errors.add("Invalid blank line input: Line " + inNum);
                }
                if (!error){
                    if (ret > 0){
                        blanks = ret;
                    } else {
                        //write error
                        errors.add("Invalid number of blank lines: Line " + inNum);
                    }
                } else {
                    //write error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            case 'a':
                //number of columns
                temp = temp.substring(1);
                ret = 0;
                error = false;
                try{
                    ret = Integer.parseInt(temp);
                } catch (Exception e){
                    error = true;
                    // write error
                    errors.add("Invalid column number: Line " + inNum);
                }
                if (!error){
                    if (ret == 1){
                        twoCol = false;
                    } else if (ret == 2){
                        twoCol = true;
                    } else {
                        //write error
                        errors.add("Invalid amount of columns: Line " + inNum);
                    }
                } else {
                    //write error
                    errors.add("Invalid formatting command: Line " + inNum);
                }
                break;
            default:
                //couldn't understand command error
                errors.add("Invalid formatting command: Line "+ inNum);
        }
    }
    
    //shouldnt need error handling
    private void makeBlanks(int blankLines){
        for (int i = 0; i < blankLines; i++){
            output.add("\n");     //a blank line with nothing on/in it?
        }
    }
    
    //shouldnt need error handling
    private void makeTitle(String input){
        String formatMe = "";
        if (input.length() > lineLen){
            formatMe = input.substring(0,lineLen);
        } else {
            formatMe = input;
        }
        int spaces = (lineLen-formatMe.length())/2;
        String outStr = "";
        for (int i = 0; i < spaces; i++){
            outStr = outStr+" ";
        }
        output.add(outStr+formatMe);
        for (int i = 0; i < formatMe.length(); i++){
            outStr = outStr+"-";
        }
        output.add(outStr);
    }
    
    //shouldn't need error handling
    private void handleStr(String formatMe){
        //all of the other reindeer i uh mean formatting
        String handler = "";
        if (!twoCol){   //if NOT two columns..
            //normal
            //we'll handle -e later - for now, justify
            //(it's right outside your function, now justify!)
            if (equal){ //incompatible with the other options, i think
                
            } else {    //justify!
                handler = formatMe;
                if (justif == 0 && parSpace > 0){
                    String temp = "";
                    for (int i = 0; i < parSpace; i++){
                        temp = " ";
                    }
                    handler = temp+handler;
                } else if (justif == 1){
                    String temp = "";
                    for (int i = 0; i < (lineLen-formatMe.length())/2; i++){
                        temp = temp+" ";
                    }
                    handler = temp+handler;
                } else if (justif == 2){
                    String temp = "";
                    for (int i = 0; i < (lineLen-formatMe.length()); i++){
                        temp = temp+" ";
                    }
                    handler = temp+handler;
                }
                //output.add(handler);
            }
            //
        } else {        //IF two columns..
            //use line length 80 instead
            //assume for now this baleets all other formatting
            if (equal){ //incompatible with the other options, i think
                
            } else {    //justify!
                //we need to do this differently - just including this for
                //reference
                //done differently
                String hand1 = formatMe.substring(0,(35<formatMe.length()?35:formatMe.length())-parSpace);
                String hand2 = "";
                if (formatMe.length() > 35){
                    hand2 = formatMe.substring(35-parSpace,formatMe.length());
                }
                
                if (justif == 0 && parSpace > 0){
                    String temp = "";
                    for (int i = 0; i < parSpace; i++){
                        temp = " ";
                    }
                    hand1 = temp+hand1;
                } else if (justif == 1){
                    String temp1 = "";
                    for (int i = 0; i < (35-hand1.length())/2; i++){
                        temp1 = temp1+" ";
                    }
                    hand1 = temp1+hand1;
                    String temp2 = "";
                    for (int i = 0; i < (35-hand2.length())/2; i++){
                        temp2 = temp2+" ";
                    }
                    hand2 = temp2+hand2;
                } else if (justif == 2){
                    String temp1 = "";
                    for (int i = 0; i < (35-hand1.length()); i++){
                        temp1 = temp1+" ";
                    }
                    hand1 = temp1+hand1;
                    String temp2 = "";
                    for (int i = 0; i < (35-hand2.length()); i++){
                        temp2 = temp2+" ";
                    }
                    hand2 = temp2+hand2;
                }
                handler = hand1+"          "+hand2;
            }
        }
        //after we format formatMe and put it in handler...
        if (doubleSpace){
            handler = handler+"\n\n";   //double space
        } else {
            handler = handler+"\n";     //single space
        }
        output.add(handler);
    }
    
    //handles it
    public void handleIt(ArrayList<String> input){
        String temp = "";
        boolean collecting = false;
        for (int i = 0; i < input.size(); i++){
            String inTem = input.get(i);
            //we now have a string - check if command
            if (inTem.charAt(0) == '-'){
                //a command/control string - handle it
                proCom(inTem,i+1);
                if (qTitle){
                    i++;
                    if (i < input.size()){
                        inTem = input.get(i);
                        if (inTem.charAt(0) == '-'){
                            //error/warning
                        }
                        if (inTem.length() > lineLen){
                            //error/warning
                        }
                        makeTitle(inTem);
                        qTitle = false; //title done.
                    }
                } else if (blanks > 0){
                    makeBlanks(blanks);
                    blanks = 0;
                }
            } else {
                //is it too big/too small?
                //we'll chop up too-large strings, and put them out line by line
                temp = temp+inTem;
                if (temp.length()+parSpace+(twoCol?10:0) > lineLen){
                    handleStr(temp.substring(0, lineLen-(parSpace+(twoCol?10:0))));
                    temp = temp.substring(lineLen);
                } else if (temp.length()+parSpace+(twoCol?10:0) <= lineLen && wrapping == false){
                    handleStr(temp);
                    temp = "";
                } else if (temp.length()+parSpace+(twoCol?10:0) <= lineLen && wrapping == true){
                    //get another line.. unless the next line is a command line
                    //or there is no next line, then print
                    if (i+1 < input.size()){
                        if (input.get(i+1).charAt(0) == '-'){
                            handleStr(temp);
                            temp = "";
                        }
                    } else {
                        handleStr(temp);
                        temp = "";
                    }
                }
                //if too small, we'll go collect more and append
                
            }
        }
    }
    
}