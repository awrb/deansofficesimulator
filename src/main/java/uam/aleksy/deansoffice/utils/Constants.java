package uam.aleksy.deansoffice.utils;

import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.employee.enums.Opinion;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static Map<Class<? extends Applicant>, Double> queueProbabilities;

    public static Map<Opinion, Integer> opinionThresholds;

    public static final double STUDENT_PROBABILITY = 0.65;
    public static final double DOCTORAL_STUDENT = 0.07;
    public static final double ACQUAINTANCE = 0.15;
    public static final double ADJUNCT = 0.06;
    public static final double PROFESSOR = 0.05;
    public static final double DEAN = 0.02;
    
    static {
        queueProbabilities = new HashMap<>();
        queueProbabilities.put(Student.class, 0.65);
        queueProbabilities.put(DoctoralStudent.class, 0.07);
        queueProbabilities.put(Acquaintance.class, 0.15);
        queueProbabilities.put(Adjunct.class, 0.06);
        queueProbabilities.put(Professor.class, 0.05);
        queueProbabilities.put(Dean.class, 0.02);

        opinionThresholds = new HashMap<>();
        opinionThresholds.put(Opinion.DOBRY_CZLOWIEK, 100);
    }
}
