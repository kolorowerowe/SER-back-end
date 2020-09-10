package com.github.ser.util;

import com.github.ser.exception.badRequest.InvalidDeadlinesException;
import com.github.ser.model.database.Deadline;

import java.util.List;

public class DeadlineUtils {

    private DeadlineUtils(){
        // private constructor
    }

    public static void validateDeadlines(List<Deadline> deadlines) {

        if (deadlines.size() != 5) {
            throw new InvalidDeadlinesException("Expected 5 deadlines, " + deadlines.size() + " provided");
        }

    }
}
