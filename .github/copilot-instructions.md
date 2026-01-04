# dedav4s - scala 3 plotting

ALWAYS reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

Dedav4s is a Scala 3 project using the mill (version 1.1.0+) build tool. It seeks to wrap vega, vega-lite and echarts plotting libraries, primarily for use in the termainal or scripting environments.

- **BUILDS**: Mill cold compilation can be slow a minute or so, stay patient, incremental compilation should be fast
- Compile all modules:
  - `./mill __.compile` -- Compiles all modules (JVM, JS, tests). Takes 3-5 minutes cold, fast cached.
- Generate documentation:
  - `./mill site.siteGen` -- Generate documentation site (takes 1-2 minutes cold, fast cached)
- For fast feedback, it shoudl be possilbe to use

- **TEST**
- Run `./mill __.test`.

** BUILD **
- Use `Seq` not `Agg`. Agg doesn't exist in mill 1+

After making changes to the codebase;
- Format code - `./mill mill.scalalib.scalafmt.ScalafmtModule/reformatAll __.sources`
- Compile all - `./mill __.compile`
- Test all - `./mill __.test`
- Generate docs - `./mill site.siteGen`

