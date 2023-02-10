package am.ik.pagination.web;

public record OffsetPageRequestProperties(String pageParameterName, String sizeParameterName, int pageDefault,
										  int sizeDefault, int sizeMax) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String pageParameterName = "page";

		private String sizeParameterName = "size";

		private int pageDefault = 0;

		private int sizeDefault = 20;

		private int sizeMax = 200;

		public Builder withPageParameterName(String pageParameterName) {
			this.pageParameterName = pageParameterName;
			return this;
		}

		public Builder withSizeParameterName(String sizeParameterName) {
			this.sizeParameterName = sizeParameterName;
			return this;
		}

		public Builder withPageDefault(int pageDefault) {
			this.pageDefault = pageDefault;
			return this;
		}

		public Builder withSizeDefault(int sizeDefault) {
			this.sizeDefault = sizeDefault;
			return this;
		}

		public Builder withSizeMax(int sizeMax) {
			this.sizeMax = sizeMax;
			return this;
		}

		public OffsetPageRequestProperties build() {
			return new OffsetPageRequestProperties(pageParameterName, sizeParameterName, pageDefault, sizeDefault, sizeMax);
		}
	}
}
