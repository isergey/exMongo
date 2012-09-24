package com.sekon.ppin;

import java.util.ArrayList;

import com.sekon.ppin.exceptions.NoCandidatesException;
/**
 * Планировщик индексации документов. 
 *
 */
public class IndexPlanner {
	private ArrayList<Shard> shards = null;
	
	public IndexPlanner(){
		this.shards = new ArrayList<Shard>();
	}
	
	public void addShard(Shard shard){
		this.shards.add(shard);
	}
	/**
	 * @return возвращает объект шарды-кандидаты, для отправки ей документа на индексацию
	 * @throws NoCandidatesException возникает при отсутвии кандидата
	 */
	public Shard getCandidate() throws NoCandidatesException{
		Shard candidate = null;
		for (Shard shard: this.shards){
			if (candidate == null){
				candidate = shard;
				continue;
			}
			if (candidate.getDocsRemains() < 1 || candidate.getDocsCount() > shard.getDocsCount()){
				candidate = shard;
			}
		}
		
		if (candidate == null || candidate.getDocsRemains() < 1){
			throw new NoCandidatesException("No more candidates for index documents");
		}
		return candidate;
	}
	
	public void test(){
		this.shards.add(new Shard("127.0.0.1:16000", "br", 25, 25));
		this.shards.add(new Shard("127.0.0.1:16001", "br", 15, 15));
		this.shards.add(new Shard("127.0.0.1:16002", "br", 32, 32));
		this.shards.add(new Shard("127.0.0.1:16003", "br", 32, 32));
		
		Shard candidate = null;
		try {
			candidate = this.getCandidate();
		} catch (NoCandidatesException e) {
			e.printStackTrace();
		}
		System.out.println(candidate);
	}
}
