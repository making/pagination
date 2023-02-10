package am.ik.pagination.web;

public class OffsetPageRequestPropertiesBuilder {
	private String pageParameterName = "page";

	private String sizeParameterName = "size";

	private int pageDefault = 0;

	private int sizeDefault = 20;

	private int sizeMax = 200;

	public OffsetPageRequestPropertiesBuilder withPageParameterName(String pageParameterName) {
		this.pageParameterName = pageParameterName;
		return this;
	}

	public OffsetPageRequestPropertiesBuilder withSizeParameterName(String sizeParameterName) {
		this.sizeParameterName = sizeParameterName;
		return this;
	}

	public OffsetPageRequestPropertiesBuilder withPageDefault(int pageDefault) {
		this.pageDefault = pageDefault;
		return this;
	}

	public OffsetPageRequestPropertiesBuilder withSizeDefault(int sizeDefault) {
		this.sizeDefault = sizeDefault;
		return this;
	}

	public OffsetPageRequestPropertiesBuilder withSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
		return this;
	}

	public OffsetPageRequestProperties build() {
		return new OffsetPageRequestProperties(pageParameterName, sizeParameterName, pageDefault, sizeDefault, sizeMax);
	}
}