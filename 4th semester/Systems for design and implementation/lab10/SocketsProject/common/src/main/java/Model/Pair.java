package Model;

import java.io.Serializable;

public class Pair<L,R> implements Serializable {
    L leftPart;
    R rightPart;

    public Pair(L leftPart, R rightPart) {
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    public L getLeftPart() {
        return leftPart;
    }

    public void setLeftPart(L leftPart) {
        this.leftPart = leftPart;
    }

    public R getRightPart() {
        return rightPart;
    }

    public void setRightPart(R rightPart) {
        this.rightPart = rightPart;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "leftPart=" + leftPart +
                ", rightPart=" + rightPart +
                '}';
    }
}

