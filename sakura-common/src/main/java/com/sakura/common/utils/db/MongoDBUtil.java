package com.sakura.common.utils.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class MongoDBUtil {
    private MongoClient mongoClient;
    private MongoDatabase database = null;

    public MongoDBUtil(String host, String port, String databaseName, String username, String password) {
        String connectionString = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + databaseName + "?authSource=admin";
        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();

            this.mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase(databaseName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MongoDB client", e);
        }
    }

    public MongoDBUtil(String url) {
        try {
            ConnectionString connString = new ConnectionString(url);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();
            this.mongoClient = MongoClients.create(settings);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MongoDB client", e);
        }
    }

    // 创建集合，如果集合不存在则创建
    public void createCollection(String collectionName) {
        List<String> collectionNames = new ArrayList<>();
        database.listCollectionNames().into(collectionNames);
        if (!collectionNames.contains(collectionName)) {
            database.createCollection(collectionName);
            log.info("Collection created: " + collectionName);
        } else {
            log.info("Collection already exists: " + collectionName);
        }
    }

    // 执行增删改查操作的方法
    public List<String> executeOperation(String operationType, String collectionName, Document filterDoc, Document updateDoc) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        switch (operationType) {
            case "CREATE":
                collection.insertOne(filterDoc); // 这里filterDoc作为要插入的文档
                return null;
            case "DELETE":
                collection.deleteOne(filterDoc).getDeletedCount();
                return null;
            case "UPDATE":
                collection.updateOne(filterDoc, new Document("$set", updateDoc));
                return null;
            case "SELECT":
                // 查询所有
                FindIterable<Document> foundDocuments;
                List<String> results = new ArrayList<>();
                if (filterDoc == null || filterDoc.isEmpty()) {
                    foundDocuments = collection.find();
                } else {
                    foundDocuments = collection.find(filterDoc);
                }
                for (Document doc : foundDocuments) {
//                    log.info(doc.toJson());
                    results.add(doc.toJson());
                }
                log.info("Count: " + results.size());
                log.info("Results: " + results);
                return results; // 返回查询结果
            default:
                throw new IllegalArgumentException("Unsupported operation type.");
        }
    }

    // 关闭连接
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        log.info("数据库关闭成功");
    }

    public enum OperationType {
        CREATE, DELETE, UPDATE, SELECT
    }

    public static void main(String[] args) {
        MongoDBUtil mongoDBUtil = new MongoDBUtil("172.19.1.250", "27017", "test", "root", "Ceshi123");
        // 创建集合
//        mongoDBUtil.createCollection("test");
//
//        // 插入数据
//        Document toInsert = new Document("name", "MongoDB")
//                .append("type", "database")
//                .append("count", 1)
//                .append("info", new Document("x", 203).append("y", 102));
//        Document doc = new Document("title", "MongoDB")
//                .append("description", "Musql is a RDBMS")
//                .append("by", "sql练习")
//                .append("url", "http://www.runoob.com")
//                .append("tages", Arrays.asList("mongodb", "database", "NoSQL"))
//                .append("likes", 100);
//        mongoDBUtil.executeOperation("CREATE", "test", toInsert, null);
//
//        // 删除数据
//        Document deleteFilter = new Document("name", "MongoDB");
//        mongoDBUtil.executeOperation(OperationType.DELETE, "test", deleteFilter, null);
//
//        // 更新数据
//        Document updateFilter = new Document("name", "MongoDB");
//        Document updateDoc = new Document("count", 2);
//        mongoDBUtil.executeOperation(OperationType.UPDATE, "yourCollectionName", updateFilter, updateDoc);

//         查询数据
        Document findFilter = new Document("name", "MongoDB");
        mongoDBUtil.executeOperation("SELECT", "test", null, null);
        // 关闭连接
        mongoDBUtil.close();
    }
}
