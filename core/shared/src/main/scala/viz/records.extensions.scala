package viz

import com.github.tarao.record4s.%
import com.github.tarao.record4s.Tag
import ujson.Value

import upickle.default.writeJs
import upickle.default.ReadWriter
import com.github.tarao.record4s.*
// import com.github.tarao.record4s.upickle.Record.readWriter

trait VegaPlot

object RecordsExtensions:
  extension [R <: %](e: R & Tag[VegaPlot])(using rw: ReadWriter[R], lpt: LowPriorityPlotTarget)
    def plot: WithBaseSpec =
      val u = writeJs(e)(rw)
      new WithBaseSpec:
        override lazy val baseSpec: Value = u




end RecordsExtensions
