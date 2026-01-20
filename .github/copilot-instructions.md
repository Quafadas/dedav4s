# dedav4s - scala 3 plotting

ALWAYS reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

Dedav4s is a Scala 3 project using the mill (version 1.1.0+) build tool. It seeks to wrap vega, vega-lite, echarts and ELK plotting libraries, primarily for use in the termainal or scripting environments.

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

## After Making Changes

**CRITICAL**: After EVERY code change, you MUST run the following commands in order and ensure each succeeds before proceeding:

1. **Format code** - `./mill mill.scalalib.scalafmt.ScalafmtModule/reformatAll __.sources`
   - MUST run this after every code change
   - Ensures consistent code style

2. **Compile** - `./mill __.compile`
   - MUST verify compilation succeeds after formatting
   - If compilation fails, fix the issues before proceeding

3. **Test** - `./mill __.test`
   - MUST verify all tests pass
   - If tests fail, fix the failing tests before proceeding

4. **Generate docs** (optional for code changes) - `./mill site.siteGen`
   - Required for documentation changes

**Do NOT commit changes until formatting, compilation, and tests all succeed.**

