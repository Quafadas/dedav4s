// Browser APIs the bundles expect, which a bare GraalJS context doesn't provide. Loaded before the bundles: vega's
// loader captures `fetch` when its module initialises.
(function (g) {
  // A static render never needs to wait. Running callbacks later would need an event loop we don't have, and
  // ECharts' animation loop would reschedule itself forever, so timers are dropped.
  g.setTimeout = () => 0;
  g.clearTimeout = () => {};
  g.setInterval = () => 0;
  g.clearInterval = () => {};
  g.requestAnimationFrame = () => 0;
  g.cancelAnimationFrame = () => {};

  // Specs are plain JSON, so a JSON round trip is a faithful clone.
  g.structuredClone ??= (x) => (x === undefined ? undefined : JSON.parse(JSON.stringify(x)));

  // fetch backed by the JVM (`__dedavHttpGet`, bound by StaticRenderer), so specs with `data.url` render. Only
  // absolute http(s) URLs are fetched; anything else rejects and the static render is skipped.
  const response = (url, status, body) => ({
    url,
    status,
    ok: status >= 200 && status < 300,
    statusText: String(status),
    text: () => Promise.resolve(body),
    json: () => Promise.resolve(JSON.parse(body)),
  });
  g.fetch = (input) => {
    const url = String(input && input.url ? input.url : input);
    if (!/^https?:\/\//i.test(url)) return Promise.reject(new Error(`cannot fetch ${url}: only absolute http(s) URLs`));
    try {
      const [status, body] = g.__dedavHttpGet(url);
      return Promise.resolve(response(url, status, body));
    } catch (e) {
      return Promise.reject(e);
    }
  };
})(globalThis);
