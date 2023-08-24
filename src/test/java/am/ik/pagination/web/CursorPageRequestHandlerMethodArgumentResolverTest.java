package am.ik.pagination.web;

import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.Map;

import am.ik.pagination.CursorPageRequest;
import org.junit.jupiter.api.Test;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.assertj.core.api.Assertions.assertThat;

class CursorPageRequestHandlerMethodArgumentResolverTest {

	@Test
	@SuppressWarnings("unchecked")
	void resolveArgument_default() {
		CursorPageRequestHandlerMethodArgumentResolver<Instant> argumentResolver = new CursorPageRequestHandlerMethodArgumentResolver<>(
				Instant::parse);
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[] { NativeWebRequest.class }, (proxy, method, args) -> null);
		final CursorPageRequest<Instant> pageRequest = (CursorPageRequest<Instant>) argumentResolver
			.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.cursor()).isNull();
		assertThat(pageRequest.pageSize()).isEqualTo(20);
		assertThat(pageRequest.navigation()).isEqualTo(CursorPageRequest.Navigation.NEXT);
	}

	@Test
	@SuppressWarnings("unchecked")
	void resolveArgument_param() {
		CursorPageRequestHandlerMethodArgumentResolver<Instant> argumentResolver = new CursorPageRequestHandlerMethodArgumentResolver<>(
				Instant::parse);
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[] { NativeWebRequest.class }, (proxy, method, args) -> {
					if ("getParameter".equals(method.getName())) {
						return Map.of("size", "30", "cursor", "2023-08-24T00:00:00Z", "navigation", "previous")
							.get(args[0]);
					}
					return null;
				});
		final CursorPageRequest<Instant> pageRequest = (CursorPageRequest<Instant>) argumentResolver
			.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.cursor()).isEqualTo(Instant.parse("2023-08-24T00:00:00Z"));
		assertThat(pageRequest.pageSize()).isEqualTo(30);
		assertThat(pageRequest.navigation()).isEqualTo(CursorPageRequest.Navigation.PREVIOUS);
	}

}