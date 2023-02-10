package am.ik.pagination.web;

import java.lang.reflect.Proxy;
import java.util.Map;

import am.ik.pagination.OffsetPageRequest;
import org.junit.jupiter.api.Test;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.assertj.core.api.Assertions.assertThat;

class OffsetPageRequestHandlerMethodArgumentResolverTest {

	@Test
	void resolveArgument_default() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver();
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> null);
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(0);
		assertThat(pageRequest.pageSize()).isEqualTo(20);
	}

	@Test
	void resolveArgument_param() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver();
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> {
			if ("getParameter".equals(method.getName())) {
				return Map.of("size", "100", "page", "1").get(args[0]);
			}
			return null;
		});
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(1);
		assertThat(pageRequest.pageSize()).isEqualTo(100);
	}

	@Test
	void resolveArgument_overMaxSize() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver();
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> {
			if ("getParameter".equals(method.getName())) {
				return Map.of("size", "201").get(args[0]);
			}
			return null;
		});
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(0);
		assertThat(pageRequest.pageSize()).isEqualTo(200);
	}

	@Test
	void resolveArgument_underMinSize() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver();
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> {
			if ("getParameter".equals(method.getName())) {
				return Map.of("size", "0").get(args[0]);
			}
			return null;
		});
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(0);
		assertThat(pageRequest.pageSize()).isEqualTo(1);
	}

	@Test
	void resolveArgument_underMinPage() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver();
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> {
			if ("getParameter".equals(method.getName())) {
				return Map.of("page", "-1").get(args[0]);
			}
			return null;
		});
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(0);
		assertThat(pageRequest.pageSize()).isEqualTo(20);
	}

	@Test
	void resolveArgument_customDefaultSize() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver(b -> b.withPageDefault(1).withSizeDefault(10));
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> null);
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(1);
		assertThat(pageRequest.pageSize()).isEqualTo(10);
	}

	@Test
	void resolveArgument_customParam() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver(b -> b.withPageParameterName("p").withSizeParameterName("s"));
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> {
			if ("getParameter".equals(method.getName())) {
				return Map.of("s", "100", "p", "1").get(args[0]);
			}
			return null;
		});
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(1);
		assertThat(pageRequest.pageSize()).isEqualTo(100);
	}

	@Test
	void resolveArgument_customMaxSize() {
		final OffsetPageRequestHandlerMethodArgumentResolver argumentResolver = new OffsetPageRequestHandlerMethodArgumentResolver(b -> b.withSizeMax(1000));
		final NativeWebRequest webRequest = (NativeWebRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {NativeWebRequest.class}, (proxy, method, args) -> {
			if ("getParameter".equals(method.getName())) {
				return Map.of("size", "1001").get(args[0]);
			}
			return null;
		});
		final OffsetPageRequest pageRequest = (OffsetPageRequest) argumentResolver.resolveArgument(null, new ModelAndViewContainer(), webRequest, (r, target, objectName) -> null);
		assertThat(pageRequest.pageNumber()).isEqualTo(0);
		assertThat(pageRequest.pageSize()).isEqualTo(1000);
	}
}