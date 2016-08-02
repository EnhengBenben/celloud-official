package com.celloud.model.mongo;

public class RockyRecord {
	/**
	 * 突变基因
	 */
	private String gene;
	/**
	 * 碱基
	 */
	private String bases;
	/**
	 * 氨基酸
	 */
	private String acids;
	/**
	 * 核苷酸变异
	 */
	private String nucleotides;
	/**
	 * 染色体
	 */
	private String chromosome;
	/**
	 * 位置
	 */
	private String position;
	/**
	 * 临床意义
	 */
	private String significance;
	/**
	 * 说明
	 */
	private String description;

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

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

	public String getNucleotides() {
		return nucleotides;
	}

	public void setNucleotides(String nucleotides) {
		this.nucleotides = nucleotides;
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

	public String getSignificance() {
		return significance;
	}

	public void setSignificance(String significance) {
		this.significance = significance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
