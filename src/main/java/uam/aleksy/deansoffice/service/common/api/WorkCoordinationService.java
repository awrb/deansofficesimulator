package uam.aleksy.deansoffice.service.common.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.data.Task;

/**
 * Coordinates interactions between employees and applicants
 */
public interface WorkCoordinationService {

    boolean helpedEveryApplicant();

    void continueHelping(Employee employee, Applicant applicant, Task task);

    void workOnNewTask(Employee employee, Applicant applicant, Task task);

    void finishHelping(Employee employee, Applicant applicant);

    void startHelping(Employee employee, Applicant applicant);
}
