package si.pecan.logging;

import org.apache.xml.utils.DefaultErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Log4jToLogback {
    private static final Logger log = LoggerFactory.getLogger(Log4jToLogback.class);

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length <= 0) {
            log.error("First argument must be input filename");
            return;
        }

        String inFileName = args[0];
        String outFileName = args.length > 1 ? args[1] : null;
        TransformerFactory tfactory = TransformerFactory.newInstance();
        tfactory.setErrorListener(new DefaultErrorHandler(false));
        try {
            tfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
        } catch (TransformerConfigurationException e) {
            log.error("Could not configure transformer", e);
            return;
        }
        final StreamResult strResult;
        if (null != outFileName) {
            strResult = new StreamResult(new FileOutputStream(outFileName));
            strResult.setSystemId(outFileName);
        } else {
            strResult = new StreamResult(System.out);
        }

        final InputStream resourceAsStream = Log4jToLogback.class.getClassLoader().getResourceAsStream("log4j-to-logback.xsl");
        try {
            javax.xml.parsers.SAXParserFactory factory =
                    javax.xml.parsers.SAXParserFactory.newInstance();

            factory.setNamespaceAware(true);

            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);


            javax.xml.parsers.SAXParser jaxpParser =
                    factory.newSAXParser();

            XMLReader reader = jaxpParser.getXMLReader();
            Templates stylesheet = tfactory.newTemplates(new StreamSource(resourceAsStream));
            final Transformer transformer = stylesheet.newTransformer();
            transformer.transform(
                    new SAXSource(reader, new InputSource(inFileName)),
                    strResult);
        } catch (TransformerConfigurationException e) {
            log.error("Could not load stylesheet", e);
        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            log.error("Could not initialize parser", e);
        }

    }
}
