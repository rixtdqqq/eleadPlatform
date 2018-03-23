package com.elead.module;

public class TbSelecterInfo {
	private String text;
	private int defaultTvColor;
	private int checkedTvColor;
	private int defaultIcon;
	private int checkedIcon;

	/**
	 * @param text
	 * @param defaultTvColor
	 * @param checkedTvColor
	 * @param defaultIcon
	 * @param checkedIcon
	 */
	public TbSelecterInfo(String text, int defaultTvColor, int checkedTvColor,
			int defaultIcon, int checkedIcon) {
		this.text = text;
		this.defaultTvColor = defaultTvColor;
		this.checkedTvColor = checkedTvColor;
		this.defaultIcon = defaultIcon;
		this.checkedIcon = checkedIcon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDefaultTvColor() {
		return defaultTvColor;
	}

	public void setDefaultTvColor(int defaultTvColor) {
		this.defaultTvColor = defaultTvColor;
	}

	public int getCheckedTvColor() {
		return checkedTvColor;
	}

	public void setCheckedTvColor(int checkedTvColor) {
		this.checkedTvColor = checkedTvColor;
	}

	public int getDefaultIcon() {
		return defaultIcon;
	}

	public void setDefaultIcon(int defaultIcon) {
		this.defaultIcon = defaultIcon;
	}

	public int getCheckedIcon() {
		return checkedIcon;
	}

	public void setCheckedIcon(int checkedIcon) {
		this.checkedIcon = checkedIcon;
	}

}