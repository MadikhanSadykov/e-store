import com.madikhan.estore.model.Language;
import com.madikhan.estore.model.Status;
import static com.madikhan.estore.constants.DBParameterConstants.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

public class TestDataGenerator {

    private static final ResourceBundle resource = ResourceBundle.getBundle("ConnectionPool");

    private static final String JDBC_URL = resource.getString(URL_KEY);
    private static final String JDBC_USERNAME = resource.getString(USERNAME_KEY);
    private static final String JDBC_PASSWORD = resource.getString(PASSWORD_KEY);

    private static final String IMG_PATH = "external/test-data/";
    private static final String MEDIA_PATH = "src/main/webapp/media/";

    private static final boolean useImageLinkInsteadOfCopy = true;

    static final String[] E_BOOK_PRODUCER = { "PocketBook", "Amazon", "AirBook", "EvroMedia" };
    static final String[] MP3_PLAYER_PRODUCER = { "Apple", "FiiO", "Jeka", "Sony", "Transcend" };
    static final String[] PHONE_PRODUCER = { "Nokia", "LG", "Gigabyte", "Samsung", "Philips", "Prestigio" };
    static final String[] SMARTPHONE_PRODUCER = { "Apple", "Nokia", "Prestigio", "Lenovo", "Sony", "Samsung", "LG", "Huawei", "HTC", "Fly" };
    static final String[] SMARTWATCH_PRODUCER = { "Apple", "AirOn", "FixiTime", "Samsung" };
    static final String[] LAPTOP_PRODUCER = { "Acer", "Asus", "Apple", "Dell", "HP", "Lenovo" };
    static final String[] TABLET_PRODUCER = { "Acer", "Asus", "Apple", "Dell", "Prestigio", "Lenovo", "Sony", "Samsung", "PocketBook" };

    static final String[] E_BOOK_DESC = { "Monitor diagonal {6,12,1}\"", "[Matrix type: E lnk;Matrix type: E lnk Pearl;Matrix type: E lnk Carta]",
            "[Resolution: 800x600;Resolution: 1600x1200;Resolution: 1024x758;Resolution: 1200x800;Resolution: 800x600]", "Memory {4,12,1} GB", "Weight {155,200,5} g" };
    static final String[] E_BOOK_DESC_OPTIONS = {};

    static final String[] MP3_PLAYER_DESC = { "Memory {4,20,4} Gb", "Weight {20,100,3} g", "[Silver;Blue;Orange;White;Black]", "MP3" };
    static final String[] MP3_PLAYER_DESC_OPTIONS = { "WAV", "OGG", "WMA", "MPEG-4", "AMV", "AVI", "SD slot", "FM receiver", "Dictophone", "USB", "Bluetooth" };

    static final String[] PHONE_DESC = { "Monitor diagonal {1.8,3,0.2}\"", "[Camera: 1Mp;Camera: 1.8Mp;Camera: 2.2Mp;Camera: 2.6Mp]", "RAM {16,64,16} Mb", "{1000,2000,100} mA/h",
            "[Silver;Blue;Orange;White;Black]", "Weight {60,400,20} g" };
    static final String[] PHONE_DESC_OPTIONS = { "2 Sim cards", "Dictophone", "USB", "Bluetooth", "SD" };

    static final String[] SMARTPHONE_DESC = { "Monitor diagonal {3.2,6,0.6}\"", "[Camera: 2.2Mp;Camera: 3.2Mp;Camera: 4.0Mp;Camera: 3.0Mp]", "[RAM: 512 Mb;RAM: 1 Gb;RAM: 2 Gb;RAM: 4 Gb]",
            "[Silver;Blue;Orange;White;Black]", "{1000,2000,100} mA/h", "Weight {320,800,40} g" };
    static final String[] SMARTPHONE_DESC_OPTIONS = { "2 Sim cards", "FM receiver", "Dictophone", "USB", "Bluetooth", "WiFi", "GPS", "3G" };

    static final String[] SMARTWATCH_DESC = { "Monitor diagonal {1.1,1.8,0.1}\"", "[RAM: 512 Mb;RAM: 256 Mb;RAM: 512 Mb;RAM: 1 Gb]", "[Silver;Blue;Orange;White;Black]", "{1000,2000,100} mA/h",
            "Weight {60,300,40} g" };
    static final String[] SMARTWATCH_DESC_OPTIONS = { "Dictophone", "USB", "Bluetooth", "WiFi", "GPS" };

