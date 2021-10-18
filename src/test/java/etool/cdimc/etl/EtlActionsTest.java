package etool.cdimc.etl;

import etool.cdimc.stream.DataExtractStream;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class EtlActionsTest {

    @DataProvider(name = "jsonObject")
    Object[] jsonObject() {
        return new Object[]{
                "{\n" +
                        "  \"columns\": {\n" +
                        "    \"column\": [\n" +
                        "      {\n" +
                        "        \"name\": \"Jan\",\n" +
                        "        \"surname\": \"Kowalski\",\n" +
                        "        \"pesel\": 999999999\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"Adam\",\n" +
                        "        \"surname\": \"Nowak\",\n" +
                        "        \"pesel\": 111111111\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}"

        };
    }

    @Test(dataProvider = "jsonObject")
    void shouldCorrectExtractColumnNames(String json){
        DataExtractStream data = mock(DataExtractStream.class);
        when(data.getData()).thenReturn(new JSONObject(json));
        EtlActions etlActions = new EtlActions();
        Set<String> columns = etlActions.getColumns(data);

        Assertions.assertThat(columns).contains("name");
        Assertions.assertThat(columns).contains("surname");
        Assertions.assertThat(columns).contains("pesel");
    }

}