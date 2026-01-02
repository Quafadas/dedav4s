console.log("I ran");
const sse = new EventSource("/refresh/v1/sse");
sse.addEventListener("message", (e) => {
  const msg = JSON.parse(e.data);

  if ("KeepAlive" in msg) console.log("KeepAlive");

  if ("PageRefresh" in msg) {
    console.log("Page is refreshing...");
    location.reload();
  }
});
