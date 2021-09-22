package etool.cdimc.repository;

import etool.cdimc.Constants;
import etool.cdimc.scenes.SceneManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RepositoryManager extends JPanel {
    private final Logger logger = Constants.logger();
    private final Set<Repository> repositories = new HashSet<>();
    private Repository currentRepository = null;
    private JButton buttonConnect;

    public RepositoryManager() {
        try{
            readRepositories();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            logger.warning("No active repositories!");
            e.printStackTrace();
        }
        logger.info("Initialize Repository Manager");

        setBackground(Constants.WORKSPACE_COLOR);
        setBorder(new CompoundBorder(new TitledBorder("Repositories"), new EmptyBorder(8, 0, 0, 0)));

        JButton buttonNew = new JButton("+ New +");
        buttonNew.addActionListener(e -> addRepository());

        buttonConnect = new JButton("Connect");
        buttonConnect.setEnabled(false);
        buttonConnect.addActionListener(e -> {
            loadRepository(currentRepository);
        });

        add(getRepoList());
        add(buttonConnect);
        add(buttonNew);
    }

    private void loadRepository(Repository repository) {
        RepositoryLoader repositoryLoader = new RepositoryLoader();
    }

    private void addRepository() {
        JFrame addRepositoryFrame = new JFrame("New Repository");
        addRepositoryFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addRepositoryFrame.setLocationRelativeTo(null);
        addRepositoryFrame.setSize(200, 200);
        addRepositoryFrame.setLayout(null);
        addRepositoryFrame.setBackground(Constants.WORKSPACE_COLOR);

        JTextField repositoryName = new JTextField();
        repositoryName.setBounds(42, 20, 100, 30);
        JComboBox vendorBox = new JComboBox();
        for(Vendor v: Vendor.values()){
            vendorBox.addItem(v.name());
        }
        vendorBox.setBounds(42, 70, 100, 30);

        JButton connect = new JButton("Connect");
        connect.setBounds(42, 120, 100, 20);
        connect.addActionListener(e -> {
            addRepository(repositoryName.getText(), Objects.requireNonNull(vendorBox.getSelectedItem()).toString());
        });

        addRepositoryFrame.getContentPane().add(repositoryName);
        addRepositoryFrame.getContentPane().add(vendorBox);
        addRepositoryFrame.getContentPane().add(connect);
        addRepositoryFrame.setVisible(true);
    }

    public Optional<Repository> addRepository(String repositoryName, String vendor) {
        if(getRepositoriesNames().contains(repositoryName)) {
            JOptionPane.showMessageDialog(null, "Repository with this name already exist! \nChange name and then proceed.");
            logger.warning("Repository already exist!");
        } else {
            Repository repository = new Repository((repositoryName),
                                            Vendor.valueOf(vendor),
                                    (repositoryName) + "_" + vendor);
            repositories.add(repository);
            try {
                registerRepository();
                return Optional.of(repository);
            } catch (ParserConfigurationException | TransformerException parserConfigurationException) {
                logger.log(Level.WARNING, "ERROR: New repository not created!");
                parserConfigurationException.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public void deleteRepository(String name) {

    }

    private JScrollPane getRepoList(){
        JList<String> repoList = new JList(getRepositoriesNames().toArray());
        repoList.setFixedCellWidth(80);
        repoList.setFixedCellHeight(15);
        repoList.addListSelectionListener(e -> {
            currentRepository = of(repoList.getSelectedValue());
            if(currentRepository == null){
                logger.warning("Repository: " + repoList.getSelectedValue() + " is invalid!");
            } else {
                buttonConnect.setEnabled(true);
            }
        });
        repoList.setVisibleRowCount(5);

        JScrollPane listScroller = new JScrollPane(repoList);
        listScroller.setBounds(0,0,80, 80);
        listScroller.setBackground(Constants.MENU_COLOR);
        return listScroller;
    }

    private Repository of(String name) {
        return repositories.stream().filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Set<String> getRepositoriesNames() {
         return repositories.stream()
                 .map(Repository::getName)
                 .collect(Collectors.toSet());
    }

    public void registerRepository()
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("repositories");
        doc.appendChild(rootElement);

        Element repository;
        Element name;
        Element vendor;
        Element location;

        for(Repository repo: repositories){
            repository = doc.createElement("repository");
            rootElement.appendChild(repository);
            name = doc.createElement("name");
            name.setTextContent(repo.getName());
            repository.appendChild(name);

            vendor = doc.createElement("vendor");
            vendor.setTextContent(repo.getVendor().name());
            repository.appendChild(vendor);

            location = doc.createElement("location");
            location.setTextContent(repo.getLocation());
            repository.appendChild(location);

        }
        writeXml(doc, System.out);

    }

    private static void writeXml(Document doc, OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    private void readRepositories() throws IOException, ParserConfigurationException, SAXException {

        File file = new File(Constants.REPOSITORIES_PATH + "repositories.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("repository");
        String repositoryName;
        String repositoryVendor;
        String repositoryLocale;
        Vendor vendor;

        for(Node node: iterable(nodeList)) {
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element repo = (Element) node;
                repositoryName = repo.getElementsByTagName("name").item(0).getTextContent();
                repositoryVendor = repo.getElementsByTagName("vendor").item(0).getTextContent();
                repositoryLocale = repo.getElementsByTagName("location").item(0).getTextContent();

                vendor = Vendor.valueOf(repositoryVendor.toUpperCase());

                repositories.add(new Repository(repositoryName, vendor, repositoryLocale));
            }
        }
        String loggerString = repositories.stream()
                .map(Repository::toString)
                .collect(Collectors.joining());
        logger.info("Find following repositories: \n" + loggerString);
    }

    public static Iterable<Node> iterable(final NodeList nodeList) {
        return () -> new Iterator<Node>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            @Override
            public Node next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return nodeList.item(index++);
            }
        };
    }
}