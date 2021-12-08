/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

/**
 *
 * @author manan
 * I, Manan Patel, 000735153 certify that this material is my
 * original work. No other person's work has been used without due
 * acknowledgement.
 *
 */

public class OpValue {

    public final char operation;    //to store the operation 
    public final double value;      //to store the value 

    
    public OpValue() {
        this.operation = 'e';
        this.value = 0;
    }

    //constructor to initiate the two read-only members of the class and accessors for two properties
     OpValue(char o, double v) {
        this.operation = o;
        this.value = v;
    }

    //Override toString method to display the contents of the object neatly 
    @Override
    public String toString() {
        return this.operation + " " + this.value;
    }

}
