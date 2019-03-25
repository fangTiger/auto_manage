package com.xl.manage.bean.esBean;

/**
 * 索引参数模板对象
 * @Author:lww
 * @Date:17:46 2017/7/13
 */
public class IndexTempBean {
	private String index;//索引名称
	private Integer shards;//一个索引中含有的主分片(Primary Shard)的数量。在索引创建后这个值是不能被更改的。
	private Integer replicas;//每一个主分片关联的副本分片(Replica Shard)的数量。这个设置在任何时候都可以被修改。
	private String mappingBuilder;//索引mapping

	public IndexTempBean() {
	}

	public IndexTempBean(String index, Integer shards, Integer replicas, String mappingBuilder) {
		this.index = index;
		this.shards = shards;
		this.replicas = replicas;
		this.mappingBuilder = mappingBuilder;
	}

	@Override
	public String toString() {
		return "IndexTempBean{" +
				"index='" + index + '\'' +
				", shards=" + shards +
				", replicas=" + replicas +
				", mappingBuilder='" + mappingBuilder + '\'' +
				'}';
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Integer getShards() {
		return shards;
	}

	public void setShards(Integer shards) {
		this.shards = shards;
	}

	public Integer getReplicas() {
		return replicas;
	}

	public void setReplicas(Integer replicas) {
		this.replicas = replicas;
	}

	public String getMappingBuilder() {
		return mappingBuilder;
	}

	public void setMappingBuilder(String mappingBuilder) {
		this.mappingBuilder = mappingBuilder;
	}
}
