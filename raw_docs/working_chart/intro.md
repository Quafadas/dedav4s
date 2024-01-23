# Styles

The library has evolved, currently, I believe the most productive engagement to be using the excellent [records4s](https://tarao.orezdnu.org/record4s/) as easy way to express sub-parts of vega specs.

## Design Note

All charts are, in the end ... just a case class. For a given case class, the plot will _always_ be the same. Onen could argue that it is "functional" at that boundary. This therefore opens the potential for abstraction over charts, and in particular for unit testing strategies which have a high degree of confidence.

## Visualisation as JSON

We can easily manipulate JSON objects using [ujson](https://www.lihaoyi.com/post/uJsonfastflexibleandintuitiveJSONforScala.html), and that gives us a lot of flexibility.