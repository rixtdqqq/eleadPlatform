package com.elead.card.entity;

public class AnnularChartData implements Comparable<AnnularChartData> {

	/**
	 * 16进制ARGB色彩值
	 */
	private int color;
	private float progress;
	private String name;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AnnularChartData() {
	}

	public AnnularChartData(int color, float progress, String name,
							String description) {
		this.color = color;
		this.progress = progress;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	@Override
	public int compareTo(AnnularChartData arg0) {
		float a = arg0.getProgress();
		float b = getProgress();
		if (a > b) {
			return 1;
		} else if (a < b) {
			return -1;
		} else {
			return 0;
		}
	}
}
