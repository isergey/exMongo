package com.sekon.ppin;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sekon.ppin.exceptions.NoCandidatesException;

public class TestIndexPlanner {
	private IndexPlanner indexPlanner = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.indexPlanner = new IndexPlanner();
	}

	@After
	public void tearDown() throws Exception {
		this.indexPlanner = null;
	}

	@Test
	public void testGetCandidate() {
		this.indexPlanner.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.indexPlanner.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.indexPlanner.addShard(new Shard("127.0.0.1:16002", "br", 22, 32));
		this.indexPlanner.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		Shard candidate = null;
		try {
			candidate = indexPlanner.getCandidate();
		} catch (NoCandidatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(candidate.getDocsCount(), 22);
	}
	
	@Test(expected=NoCandidatesException.class)
	public void testGetCandidateNoCandidatesException() throws NoCandidatesException {
		this.indexPlanner.addShard(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.indexPlanner.addShard(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.indexPlanner.addShard(new Shard("127.0.0.1:16002", "br", 32, 32));
		this.indexPlanner.addShard(new Shard("127.0.0.1:16003", "br", 32, 32));
		indexPlanner.getCandidate();
	}

}
