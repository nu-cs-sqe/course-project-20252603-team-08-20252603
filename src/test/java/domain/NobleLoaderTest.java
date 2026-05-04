package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class NobleLoaderTest {

    @Test
    void loadFromClasspath_presentResourceWithOneNoble_returnsNonEmptyListWithMatchingFields() throws IOException {
        NobleLoader loader = new NobleLoader();
        List<Noble> nobles = loader.loadFromClasspath(NobleLoaderTest.class, "/nobles/one-noble.json");

        assertEquals(1, nobles.size());
        Noble noble = nobles.get(0);
        assertEquals(3, noble.prestigePoints);
        assertEquals(3, noble.requirements.get(TokenColor.EMERALD));
        assertEquals(3, noble.requirements.get(TokenColor.RUBY));
        assertEquals(3, noble.requirements.get(TokenColor.ONYX));
    }
}
