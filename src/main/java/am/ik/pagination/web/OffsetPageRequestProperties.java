package am.ik.pagination.web;

public record OffsetPageRequestProperties(String pageParameterName, String sizeParameterName, int pageDefault,
										  int sizeDefault, int sizeMax) {
}
