/*
 * Copyright 2025 quafadas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viz

type VizReturn = Unit | os.Path

trait PlatformShow(chartLibrary: ChartLibrary)(using plotTarget: LowPriorityPlotTarget) extends Spec:

  // def show(using plotTarget: PlotTarget): Unit | os.Path = plotTarget.show(spec)

  // lazy val tempPath : Option[os.Path] = plotTarget match
  //   case t : TempFileTarget[A] => Some(t.tempPath(spec))
  //   case _ => None

  // private lazy val conf = org.ekrich.config.ConfigFactory.load()
  // private lazy val outPath: Option[String] =
  //   val pathIsSet: Boolean = conf.hasPath("dedavOutPath")
  //   if pathIsSet then Some(conf.getString("dedavOutPath"))
  //   else None
  //   end if
  // end outPath

  val tmpPath: Option[os.Path] = plotTarget.show(spec, chartLibrary) match
    case () => None
    case path =>
      Some(path.asInstanceOf[os.Path])

end PlatformShow

// This is the line, which actually triggers plotting the chart
