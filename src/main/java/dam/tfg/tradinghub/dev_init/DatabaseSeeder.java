package dam.tfg.tradinghub.dev_init;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Profile("dev")
@Component
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private MongoTemplate mongoTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public DatabaseSeeder(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
        log.info("===== INSERTANDO DATOS DE PRUEBA (dev profile) =====");

        log.info("Borrando datos de la BBDD...");
        mongoTemplate.dropCollection("users");
        mongoTemplate.dropCollection("products");
        mongoTemplate.dropCollection("categorias");
        mongoTemplate.dropCollection("chat_messages");
        mongoTemplate.dropCollection("trades");
        mongoTemplate.dropCollection("reports");
        mongoTemplate.dropCollection("fs.chunks");
        mongoTemplate.dropCollection("fs.files");

        // Inserta datos desde JSON
        try {
            log.info("Insertado de datos:");
            insertarJson("data/tradinghub.users.json", "users");
            insertarJson("data/tradinghub.categorias.json", "categorias");
            insertarJson("data/tradinghub.products.json", "products");
            insertarJson("data/tradinghub.trades.json", "trades");
            insertarJson("data/tradinghub.fs.files.json", "fs.files");
            insertarJson("data/tradinghub.fs.chunks.json", "fs.chunks");
            insertarJson("data/tradinghub.chat_messages.json","chat_messages");
            insertarJson("data/tradinghub.reports.json","reports");
            log.info("===== DATOS DE PRUEBA INSERTADOS =====");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void insertarJson(String jsonResource, String collectionName) throws IOException {
        InputStream is = new ClassPathResource(jsonResource).getInputStream();
        JsonNode arrayNode = mapper.readTree(is);
        for (JsonNode node : arrayNode) {
            mongoTemplate.insert(Document.parse(node.toString()), collectionName);
        }
        log.info("\t* Insertado datos en la colección: " + collectionName);
    }
}
