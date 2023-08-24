package am.ik.pagination.web;

import java.util.function.Function;

import am.ik.pagination.CursorPageRequest;

public record CursorPageRequestProperties<T>(Function<String, T> paramToCursor, String cursorParameterName,
		String sizeParameterName, String navigationParameterName, int sizeDefault, int sizeMax,
		CursorPageRequest.Navigation navigationDefault) {

	public static <T> Builder<T> builder(Function<String, T> paramToCursor) {
		return new Builder<>(paramToCursor);
	}

	public static class Builder<T> {

		private final Function<String, T> paramToCursor;

		private String cursorParameterName = "cursor";

		private String sizeParameterName = "size";

		private String navigationParameterName = "navigation";

		private int sizeDefault = 20;

		private int sizeMax = 200;

		private CursorPageRequest.Navigation navigationDefault = CursorPageRequest.Navigation.NEXT;

		public Builder(Function<String, T> paramToCursor) {
			this.paramToCursor = paramToCursor;
		}

		public Builder<T> withCursorParameterName(String cursorParameterName) {
			this.cursorParameterName = cursorParameterName;
			return this;
		}

		public Builder<T> withSizeParameterName(String sizeParameterName) {
			this.sizeParameterName = sizeParameterName;
			return this;
		}

		public Builder<T> withSizeDefault(int sizeDefault) {
			this.sizeDefault = sizeDefault;
			return this;
		}

		public Builder<T> withSizeMax(int sizeMax) {
			this.sizeMax = sizeMax;
			return this;
		}

		public Builder<T> withNavigationParameterName(String navigationParameterName) {
			this.navigationParameterName = navigationParameterName;
			return this;
		}

		public Builder<T> withNavigationDefault(CursorPageRequest.Navigation navigationDefault) {
			this.navigationDefault = navigationDefault;
			return this;
		}

		public CursorPageRequestProperties<T> build() {
			return new CursorPageRequestProperties<>(paramToCursor, cursorParameterName, sizeParameterName,
					navigationParameterName, sizeDefault, sizeMax, navigationDefault);
		}

	}
}
