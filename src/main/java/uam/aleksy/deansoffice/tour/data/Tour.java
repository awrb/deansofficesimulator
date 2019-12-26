package uam.aleksy.deansoffice.tour.data;

import uam.aleksy.deansoffice.tour.consequences.data.Consequence;

import java.util.List;

public class Tour {

    private Long id;

    private List<Consequence> consequences;

    public void setId(Long id) {
        this.id = id;
    }

    public List<Consequence> getConsequences() {
        return consequences;
    }

    public void setConsequences(List<Consequence> consequences) {
        this.consequences = consequences;
    }

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
