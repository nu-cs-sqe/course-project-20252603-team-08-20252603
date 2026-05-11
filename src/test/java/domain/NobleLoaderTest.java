package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void loadFromClasspath_missingResource_throwsIOExceptionWithPathInMessage() {
        NobleLoader loader = new NobleLoader();
        String missingPath = "/nobles/no-such-file.json";

        IOException thrown =
                assertThrows(IOException.class, () -> loader.loadFromClasspath(NobleLoaderTest.class, missingPath));

        assertTrue(thrown.getMessage().contains(missingPath));
    }

    @Test
    void loadFromClasspath_fullProductionNoblesResource_returnsCompleteNobles() throws IOException {
        NobleLoader loader = new NobleLoader();
        List<Noble> nobles = loader.loadFromClasspath(NobleLoaderTest.class, "/nobles/nobles.json");

        assertEquals(10, nobles.size());
        assertTrue(nobles.stream().allMatch(noble -> noble.prestigePoints == 3));
    }

    @Test
    void loadFromClasspath_invalidTokenColor_throwsIOExceptionWithPathInMessage() {
        NobleLoader loader = new NobleLoader();
        String invalidPath = "/nobles/invalid-color-noble.json";

        IOException thrown = assertThrows(IOException.class, () -> loader.loadFromClasspath(NobleLoaderTest.class, invalidPath));

        assertTrue(thrown.getMessage().contains(invalidPath));
    }
}
