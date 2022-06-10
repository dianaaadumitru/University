package ro.ubb.lab11.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pair<L, R> {
    L left;
    R right;

    @Override
    public String toString() {
        return "<" + this.left + ", " + this.right + ">";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pair && this.left.equals(((Pair<?, ?>) obj).left)&&
                this.right.equals(((Pair<?, ?>) obj).right);
    }

}
