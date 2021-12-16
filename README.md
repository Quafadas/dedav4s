# dedav4s
Declarative Data Viz 4 Scala

# In an ammonite terminal 
    import $ivy.`io.github.quafadas::dedav4s:0.0.5-SNAPSHOT`
    import viz.PlotTargets.desktopBrowser
    import viz.vega.extensions.*

# To Do
1. For URL specs, have a lazy loading object which retrieves the spec once... instead of hitting the internet every time
2. websocket update a server in order to avoid multiple tabs
3. A ID for each dataviz? 
4. 