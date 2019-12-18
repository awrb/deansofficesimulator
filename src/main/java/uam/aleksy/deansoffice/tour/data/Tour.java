package uam.aleksy.deansoffice.tour.data;

public class Tour {

    private Long id;

    public Tour(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        return id.equals(((Tour) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
