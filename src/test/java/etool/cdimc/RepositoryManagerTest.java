package etool.cdimc;

import etool.cdimc.repository.Repository;
import etool.cdimc.repository.RepositoryManager;
import org.testng.annotations.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class RepositoryManagerTest {

    @Test
    public void testAddRepository() {
        RepositoryManager repositoryManager = new RepositoryManager();

        assertThat(repositoryManager.addRepository("repo6", "XML")).isEqualTo(null);
        assertThat(repositoryManager.addRepository("repo1", "XML")).containsInstanceOf(Repository.class);

    }

    @Test
    public void testDeleteRepository() {
    }

    @Test
    public void testRegisterRepository() {
    }
}