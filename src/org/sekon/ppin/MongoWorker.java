package org.sekon.ppin;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.mongodb.Mongo;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.WriteConcern;


public class MongoWorker {
	private static Mongo connection = null;
	private String searchClusterDBName = "searchCluster";
	private String searchShardsCollectionName = "searchShards";
	
	public MongoWorker(String address, int port) throws UnknownHostException{
		if (MongoWorker.connection == null){
			MongoWorker.connection = new Mongo(address , port );
		}
	}
	
	public MongoWorker() throws UnknownHostException{
		this("localhost", 27017);
	}
	
	/**
	 * Добавление новой шарды
	 * @param shard
	 */
	public void addShard(Shard shard){
		DBCollection searchShards = MongoWorker.connection.getDB(this.searchClusterDBName).getCollection(this.searchShardsCollectionName);
		
		searchShards.insert(MongoWorker.shardToMongoObject(shard));
		MongoWorker.connection.setWriteConcern(WriteConcern.SAFE);
	}
	
	/**
	 * Извлекает информацию о шарде по идентификатору
	 * @param id
	 * @return
	 */
	public Shard getShard(String id){
		DBCollection searchShards = MongoWorker.connection.getDB(this.searchClusterDBName).getCollection(this.searchShardsCollectionName);
		BasicDBObject criteria = new BasicDBObject();
		criteria.put("_id", new ObjectId(id));
		DBObject mongoShard = searchShards.findOne(criteria);
		if (mongoShard == null){
			return null;
		}
		return MongoWorker.mongoObjectToShard(mongoShard);
	}
	
	/**
	 * Обновление шарды
	 * @param shard
	 */
	public void updateShard(Shard shard){
		DBCollection searchShards = MongoWorker.connection.getDB(this.searchClusterDBName).getCollection(this.searchShardsCollectionName);
		searchShards.save(MongoWorker.shardToMongoObject(shard), WriteConcern.SAFE); 
	}
	
	/**
	 * Удаление шарды
	 * @param shard
	 */
	public void deleteShard(Shard shard){
		DBCollection searchShards = MongoWorker.connection.getDB(this.searchClusterDBName).getCollection(this.searchShardsCollectionName);
		searchShards.remove(MongoWorker.shardToMongoObject(shard), WriteConcern.SAFE); 
	}
	
	/**
	 * Извлекает информацию о шардах
	 * @return Список шард
	 */
	public ArrayList<Shard> getShards(){
		DBCollection searchShards = MongoWorker.connection.getDB(this.searchClusterDBName).getCollection(this.searchShardsCollectionName);
		DBCursor cursor = searchShards.find();
		ArrayList<Shard> shards = new ArrayList<Shard>();
        try {
            while(cursor.hasNext()) {
            	shards.add(MongoWorker.mongoObjectToShard(cursor.next()));
            }
        } finally {
            cursor.close();
        }
		return shards;
	}
	
	/**
	 * Удаляет базу, содержащую коллекции с информацие о поисковом кластере
	 */
	public void dropSearchClusterDB(){
		MongoWorker.connection.dropDatabase(this.searchClusterDBName);
	}
	

	private static BasicDBObject shardToMongoObject(Shard shard){
		BasicDBObject mongoShard = new BasicDBObject();
		if (shard.getId() != null){
			mongoShard.put("_id", new ObjectId(shard.getId()));
		}
		mongoShard.put("address", shard.getAddress());
		mongoShard.put("collection", shard.getCollection());
		mongoShard.put("docsCount", shard.getDocsCount());
		mongoShard.put("maxCount", shard.getMaxCount());
		return mongoShard;
	}

	private static Shard mongoObjectToShard(DBObject mongoShard){
		ObjectId id = (ObjectId)mongoShard.get( "_id" );
		Shard shard =  new Shard(
				(String)mongoShard.get("address"), 
				(String)mongoShard.get("collection"),
				(long)mongoShard.get("docsCount"), 
				(long)mongoShard.get("maxCount")
		);
		shard.setId(id.toString());
		return shard;
	}
	

}
