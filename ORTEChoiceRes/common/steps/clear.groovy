import com.mongodb.DB;
import com.mongodb.Mongo;

Mongo mongo = new Mongo(mongodbIP, 27017);
DB db = mongo.getDB("supplierprofile");

//Delete collection
db.getCollection("channel").drop();
db.getCollection("cooperatorMapping").drop();
db.getCollection("derbyMapping").drop();
db.getCollection("hotel").drop();
db.getCollection("channelHotelSetting").drop();
