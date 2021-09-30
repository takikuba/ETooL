package etool.cdimc;

import etool.cdimc.repository.Repository;
import etool.cdimc.repository.RepositoryManager;
import org.testng.annotations.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class RepositoryManagerTest {

    @Test
    public void testAddRepository() throws ParserConfigurationException, TransformerException {
        RepositoryManager repositoryManager = mock(RepositoryManager.class);
        doNothing().when(repositoryManager).registerRepository(mock(Repository.class));
        when(repositoryManager.getRepositoriesNames()).thenReturn(Set.of("Test_repo1"));

        assertThat(repositoryManager.addRepository("Test_repo1", "CSV")).isFalse();
    }

    @Test
    public void testDeleteRepository() {

    }
}