package uam.aleksy.deansoffice.applicant.data;

public class Task {

    private int difficulty;

    // TODO czas w rundach

    public Task(int difficulty) {
        this.difficulty = difficulty;
    }

    public Task() {
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
