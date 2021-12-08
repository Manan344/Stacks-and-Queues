/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.Scanner;

/**
 *
 * @author manan
 * I, Manan Patel, 000735153 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgement
 * 
 */
public class Lab5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner input = new Scanner(System.in);

        //MQueue instance to store all of the calculator operations
        MQueue<OpValue> queue = new MQueue<>();

        //Stack class for undo operations
        MStack<OpValue> undoOperation = new MStack<>();

        //Stack class for redo operations
        MStack<OpValue> redoOperation = new MStack<>();

        //Store regular expression
        String legalToken = "[+-/*]{1}\\s\\d*\\.?\\d*";

        //trackValue is declared to keep track of Total after each successful operation
        double trackValue = 0;

        System.out.println("COMP10152 - Lab#5 - Calculator using Queues and Stacks ... by ");
        System.out.println("Enter tokens. Legal tokens are integers, +, -, *, /, U[ndo], R[edo], E[valuate] and [e]X[it] ");
        System.out.println("");

        while (true) {

            //accepts user input
            String data = input.nextLine();

            //Tells whether or not the input matches with the given regular expression.
            if (data.matches(legalToken)) {

                //Splits the given string around matches of the given regular expression
                String[] token = data.split(" ");
                OpValue op = new OpValue(token[0].charAt(0), Double.parseDouble(token[1]));
                queue.enqueue(op);
            } 

            //if user input is "U" or "R"
            else if ("U".equals(data) || "R".equals(data)) {
                OpValue op = new OpValue(data.charAt(0), 0);
                queue.enqueue(op);
            } 

            //if user input is "E"
            else if ("E".equals(data)) {

                //To evaluate all of the operations on the Queue
                calculateValue(queue, undoOperation, redoOperation, trackValue);

                //Once evaluated the Total set to '0'
                trackValue = 0;

                //Queue will be empty and ready to start a new calculation
                queue.noElement();

                //To empty undostack
                undoOperation.noElement();

                //To empty redostack
                redoOperation.noElement();
            } 

            //if user input is "X"
            else if ("X".equals(data)) {

                //On exit the contents of the Queue is evaluated
                calculateValue(queue, undoOperation, redoOperation, trackValue);
                break;
            }
            
            //for invalid user input
            else {
                OpValue error = new OpValue();
                queue.enqueue(error);
            }
        }

    }

    
    //This method will evaluate the operation and value pair entered by user 
    public static void calculateValue(MQueue<OpValue> queue, MStack<OpValue> undoStack, MStack<OpValue> redoStack, double trackValue) {

        while (!queue.isEmpty()) {
            switch (queue.peek().operation) {

                //if operator is '+'
                case '+':
                    trackValue = applyOperation(trackValue, queue, undoStack, redoStack, '+');
                    break;

                //if operator is '-'    
                case '-':
                    trackValue = applyOperation(trackValue, queue, undoStack, redoStack, '-');
                    break;

                //if operator is '*'    
                case '*':
                    trackValue = applyOperation(trackValue, queue, undoStack, redoStack, '*');
                    break;

                //if operator is '/'    
                case '/':
                    trackValue = applyOperation(trackValue, queue, undoStack, redoStack, '/');
                    break;

                //if input is 'U'    
                case 'U':
                    trackValue = undoCommand(undoStack, trackValue, redoStack, queue);
                    break;

                //if input is 'R'    
                case 'R':
                    trackValue = redoCommand(redoStack, trackValue, undoStack, queue);
                    break;

                //if user input is invalid
                default:

                    //Print out error message
                    System.out.println("ERROR --> Invalid Token - line ignored");

                    //Line is ignored from queue
                    queue.dequeue();
                    break;
            }
        }
    }
    
    
    public static double applyOperation(double trackValue, MQueue<OpValue> queue, MStack<OpValue> undoStack, MStack<OpValue> redoStack, char c) {

        switch (c) {

            //if operator is '+'
            case '+':
                
                //trackValue is added with the user input
                trackValue += queue.peek().value;
                break;

            //if operator is '-'
            case '-':

                //trackValue is subtracted with the user input
                trackValue -= queue.peek().value;
                break;

            //if operator is '*'
            case '*':

                //trackValue is multiplied with the user input
                trackValue *= queue.peek().value;
                break;

            //if operator is '/'
            case '/':

                //trackValue is divided with the user input
                trackValue /= queue.peek().value;
                break;

            default:
                break;
        }

        //Output is displayed neatly by overriding the toString method 
        System.out.println(queue.peek().toString());      
        System.out.printf("Total = %.1f\n", trackValue);
       
        //redo value from the queue is stored in undo stack
        undoStack.push(queue.peek());
        queue.dequeue();
        redoStack.noElement();
        return trackValue;
    }
    

    public static double undoCommand(MStack<OpValue> undoStack, double trackValue, MStack<OpValue> redoStack, MQueue<OpValue> queue) {

        //if undo stack is empty
        if (undoStack.isEmpty()) {

            System.out.println("U");
            //Print out error message
            System.out.printf("ERROR --> Undo is empty - Can't Undo ");
            System.out.println("");

            //Print current total value
            System.out.printf("Total = %.1f", trackValue);
            System.out.println("");
            queue.dequeue();
        } 

        //if undo stack is not empty
        else {
            switch (undoStack.peek().operation) {

                //if operator is '+'
                case '+':
                    trackValue = processUndo(trackValue, undoStack, redoStack, queue, '+');

                    //trackValue is subtracted with the user input
                    trackValue -= undoStack.peek().value;
                    break;

                //if operator is '-'
                case '-':
                    trackValue = processUndo(trackValue, undoStack, redoStack, queue, '-');

                    //trackValue is added with the user input
                    trackValue += undoStack.peek().value;
                    break;

                //if operator is '*'
                case '*':
                    trackValue = processUndo(trackValue, undoStack, redoStack, queue, '*');

                    //trackValue is divided with the user input
                    trackValue /= undoStack.peek().value;
                    break;

                //if operator is '/'
                case '/':
                    trackValue = processUndo(trackValue, undoStack, redoStack, queue, '/');

                    //trackValue is multiplied with the user input
                    trackValue *= undoStack.peek().value;
                    break;

                default:
                    break;
            }

            //Display result for undo operation
            System.out.println("U");
            System.out.printf("Total = %.1f", trackValue);
            System.out.println("");

            //undo value from the queue is stored in redo stack
            redoStack.push(undoStack.peek());

            //Removes last element from undo stack
            undoStack.pop();
            queue.dequeue();
        }
        return trackValue;
    }

    
    public static double processUndo(double trackValue, MStack<OpValue> undoStack, MStack<OpValue> redoStack, MQueue<OpValue> queue, char c) {
        return trackValue;
    }
    

    public static double redoCommand(MStack<OpValue> redoStack, double trackValue, MStack<OpValue> undoStack, MQueue<OpValue> queue) {

        //if redo stack is empty
        if (redoStack.isEmpty()) {

            System.out.println("R");
            //Print out error message
            System.out.printf("ERROR --> Redo is empty - Can't Redo ");
            System.out.println("");

            //Print current total value
            System.out.printf("Total = %.1f", trackValue);
            System.out.println("");
            queue.dequeue();
        } 

        //if redo stack is not empty
        else {
            switch (redoStack.peek().operation) {

                //if operator is '+'
                case '+':
                    trackValue = processRedo(trackValue, redoStack, undoStack, queue, '+');

                    //trackValue is added with the user input
                    trackValue += redoStack.peek().value;
                    break;

                //if operator is '-'
                case '-':
                    trackValue = processRedo(trackValue, redoStack, undoStack, queue, '-');

                    //trackValue is subtracted with the user input
                    trackValue -= redoStack.peek().value;
                    break;

                //if operator is '*'
                case '*':
                    trackValue = processRedo(trackValue, redoStack, undoStack, queue, '*');

                    //trackValue is multiplied with the user input
                    trackValue *= redoStack.peek().value;
                    break;

                //if operator is '/'
                case '/':
                    trackValue = processRedo(trackValue, redoStack, undoStack, queue, '/');

                    //trackValue is divided with the user input
                    trackValue /= redoStack.peek().value;
                    break;

                default:
                    break;
            }

            //Display result for redo operation
            System.out.println("R");
            System.out.printf("Total = %.1f", trackValue);
            System.out.println("");

            //redo value from the queue is stored in undo stack
            undoStack.push(redoStack.peek());

            //Removes last element from redo stack
            redoStack.pop();
            queue.dequeue();
        }
        return trackValue;
    }

    
    public static double processRedo(double trackValue, MStack<OpValue> redoStack, MStack<OpValue> undoStack, MQueue<OpValue> queue, char c) {
        return trackValue;
    }
}
