package am.ik.pagination.web;

import java.util.function.Consumer;

import am.ik.pagination.OffsetPageRequest;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OffsetPageRequestHandlerMethodArgumentResolver
		implements HandlerMethodArgumentResolver {
	private final OffsetPageRequestProperties properties;

	public OffsetPageRequestHandlerMethodArgumentResolver(OffsetPageRequestProperties properties) {
		this.properties = properties;
	}

	public OffsetPageRequestHandlerMethodArgumentResolver(Consumer<OffsetPageRequestProperties.Builder> consumer) {
		final OffsetPageRequestProperties.Builder builder = OffsetPageRequestProperties.builder();
		consumer.accept(builder);
		this.properties = builder.build();
	}

	public OffsetPageRequestHandlerMethodArgumentResolver() {
		this(__ -> {
		});
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return OffsetPageRequest.class.equals(parameter.getParameterType());
	}

	@Override
	@NonNull
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) {
		int page = this.properties.pageDefault();
		int size = this.properties.sizeDefault();
		final String pageParameter = webRequest.getParameter(this.properties.pageParameterName());
		if (pageParameter != null) {
			page = Integer.parseInt(pageParameter);
		}
		final String sizeParameter = webRequest.getParameter(this.properties.sizeParameterName());
		if (sizeParameter != null) {
			size = Integer.parseInt(sizeParameter);
		}
		return new OffsetPageRequest(Math.max(0, page), Math.max(1, Math.min(size, this.properties.sizeMax())));
	}
}