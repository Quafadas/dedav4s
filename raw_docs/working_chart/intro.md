# Styles

There are essentially three strategies for working with a chart

1. [Mutable](mutable.md)
2. [The DSL](typesafe.md)
3. a [mix](mixed.md) of the two...

The typesafe DSL is generated out of the vega [JSON schema](https://github.com/vega/schema).

I do not believe it to be 100% correct. About 95% correct.

For this reason, the "mutable" option is the ultimate fallback. Typesafe is pretty nice to have, though.

## Design Note

All charts are, in the end ... just a case class. For a given case class, the plot will _always_ be the same. Onen could argue that it is "functional" at that boundary.

Finally, because we're just producing a chart;

1. Your plot can be produced via a method defined directly on some data class itself
1. You don't have to own the data structure - have a look at the extension methods. The homepage works through an extension method defined on ```Numeric[Iterable]```

## Visualisation as JSON

We can easily manipulate JSON objects using [ujson](https://www.lihaoyi.com/post/uJsonfastflexibleandintuitiveJSONforScala.html), and that gives us a lot of flexibility.