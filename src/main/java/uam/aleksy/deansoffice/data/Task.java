package uam.aleksy.deansoffice.data;

public class Task {

    private int difficulty;

    private Applicant applicant;

    // TODO czas w rundach

    public Task(int difficulty) {
        this.difficulty = difficulty;
    }

    public Task() {
    }

    public Task(int difficulty, Applicant applicant) {
        this.difficulty = difficulty;
        this.applicant = applicant;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
