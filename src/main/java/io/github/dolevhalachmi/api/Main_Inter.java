package io.github.dolevhalachmi.api;

import java.util.Scanner;

public interface Main_Inter {

    //Main_impl doesn't implement Main, this is more so for convenience.

    /**
     * read's inputs from the client
     *
     * @param action represents the constraints of the number we'd like to recieve.
     * @return a valid value for each action.
     */
    
    double read(Scanner scanner, String prompt, String action);

}
