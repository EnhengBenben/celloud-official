package com.celloud.model.mongo;

public class RockyRecord {
	/**
	 * 碱基
	 */
	private String bases;
	/**
	 * 氨基酸
	 */
	private String acids;
	/**
	 * 突变基因
	 */
	private String gene;
	/**
	 * 说明
	 */
	private String description;
	/**
	 * 染色体
	 */
	private String chromosome;
	/**
	 * 位置
	 */
	private String position;

	public String getBases() {
		return bases;
	}

	public void setBases(String bases) {
		this.bases = bases;
	}

	public String getAcids() {
		return acids;
	}

	public void setAcids(String acids) {
		this.acids = acids;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
