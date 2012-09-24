package com.sekon.ppin;
/**
 * Шарда поискового сервера.
 * 
 */
public class Shard {
	private String id = null;
	private String address = null;
	private String collection = null;
	private long docsCount = 0;
	private long maxDocsCount = 0;
	/**
	 * @param id идентификатор шарды
	 * @param address адрес поискового сервера шарды
	 * @param collection коллекция документов, которую обслуживает шарда
	 * @param docsCount текущее количество проиндексированных документов
	 * @param maxCount максимальное количество документов, которое может содержать шарда
	 */
	public Shard(String address, String collection, long docsCount, long maxCount){
		this.setAddress(address);
		this.setDocsCount(docsCount);
		this.setMaxCount(maxCount);
		this.setCollection(collection);
	}
	/**
	 * 
	 * @return разница между максимальным и текущим количествами документов (остаток).
	 */
	public long getDocsRemains(){
		return  this.getMaxCount() - this.getDocsCount();
	}
	
	public void setDocsCount(String id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	public long getDocsCount() {
		return this.docsCount;
	}

	public void setDocsCount(long docsCount) {
		this.docsCount = docsCount;
	}
	
	public long getMaxCount() {
		return this.maxDocsCount;
	}

	public void setMaxCount(long maxCount) {
		this.maxDocsCount = maxCount;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	public String toString(){
		return "Shard #" + this.id + ". Docs: " + this.getDocsCount() + ". Max: " + this.getMaxCount();
	}




}
