package org.sekon.ppin;

import static org.junit.Assert.*;

import java.util.ArrayList;


import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sekon.ppin.MongoWorker;
import org.sekon.ppin.Shard;

public class TestMongoWorker {
	private MongoWorker mongoWorker = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.mongoWorker = new MongoWorker();
	}

	@After
	public void tearDown() throws Exception {
		this.mongoWorker.dropSearchClusterDB();
	}

	@Test
	public void testAddShards() {
		this.mongoWorker.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16002", "br", 22, 32));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		assertEquals(4, this.mongoWorker.getShards().size());
	}
	
	@Test
	public void testDeleteShard() {
		this.mongoWorker.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16002", "br", 22, 32));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		Shard shard = this.mongoWorker.getShards().get(1);
		this.mongoWorker.deleteShard(shard);
		assertEquals(3, this.mongoWorker.getShards().size());
	}
	
	@Test
	public void testGetShardAttribute() {
		this.mongoWorker.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16002", "br", 22, 32));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		ArrayList<Shard> shards = this.mongoWorker.getShards();
		assertEquals("127.0.0.1:16001", shards.get(1).getAddress());
		assertEquals("br", shards.get(1).getCollection());
		assertEquals(15, shards.get(1).getDocsCount());
		assertEquals(15, shards.get(1).getMaxCount());
	}
	
	@Test
	public void testUpdateShard() {
		this.mongoWorker.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16002", "br", 22, 32));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		Shard shard = this.mongoWorker.getShards().get(1);
		shard.setDocsCount(5);
		this.mongoWorker.updateShard(shard);
		assertEquals(5, this.mongoWorker.getShards().get(1).getDocsCount());
	}
	@Test
	public void testGetShard() {
		this.mongoWorker.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16002", "br", 22, 32));
		this.mongoWorker.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		Shard shard = this.mongoWorker.getShards().get(1);
		Shard shard1 = this.mongoWorker.getShard(shard.getId());
		assertEquals("Поиск по существующему идентификатору", shard1.getId(), shard.getId());
		assertNull("Поиск по несуществующему идентификатору",this.mongoWorker.getShard(new ObjectId().toString()));
	}

	
}
