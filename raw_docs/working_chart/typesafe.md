# The DSL

There was once a DSL is generated via [quicktype](https://quicktype.io), from the Vega Schema.

I have since abandoned this approach, as it is too brittle and imposes a heavy mental burden on the user.

# Discussion
Typesafety is nice to have in the sense that it removes entire categories of "unplottable" states.

However, many charts that typecheck, will not b _visually correct_. Given the flexibility of the vega schema, typesafety has a high mental burden and imposes an terrific maintenance and legibility burden.

My view has evolved to be that plotting is a domain similar to CSS - in which it is the _visual_ outcome which matters.

Typesafety not only doesn't help, it actively impedes the experience by imposing a heavy mental burden surrounding the "types". I do not recommend this approach. It is seperated into a module, which is essentially "deprecated" and unmaintained.