package viz.doNothing

import viz.LowPriorityPlotTarget

// Want a way to instantiate this as a default, but out the way of the user
given doNothing: LowPriorityPlotTarget = new LowPriorityPlotTarget {  
}
  