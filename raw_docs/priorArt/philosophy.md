# Prior Art
If you are interested in plotting things in scala, you may find any or all of the below libraries of interest. I obviously felt strongly enough to attempt to create my own version... but YMMV and here are other attempts at the paradigm. In each case I either found they were unmaintained or didn't suit my way of working. Plotly scala in particular is probably the "standout candidate" from the below list.

All are valuable resources - this library takes bits from all of them...

- https://github.com/alexarchambault/plotly-scala
- https://github.com/vegas-viz/Vegas
- https://index.scala-lang.org/coxautomotivedatasolutions/vegalite4s/vegalite4s/0.4?target=_2.12
- https://index.scala-lang.org/quantifind/wisp/wisp/0.0.4?target=_2.10
- https://cibotech.github.io/evilplot/
- https://github.com/scalanlp/breeze-viz

A lot more developed... but not scala!
- https://github.com/metasoarous/oz

Or use scalably typed and generate a typesafe facade for your scala JS library of choice.

# Why this library?
I like scala. I've found it to be a wonderful experience, with, to my mind a weakness - anything which needs to be visually correct. I certainly feel I put in a solid "best effort" to find an existing library, and struggled with them. Also... this was fun.

## From first principles
Good data visulaisation is hard. In terms of library authorship, there are two formidable problems one must overcome in order for such a library to be viable.

1. Drawing on screen
2. Sharing

It turns out, there are programs which are already amazing at doing this; browsers. When thinking about this problem, I concluded pretty quickly that my first class citizen had to be the browser. The engineering effort to recreate or even dent the two problems above is truly formiddable otherwise.

## Good news
There are already some very good javascript plotting libraries. Given the prevalence of them in the list of Prior Art above - much of the community already came to the same set of conclusions. Let's provide a thin shim for one!

Vega/lite let's us square the scala / browser circle. We need only a tiny amount of javascript to shim vega embed and host the libraries. The actual visualisation definition is a JSON object. Scala already has good tools for manipulating JSON objects, if we can create the right object, we just need to pipe it into a page template as a string.

Scala's NamedTuple feature seems to be a great fit for this domain.

## What of typesafety?
Many of the libraries above set out to define a typesafe DSL. Do we need one?

I gave up on a general solution to that. Typesafety is attractive because of the runtime guarantees it provides. It also comes with cost.

In data visualisation, I suspect a genuinely typesafe DSL to be a very hard problem. An axis doesn't have meaning without a scale - that's easy I hear you cry! Okay, but which part of the scale are we interested in? Is that sensible / interesting? I'm not even sure you could say *before having the data available*. I think a truly typesafe DSL is beyond scala's typesystem.