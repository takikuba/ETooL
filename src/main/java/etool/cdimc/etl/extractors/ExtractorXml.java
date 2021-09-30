package etool.cdimc.etl.extractors;

import etool.cdimc.stream.DataExtractStream;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class ExtractorXml implements Extractor {
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();

            DocumentBuilderFactory dbf
                    = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(data);

            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            System.out.println(writer.toString());

            JSONObject json = XML.toJSONObject(writer.toString());
            DataExtractStream dataExtractStream = new DataExtractStream(json);

            System.out.println(dataExtractStream.getData());
            return dataExtractStream;

        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            logger.warning("ERROR when extract file!");
            e.printStackTrace();

        }

        return null;
    }
}
