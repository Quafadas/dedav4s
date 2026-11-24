// Entry points called from StaticRenderer. Loaded after shims.js and the bundles.
(function (g) {
  const errorText = (e) => String((e && e.message) || e);

  // Vega reports some failures by logging them and rendering on, e.g. data that didn't load is a warning and the chart
  // renders empty. An image of an empty chart would mislead, so errors, and warnings about data, fail the render.
  const dataWarnings = ['Loading failed', 'Data ingestion failed'];
  const errorLogger = (errors) => {
    let level = vega.Error;
    const record = (args) => errors.push(args.map(errorText).join(' '));
    const logger = {
      level(l) {
        if (l === undefined) return level;
        level = l;
        return logger;
      },
      error(...args) {
        record(args);
        return logger;
      },
      warn(...args) {
        if (dataWarnings.includes(args[0])) record(args);
        return logger;
      },
      info: () => logger,
      debug: () => logger,
    };
    return logger;
  };

  // Promises settle while the JVM call that started them returns, so the result is written into `out`, which the
  // JVM reads afterwards.
  const settle = (promise, errors) => {
    const out = {};
    promise.then(
      (svg) => {
        if (errors.length) out.error = errors.join('; ');
        else out.svg = svg;
      },
      (e) => { out.error = errorText(e); }
    );
    return out;
  };

  g.__dedav = {
    vega(specJson, isLite, width, height) {
      try {
        const spec = JSON.parse(specJson);
        const errors = [];
        const runtime = vega.parse(isLite ? vegaLite.compile(spec).spec : spec);
        const view = new vega.View(runtime, { renderer: 'none', logger: errorLogger(errors) });
        // Specs sized to their container (`containerSize()`, `"width": "container"`) get the static size instead.
        const container = { clientWidth: width, clientHeight: height, style: {} };
        view.container = () => container;
        // A transparent background is unreadable in dark viewers, e.g. GitHub's dark theme.
        if (!view.background()) view.background('white');
        return settle(view.toSVG().finally(() => view.finalize()), errors);
      } catch (e) {
        return { error: errorText(e) };
      }
    },

    echarts(optionJson, width, height) {
      try {
        const chart = echarts.init(null, null, { renderer: 'svg', ssr: true, width, height });
        try {
          chart.setOption({ backgroundColor: '#fff', ...JSON.parse(optionJson), animation: false });
          return { svg: chart.renderToSVGString() };
        } finally {
          chart.dispose();
        }
      } catch (e) {
        return { error: errorText(e) };
      }
    },

    compressForUrl(text) {
      return LZString.compressToEncodedURIComponent(text);
    },
  };
})(globalThis);
