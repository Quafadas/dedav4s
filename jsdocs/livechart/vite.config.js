import { defineConfig } from "vite";
import scalaJSPlugin from "@scala-js/vite-plugin-scalajs";

export default defineConfig({
  plugins: [scalaJSPlugin({
      // path to the directory containing the sbt build
      // default: '.'
      cwd: '../..',
      projectID: 'jsdocs',
  })],
});