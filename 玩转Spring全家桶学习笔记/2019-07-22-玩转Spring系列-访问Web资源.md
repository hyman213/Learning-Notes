# 访问Web资源

## 通过RestTemplate访问Web资源

**通过RestTemplate访问Web资源**

- SpringBoot中没有自动配置RestTemplate
- SpringBoot提供了RestTemplateBuilder
  - RestTemplateBuilder.build()

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(100))
        .setReadTimeout(Duration.ofMillis(500))
        .requestFactory(this::requestFactory).build();
}

public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readMenu();
        Long id = orderCoffee();
        queryOrder(id);
    }
    
    private void queryOrder(Long id) {
        CoffeeOrder order = restTemplate.getForObject("http://localhost:8080/order/{id}", CoffeeOrder.class, id);
        log.info("Order: {}", order);
    }

    private Long orderCoffee() {
        // 自动创建t_order和t_order_coffee
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("Li Lei")
                .items(Arrays.asList("capuccino", "Americano"))// 在t_coffee中存在
                .build();
        RequestEntity<NewOrderRequest> requestEntity = RequestEntity
                .post(UriComponentsBuilder.fromUriString("http://localhost:8080/order/").build().toUri())
                .body(orderRequest);
        ResponseEntity<CoffeeOrder> responseEntity = restTemplate.exchange(requestEntity, CoffeeOrder.class);
        log.info("Create OrderCoffee: {}", responseEntity.getBody());
        Long id = responseEntity.getBody().getId();
        log.info("Order ID: {}", id);
        return id;
    }

    private void readMenu() {
        ParameterizedTypeReference<List<Coffee>> ptr = new ParameterizedTypeReference<List<Coffee>>() {
        };
        ResponseEntity<List<Coffee>> list = restTemplate.exchange("http://localhost:8080/coffee/", HttpMethod.GET, null, ptr);
        list.getBody().forEach(c -> log.info("coffee: {}", c));
    }
}
```

**GET请求**

- getForObject() / getForEntity()

**POST请求**

- postForObject() / postForEntity()

**PUT请求**

- put()

**DELETE请求**

- delete()

**构造URI**

- UriComponentsBuilder
- ServletUriComponentBuilder - 相对于当前请求
- MvcUriComponentBuilder - 指向Controller的URI

```java
URI url = UriComponentsBuilder.fromUriString("http://localhost:8080/coffee/{id}").build(1);
```



## RestTemplate支持的HTTP库

- ClientHttpRequestFactory
- SimpleClientHttpRequestFactory - 默认实现
- HttpComponentsClientHttpRequestFactory - Apache HttpComponents
- Netty4ClientHttpRequestFactory - Netty
- OkHttp3ClientHttpRequestFactory - OkHttp

**优化底层请求策略**

连接管理

- PoolingHttpClientConnectionManager
- KeepAlive策略

超时设置

- connectTimeout / readTimeout

SSL校验

- 证书检查策略

## 通过WebClient访问Web资源

一个以Reactive方式处理Http请求的非堵塞得客户端

[Spring - WebClient](https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/web-reactive.html#webflux-client)

> The `spring-webflux` module includes a reactive, non-blocking client for HTTP requests with a functional-style API client and Reactive Streams support. `WebClient` depends on a lower level HTTP client library to execute requests and that support is pluggable.
>
> `WebClient` uses the same [codecs](https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/web-reactive.html#webflux-codecs) as WebFlux server applications do, and shares a common base package, some common APIs, and infrastructure with the server [functional web framework](https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/web-reactive.html#webflux-fn). The API exposes Reactor `Flux` and `Mono` types, also see [Reactive Libraries](https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/web-reactive.html#webflux-reactive-libraries). By default it uses it uses [Reactor Netty](https://github.com/reactor/reactor-netty) as the HTTP client library but others can be plugged in through a custom `ClientHttpConnector`.
>
> By comparison to the [RestTemplate](https://docs.spring.io/spring/docs/5.0.9.RELEASE/spring-framework-reference/integration.html#rest-resttemplate), the `WebClient` is:
>
> - non-blocking, reactive, and supports higher concurrency with less hardware resources.
> - provides a functional API that takes advantage of Java 8 lambdas.
> - supports both synchronous and asynchronous scenarios.
> - supports streaming up or down from a server.
>
> The `RestTemplate` is not a good fit for use in non-blocking applications, and therefore Spring WebFlux application should always use the `WebClient`. The `WebClient` should also be preferred in Spring MVC, in most high concurrency scenarios, and for composing a sequence of remote, inter-dependent calls.

**支持的底层HTTP库**

- Reactor Netty - ReactorClientHttpConnector
- Jetty ReactiveStream HttpClient  - JettyClientHttpConnector

```java
@SpringBootApplication
@Slf4j
public class WebclientDemoApplication implements ApplicationRunner {
	@Autowired
	private WebClient webClient;

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebclientDemoApplication.class)
				.web(WebApplicationType.NONE)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
        // 创建WebClient:WebClient.create() / WebClient.build()
		return builder.baseUrl("http://localhost:8080").build();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		CountDownLatch cdl = new CountDownLatch(2);

        // 发起请求: get() / post() / put() / delete() / patch()
		webClient.get()
				.uri("/coffee/{id}", 1)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.retrieve() // 获得结果: retrieve() / exchange()
				.bodyToMono(Coffee.class) // 应答正文: bodyToMono() / bodyToFlux()
				.doOnError(t -> log.error("Error: ", t))
				.doFinally(s -> cdl.countDown())
				.subscribeOn(Schedulers.single())
				.subscribe(c -> log.info("Coffee 1: {}", c));

		Mono<Coffee> americano = Mono.just(
				Coffee.builder()
						.name("americano")
						.price(Money.of(CurrencyUnit.of("CNY"), 25.00))
						.build()
		);
		webClient.post()
				.uri("/coffee/")
				.body(americano, Coffee.class)
				.retrieve()
				.bodyToMono(Coffee.class)
				.doFinally(s -> cdl.countDown())
				.subscribeOn(Schedulers.single())
				.subscribe(c -> log.info("Coffee Created: {}", c));

		cdl.await();

		webClient.get()
				.uri("/coffee/")
				.retrieve()
				.bodyToFlux(Coffee.class)
				.toStream()
				.forEach(c -> log.info("Coffee in List: {}", c));
	}
}
```























