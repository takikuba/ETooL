package etool.cdimc.repository;

import etool.cdimc.Constants;
import etool.cdimc.db.DbFile;
import etool.cdimc.models.Table;
import etool.cdimc.scenes.SceneManager;
import etool.cdimc.stream.DataColumnStream;
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
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RepositoryManager extends JPanel {
    private final Logger logger = Constants.logger();
    private final Set<Repository> repositories = new HashSet<>();
    private static Repository currentRepository = null;
    private final JButton buttonConnect;
    private RepositoryLoader repositoryLoader;
    private final JButton delete = new JButton("Delete");

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
        setLayout(null);
        setBounds(15, 0, 170, 235);

        JButton buttonNew = new JButton("+ New +");
        buttonNew.setBounds(35, 140, 100, 20);
        buttonNew.addActionListener(e -> addRepository());

        buttonConnect = new JButton("Connect");
        buttonConnect.setBounds(35, 170, 100, 20);
        buttonConnect.setEnabled(false);
        buttonConnect.addActionListener(e -> {
            SceneManager.repaintFrame();
            loadRepository(currentRepository);
        });

        delete.setEnabled(false);
        delete.setBackground(new Color(189, 101, 112));
        delete.setBounds(35, 200, 100, 20);
        delete.addActionListener(e -> {
            SceneManager.repaintFrame();
            deleteRepository();
        });

        add(getRepoList());
        add(buttonConnect);
        add(buttonNew);
        add(delete);
    }

    private void loadRepository(Repository repository) {
        repositoryLoader = null;
        repositoryLoader = new RepositoryLoader(repository);
        repositoryLoader.setBounds(10, 10, 100, 300);
        repositoryLoader.revalidate();
        SceneManager.addRepositoryLoader(repositoryLoader);
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
        JComboBox<Vendor> vendorBox = new JComboBox<>(Vendor.values());
        vendorBox.setBounds(42, 70, 100, 30);

        JButton connect = new JButton("Connect");
        connect.setBounds(42, 120, 100, 20);
        connect.addActionListener(e -> {
            addRepository(repositoryName.getText());
        });

        addRepositoryFrame.getContentPane().add(repositoryName);
        addRepositoryFrame.getContentPane().add(vendorBox);
        addRepositoryFrame.getContentPane().add(connect);
        addRepositoryFrame.setVisible(true);
    }

    public boolean addRepository(String repositoryName) {
        if(getRepositoriesNames().contains(repositoryName)) {
            JOptionPane.showMessageDialog(null, "Repository with this name already exist! \nChange name and then proceed.");
            logger.warning("Repository already exist!");
        } else {
            Repository repository = new Repository((repositoryName),
                                    (repositoryName));
            repositories.add(repository);
            try {
                registerRepository(repository);
                return true;
            } catch (ParserConfigurationException | TransformerException parserConfigurationException) {
                logger.log(Level.WARNING, "ERROR: New repository not created!");
                parserConfigurationException.printStackTrace();
            }
        }
        return false;
    }

    public void deleteRepository() {
        File repoFile = new File(Constants.REPOSITORIES_PATH + currentRepository.getLocation());
        boolean fDelete = repoFile.delete();
        if (fDelete) {
            logger.log(Level.INFO, "Repository deleted successfully");
        } else {
            logger.warning("Problem while deleting repository");
        }
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
                delete.setEnabled(true);
            }
        });
        repoList.setVisibleRowCount(5);

        JScrollPane listScroller = new JScrollPane(repoList);
        listScroller.setBounds(25, 25, 120, 100);
        listScroller.setBackground(Constants.MENU_COLOR);
        return listScroller;
    }

    private Repository of(String name) {
        return repositories.stream().filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Set<Repository> getRepositories(){
        return repositories;
    }

    public Set<String> getRepositoriesNames() {
         return getRepositories().stream()
                 .map(Repository::getName)
                 .collect(Collectors.toSet());
    }

    public void registerRepository(Repository folderRepository)
            throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("repositories");
        doc.appendChild(rootElement);

        Element repository;
        Element name;
        Element location;

        for(Repository repo: repositories){
            repository = doc.createElement("repository");
            rootElement.appendChild(repository);
            name = doc.createElement("name");
            name.setTextContent(repo.getName());
            repository.appendChild(name);

            location = doc.createElement("location");
            location.setTextContent(repo.getLocation());
            repository.appendChild(location);

        }
        try {
            writeXml(doc, new FileOutputStream(Constants.REPOSITORIES_PATH + "repositories.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addRepositoryHomeFolder(folderRepository);
    }

    public static void registerRepositoryTable(Repository repository, Table table, DataColumnStream data, Vendor vendor) {
        try(FileWriter fw = new FileWriter(Constants.REPOSITORIES_PATH + repository.getLocation() + "/tables.etl", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(table.getTableWriter());
            if(vendor.equals(Vendor.MYSQL)) return;
            loadRepositoryTable(repository, table, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRepositoryTable() {

    }

    public static void loadRepositoryTable(Repository repository, Table table, DataColumnStream data) throws IOException {
        try(FileWriter fw = new FileWriter(Constants.REPOSITORIES_PATH + repository.getLocation() + "/" + table.getLocation(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(DbFile.getOutputFormat(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRepositoryHomeFolder(Repository repository) {
        boolean c = new File(Constants.REPOSITORIES_PATH + repository.getLocation()).mkdirs();
        if(c){
            logger.info("Repository folder created successfully!");
        } else logger.warning("Problem with creating repository home folder.");
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
        String repositoryLocale;

        for(Node node: iterable(nodeList)) {
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element repo = (Element) node;
                repositoryName = repo.getElementsByTagName("name").item(0).getTextContent();
                repositoryLocale = repo.getElementsByTagName("location").item(0).getTextContent();

                repositories.add(new Repository(repositoryName, repositoryLocale));
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