package kata.position;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void from_index() {
        assertThat(Position.fromIndex(0)).isEqualTo(Position.atRow(1).atColumn(1));
        assertThat(Position.fromIndex(1)).isEqualTo(Position.atRow(1).atColumn(2));
        assertThat(Position.fromIndex(2)).isEqualTo(Position.atRow(1).atColumn(3));
        assertThat(Position.fromIndex(3)).isEqualTo(Position.atRow(1).atColumn(4));

        assertThat(Position.fromIndex(4)).isEqualTo(Position.atRow(2).atColumn(1));
        assertThat(Position.fromIndex(5)).isEqualTo(Position.atRow(2).atColumn(2));
        assertThat(Position.fromIndex(6)).isEqualTo(Position.atRow(2).atColumn(3));
        assertThat(Position.fromIndex(7)).isEqualTo(Position.atRow(2).atColumn(4));

        assertThat(Position.fromIndex(8)).isEqualTo(Position.atRow(3).atColumn(1));
        assertThat(Position.fromIndex(9)).isEqualTo(Position.atRow(3).atColumn(2));
        assertThat(Position.fromIndex(10)).isEqualTo(Position.atRow(3).atColumn(3));
        assertThat(Position.fromIndex(11)).isEqualTo(Position.atRow(3).atColumn(4));
    }

    @Test
    void to_index() {
        assertThat(Position.atRow(1).atColumn(1).toIndex()).isEqualTo(0);
        assertThat(Position.atRow(1).atColumn(2).toIndex()).isEqualTo(1);
        assertThat(Position.atRow(1).atColumn(3).toIndex()).isEqualTo(2);
        assertThat(Position.atRow(1).atColumn(4).toIndex()).isEqualTo(3);

        assertThat(Position.atRow(2).atColumn(1).toIndex()).isEqualTo(4);
        assertThat(Position.atRow(2).atColumn(2).toIndex()).isEqualTo(5);
        assertThat(Position.atRow(2).atColumn(3).toIndex()).isEqualTo(6);
        assertThat(Position.atRow(2).atColumn(4).toIndex()).isEqualTo(7);

        assertThat(Position.atRow(3).atColumn(1).toIndex()).isEqualTo(8);
        assertThat(Position.atRow(3).atColumn(2).toIndex()).isEqualTo(9);
        assertThat(Position.atRow(3).atColumn(3).toIndex()).isEqualTo(10);
        assertThat(Position.atRow(3).atColumn(4).toIndex()).isEqualTo(11);
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Position.class).verify();
    }
}