    static final String[] LAPTOP_DESC = { "Monitor {11,23,1}\"", "[Intel Core I7 ({2.0, 3.2, 0.2} GHz);Intel Core I5 ({2.0, 2.8, 0.2} GHz);Intel Core I3 ({1.6, 2.6, 0.2} GHz)]", "RAM {1,16,1} GB",
            "HDD {60,800,10} GB", "Weight {2.4, 6.6, 0.2} kg" };
    static final String[] LAPTOP_DESC_OPTIONS = { "Intel HD Graphics", "DVD+/-RW", "LAN", "WiFi", "Bluetooth", "Webcam", "USB", "HDMI" };

    static final String[] TABLET_DESC = { "Monitor {7,10,1}\"", "RAM {1,4,1} GB", "HDD {60,800,10} GB", "[Silver;Blue;Orange;White;Black]", "Weight {60,300,40} g" };
    static final String[] TABLET_DESC_OPTIONS = { "3G", "WiFi", "Bluetooth", "Webcam", "USB", "HDMI", "GPS" };

    public static void main(String[] args) throws Exception {
        List<Category> categories = loadCategories();
        List<Producer> producers = getProducers(categories);
        clearMediaDir();
        try (Connection c = DriverManager.getConnection(JDBC_URL)) {
            c.setAutoCommit(false);
            clearDb(c);


            List<Language> languages = new LinkedList<>();
            languages.add(new Language("English", "en"));
            languages.add(new Language("Русский", "ru"));
            languages.add(new Language("Franche", "fr"));
            insertLanguages(c, languages);

            List<Status> statuses = new ArrayList<>();

            Status statusProcessingEn = new Status("Processing", 1);
            statusProcessingEn.setId(1);
            statuses.add(statusProcessingEn);

            Status statusCompletedEn = new Status("Completed", 1);
            statusCompletedEn.setId(2);
            statuses.add(statusCompletedEn);

            Status statusCanceledEn = new Status("Canceled", 1);
            statusCanceledEn.setId(3);
            statuses.add(statusCanceledEn);

            Status statusProcessingRu = new Status("Обработка", 2);
            statusProcessingRu.setId(1);
            statuses.add(statusProcessingRu);

            Status statusCompletedRu = new Status("Завершен", 2);
            statusCompletedRu.setId(2);
            statuses.add(statusCompletedRu);

            Status statusCanceledRu = new Status("Отменен", 2);
            statusCanceledRu.setId(3);
            statuses.add(statusCanceledRu);

            insertStatus(c, statuses);

            Map<String, Integer> producerIdMap = insertProducers(c, producers);
            Map<String, Integer> categoryIdMap = insertCategories(c, categories, 1);
            insertProducts(c, categories, categoryIdMap, producerIdMap);






            List<Category> ruLangCategories = categories;
            Map<String, String> englishRussianCategoryName = new LinkedHashMap<>();
            englishRussianCategoryName.put("E-book", "Электронная книга");
            englishRussianCategoryName.put("Laptop", "Ноутбук");
            englishRussianCategoryName.put("Mp3-player", "Mp3-плеер");
            englishRussianCategoryName.put("Phone", "Телефон");
            englishRussianCategoryName.put("SmartPhone", "Смартфон");
            englishRussianCategoryName.put("SmartWatch", "Смартчасы");
            englishRussianCategoryName.put("Tablet", "Планшет");
            List<String> enCategories = new LinkedList<>();
            enCategories.add("E-book");
            enCategories.add("Laptop");
            enCategories.add("Mp3-player");
            enCategories.add("Phone");
            enCategories.add("SmartPhone");
            enCategories.add("SmartWatch");
            enCategories.add("Tablet");
            int i = 0;
            for (Category category : ruLangCategories) {
                category.setEnName(enCategories.get(i++));
                for (String key : englishRussianCategoryName.keySet()){
                    if (key.equals(category.getEnName())){
                        category.setName(englishRussianCategoryName.get(key));
                    }
                }
            }
            insertCategories(c, ruLangCategories, 2);


            c.commit();
        } catch (SQLException e) {
            if (e.getNextException() != null) {
                e.getNextException().printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
        System.out.println("Completed");
    }

    private static List<Category> loadCategories() {
        List<Category> list = new ArrayList<>();
        File imgDir = new File(IMG_PATH);
        if (!imgDir.exists() || !imgDir.isDirectory()) {
            throw new IllegalArgumentException("Directory " + imgDir.getAbsolutePath() + " not found or not directory!");
        }
        for (File file : imgDir.listFiles()) {
            list.add(new Category(file.getName().replace(".jpg", ""), file));
        }
        Collections.sort(list);
        return list;
    }

    private static List<Producer> getProducers(List<Category> categories) {
        Map<String, Producer> map = new HashMap<>();
        for (Category category : categories) {
            for (Producer producer : category.producers) {
                Producer p = map.get(producer.name);
                if (p == null) {
                    map.put(producer.name, producer);
                } else {
                    map.put(producer.name, new Producer(p.name, p.productCount + producer.productCount));
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    private static void clearDb(Connection c) throws SQLException {
        try (Statement st = c.createStatement()) {
            st.executeUpdate("truncate table order_item RESTART IDENTITY cascade ");
            st.executeUpdate("truncate table \"order\" RESTART IDENTITY cascade");
            st.executeUpdate("truncate table \"user\" RESTART IDENTITY cascade");
            st.executeUpdate("truncate table product RESTART IDENTITY cascade");
            st.executeUpdate("truncate table category RESTART IDENTITY cascade");
            st.executeUpdate("truncate table producer RESTART IDENTITY cascade");
            st.executeUpdate("truncate table language RESTART IDENTITY cascade");
            st.executeUpdate("truncate table status RESTART IDENTITY cascade");
        }
        System.out.println("Db cleared");
    }

    private static void clearMediaDir() {
        if (!useImageLinkInsteadOfCopy) {
            File dir = new File(MEDIA_PATH);
            if (!dir.exists() || !dir.isDirectory()) {
                throw new IllegalArgumentException("Directory " + dir.getAbsolutePath() + " not found or not directory!");
            }
            int count = 0;
            for (File file : dir.listFiles()) {
                if (file.delete()) {
                    count++;
                }
            }
            System.out.println("Removed " + count + " image files");
        }
    }

    private static Map<String, Integer> insertProducers(Connection c, List<Producer> producers) throws Exception {
        Map<String, Integer> idMap = new HashMap<>();
        int i = 1;
        try (PreparedStatement ps = c.prepareStatement("insert into producer(name,product_count) values (?,?)")) {
            for (Producer producer : producers) {
                idMap.put(producer.name, i++);
                ps.setString(1, producer.name);
                ps.setInt(2, producer.productCount);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println("Inserted " + producers.size() + " producers");
        return idMap;
    }

    private static void insertStatus(Connection c, List<Status> statuses) throws Exception {
        int i = 1;
        try (PreparedStatement ps = c.prepareStatement("insert into status(id,name, id_language) values (?,?,?)")) {
            for (Status status : statuses) {
                ps.setInt(1, status.getId());
                ps.setString(2, status.getName());
                ps.setInt(3, status.getIdLanguage());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println("Inserted statuses");

    }

    private static void insertLanguages(Connection c, List<Language> languages) throws Exception {
        int i = 1;
        try (PreparedStatement ps = c.prepareStatement("insert into language(name,short_name) values (?,?)")) {
            for (Language language : languages) {
                ps.setString(1, language.getName());
                ps.setString(2, language.getShortName());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println("Inserted languages");

    }

    private static Map<String, Integer> insertCategories(Connection c, List<Category> categories, Integer languageID) throws Exception {
        Map<String, Integer> idMap = new HashMap<>();
        int i = 1;
        try (PreparedStatement ps = c.prepareStatement("insert into category(id, name,url,product_count,id_language) values (?,?,?,?,?)")) {
            for (Category category : categories) {
                idMap.put(category.name, i);
                ps.setLong(1, i);
                ps.setString(2, capitalize(category.name));

                if (languageID == 2) {
                    ps.setString(3, "/" + category.enName.toLowerCase().trim());
                } else {
                    ps.setString(3, "/" + category.name.toLowerCase().trim());
                }
                ps.setInt(4, category.getProductCount());
                ps.setInt(5, languageID);
                i++;
                ps.addBatch();
            }
            ps.executeBatch();
        }
        i = 0;
        System.out.println("Inserted " + categories.size() + " categories");
        return idMap;
    }

    private static String capitalize(String name) {
        if (name == null) {
            return null;
        } else if (name.length() == 1) {
            return name.toUpperCase();
        } else {
            return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        }
    }

    private static void insertProducts(Connection c, List<Category> categories, Map<String, Integer> categoryIdMap, Map<String, Integer> producerIdMap) throws SQLException, IOException {
        List<Product> products = generateProducts(categories, categoryIdMap, producerIdMap);
        try (PreparedStatement ps = c.prepareStatement("insert into product(name,description,image_link,price,id_category,id_producer) values (?,?,?,?,?,?)")) {
            for (Product product : products) {
                ps.setString(1, product.name);
                ps.setString(2, generateProductDescription(product.category));
                ps.setString(3, product.imageLink);
                ps.setInt(4, RANDOM.nextInt(300) * 10);
                ps.setInt(5, product.idCategory);
                ps.setInt(6, product.idProducer);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        System.out.println("Inserted " + products.size() + " products");
    }

    private static List<Product> generateProducts(List<Category> categories, Map<String, Integer> categoryIdMap, Map<String, Integer> producerIdMap) throws IOException {
        List<Product> products = new ArrayList<>();
        for (Category category : categories) {
            for (Producer producer : category.producers) {
                int idProducer = producerIdMap.get(producer.name);
                int idCategory = categoryIdMap.get(category.name);
                for (int i = 0; i < producer.productCount; i++) {
                    String name = generateProductName(producer.name);
                    String imageLink = getProductImageLink(category);
                    products.add(new Product(category.name, idCategory, idProducer, name, imageLink));
                }
            }
        }
        Collections.shuffle(products);
        return products;
    }

    private static String getProductImageLink(Category category) throws IOException {
        String productImageName = getImageFileName(category);
        File dest = new File(MEDIA_PATH + productImageName);
        if (!dest.exists()) {
            Files.copy(Paths.get(category.imageFile.toURI()), Paths.get(dest.toURI()));
        }
        return "/media/" + productImageName;
    }

    private static String getImageFileName(Category category) {
        if (useImageLinkInsteadOfCopy) {
            StringBuilder fileName = new StringBuilder();
            for (int i = 0; i < category.name.length(); i++) {
                fileName.append(Integer.toHexString((int) category.name.charAt(i)));
            }
            return fileName.toString() + ".jpg";
        } else {
            return UUID.randomUUID().toString().replace("-", "") + ".jpg";
        }
    }

    private static String generateProductName(String producerName) {
        StringBuilder productCode = new StringBuilder();
        for (int i = RANDOM.nextInt(2) + 1; i >= 0; i--) {
            productCode.append((char) ('A' + RANDOM.nextInt(22)));
        }
        for (int i = RANDOM.nextInt(5) + 3; i >= 0; i--) {
            productCode.append(String.valueOf(RANDOM.nextInt(10)));
        }
        return producerName + " " + productCode;
    }

    private static String generateProductDescription(String categoryName) {
        String staticFieldDescName = categoryName.toUpperCase().replace("-", "_") + "_DESC";
        String staticFieldDescOptionsName = categoryName.toUpperCase().replace("-", "_") + "_DESC_OPTIONS";
        try {
            String[] desc = {};
            String[] options = {};
            Field fieldDesc = TestDataGenerator.class.getDeclaredField(staticFieldDescName);
            desc = (String[]) fieldDesc.get(TestDataGenerator.class);
            try {
                Field fieldDescOptions = TestDataGenerator.class.getDeclaredField(staticFieldDescOptionsName);
                options = (String[]) fieldDescOptions.get(TestDataGenerator.class);
            } catch (NoSuchFieldException e) {
                System.err.println(staticFieldDescName + "field not found.");
            }
            return new ProductDescriptionGenerator(desc, options).generateDesc();
        } catch (NoSuchFieldException e) {
            System.err.println(staticFieldDescName + " field not found for category: " + categoryName);
            return "";
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private static class ProductDescriptionGenerator {
        private final String[] patterns;
        private final String[] options;

        ProductDescriptionGenerator(String[] patterns, String[] options) {
            super();
            this.patterns = patterns;
            this.options = options;
        }

        String generateDesc() {
            StringBuilder result = new StringBuilder();
            for (String pattern : patterns) {
                pattern = handleChoice(pattern);
                String value = handleRandomVariable(pattern);
                result.append(value).append(" / ");
            }
            if (options.length > 0) {
                appendOptions(result, options);
            } else {
                result.deleteCharAt(result.length() - 1);
                result.deleteCharAt(result.length() - 1);
                result.deleteCharAt(result.length() - 1);
            }
            return result.toString();
        }

        private String handleChoice(String pattern) {
            if (pattern.startsWith("[") && pattern.endsWith("]")) {
                String[] variants = pattern.replace("[", "").replace("]", "").split(";");
                return variants[RANDOM.nextInt(variants.length)].trim();
            } else {
                return pattern;
            }
        }

        private String handleRandomVariable(String pattern) {
            int start = pattern.indexOf('{');
            int end = pattern.indexOf('}', start);
            if (start != -1 && end != -1) {
                String var = pattern.substring(start + 1, end).trim();
                String[] params = var.replace("{", "").replace("}", "").split(",");
                return pattern.replace("{" + var + "}", generateRandom(params));
            } else {
                return pattern;
            }
        }

        private String generateRandom(String[] params) {
            Set<Double> variants = new HashSet<>();
            double min = Double.parseDouble(params[0].trim());
            double max = Double.parseDouble(params[1].trim());
            double step = Double.parseDouble(params[2].trim());
            for (double v = min; v <= max; v += step) {
                variants.add(v);
            }
            variants.add(max);

            Double[] array = variants.toArray(new Double[] {});
            Double randomValue = array[RANDOM.nextInt(array.length)];
            return isDouble(params) ? new DecimalFormat("#0.0").format(randomValue) : String.valueOf(randomValue.intValue());
        }

        private boolean isDouble(String[] params) {
            for (String param : params) {
                if (param.contains(".")) {
                    return true;
                }
            }
            return false;
        }

        private void appendOptions(StringBuilder result, String[] options) {
            int count = RANDOM.nextInt(options.length);
            if (count < 3) {
                count = 3;
            }
            List<String> list = new ArrayList<>(Arrays.asList(options));
            Collections.shuffle(list);
            for (int i = 0; i < count && i < options.length; i++) {
                result.append(list.get(i).trim());
                if (i != count - 1) {
                    result.append(" / ");
                }
            }
        }
    }

    private static final Random RANDOM = new Random();


    private static class Category implements Comparable<Category> {
        String name;
        String enName;
        final File imageFile;
        final List<Producer> producers;
        Integer languageID = 1;

        Category(String name, File imageFile) {
            super();
            this.name = name;
            this.imageFile = imageFile;
            this.producers = Collections.unmodifiableList(createProducers());
        }

        private List<Producer> createProducers() {
            String staticFieldName = name.toUpperCase().replace("-", "_") + "_PRODUCER";
            try {
                Field field = TestDataGenerator.class.getDeclaredField(staticFieldName);
                String[] data = (String[]) field.get(TestDataGenerator.class);
                List<Producer> result = new ArrayList<>();
                for (String producer : data) {
                    result.add(new Producer(producer));
                }
                return result;
            } catch (NoSuchFieldException e) {
                System.err.println(staticFieldName + " field not found for category: " + name);
                return Collections.emptyList();
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        int getProductCount() {
            int res = 0;
            for (Producer p : producers) {
                res += p.productCount;
            }
            return res;
        }

        public void setLanguageID(Integer langID) {
            languageID = langID;
        }

        public String getName() {
            return name;
        }

        public void setName(String newName) {
            name = newName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        @Override
        public int compareTo(Category o) {
            return name.compareToIgnoreCase(o.name);
        }
    }


    private static class Producer {
        final String name;
        final int productCount;

        Producer(String name) {
            this(name, RANDOM.nextInt(35) + 5);
        }

        Producer(String name, int productCount) {
            super();
            this.name = name;
            this.productCount = productCount;
        }
    }


    private static class Product {
        final String name;
        final int idCategory;
        final String category;
        final int idProducer;
        final String imageLink;

        Product(String category, int idCategory, int idProducer, String name, String imageLink) {
            super();
            this.category = category;
            this.idCategory = idCategory;
            this.idProducer = idProducer;
            this.name = name;
            this.imageLink = imageLink;
        }
    }
}