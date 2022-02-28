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

# Why this library?
I like scala. I've found it to be a wonderful experience, with, to my mind a weakness - data visualisation. I certainly feel I put in a solid "best effort" to find an existing library. 

I concluded I wanted something slightly different. The below explains the rationale behind that statement. 

## From first principles
Good data visulaisation is hard. In terms of library authorship, there are two formidable problems one must overcome in order for such a library to be viable. 

1. Drawing on screen
2. Sharing

It turns out, there are programs which are already amazing at doing this; browsers. When thinking about this problem, I concluded pretty quickly that my first class citizen had to be the browser. The engineering effort to recreate or even dent the two problems above is truly formiddable otherwise. 

Unfortunately, this leaves us with a problem. Scala doesn't run in the browser... well actually, [it can](https://www.scala-js.org)... and that project is amazing and powers this website. Sadly, my target is a repl / interactive JVM environment, and the language of browsers is javascript. Ouch.

## Good news
There are already some very good javascript plotting libraries. Given the prevalence of them in the list of Prior Art above - much of the community already came to the same set of conclusions. Let's provide a thin shim for one! 

Vega/lite let's us square the scala / browser circle. We need only a tiny amount of javascript to shim vega embed and host the libraries. The actual visualisation definition is a JSON object. Scala already has good tools for manipulating JSON objects, if we can create the right object, we just need to pipe it into a page template as a string!


## What of typesafety? 
Many of the libraries above set out to define a typesafe DSL. Do we need one? 

When thinking about this, one has to ask, why? Typesafety is attractive because of the runtime guarantees it provides. It also comes with cost. I argue for this problem, the benefits outweight the costs.

In data visualisation, I suspect a typesafe DSL to be a very hard problem. An axis doesn't have meaning without a scale - that's easy I hear you cry! Okay, but which part of the scale are we interested in? Is that sensible / interesting? I'm not even sure you could say *before having the data available*.

And if you don't have the runtime guarantees? Insead I propose to simply embrace the tightest feedback loop you can get in exchange. See [vega editor](https://vega.github.io/editor).

The actual visualisation here? It's a ```ujson.Value```. That's the only type information you have - but... if you read the vega docs and use the vega editor... you'll find you can make those objects do amazing things... which you can now port into scala. 

But... they are not typesafe.

## But I realllly want typesafety
The general problem is hard. I see two ways plotting can be made safer in this paradigm.

Mostly, you'll want type safety behind _your data structure_. It is fairly simple, to add a plot method to an existing datastructure. As you then control the spec and have knowledge of the data... as long as you test correcty, it's "semi-typesafe". 

Also, it is possible to write "safe" modifications to the a spec. Trivial examples (see ```viz.Utils```) would be removing axes. Unfortunately, "general" modifications are not simple - a pie chart doesn't really want x/y axes for example. Instead of the general problem, this library makes it _possible_ although not necessarily recommended, to construct a "typesafe" DSL _per example chart_, in the companion objects of the case classes. This is also an infinite amount of work, to which I will not commit... but your contributions (with tests) are welcome if you wish this...

## Conclusion
I think you will not refgret learning a declarative paradigm. The 10 minutes you'll spend learning this library (it's absurdly simple) are sunk cost. I propose that the hours you'll invest learning vega are a valuable addition to your study of software engineering. It's amazing how far you can go, and how much value you can deliver, just by pattern matching the vega examples.