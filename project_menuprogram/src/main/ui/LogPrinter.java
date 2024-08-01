package ui;

import exceptions.LogException;
import model.EventLog;

/**
 * Defines behaviours that event log printers must support.
 * code is from CPSC 210 https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/tree/main/src/main/ca/ubc/cpsc210/alarm/ui
 */
public interface LogPrinter {
    /**
     * Prints the log
     * @param el  the event log to be printed
     * @throws LogException when printing fails for any reason
     */
    void printLog(EventLog el) throws LogException;
}