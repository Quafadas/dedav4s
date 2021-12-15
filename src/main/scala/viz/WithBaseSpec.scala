package viz

import ujson.Value

trait WithBaseSpec extends Spec {
  
    lazy val baseSpec : ujson.Value = ???
    
    val modifiers : Seq[ujson.Value => Unit] = List()    

    /*
    The idea - start from a base spec, "deep copy" it to prevent mutating "state" of any subclass. 
    Modify the copy with the list of "modifiers"
    
    Ideally : validate the outcome against a Schema...
    */
    override def spec : String = {
        val temp = ujson.read(baseSpec.toString)
        for (m <- modifiers) {
            m(temp)
        }
        ujson.write(temp, 2)
    }
}
