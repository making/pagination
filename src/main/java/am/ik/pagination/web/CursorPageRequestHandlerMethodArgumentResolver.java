package am.ik.pagination.web;

import java.util.function.Consumer;
import java.util.function.Function;

import am.ik.pagination.CursorPageRequest;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CursorPageRequestHandlerMethodArgumentResolver<T> implements HandlerMethodArgumentResolver {

	private final CursorPageRequestProperties<T> properties;

	public CursorPageRequestHandlerMethodArgumentResolver(CursorPageRequestProperties<T> properties) {
		this.properties = properties;
	}

	public CursorPageRequestHandlerMethodArgumentResolver(Function<String, T> paramToCursor,
			Consumer<CursorPageRequestProperties.Builder<T>> consumer) {
		final CursorPageRequestProperties.Builder<T> builder = CursorPageRequestProperties.builder(paramToCursor);
		consumer.accept(builder);
		this.properties = builder.build();
	}

	public CursorPageRequestHandlerMethodArgumentResolver(Function<String, T> paramToCursor) {
		this(paramToCursor, __ -> {
		});
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return CursorPageRequest.class.equals(parameter.getParameterType());
	}

	@Override
	@NonNull
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		int size = this.properties.sizeDefault();
		T cursor = null;
		CursorPageRequest.Navigation navigation = this.properties.navigationDefault();
		final String cursorParameter = webRequest.getParameter(this.properties.cursorParameterName());
		if (StringUtils.hasText(cursorParameter)) {
			cursor = this.properties.paramToCursor().apply(cursorParameter);
		}
		final String sizeParameter = webRequest.getParameter(this.properties.sizeParameterName());
		if (sizeParameter != null) {
			size = Integer.parseInt(sizeParameter);
		}
		final String navigationParameter = webRequest.getParameter(this.properties.navigationParameterName());
		if (navigationParameter != null) {
			navigation = CursorPageRequest.Navigation.valueOf(navigationParameter.toUpperCase());
		}
		return new CursorPageRequest<>(cursor, Math.max(1, Math.min(size, this.properties.sizeMax())), navigation);
	}

}