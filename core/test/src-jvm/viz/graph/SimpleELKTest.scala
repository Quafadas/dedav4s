package viz.graph

import munit.FunSuite

class SimpleELKTest extends FunSuite:

  test("Can load ELK metadata") {
    import org.eclipse.elk.core.data.LayoutMetaDataService
    val service = LayoutMetaDataService.getInstance()
    assert(service != null)
  }

  test("Can create ELK graph") {
    import org.eclipse.elk.graph.util.ElkGraphUtil
    val root = ElkGraphUtil.createGraph()
    assert(root != null)
  }

end SimpleELKTest
