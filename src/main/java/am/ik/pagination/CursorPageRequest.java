package am.ik.pagination;

import java.util.Optional;

public record CursorPageRequest<C> (C cursor, int pageSize, Navigation navigation) {

	public Optional<C> cursorOptional() {
		return Optional.ofNullable(this.cursor);
	}

	public enum Navigation {

		NEXT, PREVIOUS;

		public boolean isNext() {
			return this == NEXT;
		}

	}
}
