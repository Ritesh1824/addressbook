package com.jemmic.addressbook.utility;

import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Utility class for validation
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 *
 */
@Component
public class InputValidation {
    /**
     * Method to validate user input Integer format and avoid exception
     * @param in
     * @param lower
     * @param higher
     * @param message
     * @return
     */
    public int validateIntegerInput(Scanner in, int lower, int higher, String message) {
        boolean loopFlag = false;
        int input = -1;
        do {
            try {
                input = in.nextInt();
                if (input >= lower && input <= higher) {
                    loopFlag = false;
                } else {
                    System.out.println(message + "Please enter data between[" + lower + "-" + higher + "]");
                    loopFlag = true;
                }
            }catch(InputMismatchException im){
                System.err.println(message + "Please enter a valid input");
                loopFlag = true;
                in.skip(".*");
            }

        } while (loopFlag);
        return input;

    }

    /**
     * Method to Validate mandatory field
     * @param in
     * @param message
     * @return
     */
    public String validateMandatoryField(Scanner in, String message) {
        boolean loopFlag = false;
        String input = null;
        do{
            input = in.next();
            if(input.isBlank() || input.isEmpty()){
                loopFlag = true;
                System.err.println(message + "This field is mandatory");
            }
            loopFlag = false;
        }while(loopFlag);

        return input;
    }

}
