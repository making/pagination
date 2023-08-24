package am.ik.pagination;

import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record CursorPage<T, C>(List<T> content, int size, @JsonIgnore Function<T, C> toCursor, boolean hasPrevious,
		boolean hasNext) {

	public C tail() {
		if (this.content.isEmpty()) {
			return null;
		}
		return toCursor.apply(this.content.get(0));
	}

	public C head() {
		if (this.content.isEmpty()) {
			return null;
		}
		int size = this.content.size();
		return toCursor.apply(this.content.get(size - 1));
	}
}
