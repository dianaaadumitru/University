package Model;

public class CastedSpell extends BaseEntity<Pair<Long, Long>> {
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public CastedSpell(Long wizardId, Long spellId, String details) {
        this.setId(new Pair<>(wizardId, spellId));
        this.details = details;
    }

    @Override
    public String toString() {
        return "CastedSpell{" +
                super.toString() + '\'' +
                "details='" + details + '\'' +
                '}';
    }
}