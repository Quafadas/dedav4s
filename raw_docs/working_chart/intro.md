# Styles

There are essentially three strategies for working with a chart

1. "[Mutable](mutable.md)"
2. [The DSL](typesafe.md)
3. a [mix](mixed.md) of the two... 

The typesafe DSL is generated out of the vega [JSON schema](https://github.com/vega/schema). 

I do not believe it to be 100% correct. About 95% correct. 

For this reason, the "mutable" option is the ultimate fallback. Typesafe is pretty nice to have, though.

## Design Note

All charts are, in the end ... just a case class. For a given case class, the plot will _always_ be the same. Onen could argue that it is "functional" at that boundary. 

Finally, because we're just producing a chart, there's nothing that says 

1. Your plot can't be produced via a method defined on some data class itself.
1. You have to own the data structure - have a look at the extension methods. The homepage works through an extension method defined on ```Numeric[Iterable]```




